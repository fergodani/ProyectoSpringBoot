package com.capgemini.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.AutorService;
import com.capgemini.biblioteca.services.CopiaService;
import com.capgemini.biblioteca.services.LectorService;
import com.capgemini.biblioteca.services.LibroService;
import com.capgemini.biblioteca.services.PrestamoService;
import com.capgemini.biblioteca.services.UsuarioService;

@Controller
public class LibroController {

	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private AutorService autorService;
	
	@Autowired
	private CopiaService copiaService;
	
	@Autowired
	private LectorService lectorService;
	
	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private UsuarioService usuarioService;

	
		
	@GetMapping("/")
	public String getIndex() {
		return "redirect:/libros";
	}
	
	
	@GetMapping("/libros/{id}") 
	public String getLibroById(@PathVariable("id") long id, Model model) {
		Libro libro=  libroService.getEntityById(id);
		Prestamo prestamo = new Prestamo();
		List<Copia> copias = this.copiaService.findCopiasByLibroId(id);
		List<Lector> lectores = this.lectorService.findAll();
		List<Prestamo> prestamos = new ArrayList<Prestamo>();
		for (Copia copia : copias) {
			for (Prestamo p : prestamoService.findByCopiaId(copia.getId())) {
				prestamos.add(p);
			}			
		}
		model.addAttribute("listaPrestamos", prestamos);
		model.addAttribute("libro", libro);
		model.addAttribute("numCopias", copias.size());
		model.addAttribute("lectores", lectores);
		model.addAttribute("prestamo", prestamo);
		return "admin/detallesLibro";
	}
	
	@GetMapping("/libros/create")
	public String mostrarFormularioNuevoLibro(Model model) {
		Libro libro = new Libro();
		List<Autor> autores = this.autorService.findAll();
		model.addAttribute("libro", libro);
		model.addAttribute("autores", autores);
		return "admin/crearLibro";
	}
	
	@GetMapping("/libros")
	public String getLibros(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
//		List<Libro> libros = this.libroService.findAll();
//		model.addAttribute("listaLibros", libros);
//		model.addAttribute("name", user.getName());
//		model.addAttribute("user_id", user.getId());
		return findLibrosPaginated(1, "titulo", "asc", model);
	}
	
	@GetMapping("/libros/editar")
	public String getEditarLibros(Model model) {
//		List<Libro> libros = this.libroService.findAll();
//		model.addAttribute("listaLibros", libros);
		return findPaginated(1, "titulo", "asc", model);
	}
	
	
	@GetMapping("/libros/update/{id}")
	public String getEditorLibroForm(@PathVariable("id") long id, Model model) {
		Libro libro = this.libroService.getEntityById(id);
		model.addAttribute("libro", libro);
		return "admin/editLibros";
	}
	
	@PostMapping("/libros")
	public String altaLibro(@ModelAttribute("libro") Libro l)
	{
		libroService.saveEntity(l);
		return "redirect:/";
	}
	
	@DeleteMapping("/libros")
	public String delete(@PathVariable("/libros") long id) 
	{
		libroService.deleteEntity(id);
		return "index";
	}
	
	@GetMapping("/error")
	public String getError() {
		return "error";
	}
	
	//Método para paginacion
	@GetMapping("/libros/editar/page/{pageNo}")//Asociacion de este nº de page con la que usa el metodo
	public String findPaginated(@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField, //campo de ordenamiento que me pasa el html
			@RequestParam("sortDir") String sortDir, //campo de direccion de ordenamiento que me pasa el html
			Model model 
			)
	{
		int pageSize = 4;
		Page<Libro> page=libroService.findPaginated(pageNo, pageSize, sortField, sortDir);
		//Luego creamos coleccion de cursos
		List<Libro> listLibros = page.getContent();	
		
		//Agregamos al modelo atributos que leemos de html
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages() );//Total de páginas
		model.addAttribute("totalItems",page.getTotalElements());//Total de cursos en cada colección
		//Le paso lo que necesita el libro
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		model.addAttribute("name", user.getName());
		model.addAttribute("user_id", user.getId());
		model.addAttribute("listaLibrosPag", listLibros);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		//Este ultimo, si es ascendente que lo ponga al reves y si no lo deje como estaba
		
		//Todos estos valores estaran en el index
		return "admin/libros";
	}
	
	
	//Método para paginacion
	@GetMapping("/libros/page/{pageNo}")//Asociacion de este nº de page con la que usa el metodo
	public String findLibrosPaginated(@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField, //campo de ordenamiento que me pasa el html
			@RequestParam("sortDir") String sortDir, //campo de direccion de ordenamiento que me pasa el html
			Model model 
			)
	{
		int pageSize = 12;
		Page<Libro> page=libroService.findPaginated(pageNo, pageSize, sortField, sortDir);
		//Luego creamos coleccion de cursos
		List<Libro> listLibros = page.getContent();	
		
		//Agregamos al modelo atributos que leemos de html
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages() );//Total de páginas
		model.addAttribute("totalItems",page.getTotalElements());//Total de cursos en cada colección
		//Le paso lo que necesita el libro
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		model.addAttribute("name", user.getName());
		model.addAttribute("user_id", user.getId());
		model.addAttribute("listaLibrosPag", listLibros);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		//Este ultimo, si es ascendente que lo ponga al reves y si no lo deje como estaba
		
		//Todos estos valores estaran en el index
		return "index";
	}
	
	
}
