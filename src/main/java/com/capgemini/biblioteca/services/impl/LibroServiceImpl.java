package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	@Override
	public Page<Libro> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		//If reducido --> variable = logica ? true: false
				//Si la direccion es igual a "ASC" entonces los campos se ordenaran de manera ascendente y sino, descendentes
				Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
						Sort.by(sortField).descending() : Sort.by(sortField).ascending();
				
				//Paginacion, le paso numero de pagina, su tamaño y la ordenacion previamente hecha
				Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
				
				//Finalmente retornamos mediante el metodo findAll que recibe la paginacion
				return this.libroRepository.findAll(pageable);
	}
	
	@Override
	public Page<Libro> findLibrosPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		//If reducido --> variable = logica ? true: false
				//Si la direccion es igual a "ASC" entonces los campos se ordenaran de manera ascendente y sino, descendentes
				Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
						Sort.by(sortField).descending() : Sort.by(sortField).ascending();
				
				//Paginacion, le paso numero de pagina, su tamaño y la ordenacion previamente hecha
				Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
				
				//Finalmente retornamos mediante el metodo findAll que recibe la paginacion
				return this.libroRepository.findAll(pageable);
	}
}
