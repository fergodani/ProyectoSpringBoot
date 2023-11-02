package com.capgemini.biblioteca.services.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Copia;
import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.enums.EstadoCopia;
import com.capgemini.biblioteca.repositories.LectorRepository;
import com.capgemini.biblioteca.repositories.LibroRepository;
import com.capgemini.biblioteca.repositories.PrestamoRepository;
import com.capgemini.biblioteca.services.PrestamoService;

@Service
public class PrestamoServiceImpl implements PrestamoService  {

	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private LectorRepository lectorRepository;

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
		
		comprobarMulta(prestamo.getLector());
		
		if (prestamo.getLector().getMulta() != null) {
			throw new RuntimeException("El lector tiene multa y no puede retirar el libro");
		}
		
		Set<Prestamo> prestamos = prestamo.getLector().getPrestamos();
		int prestamosNoDevueltos = 0;
		for (Prestamo p : prestamos) {
			if (p.getCopia() != null)
				prestamosNoDevueltos++;
		}
		if (prestamosNoDevueltos >= 3) {
			throw new RuntimeException("El lector " + prestamo.getLector().getNombre() + " no puede sacar más préstamos");
		}
		
		Optional<Libro> libro = this.libroRepository.findById(libro_id);
		if (libro.isPresent()) {
			Set<Copia> copias = libro.get().getCopias();
			for (Copia copia : copias) {
				if (copia.getEstadoCopia() == EstadoCopia.BIBLIOTECA) {
					copia.setEstadoCopia(EstadoCopia.PRESTADO);
					prestamo.setCopia(copia);
					prestamo.setTitulo_libro(libro.get().getTitulo());
					this.prestamoRepository.save(prestamo);
					return;
				}
			}
		}
		throw new RuntimeException("No existen copias disponibles");
		
	}
	
	private void comprobarMulta(Lector lector) {
		// Si la fecha de fin de la multa es anterior a la actual, quitarla
		if (lector.getMulta() != null) {
			if (lector.getMulta().getfFin().before(new Date())) {
				lector.setMulta(null);
				this.lectorRepository.save(lector);
			}else {
				return;
			}
		}
		
		// 1. Filtrar los préstamos que no han sido devueltos
		List<Prestamo> prestamosNoDevueltos = lector.getPrestamos().stream().filter(p -> p.getCopia() != null).toList();
		for (Prestamo prestamo : prestamosNoDevueltos) {
			if (prestamo.getFin().before(new Date(2023, 11, 6))) {
				prestamo.getCopia().setEstadoCopia(EstadoCopia.RETRASO);
				this.prestamoRepository.save(prestamo);
			}
		}
		if (prestamosNoDevueltos.size() == 0)
			return;
		
		// 2. Seleccionar aquel cuya fecha de fin sea la más antigua
		prestamosNoDevueltos.stream().sorted((p1, p2) -> p1.getFin().compareTo(p2.getFin()));
		LocalDate fechaPrestamo = prestamosNoDevueltos.get(0).getFin().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
//		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaActual = LocalDate.of(2023, 11, 12);
		
		// 3. Ver la diferencia de días de dicha fecha con la actual y multar
		long diasDeDiferencia = fechaPrestamo.until(fechaActual, java.time.temporal.ChronoUnit.DAYS);
		
		if (diasDeDiferencia > 0) {
			lector.multar((int) diasDeDiferencia * 2);
			this.lectorRepository.save(lector);
		}
	}

	@Override
	public void deleteEntity(long id) {
		Optional<Prestamo> prestamo = this.prestamoRepository.findById(id);
		if(prestamo.isPresent())
			this.prestamoRepository.deleteById(id);
		else
			throw new RuntimeException("No se encuentra el autor con id: " + id);
		
	}

	@Override

	public List<Prestamo> findByCopiaId(long id) {
		return this.prestamoRepository.findByCopiaId(id);	
	}	


	public Page<Prestamo> findByLectorId(long lector_id, int pageNum, int pageSize,
			String sortField, String sortDirection) {
		//If reducido --> variable = logica ? true: false
		//Si la direccion es igual a "ASC" entonces los campos se ordenaran de manera ascendente y sino, descendentes
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortField).descending() : Sort.by(sortField).ascending();
		
		//Paginacion, le paso numero de pagina, su tamaño y la ordenacion previamente hecha
		Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
		
		//Finalmente retornamos mediante el metodo findAll que recibe la paginacion
		return this.prestamoRepository.findByLectorId(pageable, lector_id);

	}	
	
	

}
