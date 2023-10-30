package com.capgemini.biblioteca.model;

import java.util.List;
import java.util.Set;

import com.capgemini.biblioteca.model.enums.EstadoCopia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "copias")
public class Copia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column
	private EstadoCopia estadoCopia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "libro_id")
	private Libro libro;
	
	@OneToMany(mappedBy = "copia", targetEntity = Prestamo.class)
	private Set<Prestamo> prestamos;

	public Long getId() {
		return id;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoCopia getEstadoCopia() {
		return estadoCopia;
	}

	public void setEstadoCopia(EstadoCopia estadoCopia) {
		this.estadoCopia = estadoCopia;
	}
	

}
