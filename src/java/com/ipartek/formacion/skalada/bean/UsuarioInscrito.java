package com.ipartek.formacion.skalada.bean;

import java.sql.Timestamp;
import java.util.Date;

public class UsuarioInscrito extends Usuario {


	private static final long serialVersionUID = 1L;

	private Timestamp fechaInscripcion;

	

	public UsuarioInscrito(String nombre, String email, String password, Rol rol) {
		super(nombre, email, password, rol);
		Date date = new Date();
		fechaInscripcion= new Timestamp(date.getTime());
	}



	/**
	 * @return the fechaInscripcion
	 */
	public Timestamp getFechaInscripcion() {
		return fechaInscripcion;
	}



	/**
	 * @param fechaInscripcion the fechaInscripcion to set
	 */
	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuarioInscrito [fechaInscripcion=" + fechaInscripcion + "]";
	}

	
	
}
