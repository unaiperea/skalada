package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

public class TipoEscalada implements Serializable {
	private static final long serialVersionUID = 4866785056536207133L;

	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private String descripcion;

	/**
	 * Constructor
	 */
	public TipoEscalada(String nombre) {
		super();
		this.setNombre(nombre); // PMD no lo permite por éso habrá que poner
								// final en el método
		this.setId(-1); // PMD no lo permite
	}

	/**
	 * Getters y Setters
	 */
	public int getId() {
		return this.id;
	}
	public final void setId(int id) { // Evitas que puedan
										// sobreescribir/Override el método al
										// ser extendido desde un hijo
		this.id = id;
	}
	public String getNombre() {
		return this.nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}