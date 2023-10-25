package com.capgemini.biblioteca.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.repositories.PrestamoRepository;

@Service
public class PrestamoServiceImpl extends CRUDServiceImpl<Prestamo, Long>  {

	@Autowired
	private PrestamoRepository prestamoRepository;
	
	

}
