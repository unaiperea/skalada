package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

import com.ipartek.formacion.skalada.Constantes;

public class Usuario implements Serializable {
	private static final long serialVersionUID = -6253794296547129164L;

	// **********************************
	// **** Atributos ****
	// **********************************
	private int id;
	private String nombre;
	private String email;
	private String password;
	private boolean validado;
	private Rol rol;
	private String token;

	// **********************************
	// **** Constructor ****
	// **********************************
	/**
	 * @param nombre
	 *            nombre
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param rol
	 *            rol
	 */
	public Usuario(String nombre, String email, String password, Rol rol) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setPassword(password);
		if (rol != null) {
			this.setRol(rol);
		} else {
			this.setRol(new Rol(Constantes.ROLE_USER));
		}
		this.setToken();
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

	public String getEmail() {
		return this.email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidado() {
		return this.validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public Rol getRol() {
		return this.rol;
	}

	public final void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getToken() {
		return this.token;
	}

	public final void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		boolean resul = false;

		if ((this.rol != null)
				&& (Constantes.ROLE_ID_ADMIN == this.rol.getId())) {
			resul = true;
		}
		return resul;
	}

	public final void setToken() {
		SecureRandom random = new SecureRandom();
		this.token = new BigInteger(130, random).toString(32);
	}

	// **********************************
	// **** ToString() ****
	// **********************************
	@Override()
	public String toString() {
		return "Usuario [id=" + this.id + ", nombre=" + this.nombre
				+ ", email=" + this.email + ", password=" + this.password
				+ ", validado=" + this.validado + ", rol=" + this.rol + "]";
	}

}
