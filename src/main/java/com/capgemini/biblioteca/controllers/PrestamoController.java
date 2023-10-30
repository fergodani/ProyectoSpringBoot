package com.capgemini.biblioteca.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.services.PrestamoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	


	@GetMapping("/prestamos/{id}") // http://localhost:8080/users/id/2 se invocara asi
	public String getPrestamoById(@PathVariable("id") long id) {
		Prestamo prestamo = prestamoService.getEntityById(id);
		return "detalles_prestamo";
	}

	@GetMapping("/prestamos")
	public String getPrestamos(Model model) {
		List<Prestamo> prestamos = this.prestamoService.findAll();
		model.addAttribute("listaPrestamos", prestamos);
		return "prestamos";
	}

	@PostMapping("/prestamos/crear")
	public String altaPrestamo(
			@ModelAttribute("prestamo") Prestamo p,
			@ModelAttribute("libro_id") long libro_id,
			Model model) {
		p.setInicio(new Date());
		
		if (p.getInicio().after(p.getFin())) {
			return "error";
		}else {
			prestamoService.saveEntity(p, libro_id);
		
			return "redirect:/libros/" + libro_id;
		}
			
	}

	@GetMapping("/delete/{id}/{libro_id}")
	public String deleteCurso(@PathVariable(value = "id") long id,
			@PathVariable(value = "libro_id") long libro_id,
			 HttpServletRequest request)
	{
		this.prestamoService.deleteEntity(id);
	    return "redirect:/libros/" + libro_id;
		
	}

}
