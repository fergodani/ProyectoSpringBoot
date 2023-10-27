package com.capgemini.biblioteca.controllers;

import java.util.Date;
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

import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.PrestamoService;
import com.capgemini.biblioteca.services.UsuarioService;

@Controller
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/prestamos/{lector_id}")
	public String getPrestamoByLectorId(@PathVariable("lector_id") long lector_id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		if (user.getId() != lector_id) {
			return "error";
		}
		List<Prestamo> prestamos = this.prestamoService.findByLectorId(lector_id);
		model.addAttribute("prestamos", prestamos);
		return "lector/listaPrestamos";
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
		System.out.println("hoddsjlfsdka");
		if (p.getInicio().after(p.getFin())) {
			return "error";
		}else {
			prestamoService.saveEntity(p, libro_id);
			return "redirect:/";
		}
		
	}

	@DeleteMapping("/prestamos")
	public String delete(@PathVariable("/prestamos") long id) {
		prestamoService.deleteEntity(id);
		return "index";
	}

}
