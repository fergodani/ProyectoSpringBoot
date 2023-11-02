package com.capgemini.biblioteca.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;

public interface PrestamoService {

	Prestamo getEntityById(long id);

	List<Prestamo> findAll();
	
	Page<Prestamo> findByLectorId(long lector_id, int pageNum, int pageSize,
			String sortField, String sortDirection);

	void saveEntity(Prestamo prestamo, long id);

	void deleteEntity(long id);
	
	List<Prestamo> findByCopiaId(long id);
}
