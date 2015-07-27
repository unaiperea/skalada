package com.ipartek.formacion.skalada.modelo;

import java.util.ArrayList;

/**
 * Interfaz para permitir guardar, recuperar, modificar y eliminar Beans
 * Soporta las operaciones básicas de CRUD
 * @author Curso
 *
 */

//UN INTERFACE NO TIENE CÓDIGO, LE PASA EL MARRÓN AL QUE LO IMPLEMENTA (ModeloVia)
public interface Persistable {
	
	// {@code int} y {@code Object} para que lo ponga en los Tag de code
	/**
	 * Persiste el Objeto y lo guarda
	 * @param o {@code Object} a guardar
	 * @return {@code int} Identificador del objeto guardado, -1 en caso de fallo
	 */
	int save ( Object o );
	
	/**
	 * Recupera Objeto por su identificador
	 * @param id {@code int} identificador del objeto a recuperar
	 * @return {@code Object} objeto encontrado o null en caso contrario
	 */
	Object getById(int id);
	
	/**
	 * Recupera una colección de Objetos
	 * @return colección de objetos, si no existe colección vacía
	 */
	ArrayList<Object> getAll();
	
	/**
	 * Modifica un objeto el cual debe tener un identificador definido
	 * @param o {@code Object} a modificar
	 * @return true si se modifica bien, false en caso contrario
	 */
	boolean update(Object o);
	
	/**
	 * Eliminar un Objeto por su identificador
	 * @param id {@code int} identificador del objeto a eliminar
	 * @return true si elimina, false en caso contrario
	 */
	boolean delete(int id);
	
	
	
	
	
	
}
