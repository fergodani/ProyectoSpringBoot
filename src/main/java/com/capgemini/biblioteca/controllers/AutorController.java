package com.capgemini.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.services.AutorService;


@Controller
public class AutorController {

	@Autowired
	private AutorService autorService;
	
//	@GetMapping("/autores/{id}") 
//	public String getAutorById(@PathVariable("id") long id, Model model) {
//		Autor autor =  autorService.getEntityById(id);
//		model.addAttribute("autor", autor);
//		return "detalles_autor";
//	}
		
	@GetMapping("/autores")
	public String getAutores(Model model) {
		List<Autor> autores = this.autorService.findAll();
		model.addAttribute("autores", autores);
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
		return "redirect:/autores";
	}
	
	@GetMapping("/autores/delete/{id}")
	public String delete(@PathVariable("id") long id) 
	{
		autorService.deleteEntity(id);
		return "redirect:/autores";
	}
	
}
