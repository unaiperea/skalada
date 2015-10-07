package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

public class Zona implements Serializable {
	private static final long serialVersionUID = 2585448916481098409L;

	/**
	 * Atributos
	 */
	private int id;
	private String nombre;
	private List<Sector> sectores;
	private Usuario creador;
	private boolean publicado;
	private Timestamp fechaCreado;
	private Timestamp fechaModificado;

	/**
	 * Constructor
	 */
	public Zona(String nombre) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setSectores(new ArrayList<Sector>());
		this.setPublicado(false);
		ModeloUsuario mu = new ModeloUsuario();
		Usuario admin = (Usuario) mu.getById(1);
		this.setCreador(admin);
	}

	public Zona(String nombre, Usuario creador) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setSectores(new ArrayList<Sector>());
		this.setCreador(creador);
		this.setPublicado(false);
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

	public List<Sector> getSectores() {
		return this.sectores;
	}

	public final void setSectores(List<Sector> sectores) {
		this.sectores = sectores;
	}

	/**
	 * @return the creador
	 */
	public Usuario getCreador() {
		return this.creador;
	}

	/**
	 * @param creador
	 *            the creador to set
	 */
	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	/**
	 * @return the fechaCreado
	 */
	public Timestamp getFechaCreado() {
		return this.fechaCreado;
	}

	/**
	 * Crea la fecha con la hora y el dia actual
	 */
	public void setFechaCreado() {
		Date date = new Date();
		this.fechaCreado = new Timestamp(date.getTime());
	}

	/**
	 * @param fechaCreado
	 *            the fechaCreado to set
	 */
	public void setFechaCreado(Timestamp fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	/**
	 * @return the fechaModificado
	 */
	public Timestamp getFechaModificado() {
		return this.fechaModificado;
	}

	/**
	 * @param fechaModificado
	 *            the fechaModificado to set
	 */
	public void setFechaModificado(Timestamp fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

	/**
	 * Crea la fecha de modificación con la hora y el dia actual
	 */
	public void setFechaModificado() {
		Date date = new Date();
		this.fechaModificado = new Timestamp(date.getTime());
	}

	/**
	 * @return the publicado
	 */
	public boolean isPublicado() {
		return this.publicado;
	}

	/**
	 * @param publicado
	 *            the publicado to set
	 */
	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

}
