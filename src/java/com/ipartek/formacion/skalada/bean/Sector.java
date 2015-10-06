package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

import com.ipartek.formacion.skalada.Constantes;

public class Sector implements Serializable {
	private static final long serialVersionUID = -2537948970066821711L;

	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private Zona zona;
	private String imagen; // Path + nombre de la im�gen a mostrar

	/**
	 * Constructor
	 */
	public Sector(String nombre, Zona zona) {
		super();
		this.setId(-1); // PMD no lo permite por éso habrá que poner final en el
						// método
		this.setNombre(nombre); // PMD no lo permite
		this.setZona(zona); // PMD no lo permite
		this.setImagen(Constantes.IMG_DEFAULT_SECTOR); // PMD no lo permite
	}

	/**
	 * Getters y Setters
	 */
	public int getId() {
		return this.id;
	}
	public final void setId(int id) { // Evitas que puedan sobreescribir/Override el
								// método al ser extendido desde un hijo
		this.id = id;
	}
	public String getNombre() {
		return this.nombre;
	}
	public final void setNombre(String nombre) { // Evitas que puedan
											// sobreescribir/Override el método
											// al ser extendido desde un hijo
		this.nombre = nombre;
	}
	public Zona getZona() {
		return this.zona;
	}
	public final void setZona(Zona zona) { // Evitas que puedan sobreescribir/Override
										// el método al ser extendido desde un
										// hijo
		this.zona = zona;
	}
	public String getImagen() {
		return this.imagen;
	}
	public final void setImagen(String imagen) { // Evitas que puedan
											// sobreescribir/Override el método
											// al ser extendido desde un hijo
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Sector [id=" + this.id + ", nombre=" + this.nombre + ", zona="
				+ this.zona + ", imagen=" + this.imagen + "]";
	}

}
