package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

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
	private int validado;
	private Rol rol;
	private String token;

	// **********************************
	// **** Constructor ****
	// **********************************
	/**
	 * @param nombre
	 * @param email
	 * @param password
	 * @param rol
	 */
	public Usuario(String nombre, String email, String password, Rol rol) {
		super();
		this.setId(-1); // @Unai: PMD no lo permite por éso habrá que poner
						// final en el método
		this.setNombre(nombre); // PMD no lo permite
		this.setEmail(email); // PMD no lo permite
		this.setPassword(password); // PMD no lo permite
		if (rol != null) {
			this.setRol(rol); // PMD no lo permite
		} else {
			this.setRol(new Rol(Constantes.ROLE_USER)); // PMD no lo permite
		}
	}

	// **********************************
	// **** Getters/Setters ****
	// **********************************
	public int getId() {
		return this.id;
	}
	public final void setId(int id) { // @Unai: Evitas que puedan
										// sobreescribir/Override
										// el método al ser extendido desde un
										// hijo
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
	public int getValidado() {
		return this.validado;
	}
	public final void setValidado(int validado) {
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

	// **********************************
	// **** ToString() ****
	// **********************************
	@Override
	public String toString() {
		return "Usuario [id=" + this.id + ", nombre=" + this.nombre
				+ ", email=" + this.email + ", password=" + this.password
				+ ", validado=" + this.validado + ", rol=" + this.rol + "]";
	}

}