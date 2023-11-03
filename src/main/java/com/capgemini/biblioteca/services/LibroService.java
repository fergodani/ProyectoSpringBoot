package com.capgemini.biblioteca.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.biblioteca.model.Libro;

public interface LibroService {

	Libro getEntityById(long id);

	List<Libro> findAll();

	void saveEntity(Libro libro);

	void deleteEntity(long id);
	
	//Paginacion
	Page<Libro> findPaginated(int pageNum, int pageSize,
				String sortField, String sortDirection);
	
	//Paginacion
		Page<Libro> findLibrosPaginated(int pageNum, int pageSize,
					String sortField, String sortDirection);
}
