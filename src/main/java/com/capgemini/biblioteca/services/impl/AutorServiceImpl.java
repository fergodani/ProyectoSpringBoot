package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.repositories.AutorRepository;
import com.capgemini.biblioteca.services.AutorService;

@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public Autor getEntityById(long id) {
		Optional<Autor> autor = this.autorRepository.findById(id);
		if(autor.isPresent())
			return autor.get();
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);

	}

	@Override
	public List<Autor> findAll() {
		
		return this.autorRepository.findAll();
	}

	@Override
	public void saveEntity(Autor autor) {
		this.autorRepository.save(autor);
		
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Autor> autor = this.autorRepository.findById(id);
		if(autor.isPresent())
			this.autorRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}

}
