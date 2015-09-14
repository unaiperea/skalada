package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

/**
 * Ruta para escalada en una pared
 *  
 * @author ur00
 *
 */
public class Via implements Serializable {

	/**
	 * Identificador
	 */
	private int id; 
	
	/**
	 * Nombre de la via
	 */
	private String nombre;
	
	/**
	 * Nivel de dificultad de la via
	 */
	private Grado grado; 
	
	/**
	 * Longitud de la via en metros
	 */
	private int longitud; 
	
	/**
	 * Descripcion
	 */
	private String descripcion;
	
	/**
	 * TipoEscalada
	 */
	private TipoEscalada tipoEscalada;
	
	/**
	 * Sector
	 */
	private Sector sector;

	/**
	 * @param nombre
	 * @param grado
	 * @param longitud
	 * @param tipoEscalada
	 * @param sector
	 */
	public Via(String nombre, Grado grado, int longitud,
			TipoEscalada tipoEscalada, Sector sector) {
		super();
		this.nombre = nombre;
		this.grado = grado;
		this.longitud = longitud;
		this.tipoEscalada = tipoEscalada;
		this.sector = sector;
	}
	
	
	public Via (String nombre){
		super();
		this.nombre = nombre;
		this.grado = null;		
		this.tipoEscalada = null;
		this.sector = null;
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

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoEscalada getTipoEscalada() {
		return tipoEscalada;
	}

	public void setTipoEscalada(TipoEscalada tipoEscalada) {
		this.tipoEscalada = tipoEscalada;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "Via [id=" + id + ", nombre=" + nombre + ", grado=" + grado
				+ ", longitud=" + longitud + ", descripcion=" + descripcion
				+ ", tipoEscalada=" + tipoEscalada + ", sector=" + sector + "]";
	}

	
	

	
	
}