package com.ipartek.formacion.skalada.modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Via;

/**
 * Clase encargada de persistir los objetos de tipo Via {@code Via} en ficheros
 * serializando y des-serializando
 * @author Curso
 *
 */

public class ModeloVia implements Persistable{
	
	private static final String PATH_DATA = "data/via/"; //Creamos la carpeta a mano, luego será una tabla de la bbdd
	private static final String PATH_INDEX = "data/via/index.txt"; //Fichero con el último índice guardado
	private static final String FILE_EXTENSION = ".dat"; //extensión de ficheros a utilizar
	
	/**
	 * Indentificador con el último objeto creado, valor inicial 0
	 */
	private static int indice;
	
	/**
	 * Actualiza el índice
	 */
	public ModeloVia() { //Constructor ModeloVia
		super();
		
		file fIndex = new File(PATH_INDEX);
		if (fIndex!=null){
			
		}
		
		getIndex();
		
	}

	//Menú Source --> Override/Implements Methods
	@Override
	public int save(Object o) {
		
		ObjectOutputStream oos = null;
		int resul = -1;
		
		try{
			Via v  = (Via)o; //Casteamos el objeto tipo Object para que contenga el objeto tipo Via
			String file = PATH_DATA + (indice+1) + FILE_EXTENSION;			
			oos = new ObjectOutputStream(new FileOutputStream( file ));
			//guardar objeto
			oos.writeObject(v);
			//actualizar indice
			resul = updateIndex();			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if ( oos != null ){
				try {
					oos.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}		
			
		}			
		return resul;
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
		FileReader fr = null;
		
		try {
			fr = new FileReader(new File(PATH_INDEX));
			indice = Character.getNumericValue(fr.read()); //que lea el primer caracter numérico
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fr != null){
				try{
					fr.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return indice;
	}
	
	/**
	 * Incrementa en 1 el índice actual del fichero de texto {@code PATH_INDEX}
	 * @return el índice incrementado
	 */
	private int updateIndex(){
		FileWriter fw = null;
		indice++;
		
		try {
			fw = new FileWriter(new File(PATH_INDEX));
			fw.write(indice);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fw != null){
				try{
					fw.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return indice;
	}
	
	/**
	 * Crea fichero
	 */
	private void createIndex(){
		
	}

}
