package com.capgemini.biblioteca.unitTests;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capgemini.biblioteca.BibliotecaApplication;
import com.capgemini.biblioteca.WebSecurityConfig;
import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.model.Prestamo;
import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.AutorService;
import com.capgemini.biblioteca.services.LectorService;
import com.capgemini.biblioteca.services.LibroService;
import com.capgemini.biblioteca.services.PrestamoService;
import com.capgemini.biblioteca.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { WebSecurityConfig.class, BibliotecaApplication.class })
@AutoConfigureMockMvc
public class RestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private PrestamoService prestamoService;

	@MockBean
	private LibroService libroService;

	@MockBean
	private LectorService lectorService;

	@MockBean
	private AutorService autorService;

	private ObjectMapper objectMapper = new ObjectMapper();

//	@BeforeEach
//	public void setup() {
//		MockitoAnnotations.openMocks(this);
//		Usuario usuario = new Usuario();
//		usuario.setName("Daniel");
//		usuario.setLastName("Ferreira");
//		usuario.setId(1);
//		Mockito.when(usuarioService.getEntityById(1)).thenReturn(usuario);
//	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetUserById() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setName("Daniel");
		usuario.setLastName("Ferreira");
		usuario.setId(2);
		when(usuarioService.getEntityById(2L)).thenReturn(usuario);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Daniel"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Ferreira"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAllUsers() throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario());
		usuarios.add(new Usuario());
		usuarios.add(new Usuario());
		when(usuarioService.findAll()).thenReturn(usuarios);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(usuarios)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAutorById() throws Exception {
		Autor autor = new Autor();
		autor.setNombre("Daniel");
		autor.setNacionalidad("EEUU");
		autor.setId(2L);
		when(autorService.getEntityById(2L)).thenReturn(autor);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/autores/2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Daniel"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nacionalidad").value("EEUU"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAllAutores() throws Exception {
		List<Autor> autores = new ArrayList<Autor>();
		autores.add(new Autor());
		autores.add(new Autor());
		autores.add(new Autor());
		when(autorService.findAll()).thenReturn(autores);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/autores")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(autores)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetLectorById() throws Exception {
		Lector lector = new Lector();
		lector.setNombre("Daniel");
		lector.setDireccion("Calle 1");
		lector.setNSocio(2L);
		when(lectorService.getEntityById(2L)).thenReturn(lector);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/lectores/2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Daniel"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.direccion").value("Calle 1"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAllLectores() throws Exception {
		List<Lector> lectores = new ArrayList<Lector>();
		lectores.add(new Lector());
		lectores.add(new Lector());
		lectores.add(new Lector());
		when(lectorService.findAll()).thenReturn(lectores);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/lectores")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(lectores)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetLibroById() throws Exception {
		Libro libro = new Libro();
		libro.setTitulo("Libro1");
		libro.setId(2L);
		libro.setAnyo(2001);
		when(libroService.getEntityById(2L)).thenReturn(libro);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/libros/2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Libro1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.anyo").value(2001));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAllLibros() throws Exception {
		List<Libro> libros = new ArrayList<Libro>();
		libros.add(new Libro());
		libros.add(new Libro());
		libros.add(new Libro());
		when(libroService.findAll()).thenReturn(libros);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/libros")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(libros)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetPrestamoById() throws Exception {
		Prestamo prestamo = new Prestamo();
		prestamo.setId(2L);
		prestamo.setInicio(new Date());
		prestamo.setFin(new Date());
		when(prestamoService.getEntityById(2L)).thenReturn(prestamo);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/prestamos/2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.inicio").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.fin").isNotEmpty());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	public void testGetAllPrestamos() throws Exception {
		List<Prestamo> prestamo = new ArrayList<Prestamo>();
		prestamo.add(new Prestamo());
		prestamo.add(new Prestamo());
		prestamo.add(new Prestamo());
		when(prestamoService.findAll()).thenReturn(prestamo);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/prestamos")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(prestamo)));
	}
}
