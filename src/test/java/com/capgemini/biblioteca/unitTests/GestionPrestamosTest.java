package com.capgemini.biblioteca.unitTests;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.services.PrestamoService;

class GestionPrestamosTest {
	
	private Lector lector;
	private List<Libro> libros;
	
	@Autowired
	private PrestamoService prestamoService;
	
	@BeforeAll
	void setUp() {
		this.lector = new Lector();
		this.libros = new ArrayList<Libro>();
		Libro libro1 = new Libro("Libro 1");
		Set<Copia> copias = new HashSet<Copia>();
		copias.add(new Copia(libro1));
		copias.add(new Copia(libro1));
		libro1.setCopias(copias);
		
		Libro libro2 = new Libro("Libro 2");
		Set<Copia> copias2 = new HashSet<Copia>();
		copias2.add(new Copia(libro2));
		copias2.add(new Copia(libro2));
		libro1.setCopias(copias);
		this.libros.add(libro1);
		this.libros.add(libro2);
	}

	@Test
	void pedirPrimerPrestamo() {
		Prestamo prestamo = new Prestamo();
		prestamo.setCopia((Copia) this.libros.get(0).getCopias().toArray()[0]);
		this.prestamoService.saveEntity(prestamo, this.libros.get(0).getId());
	}
	
	@Test
	void pedirCuartoPrestamo() {
		fail("Not yet implemented");
	}
	
	@Test
	void pedirPrestamoSinCopiasDisponibles() {
		fail("Not yet implemented");
	}
	
	@Test
	void pedirPrestamoConMulta() {
		fail("Not yet implemented");
	}

}
