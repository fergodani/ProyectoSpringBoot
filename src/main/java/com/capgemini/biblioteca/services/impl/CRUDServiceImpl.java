package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.biblioteca.services.CRUDService;

public abstract class CRUDServiceImpl<T, ID> implements CRUDService<T, ID>{

	@Autowired
	private JpaRepository<T, ID> repository;

	@Override
	public T getEntityById(ID id) {
		Optional<T> element = this.repository.findById(id);
		if (element.isPresent())
			return element.get();
		else
			throw new RuntimeException("No se ha encontrado el elemento con el id: " + id);
	}

	@Override
	public List<T> findAll() {
		return this.repository.findAll();
	}

	@Override
	public void saveEntity(T entity) {
		this.repository.save(entity);
	}

	@Override
	public void deleteEntity(ID id) {
		Optional<T> element = this.repository.findById(id);
		if (element.isPresent())
			this.repository.delete(element.get());
		else
			throw new RuntimeException("No existe el elemento con el id: " + id);
	}
	
	
}
