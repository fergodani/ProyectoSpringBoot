package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.repositories.LectorRepository;

@Service
public class LectorServiceImpl extends CRUDServiceImpl<Lector, Long> {

	@Autowired
	private LectorRepository lectorRepository;

}
