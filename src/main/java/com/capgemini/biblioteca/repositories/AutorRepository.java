package com.capgemini.biblioteca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.biblioteca.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

	
	@Query("SELECT a FROM Autor a WHERE a.nacionalidad=:nacionalidad")
	public Page<Autor> filtroAutor(Pageable pageable, @Param("nacionalidad") String nacionalidad);
}
