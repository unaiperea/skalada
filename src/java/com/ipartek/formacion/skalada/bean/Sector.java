package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

public class Sector implements Serializable{
	private static final long serialVersionUID = -2537948970066821711L;
	
	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private Zona zona;

	/**
	 * Constructor
	 */
	public Sector(String nombre, Zona zona) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setZona(zona);
	}

	/**
	 * Getters y Setters
	 */
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
	
	

}
