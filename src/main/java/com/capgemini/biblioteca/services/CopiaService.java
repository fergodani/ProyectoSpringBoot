package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Copia;

public interface CopiaService {

	Copia getEntityById(long id);

	List<Copia> findAll();

	void saveEntity(Copia copia);

	void deleteEntity(long id);
}
