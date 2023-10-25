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

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.services.LibroService;
@Controller
public class LibroController {

	
	@Autowired
	private LibroService libroService;
	
	
	@GetMapping("/lectores/{id}") //http://localhost:8080/users/id/2 se invocara asi
	public String getLibroById(@PathVariable("id") long id) {
		Libro libro=  libroService.getEntityById(id);
		return "detalles_libro";
	}
	
	@GetMapping("/libros")
	public String getLibros(Model model) {
		List<Libro> libros = this.libroService.findAll();
		model.addAttribute("listaLibros", libros);
		return "libros";
	}
	
	@PostMapping("/libros")
	public String altaLibro(@RequestBody Libro l)
	{
		libroService.saveEntity(l);
		return "index";
	}
	
	@DeleteMapping("/libros")
	public String delete(@PathVariable("/libros") long id) 
	{
		libroService.deleteEntity(id);
		return "index";
	}
}
