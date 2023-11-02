package com.capgemini.biblioteca.model;

import java.util.Set;

import com.capgemini.biblioteca.model.enums.TipoLibro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
@Table(name = "libros")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TipoLibro tipoLibro;
	
	@Column
	private int anyo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autor_id")
	@JsonBackReference
	private Autor autor;
	
	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Copia> copias;
	
	public Libro() {
		super();
	}
	
	public Libro(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Set<Copia> getCopias() {
		return copias;
	}

	public void setCopias(Set<Copia> copias) {
		this.copias = copias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", tipoLibro=" + tipoLibro + ", anyo=" + anyo + ", autor="
				+ autor + ", copias=" + copias + "]";
	}

	public TipoLibro getTipoLibro() {
		return tipoLibro;
	}

	public void setTipoLibro(TipoLibro tipoLibro) {
		this.tipoLibro = tipoLibro;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int año) {
		this.anyo = año;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	
}
