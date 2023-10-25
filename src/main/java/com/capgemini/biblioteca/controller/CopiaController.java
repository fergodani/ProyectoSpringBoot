package com.capgemini.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.services.CopiaService;

@Controller
public class CopiaController {

	@Autowired
	private CopiaService copiaService;
	
	@GetMapping("/copias/{id}") //http://localhost:8080/users/id/2 se invocara asi
	public String getCopiaById(@PathVariable("id") long id) {
		Copia copia=  copiaService.getEntityById(id);
		return "detalles_copia";
	}
		
	@GetMapping("/copias")
	public String getCopias(Model model) {
		List<Copia> copias = this.copiaService.findAll();
		model.addAttribute("listaLibros", copias);
		return "copias";
	}
	
	@PostMapping("/copias")
	public String altaCopia(@RequestBody Copia c)
	{
		copiaService.saveEntity(c);
		return "index";
	}
	
	@DeleteMapping("/copias")
	public String delete(@PathVariable("/copias") long id) 
	{
		copiaService.deleteEntity(id);
		return "index";
	}
}
