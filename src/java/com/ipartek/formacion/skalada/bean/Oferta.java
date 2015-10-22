package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Ofertas de la aplicación
 *
 * @author Curso
 *
 */
public class Oferta implements Serializable {
	private static final long serialVersionUID = -7009856922950747575L;

	/**
	 * Atributos
	 */
	private int id;
	private String titulo;
	private String descripcion;
	private float precio;
	private Timestamp fecha_alta;
	private Timestamp fecha_baja;
	private Zona zona;
	private int visible;
	private ArrayList<UsuarioInscrito> usuariosInscritos;

	
	
	public Oferta() {
		super();
		this.usuariosInscritos = new ArrayList<UsuarioInscrito>();
	}

	public Oferta(String titulo) {
		super();
		this.setTitulo(titulo);
		this.setId(-1);
		this.setZona(new Zona(""));
		this.usuariosInscritos = new ArrayList<UsuarioInscrito>();
	}
	
	/**
	 * @param id
	 * @param titulo
	 * @param descripcion
	 * @param precio
	 * @param fecha_alta
	 * @param fecha_baja
	 * @param zona
	 * @param visible
	 */
	public Oferta(int id, String titulo, String descripcion, float precio,
			Timestamp fecha_alta, Timestamp fecha_baja, Zona zona,
			int visible) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.precio = precio;
		this.fecha_alta = fecha_alta;
		this.fecha_baja = fecha_baja;
		this.zona = zona;
		this.visible = visible;
		this.usuariosInscritos = new ArrayList<UsuarioInscrito>();		
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the precio
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}

	/**
	 * @return the fecha_alta
	 */
	public Timestamp getFecha_alta() {
		return fecha_alta;
	}

	/**
	 * @param fecha_alta the fecha_alta to set
	 */
	public void setFecha_alta(Timestamp fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	/**
	 * @return the fecha_baja
	 */
	public Timestamp getFecha_baja() {
		return fecha_baja;
	}

	/**
	 * @param fecha_baja the fecha_baja to set
	 */
	public void setFecha_baja(Timestamp fecha_baja) {
		this.fecha_baja = fecha_baja;
	}

	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}

	/**
	 * @return the visible
	 */
	public int getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(int visible) {
		this.visible = visible;
	}

	/**
	 * @return the usuariosInscritos
	 */
	public ArrayList<UsuarioInscrito> getUsuariosInscritos() {
		return usuariosInscritos;
	}

	/**
	 * @param usuariosInscritos the usuariosInscritos to set
	 */
	public void setUsuariosInscritos(ArrayList<UsuarioInscrito> usuariosInscritos) {
		this.usuariosInscritos = usuariosInscritos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Oferta [id=" + id + ", titulo=" + titulo + ", descripcion="
				+ descripcion + ", precio=" + precio + ", fecha_alta="
				+ fecha_alta + ", fecha_baja=" + fecha_baja + ", zona=" + zona
				+ ", visible=" + visible + ", usuariosInscritos="
				+ usuariosInscritos + "]";
	}

	/**
	 * devuelve el tamaño del arraylist
	 * @return int
	 */
	public int getNumeroSuscritos(){
		return usuariosInscritos.size();
	}

}