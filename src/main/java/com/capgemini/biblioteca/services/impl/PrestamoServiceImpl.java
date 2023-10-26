package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.enums.EstadoCopia;
import com.capgemini.biblioteca.repositories.LibroRepository;
import com.capgemini.biblioteca.repositories.PrestamoRepository;
import com.capgemini.biblioteca.services.PrestamoService;

@Service
public class PrestamoServiceImpl implements PrestamoService  {

	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private LibroRepository libroRepository;

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
	public void saveEntity(Prestamo prestamo, long libro_id) {
		Set<Prestamo> prestamos = prestamo.getLector().getPrestamos();
		if (prestamos.size() >= 3) {
			throw new RuntimeException("El lector " + prestamo.getLector().getNombre() + " no puede sacar más préstamos");
		}
		
		Optional<Libro> libro = this.libroRepository.findById(libro_id);
		if (libro.isPresent()) {
			Set<Copia> copias = libro.get().getCopias();
			for (Copia copia : copias) {
				if (copia.getEstadoCopia() == EstadoCopia.BIBLIOTECA) {
					copia.setEstadoCopia(EstadoCopia.PRESTADO);
					prestamo.setCopia(copia);
					this.prestamoRepository.save(prestamo);
					return;
				}
			}
		}
		throw new RuntimeException("No existen copias disponibles");
		
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
