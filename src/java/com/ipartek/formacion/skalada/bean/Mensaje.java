package com.ipartek.formacion.skalada.bean;

/**
 * Bean para contener la informacion de los mensajes a mostrar en FrontEnd
 * @author ur00
 *
 */
public class Mensaje {

	private String tipo;
	private String texto;
	
	//tipos de mensajes
	public static final String MSG_SUCCESS = "success";
	public static final String MSG_INFO    = "info";
	public static final String MSG_WARNING = "warning";
	public static final String MSG_DANGER  = "danger";
	
	/**
	 * @param tipo
	 * @param texto
	 */
	public Mensaje(String tipo, String texto) {
		super();
		this.tipo = tipo;
		this.texto = texto;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override()
	public String toString() {
		return "Mensaje [tipo=" + this.tipo + ", texto=" + this.texto + "]";
	}
	
	
	
	
}
