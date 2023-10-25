package com.capgemini.biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.biblioteca.model.Copia;

public interface CopiaRepository extends JpaRepository<Copia, Long>{

	@Query("SELECT c FROM Copia c WHERE c.libro.id = :libro_id")
	List<Copia> findByLibroId(@Param("libro_id") long libro_id); 
}
