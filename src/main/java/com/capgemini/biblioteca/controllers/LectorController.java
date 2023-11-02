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

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.services.LectorService;

@Controller
public class LectorController {

	
	@Autowired
	private LectorService lectorService;
	
//	@GetMapping("/lectores/{id}")
//	public String getLectorById(@PathVariable("id") long id) {
//		Lector lector=  lectorService.getEntityById(id);
//		return "detalles_lector";
//	}
	
	
	@GetMapping("/lectores")
	public String getLectores(Model model) {
//		List<Lector> lectores = this.lectorService.findAll();
//		model.addAttribute("listaLectores", lectores);
		return findPaginated(1, "nombre", "asc", model);
	}
	
	@GetMapping("/lectores/update/{id}")
	public String getEditorLectorForm(@PathVariable("id") long id, Model model) {
		Lector lector = this.lectorService.getEntityById(id);
		model.addAttribute("lector", lector);
		return "admin/editLectores";
	}
	
	
	@PostMapping("/lectores")
	public String altaLector(@ModelAttribute("lector") Lector l)
	{
		lectorService.saveEntity(l);
		return "index";
	}
	
	@GetMapping("/lectores/delete/{id}")
	public String delete(@PathVariable("id") long id) 
	{
		lectorService.deleteEntity(id);
		return "redirect:/lectores";
	}
	
	//Método para paginacion
	@GetMapping("/lectores/page/{pageNo}")//Asociacion de este nº de page con la que usa el metodo
	public String findPaginated(@PathVariable(value="pageNo") int pageNo,
			@RequestParam("sortField") String sortField, //campo de ordenamiento que me pasa el html
			@RequestParam("sortDir") String sortDir, //campo de direccion de ordenamiento que me pasa el html
			Model model 
			)
	{
		int pageSize = 4;
		Page<Lector> page=lectorService.findPaginated(pageNo, pageSize, sortField, sortDir);
		//Luego creamos coleccion de cursos
		List<Lector> lectores = page.getContent();
		
		//Agregamos al modelo atributos que leemos de html
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages() );//Total de páginas
		model.addAttribute("totalItems",page.getTotalElements());//Total de cursos en cada colección
		model.addAttribute("listaLectores", lectores);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		//Este ultimo, si es ascendente que lo ponga al reves y si no lo deje como estaba
		
		//Todos estos valores estaran en el 
		return "admin/lectores";
	}
	
}
