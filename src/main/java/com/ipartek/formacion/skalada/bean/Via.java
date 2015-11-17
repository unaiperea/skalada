package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

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

	/**
	 * Usuario al que pertenece la via
	 */
	private Usuario usuario;

	/**
	 * Indica si la via esta validada o no. Por defecto siempre false
	 */
	private boolean validado;

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
			TipoEscalada tipoEscalada, Sector sector, Usuario usuario) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setLongitud(longitud);
		this.setGrado(grado);
		this.setTipoEscalada(tipoEscalada);
		this.setSector(sector);
		this.setUsuario(usuario);
		this.setValidado(false);
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
		this.setUsuario(null);
		this.setValidado(false);
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

	public Grado getGrado() {
		return this.grado;
	}

	public final void setGrado(Grado grado) {
		this.grado = grado;
	}

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

	public TipoEscalada getTipoEscalada() {
		return this.tipoEscalada;
	}

	public final void setTipoEscalada(TipoEscalada tipoEscalada) {
		this.tipoEscalada = tipoEscalada;
	}

	public Sector getSector() {
		return this.sector;
	}

	public final void setSector(Sector sector) {
		this.sector = sector;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the validado
	 */
	public boolean isValidado() {
		return this.validado;
	}

	/**
	 * @param validado
	 *            the validado to set
	 */
	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	// **********************************
	// **** ToString() ****
	// **********************************
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Via [id=" + this.id + ", nombre=" + this.nombre + ", grado="
				+ this.grado + ", longitud=" + this.longitud + ", descripcion="
				+ this.descripcion + ", tipoEscalada=" + this.tipoEscalada
				+ ", sector=" + this.sector + ", usuario=" + this.usuario
				+ ", validado=" + this.validado + "]";
	}
}
