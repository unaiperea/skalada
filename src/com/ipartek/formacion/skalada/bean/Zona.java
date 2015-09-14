package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Zona implements Serializable {
	
	private static final long serialVersionUID = 7940428901325389646L;
	
	
	private int id;	
	private String nombre;
	private List<Sector> sectores;

	public Zona(String nombre) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setSectores(new ArrayList<Sector>());
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
		
	
	public List<Sector> getSectores() {
		return sectores;
	}

	public void setSectores(List<Sector> sectores) {
		this.sectores = sectores;
	}

	@Override
	public String toString() {
		return "Zona [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
	
	
	
}