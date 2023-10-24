package com.capgemini.biblioteca.model;

import java.util.Set;

import com.capgemini.biblioteca.model.enums.TipoLibro;

import jakarta.persistence.*;

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
	private int año;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
	private Set<Copia> copias;
	
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

	public TipoLibro getTipoLibro() {
		return tipoLibro;
	}

	public void setTipoLibro(TipoLibro tipoLibro) {
		this.tipoLibro = tipoLibro;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	
}
