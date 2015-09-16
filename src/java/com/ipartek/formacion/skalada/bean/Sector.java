package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

import com.ipartek.formacion.skalada.Constantes;

public class Sector implements Serializable{
	private static final long serialVersionUID = -2537948970066821711L;
	
	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private Zona zona;
	private String imagen; //Path + nombre de la imágen a mostrar

	/**
	 * Constructor
	 */
	public Sector(String nombre, Zona zona) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setZona(zona);
		this.setImagen(Constantes.IMG_DEFAULT_SECTOR);
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
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", nombre=" + nombre + ", zona=" + zona
				+ ", imagen=" + imagen + "]";
	}	
	

}
