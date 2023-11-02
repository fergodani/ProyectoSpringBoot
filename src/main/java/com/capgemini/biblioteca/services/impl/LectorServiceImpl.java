package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Override
	public Page<Lector> findPaginated(int pageNum, int pageSize, String sortField, String sortDirection) {
		//If reducido --> variable = logica ? true: false
				//Si la direccion es igual a "ASC" entonces los campos se ordenaran de manera ascendente y sino, descendentes
				Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
						Sort.by(sortField).descending() : Sort.by(sortField).ascending();
				
				//Paginacion, le paso numero de pagina, su tamaño y la ordenacion previamente hecha
				Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
				
				//Finalmente retornamos mediante el metodo findAll que recibe la paginacion
				return this.lectorRepository.findAll(pageable);
	}
}
