package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

import com.ipartek.formacion.skalada.Grado;

/**
 * Ruta para escalada en una pared
 *  
 * @author ur00
 *
 */
public class Via implements Serializable {

	/**
	 * Idebtificador
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

	
	/***************************************
	 *          Constructores              *  
	****************************************/
	
	/**
	 * @param nombre
	 */
	public Via(String nombre) {
		super();
		this.id = -1;
		this.nombre = nombre;
		this.grado = Grado.NORMAL;
		this.longitud = 0;
		this.descripcion = "";		
	}

	
	
	/***************************************
	 *          GETTERS/SETTERS            *  
	****************************************/
	
	
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

	public int getId() {
		return id;
	}


	
	
	/***************************************
	 *               METODOS               *  
	****************************************/
	
	@Override
	public String toString() {
		return "Via [id=" + id + ", nombre=" + nombre + ", grado=" + grado
				+ ", longitud=" + longitud + ", descripcion=" + descripcion
				+ "]";
	} 
	
	
}
