package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;



/**
 * Grado de dificultad para las vias
 * @author ur00
 *
 */

public class Grado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nombre;
	private String descripcion;
		
	public Grado(String nombre) {
		super();
		this.nombre = nombre;
		this.id = -1;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Grado [id=" + id + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}
	
	
	
	
	
	
	
}