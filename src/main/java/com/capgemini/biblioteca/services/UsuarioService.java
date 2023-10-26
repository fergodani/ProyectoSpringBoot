package com.capgemini.biblioteca.services;

import java.util.List;

import com.capgemini.biblioteca.model.Usuario;

public interface UsuarioService {

	Usuario getEntityById(long id);
	
	Usuario getUserByUsername(String username);

	List<Usuario> findAll();

	void saveEntity(Usuario usuario);

	void deleteEntity(long id);
}
