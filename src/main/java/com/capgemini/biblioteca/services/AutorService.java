package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Autor;

public interface AutorService {

	Autor getEntityById(long id);

	List<Autor> findAll();

	void saveEntity(Autor autor);

	void deleteEntity(long id);
}
