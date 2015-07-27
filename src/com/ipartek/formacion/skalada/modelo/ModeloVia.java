package com.ipartek.formacion.skalada.modelo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
		
		File findex = new File(PATH_INDEX);
		if ( !findex.exists()){
			createIndex();
		}		
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
		File f = null;
		boolean resul = false;
		
		try{
		f = new File(PATH_DATA);
		
		if (f.isDirectory()){ //Comprobar el índice del Objeto que sea igual al número que contiene el fichero
			f.getName();
		}
		
		
		}catch(Exception e){
			e.getStackTrace();
		}
		
		return resul;
	}

	@Override
	public boolean delete(int id) {
		
		boolean resul = false;
		File f = null;
		
		try{
			f = new File(PATH_DATA + id + FILE_EXTENSION);
	
			if (f.isFile() && f.exists()){
				resul = f.delete();
			}else{
				resul = false;
			}
		}catch(Exception e){
			e.getStackTrace();
			resul = false;
		}
		return resul;
	}	

	/**
	 * Recupera el indice actual del fichero de texto {@code PATH_INDEX}
	 * @return indice actual, valor inicial 0
	 */
	private int getIndex(){
		DataInputStream fr = null; //Lee un fichero de enteros
		try {
			fr = new DataInputStream(new FileInputStream(PATH_INDEX));			
			indice = fr.readInt(); //que lea el primer dato numérico	
		} catch ( Exception e) {			
			e.printStackTrace();
		}finally{			
			
			if ( fr != null ){
				try {fr.close();} catch (IOException e) {e.printStackTrace();}
			}
			
		}	
		System.out.println("getIndex: " + indice );
		return indice;
	}
	
	/**
	 * Incrementa en 1 el indice actual del fichero de texto {@code PATH_INDEX}
	 * @return indice incrementado
	 */
	private int updateIndex(){
		System.out.println("updateIndex entrar: " + indice );
		DataOutputStream fr = null;		
		
		indice++;
		try {
			fr = new DataOutputStream(new FileOutputStream(PATH_INDEX));		
			fr.writeInt(indice);	
		} catch ( Exception e) {			
			e.printStackTrace();
		}finally{
		
			if ( fr != null ){
				try {fr.close();} catch (IOException e) {e.printStackTrace();}
			}
		}	
		System.out.println("updateIndex salir: " + indice );
		return indice;		
	}
	
	/**
	 * Crea fichero de indice
	 */
	private void createIndex(){
		
		System.out.println("createIndex");
		DataOutputStream fr = null;		
		indice=0;
		try {
			fr = new DataOutputStream(new FileOutputStream(PATH_INDEX));			
			fr.writeInt(indice);	
		} catch ( Exception e) {			
			e.printStackTrace();
		}finally{						
			if ( fr != null ){
				try {fr.close();} catch (IOException e) {e.printStackTrace();}
			}
			
		}	
		
	}
	
}
