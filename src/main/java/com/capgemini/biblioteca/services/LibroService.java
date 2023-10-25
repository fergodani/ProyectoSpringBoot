package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Libro;

public interface LibroService {

	Libro getEntityById(long id);

	List<Libro> findAll();

	void saveEntity(Libro libro);

	void deleteEntity(long id);
}
