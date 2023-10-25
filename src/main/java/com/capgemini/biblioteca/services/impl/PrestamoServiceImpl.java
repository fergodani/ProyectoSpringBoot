package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.repositories.PrestamoRepository;
import com.capgemini.biblioteca.services.PrestamoService;

@Service
public class PrestamoServiceImpl implements PrestamoService  {

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Override
	public Prestamo getEntityById(long id) {
		Optional<Prestamo> prestamo = this.prestamoRepository.findById(id);
		if(prestamo.isPresent())
			return prestamo.get();
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);

	}

	@Override
	public List<Prestamo> findAll() {
		
		return this.prestamoRepository.findAll();
	}

	@Override
	public void saveEntity(Prestamo prestamo) {
		this.prestamoRepository.save(prestamo);
		
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Prestamo> prestamo = this.prestamoRepository.findById(id);
		if(prestamo.isPresent())
			this.prestamoRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}	
	

}
