package com.capgemini.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.biblioteca.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

	Usuario findByUsername(String dni);
}
