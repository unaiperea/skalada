package com.ipartek.formacion.skalada.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Via;

/**
 * Clase encargada de persistir los objetos de tipo {@code Via} 
 * en ficheros serializando y des-serializando
 * @author ur00
 *
 */
public class ModeloVia implements Persistable {
	
	private static final String PATH_DATA      = "data/via/";
	private static final String PATH_INDEX      = "data/via/index.dat";
	private static final String FILE_EXTENSION = ".dat";
	
	/**
	 * Identificador del ultimo objeto creado, valor inicial 0
	 */
	private static int indice; 
	

	/**
	 * Actualiza el indice
	 */
	public ModeloVia() {
		super();
		getIndex();
	}
	

	@Override
	public int save(Object o) {
		
		ObjectOutputStream oos = null;
		int resul = -1;
		
		try{
			Via v  = (Via)o;		
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
		//TODO implementar
		return resul;
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
	 * Recupera el indice actual del fichero de texto {@code PATH_INDEX}
	 * @return indice actual, valor inicial 0
	 */
	private int getIndex(){
		FileReader fr = null;
		try {
			fr = new FileReader(new File(PATH_INDEX));
			indice = Character.getNumericValue( fr.read() );			
		} catch ( Exception e) {			
			e.printStackTrace();
		}finally{
			if ( fr != null ){
				try {
					fr.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}			
		return indice;
	}
	
	/**
	 * Incrementa en 1 el indice actual del fichero de texto {@code PATH_INDEX}
	 * @return indice incrementado
	 */
	private int updateIndex(){
		FileWriter fr = null;
		indice++;
		try {
			fr = new FileWriter(new File(PATH_INDEX));
			fr.write(indice);	
		} catch ( Exception e) {			
			e.printStackTrace();
		}finally{
			if ( fr != null ){
				try {
					fr.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}		
		return indice;		
	}
}
