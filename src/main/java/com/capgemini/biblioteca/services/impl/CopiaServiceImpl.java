package com.capgemini.biblioteca.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.repositories.CopiaRepository;

@Service
public class CopiaServiceImpl extends CRUDServiceImpl<Copia, Long> {

	@Autowired
	private CopiaRepository copiaRepository;

}
