package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.enums.EstadoCopia;
import com.capgemini.biblioteca.repositories.CopiaRepository;
import com.capgemini.biblioteca.repositories.LibroRepository;
import com.capgemini.biblioteca.services.LibroService;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private CopiaRepository copiaRepository;

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
		Copia copia = new Copia();
		copia.setEstadoCopia(EstadoCopia.BIBLIOTECA);
		copia.setLibro(libro);
		this.copiaRepository.save(copia);
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
