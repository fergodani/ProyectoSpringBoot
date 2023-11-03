package com.capgemini.biblioteca.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.biblioteca.model.Lector;

public interface LectorRepository extends JpaRepository<Lector, Long>{
	
	@Query("SELECT l FROM Lector l, Usuario u WHERE u.lector.id= l.nSocio AND u.role <> 'ROLE_ADMIN'")
	public Page<Lector> findAll(Pageable pageable);

}
