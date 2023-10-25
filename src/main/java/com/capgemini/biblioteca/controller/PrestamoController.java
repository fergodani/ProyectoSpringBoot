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
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.services.PrestamoService;

@Controller
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	
	@GetMapping("/prestamos/{id}") //http://localhost:8080/users/id/2 se invocara asi
	public String getPrestamoById(@PathVariable("id") long id) {
		Prestamo prestamo=  prestamoService.getEntityById(id);
		return "detalles_prestamo";
	}
		
	@GetMapping("/prestamos")
	public String getPrestamos(Model model) {
		List<Prestamo> prestamos = this.prestamoService.findAll();
		model.addAttribute("listaPrestamos", prestamos);
		return "prestamos";
	}
	
	@PostMapping("/prestamos")
	public String altaPrestamo(@RequestBody Prestamo p)
	{
		prestamoService.saveEntity(p);
		return "index";
	}
	
	@DeleteMapping("/prestamos")
	public String delete(@PathVariable("/prestamos") long id) 
	{
			prestamoService.deleteEntity(id);
			return "index";
	}
	
}
