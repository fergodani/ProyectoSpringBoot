package com.capgemini.biblioteca.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{


	
	@Query("SELECT p FROM Prestamo p WHERE p.copia.id=:copia_id")
	public List<Prestamo> findByCopiaId(@Param("copia_id") long copia_id);

	@Query("SELECT p FROM Prestamo p WHERE p.lector.id = :lector_id")
	Page<Prestamo> findByLectorId(Pageable pageable, @Param("lector_id") long lector_id); 

}
