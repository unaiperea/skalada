package com.ipartek.formacion.skalada.ws;

import java.util.ArrayList;

/**
 * 
 * Interfaz para definir los metodos del Web Service basado en SOAP e implementado con AXIS
 *
 */
public interface ServiceSOAP {

	/**
	 * Recupera una coleccion de objetos
	 * 
	 * @return {@code ArrayList<Via>} coleccion de objetos, si no existen coleccion vacia
	 *
	 */
	public ArrayList<Via> viasPOJO();

	/**
	 * Recupera un objeto por su identificador
	 * 
	 * @return {@code Via} objeto, si no existe objeto, vacia
	 *
	 */
	public Via viaDetallePOJO(int id);

}
