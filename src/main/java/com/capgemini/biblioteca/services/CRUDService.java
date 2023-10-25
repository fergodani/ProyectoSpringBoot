package com.capgemini.biblioteca.services;

import java.util.List;

public interface CRUDService<T, ID> {

	T getEntityById(ID id);

	List<T> findAll();

	void saveEntity(T entity);

	void deleteEntity(ID id);
}
