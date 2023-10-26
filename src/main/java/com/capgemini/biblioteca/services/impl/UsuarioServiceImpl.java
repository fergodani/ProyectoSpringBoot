package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.repositories.UsuarioRepository;
import com.capgemini.biblioteca.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Usuario getEntityById(long id) {
		Optional<Usuario> usuario = this.usuarioRepository.findById(id);
		if(usuario.isPresent())
			return usuario.get();
		else
			throw new RuntimeException("No se encuentra el usuario con id: " + id);
	}

	@Override
	public List<Usuario> findAll() {
		return this.usuarioRepository.findAll();
	}

	@Override
	public void saveEntity(Usuario usuario) {
		usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        this.usuarioRepository.save(usuario);
	}

	@Override
	public void deleteEntity(long id) {
		this.usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario getUserByUsername(String username) {
		return this.usuarioRepository.findByUsername(username);
	}
    
    
}
