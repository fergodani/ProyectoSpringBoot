package com.capgemini.biblioteca.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.biblioteca.model.Lector;

public interface LectorService {

	Lector getEntityById(long id);

	List<Lector> findAll();

	void saveEntity(Lector lector);

	void deleteEntity(long id);
	
	//Paginacion
	Page<Lector> findPaginated(int pageNum, int pageSize,
			String sortField, String sortDirection);
	//Metodo que busca una pagina por su numero de pagina, tamaño de pagina, campo por el q esta ordenado y direccion
}
