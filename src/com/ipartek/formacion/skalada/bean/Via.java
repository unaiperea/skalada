package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

import com.ipartek.formacion.skalada.Grado;

/**
 * Ruta para escalada en una pared
 * 
 * @author Curso
 *
 */
public class Via implements Serializable {

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
	 * Imagen
	 */
	private String imagen;

//**********************************
//****		Contructores		****
//**********************************
	/**
	 * @param nombre
	 */
	public Via(String nombre) {
		super();
		this.id = -1;
		this.setNombre(nombre);
		this.setGrado(Grado.NORMAL);
		this.setLongitud(0);
		this.setDescripcion("");
		this.setImagen("http://placehold.it/700x450");
	}
	

//**********************************
//****		Getters Setters		****
//**********************************
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
	
	public void setId(int id) {
		this.id = id;
	}
		
	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


@Override
	public String toString() {
		return "Via [id=" + id + ", nombre=" + nombre + ", grado=" + grado
				+ ", longitud=" + longitud + ", descripcion=" + descripcion
				+ ", imagen=" + imagen + "]";
	}
	

	
	
}
