package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;
import java.util.List;

public class Sector implements Serializable {

	private static final long serialVersionUID = -2537948970066821711L;
	
	private int id;
	private String nombre;
	private Zona zona;
	
	public Sector(String nombre, Zona zona) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setZona(zona);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", nombre=" + nombre + ", zona=" + zona
				+ "]";
	}
	
	
	
}