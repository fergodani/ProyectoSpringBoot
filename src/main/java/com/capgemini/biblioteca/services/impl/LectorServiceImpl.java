package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.repositories.LectorRepository;
import com.capgemini.biblioteca.services.LectorService;

@Service
public class LectorServiceImpl implements LectorService {

	@Autowired
	private LectorRepository lectorRepository;



	@Override
	public Lector getEntityById(long id) {
		Optional<Lector> lector = this.lectorRepository.findById(id);
		if(lector.isPresent())
			return lector.get();
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);

	}

	@Override
	public List<Lector> findAll() {
		
		return this.lectorRepository.findAll();
	}

	@Override
	public void saveEntity(Lector lector) {
		this.lectorRepository.save(lector);
		
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Lector> lector = this.lectorRepository.findById(id);
		if(lector.isPresent())
			this.lectorRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}
}
