package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

/**
 * Rol de los usuarios
 *
 * @author Curso
 *
 */
public class Rol implements Serializable {
	private static final long serialVersionUID = -7009856922950747575L;

	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private String descripcion;

	/**
	 * Constructor
	 */
	public Rol(String nombre) {
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
	// @Unai: Evitas que puedan sobreescribir/Override el método al ser
	// extendido desde un hijo
	public final void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return this.nombre;
	}
	// @Unai: Evitas que puedan sobreescribir/Override el método al ser
	// extendido desde un hijo
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	// @Unai: Evitas que puedan sobreescribir/Override el método al ser
	// extendido desde un hijo
	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}