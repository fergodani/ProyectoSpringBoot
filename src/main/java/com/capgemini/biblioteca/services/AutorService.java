package com.capgemini.biblioteca.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.biblioteca.model.Autor;

public interface AutorService {

	Autor getEntityById(long id);

	List<Autor> findAll();

	void saveEntity(Autor autor);

	void deleteEntity(long id);
	
	//Paginacion
	Page<Autor> findPaginated(int pageNum, int pageSize,
			String sortField, String sortDirection);
	//Metodo que busca una pagina por su numero de pagina, tamaño de pagina, campo por el q esta ordenado y direccion
	Page<Autor> filtroAutor(int pageNum, int pageSize, String sortField, String sortDirection, String nacionalidad); 
}
