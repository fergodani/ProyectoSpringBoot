package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.repositories.CopiaRepository;
import com.capgemini.biblioteca.services.CopiaService;

@Service
public class CopiaServiceImpl implements CopiaService {

	@Autowired
	private CopiaRepository copiaRepository;


	@Override
	public Copia getEntityById(long id) {
		Optional<Copia> copia = this.copiaRepository.findById(id);
		if(copia.isPresent())
			return copia.get();
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);

	}

	@Override
	public List<Copia> findAll() {
		
		return this.copiaRepository.findAll();
	}

	@Override
	public void saveEntity(Copia copia) {
		this.copiaRepository.save(copia);
		
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Copia> copia = this.copiaRepository.findById(id);
		if(copia.isPresent())
			this.copiaRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}
}
