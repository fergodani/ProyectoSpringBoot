package com.capgemini.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.biblioteca.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

}