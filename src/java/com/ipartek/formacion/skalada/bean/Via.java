package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

public class Via implements Serializable{
	private static final long serialVersionUID = 3869513622336875450L;

//**********************************
//****		Atributos			****
//**********************************
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
	 * Tipo de escalada practicada en la via
	 */
	private TipoEscalada tipoEscalada;
	
	/**
	 * Sector a la que pertenece la via
	 */
	private Sector sector;

	
//**********************************
//****		Constructores		****
//**********************************	
	/**
	 * @param id
	 * @param nombre
	 * @param grado
	 * @param longitud
	 * @param tipoEscalada
	 * @param sector
	 */
	public Via(String nombre, int longitud, Grado grado, TipoEscalada tipoEscalada, Sector sector) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setLongitud(longitud);
		this.setGrado(grado);
		this.setTipoEscalada(tipoEscalada);
		this.setSector(sector);
	}

	
	/**
	 * @param nombre
	 */
	public Via(String nombre) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setGrado(null);
		this.setTipoEscalada(null);
		this.setSector(null);
	}
	
	
//**********************************
//****		Getters/Setters		****
//**********************************	
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

//**********************************
//****		ToString()			****
//**********************************		
	@Override
	public String toString() {
		return "Via [id=" + id + ", nombre=" + nombre + ", grado=" + grado
				+ ", longitud=" + longitud + ", descripcion=" + descripcion
				+ ", tipoEscalada=" + tipoEscalada + ", sector=" + sector + "]";
	}
}
