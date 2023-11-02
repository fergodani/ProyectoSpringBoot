package com.capgemini.biblioteca.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.LectorService;
import com.capgemini.biblioteca.services.LibroService;
import com.capgemini.biblioteca.services.PrestamoService;
import com.capgemini.biblioteca.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PrestamoController {

	@Autowired
	private PrestamoService prestamoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private LectorService lectorService;


	@GetMapping("/prestamos/{lector_id}")
	public String getPrestamoByLectorId(@PathVariable("lector_id") long lector_id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		if (user.getId() != lector_id) {
			return "error";
		}
		List<Prestamo> prestamos = this.prestamoService.findByLectorId(lector_id);
		model.addAttribute("prestamos", prestamos);
		model.addAttribute("lector_id", lector_id);
		model.addAttribute("user_id", user.getId());
		return "lector/listaPrestamos";
	}

//	@GetMapping("/prestamos")
//	public String getPrestamos(Model model) {
//		List<Prestamo> prestamos = this.prestamoService.findAll();
//		model.addAttribute("listaPrestamos", prestamos);
//		return "prestamos";
//	}

	@PostMapping("/prestamos/crear")
	public String altaPrestamo(
			@ModelAttribute("prestamo") Prestamo p,
			@ModelAttribute("libro_id") long libro_id,
			Model model) {
		p.setInicio(new Date());
		System.out.println(p.getLector());
		if (p.getInicio().after(p.getFin())) {
			return "error";
		}else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario user = this.usuarioService.getUserByUsername(auth.getName());
			long lector_id = user.getLector().getNSocio();
			try {
				prestamoService.saveEntity(p, libro_id);
			} catch (RuntimeException r){
				Libro libro = this.libroService.getEntityById(libro_id);
				Prestamo prestamo = new Prestamo();
				model.addAttribute("error", r.getMessage());
				model.addAttribute("libro", libro);
				model.addAttribute("prestamo", prestamo);
				model.addAttribute("lector", user.getLector());
				model.addAttribute("user_id", user.getId());
				return "lector/crearPrestamo";
			}
		
			return "redirect:/prestamos/" + lector_id;
		}
			
	}

	@GetMapping("/delete/{id}/{libro_id}")
	public String deletePrestamo(@PathVariable(value = "id") long id,
			@PathVariable(value = "libro_id") long libro_id,
			 HttpServletRequest request)
	{
		this.prestamoService.deleteEntity(id);
	    return "redirect:/libros/" + libro_id;
		
	}
	
	@GetMapping("/prestamos/crear/{libro_id}")
	public String getCreatePrestamoForm(@PathVariable("libro_id") long libro_id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = this.usuarioService.getUserByUsername(auth.getName());
		Libro libro = this.libroService.getEntityById(libro_id);
		Prestamo prestamo = new Prestamo();
		prestamo.setLector(user.getLector());
		model.addAttribute("libro", libro);
		model.addAttribute("prestamo", prestamo);
		model.addAttribute("lector", user.getLector());
		model.addAttribute("user_id", user.getId());
		if (user.getLector().getMulta() != null) {
			LocalDate fechaFinMulta = user.getLector().getMulta().getfFin().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaActual = LocalDate.now();
			long diasDeDiferencia = fechaActual.until(fechaFinMulta, java.time.temporal.ChronoUnit.DAYS);
			model.addAttribute("dias_multa", diasDeDiferencia);
		}
		return "lector/crearPrestamo";
	}
	
	@GetMapping("/prestamos/devolver/{lector_id}/{prestamo_id}")
	public String devolverPrestamo(@PathVariable("lector_id") long lector_id, 
			@PathVariable("prestamo_id") long prestamo_id) {
		Lector lector = this.lectorService.getEntityById(lector_id);
		lector.devolver(prestamo_id);
		this.lectorService.saveEntity(lector);
		return "redirect:/prestamos/" + lector_id;
	}

}
