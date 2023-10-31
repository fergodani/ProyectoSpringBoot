package com.capgemini.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		List<Libro> libros = this.libroService.findAll();
		model.addAttribute("listaLibros", libros);
		model.addAttribute("name", user.getName());
		model.addAttribute("user_id", user.getId());
		return "index";
	}
	
	@GetMapping("/libros/editar")
	public String getEditarLibros(Model model) {
		List<Libro> libros = this.libroService.findAll();
		model.addAttribute("listaLibros2", libros);
		return "admin/libros";
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
}
