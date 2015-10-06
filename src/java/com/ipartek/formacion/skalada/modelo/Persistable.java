package com.ipartek.formacion.skalada.modelo;

import java.util.ArrayList;

/**
 * Interfaz para permitir guardar, recuperar, modificar y eliminar beans.
 * Soporta las operaciones basicas de CRUD:
 * 		- Create
 * 		- Read
 * 		- Update
 * 		- Delete
 * 
 * @author Curso
 * @param <T> {@code <T>} Objeto genérico
 */
public interface Persistable<T> {
/* T es un objeto genérico, puede ser cualquier otra letra. Por no poner Object y evitar el casteo.
 * De esta forma donde se implemente el código de cada método sólo tendremos que decirle
 * la clase que debemos implementar en cada modelo. Ej.
 * 		public class ModeloRol implements Persistable<Usuario>
*/
	/**
	 **** 		CREATE		****
	 * Persiste el Objeto y lo guarda
	 * @param t {@code T} Objeto genérico a guardar
	 * @return {@code int} Identificador del objeto guardado, -1 en caso de error
	 */
	int save(T t);
	//int save(Object o);
	
	/**
	 ****		READ I		****
	 * Recupera Objeto por su Identificador
	 * @param id {@code int} identificador del objeto a recuperar
	 * @return {@code Object} objeto encontrado o null en caso contrario
	 */
	Object getById(int id);
	//Object getById(int id);
	
	/**
	 ****		READ II		****
	 * Recupera una coleccion de  Objetos
	 * @return {@code Object} coleccion de objetos, si no existen coleccion vacia
	 */
	ArrayList<T> getAll();
	//ArrayList<Object> getAll();
	
	/**
	 ****		UPDATE		****
	 * Modificar un Objeto el cual debe tener un identificador definido
	 * @param t {@code T} Objeto genérico a modificar
	 * @return true si se modificaba bien, false en caso contrario
	 */
	boolean update(T t);
	//boolean update(Object o);
	
	/**
	 ****		DELETE		****
	 * Eliminar un Objeto por su identificador
	 * @param id {@code int} identificador del recurso a eliminar
	 * @return true si se elimina, false en caso contrario
	 */
	boolean delete(int id);
	//boolean delete(int id);
	
}
