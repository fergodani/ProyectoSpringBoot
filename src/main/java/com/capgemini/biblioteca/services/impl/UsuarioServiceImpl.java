package com.capgemini.biblioteca.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.repositories.UsuarioRepository;
import com.capgemini.biblioteca.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
    private UsuarioRepository usuarioRepository;
	

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
//    private static final String BASE_URL = "http://localhost:8080/api/users";
//    private final RestTemplate restTemplate;
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    public UsuarioServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

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
//		ResponseEntity<List<Usuario>> responseEntity = 
//				  restTemplate.exchange(
//				    BASE_URL,
//				    HttpMethod.GET,
//				    null,
//				    new ParameterizedTypeReference<List<Usuario>>() {}
//				  );
//				List<Usuario> users = responseEntity.getBody();
//				return users.stream()
//				  .map(Usuario::getName)
//				  .collect(Collectors.toList());
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
