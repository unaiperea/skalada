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
	private String imagen; // path + nombre de la imagen a mostrar
	private boolean validado;
	private Usuario usuario;
	private double latitud;
	private double longitud;

	/**
	 * Constructor
	 * 
	 * @param nombre Nombre
	 * @param zona Zona
	 */
	public Sector(String nombre, Zona zona) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setZona(zona);
		this.setImagen(Constantes.IMG_DEFAULT_SECTOR);
		this.setValidado(false);

		this.setUsuario(null);
		this.setLatitud(0);
		this.setLongitud(0);		

	}

	/**
	 * Getters y Setters
	 */
	public int getId() {
		return this.id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Zona getZona() {
		return this.zona;
	}

	public final void setZona(Zona zona) {
		this.zona = zona;
	}

	public String getImagen() {
		return this.imagen;
	}

	public final void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isValidado() {
		return this.validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", nombre=" + nombre + ", zona=" + zona + ", imagen=" + imagen + ", validado=" + validado + ", usuario="
				+ usuario + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}

}
