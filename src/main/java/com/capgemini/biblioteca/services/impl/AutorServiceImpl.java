package com.capgemini.biblioteca.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.repositories.AutorRepository;

@Service
public class AutorServiceImpl extends CRUDServiceImpl<Libro, Long> {

	@Autowired
	private AutorRepository autorRepository;

}
