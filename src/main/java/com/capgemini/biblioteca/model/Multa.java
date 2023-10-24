package com.capgemini.biblioteca.model;

import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class Multa {

	private Date fInicio;
	
	private Date fFin;

	public Date getfInicio() {
		return fInicio;
	}

	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}

	public Date getfFin() {
		return fFin;
	}

	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}
}
