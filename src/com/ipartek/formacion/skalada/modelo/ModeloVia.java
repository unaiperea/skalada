package com.ipartek.formacion.skalada.modelo;

import java.util.ArrayList;

/**
 * Clase encargada de persistir los objetos de tipo Via {@code Via} en ficheros
 * serializando y des-serializando
 * @author Curso
 *
 */

public class ModeloVia implements Persistable{
	
	private static final String PATH_DATA = "data/via/"; //Creamos la carpeta a mano, luego será una tabla de la bbdd
	private static final String PATH_INDEX = "data/via/index.txt"; //Fichero con el último índice guardado
	private static final String PATH_EXTENSION = ".dat"; //extensión de ficheros a utilizar
	
	/**
	 * Indentificador con el último objeto creado, valor inicial 0
	 */
	private static int indice;
	
	/**
	 * Actualiza el índice
	 */
	public ModeloVia() { //Constructor ModeloVia
		super();
		
	}

	//Menú Source --> Override/Implements Methods
	@Override
	public int save(Object o) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Object getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getAll() {
		ArrayList<Object> resul = new ArrayList<Object>();
		//TODO Implementar
		return null;
	}

	@Override
	public boolean update(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Recupera el índice actual del fichero de texto {@code PATH_INDEX}
	 * @return índice actual, valor inicial 0
	 */
	private int getIndex(){
		return indice;
	}
	
	/**
	 * Incrementa en 1 el índice actual del fichero de texto {@code PATH_INDEX}
	 * @return el índice incrementado
	 */
	private int updateIndex(){
		return indice;
	}
	
	
}
