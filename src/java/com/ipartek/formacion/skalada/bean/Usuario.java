package com.ipartek.formacion.skalada.bean;

import java.io.Serializable;

import com.ipartek.formacion.skalada.Constantes;

public class Usuario implements Serializable{
	private static final long serialVersionUID = -6253794296547129164L;
	
	//**********************************
	//****		Atributos			****
	//**********************************
	private int id;
	private String nombre;
	private String email;
	private String password;
	private int validado;
	private Rol rol;
	
	
	//**********************************
	//****		Constructor			****
	//**********************************
	/**
	 * @param nombre
	 * @param email
	 * @param password
	 * @param rol
	 */
	public Usuario(String nombre, String email, String password, Rol rol) {
		super();
		this.setId(-1);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setPassword(password);
		if (rol != null){
			this.setRol(rol);
		} else {
			this.setRol(new Rol(Constantes.ROLE_USER));
		}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getValidado() {
		return validado;
	}
	public void setValidado(int validado) {
		this.validado = validado;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	
	//**********************************
	//****		ToString()			****
	//**********************************	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", validado=" + validado
				+ ", rol=" + rol + "]";
	}
	
}
