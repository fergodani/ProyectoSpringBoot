package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Lector;

public interface LectorService {

	Lector getEntityById(long id);

	List<Lector> findAll();

	void saveEntity(Lector lector);

	void deleteEntity(long id);
}
