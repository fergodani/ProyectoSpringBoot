package com.capgemini.biblioteca.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lectores")
public class Lector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nSocio;
	
	@Column
	private String nombre;
	
	@Column
	private String telefono;
	
	@Column
	private String direccion;
	
	@ManyToMany(mappedBy = "lectores")
	@JoinTable(
		        name = "prestamos", // Nombre de la tabla intermedia
		        joinColumns = @JoinColumn(name = "lector_id"), // Columna que hace referencia a Autor
		        inverseJoinColumns = @JoinColumn(name = "copia_id") // Columna que hace referencia a Libro
		    )
	private Set<Copia> copias;
	
	@Embedded
	private Multa multa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getnSocio() {
		return nSocio;
	}

	public void setnSocio(String nSocio) {
		this.nSocio = nSocio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set<Copia> getPrestamos() {
		return copias;
	}

	public void setPrestamos(Set<Copia> prestamos) {
		this.copias = prestamos;
	}

}
