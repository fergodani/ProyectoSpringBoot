package com.capgemini.biblioteca.model;

import java.util.Set;

import com.capgemini.biblioteca.model.enums.EstadoCopia;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lectores")
public class Lector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long nSocio;
	
	@Column
	private String nombre;
	
	@Column
	private String telefono;
	
	@Column
	private String direccion;
	
	@OneToMany(mappedBy = "lector", targetEntity = Prestamo.class)
	@JsonManagedReference
	private Set<Prestamo> prestamos;
	
	
	public Multa getMulta() {
		return multa;
	}

	@Override
	public String toString() {
		return "Lector [nSocio=" + nSocio + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", multa=" + multa + "]";
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="fInicio", column = @Column(name="inicio_multa")),
		@AttributeOverride(name="fFin", column = @Column(name="fin_multa"))
	})
	private Multa multa;

	public Long getNSocio() {
		return this.nSocio;
	}

	public void setNSocio(Long nSocio) {
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

	public Set<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Set<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	public Prestamo devolver(long id) {
		if (this.getPrestamos().size() == 0)
			return null;
		
		for (Prestamo prestamo : prestamos) {
			if (prestamo.getId() == id) {
				prestamo.getCopia().setEstadoCopia(EstadoCopia.BIBLIOTECA);
				prestamo.setCopia(null);
			}
			return prestamo;	
		}
		return null;
	}

}
