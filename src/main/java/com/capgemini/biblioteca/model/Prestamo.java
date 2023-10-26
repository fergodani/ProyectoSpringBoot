package com.capgemini.biblioteca.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="prestamos")
public class Prestamo {

	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", copia=" + copia + ", lector=" + lector + ", inicio=" + inicio + ", fin=" + fin
				+ "]";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name="copia_id")
	private Copia copia;
	
	@ManyToOne
	@JoinColumn(name="lector_id")
	private Lector lector;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inicio;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fin;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Copia getCopia() {
		return copia;
	}
	public void setCopia(Copia copia) {
		this.copia = copia;
	}
	public Lector getLector() {
		return lector;
	}
	public void setLector(Lector lector) {
		this.lector = lector;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}

	
}
