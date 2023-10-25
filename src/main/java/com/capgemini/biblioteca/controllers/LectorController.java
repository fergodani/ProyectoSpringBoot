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

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.services.LectorService;

@Controller
public class LectorController {

	
	@Autowired
	private LectorService lectorService;
	
	@GetMapping("/lectores/{id}") //http://localhost:8080/users/id/2 se invocara asi
	public String getLectorById(@PathVariable("id") long id) {
		Lector lector=  lectorService.getEntityById(id);
		return "detalles_lector";
	}
	
	
	
	@GetMapping("/lectores")
	public String getLectores(Model model) {
		List<Lector> lectores = this.lectorService.findAll();
		model.addAttribute("listaLectores", lectores);
		return "lectores";
	}
	
	
	@PostMapping("/lectores")
	public String altaLector(@RequestBody Lector l)
	{
		lectorService.saveEntity(l);
		return "index";
	}
	
	@DeleteMapping("/lectores")
	public String delete(@PathVariable("/lectores") long id) 
	{
		lectorService.deleteEntity(id);
		return "index";
	}
}
