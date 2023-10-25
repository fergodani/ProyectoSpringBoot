package com.capgemini.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.services.AutorService;


@Controller
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@GetMapping("/autores/{id}") 
	public String getAutorById(@PathVariable("id") long id, Model model) {
		Autor autor =  autorService.getEntityById(id);
		model.addAttribute("autor", autor);
		return "detalles_autor";
	}
		
	@GetMapping("/autores")
	public String getAutores(Model model) {
		List<Autor> autores = this.autorService.findAll();
		model.addAttribute("listaAutores", autores);
		return "autores";
	}
	
	@PostMapping("/autores")
	public String altaAutor(@RequestBody Autor a)
	{
		autorService.saveEntity(a);
		return "index";
	}
	
	@DeleteMapping("/autores")
	public String delete(@PathVariable("/autores") long id) 
	{
		autorService.deleteEntity(id);
		return "index";
	}
	
}
