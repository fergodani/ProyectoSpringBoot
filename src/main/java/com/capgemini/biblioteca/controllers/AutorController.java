package com.capgemini.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.services.AutorService;


@Controller
public class AutorController {

	private static final int PAGE_SIZE = 4;
	@Autowired
	private AutorService autorService;
	
//	@GetMapping("/autores/{id}") 
//	public String getAutorById(@PathVariable("id") long id, Model model) {
//		Autor autor =  autorService.getEntityById(id);
//		model.addAttribute("autor", autor);
//		return "detalles_autor";
//	}
		

	@GetMapping("/autores/filtros/page/{pageNo}")
	public String getAutoresFiltro(@RequestParam(value = "nacionalidad", required = false) String nacionalidad,
			@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField, //campo de ordenamiento que me pasa el html
			@RequestParam("sortDir") String sortDir, //campo de direccion de ordenamiento que me pasa el html 
			Model model) {
		int pageSize = PAGE_SIZE;
		//Luego creamos lista de autores
		Page<Autor> page = this.autorService.filtroAutor(pageNo,pageSize, sortField, sortDir, nacionalidad); // Obtén tus datos de alguna fuente
		
		List<Autor> listaAutores = page.getContent();
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());//Total de páginas
		model.addAttribute("totalItems",page.getTotalElements());//Total de cursos en cada colección
		model.addAttribute("listaAutores", listaAutores);	
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    model.addAttribute("listaAutores", listaAutores);
		return "admin/autores";
	}
	
	@GetMapping("/autores/crear")
	public String getCrearAutorForm(Model model) {
		Autor autor = new Autor();
		model.addAttribute("autor", autor);
		return "admin/crearAutor";
	}
	
	@GetMapping("/autores/update/{id}")
	public String getEditAutorForm(@PathVariable("id") long id, Model model) {
		Autor autor = this.autorService.getEntityById(id);
		model.addAttribute("autor", autor);
		return "admin/editAutores";
	}
	
	@PostMapping("/autores")
	public String altaAutor(@ModelAttribute("autor") Autor autor)
	{
		autorService.saveEntity(autor);
		return "redirect:/autores?page=1&sortField=nombre&sortDir=asc";
	}
	
	@GetMapping("/autores/delete/{id}")
	public String delete(@PathVariable("id") long id) 
	{
		autorService.deleteEntity(id);
		return "redirect:/autores?page=1&sortField=nombre&sortDir=asc";
	}
	
	//Método para paginacion
		@GetMapping("/autores")//Asociacion de este nº de page con la que usa el metodo
		public String findPaginated(
				@RequestParam(value="page") int pageNo,
				@RequestParam("sortField") String sortField, //campo de ordenamiento que me pasa el html
				@RequestParam("sortDir") String sortDir, //campo de direccion de ordenamiento que me pasa el html
				Model model 
				)
		{
		
			int pageSize = PAGE_SIZE;
			Page<Autor> page=autorService.findPaginated(pageNo, pageSize, sortField, sortDir);
			//Luego creamos lista de autores
			List<Autor> listaAutores = page.getContent();
			
			//Agregamos al modelo atributos que leemos de html
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("sortField", sortField);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());//Total de páginas
			model.addAttribute("totalItems",page.getTotalElements());//Total de cursos en cada colección
			model.addAttribute("listaAutores", listaAutores);	
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			//Este ultimo, si es ascendente que lo ponga al reves y si no lo deje como estaba
			
			//Todos estos valores estaran en el html
			return "admin/autores";
		}
	
}
