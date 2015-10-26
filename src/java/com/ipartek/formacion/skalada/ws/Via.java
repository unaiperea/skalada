package com.ipartek.formacion.skalada.ws;

import java.io.Serializable;

import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;

public class Via implements Serializable {
	private static final long serialVersionUID = 3869513622336875450L;

	// **********************************
	// **** Atributos ****
	// **********************************
	/**
	 * Identificador
	 */
	private int id;

	/**
	 * Nombre de la Via
	 */
	private String nombre;

	/**
	 * Nivel de dificultad de la Via
	 */
	private String nombreGrado;

	/**
	 * Longitud de la via en metros
	 */
	private int longitud;

	/**
	 * Descripcion
	 */
	private String descripcion;

	/**
	 * Tipo de escalada practicada en la via
	 */
	private String nombreTipoEscalada;

	/**
	 * Sector a la que pertenece la via
	 */
	private String nombreSector;

	// **********************************
	// **** Constructores ****
	// **********************************
	/**
	 * @param nombre
	 *            nombre
	 * @param grado
	 *            grado
	 * @param longitud
	 *            longitud
	 * @param tipoEscalada
	 *            tipo de escalada
	 * @param sector
	 *            sector
	 */
	public Via(String nombre, int longitud, Grado grado,
			TipoEscalada tipoEscalada, Sector sector) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setLongitud(longitud);
		/*
		 * this.setGrado(grado); this.setTipoEscalada(tipoEscalada); this.setSector(sector);
		 */
	}

	/**
	 * @param nombre
	 */
	public Via(String nombre) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		/*
		 * this.setGrado(null); this.setTipoEscalada(null); this.setSector(null);
		 */
	}

	// **********************************
	// **** Getters/Setters ****
	// **********************************
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

	/*
	 * public Grado getGrado() { return this.grado; }
	 * 
	 * public final void setGrado(Grado grado) { this.grado = grado; }
	 */

	public int getLongitud() {
		return this.longitud;
	}

	public final void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public final void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreGrado() {
		return nombreGrado;
	}

	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}

	public String getNombreTipoEscalada() {
		return nombreTipoEscalada;
	}

	public void setNombreTipoEscalada(String nombreTipoEscalada) {
		this.nombreTipoEscalada = nombreTipoEscalada;
	}

	public String getNombreSector() {
		return nombreSector;
	}

	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	}

	/*
	 * public TipoEscalada getTipoEscalada() { return this.tipoEscalada; }
	 * 
	 * public final void setTipoEscalada(TipoEscalada tipoEscalada) { this.tipoEscalada = tipoEscalada; }
	 * 
	 * public Sector getSector() { return this.sector; }
	 * 
	 * public final void setSector(Sector sector) { this.sector = sector; }
	 */

	// **********************************
	// **** ToString() ****
	// **********************************

}
