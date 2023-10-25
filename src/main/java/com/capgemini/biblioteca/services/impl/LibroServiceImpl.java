package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.repositories.LibroRepository;
import com.capgemini.biblioteca.services.LibroService;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private LibroRepository libroRepository;

	@Override
	public Libro getEntityById(long id) {
		Optional<Libro> libro = this.libroRepository.findById(id);
		if(libro.isPresent())
			return libro.get();
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);

	}

	@Override
	public List<Libro> findAll() {
		
		return this.libroRepository.findAll();
	}

	@Override
	public void saveEntity(Libro libro) {
		this.libroRepository.save(libro);
		
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Libro> libro = this.libroRepository.findById(id);
		if(libro.isPresent())
			this.libroRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}
}
