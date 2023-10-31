package com.capgemini.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.AutorService;
import com.capgemini.biblioteca.services.LectorService;
import com.capgemini.biblioteca.services.LibroService;
import com.capgemini.biblioteca.services.PrestamoService;
import com.capgemini.biblioteca.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class EntityRestController {

	@Autowired
	private UsuarioService userService;

	@Autowired
	private LectorService lectorService;

	@Autowired
	private AutorService autorService;

	@Autowired
	private LibroService libroService;

	@Autowired
	private PrestamoService prestamoService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> getAllUsers() {
		List<Usuario> users = this.userService.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	

	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id) {
		Usuario usuario = this.userService.getEntityById(id);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@GetMapping("/lectores")
	public ResponseEntity<List<Lector>> getAllLectores() {
		List<Lector> lectores = lectorService.findAll();
		return new ResponseEntity<>(lectores, HttpStatus.OK);
	}

	@GetMapping("/lectores/{id}")
	public ResponseEntity<Lector> getLectorById(@PathVariable("id") long id) {
		Lector lector = this.lectorService.getEntityById(id);
		return new ResponseEntity<>(lector, HttpStatus.OK);
	}

	@GetMapping("/autores")
	public ResponseEntity<List<Autor>> getAllAutores() {
		List<Autor> autores = autorService.findAll();
		return new ResponseEntity<>(autores, HttpStatus.OK);
	}

	@GetMapping("/autores/{id}")
	public ResponseEntity<Autor> getAutorById(@PathVariable("id") long id) {
		Autor autor = this.autorService.getEntityById(id);
		return new ResponseEntity<>(autor, HttpStatus.OK);
	}

	@GetMapping("/libros")
	public ResponseEntity<List<Libro>> getAllLibros() {
		List<Libro> libros = libroService.findAll();
		return new ResponseEntity<>(libros, HttpStatus.OK);
	}

	@GetMapping("/libros/{id}")
	public ResponseEntity<Libro> getLibroById(@PathVariable("id") long id) {
		Libro libro = this.libroService.getEntityById(id);
		return new ResponseEntity<>(libro, HttpStatus.OK);
	}

	@GetMapping("/prestamos")
	public ResponseEntity<List<Prestamo>> getAllPrestamos() {
		List<Prestamo> prestamos = prestamoService.findAll();
		return new ResponseEntity<>(prestamos, HttpStatus.OK);
	}

	@GetMapping("/prestamos/{id}")
	public ResponseEntity<Prestamo> getPrestamoById(@PathVariable("id") long id) {
		Prestamo prestamo = this.prestamoService.getEntityById(id);
		return new ResponseEntity<>(prestamo, HttpStatus.OK);
	}
}
