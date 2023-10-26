package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Prestamo;

public interface PrestamoService {

	Prestamo getEntityById(long id);

	List<Prestamo> findAll();

	void saveEntity(Prestamo prestamo, long id);

	void deleteEntity(long id);
}
