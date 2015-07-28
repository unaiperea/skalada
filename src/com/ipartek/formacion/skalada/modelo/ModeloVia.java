package com.ipartek.formacion.skalada.modelo;

import static org.junit.Assert.fail;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	private static final String PATH_INDEX      = PATH_DATA + "index.dat";
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
		
		FileOutputStream outputStream = null;
		ObjectOutputStream out =  null;
		
		String file = PATH_DATA + (indice+1) + FILE_EXTENSION;
		
		int resul = -1;			
		
		try{
			
			Via v  = (Via)o;
			
			v.setId(indice+1);

			outputStream = new FileOutputStream(file);
			out = new ObjectOutputStream(outputStream);
			
			//guardar objeto
			out.writeObject(v);
			
			//actualizar indice
			resul = updateIndex();
			
		} catch (FileNotFoundException e) {
			fail("Fichero  no encontrado");
		} catch (IOException e) {
			fail("Error al abrir fichero");
		} finally {			
			try {
				if (out != null ) out.close();
				if (outputStream != null ) outputStream.close();
			} catch (IOException e) {
				fail("Fallo al cerrar fichero");
			}			
		}	
		
		return resul;
	}

	@Override
	public Object getById(int id) {
		
		FileInputStream inputStream = null;
		ObjectInputStream in =  null;	
		
		String file = PATH_DATA + id + FILE_EXTENSION;
		
		Via resul = null;
		
		try {
			inputStream = new FileInputStream(file);
			in = new ObjectInputStream(inputStream);
			
			resul = (Via) in.readObject();
						
		} catch (FileNotFoundException e) {
			fail("Fichero no encontrado");
		} catch (IOException e) {
			fail("Error al abrir fichero");
		} catch (ClassNotFoundException e) {
			fail("Objeto de clase Via no encontrado en el fichero");
		} finally {
			try {
				if (in != null ) in.close();
				if (inputStream != null ) inputStream.close();
			} catch (IOException e) {
				fail("Fallo al cerrar fichero");
			}
		}
		
		return resul;
	}

	@Override
	public ArrayList<Object> getAll() {
		ArrayList<Object> resul = new ArrayList<Object>();
		
		FileInputStream inputStream = null;
		ObjectInputStream in =  null;		
		
		File vias = new File(PATH_DATA);
		if (vias.exists()){
			
			File[] ficheros = vias.listFiles();
			
			for (int i=0 ; i < (ficheros.length-1) ; i++){    		
	    		   	
	    		
	    		try {
	    			inputStream = new FileInputStream(PATH_DATA + ficheros[i].getName());
	    			in = new ObjectInputStream(inputStream);
	    			
	    			resul.add((Via) in.readObject());
	    						
	    		} catch (FileNotFoundException e) {
	    			fail("Fichero no encontrado");
	    		} catch (IOException e) {
	    			fail("Error al abrir fichero");
	    		} catch (ClassNotFoundException e) {
	    			fail("Objeto de clase Via no encontrado en el fichero");
	    		} finally {
	    			try {
	    				if (in != null ) in.close();
	    				if (inputStream != null ) inputStream.close();
	    			} catch (IOException e) {
	    				fail("Fallo al cerrar fichero");
	    			}
	    		}
	    	}			
		}
		
		return resul;
	}

	@Override
	public boolean update(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		
		File fBorrar = null;
		
		String file = PATH_DATA + id + FILE_EXTENSION;
		
		boolean resul = false;
			
		fBorrar = new File(file);
		
		if (fBorrar.exists()){
			fBorrar.delete();
			resul = true;
		}

		return resul;
	}	
	
	/**
	 * Recupera el indice actual del fichero de texto {@code PATH_INDEX}
	 * @return indice actual, valor inicial 0
	 */
	private int getIndex(){
		DataInputStream fr = null;		
		try {
			fr = new DataInputStream(new FileInputStream(PATH_INDEX));			
			indice = fr.readInt();			
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