package com.ipartek.formacion.skalada.modelo;


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
	
	private static final String PATH_DATA_FOLDER = "data/";
	private static final String PATH_DATA_VIA = PATH_DATA_FOLDER + "via/";
	private static final String PATH_INDEX = PATH_DATA_VIA + "index.dat";
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
		
		// Crea la estructura de carpetas si no existe
		File fDtaFolder = new File(PATH_DATA_FOLDER);
		if (!fDtaFolder.exists()) {
			fDtaFolder.mkdir();
		}

		File fDataFolderVia = new File(PATH_DATA_VIA);
		if (!fDataFolderVia.exists()) {
			fDataFolderVia.mkdir();
		}

		File findex = new File(PATH_INDEX);
		if (!findex.exists()) {
			createIndex();
		}

		// obtiene el indice actual
		getIndex();
	}
	

	@Override
	public int save(Object o) {
		
		FileOutputStream outputStream = null;
		ObjectOutputStream out =  null;
		
		String file = PATH_DATA_VIA + (indice+1) + FILE_EXTENSION;
		
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
			try {
				if (out != null ) out.close();
				if (outputStream != null ) outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}	
		
		return resul;
	}

	@Override
	public Object getById(int id) {
		
		FileInputStream inputStream = null;
		ObjectInputStream in =  null;	
		
		String file = PATH_DATA_VIA + id + FILE_EXTENSION;
		
		Via resul = null;
		
		try {
			inputStream = new FileInputStream(file);
			in = new ObjectInputStream(inputStream);
			
			resul = (Via) in.readObject();
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null ) in.close();
				if (inputStream != null ) inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return resul;
	}

	@Override
	public ArrayList<Object> getAll() {
		ArrayList<Object> resul = new ArrayList<Object>();
		
		FileInputStream inputStream = null;
		ObjectInputStream in =  null;		
		
		File vias = new File(PATH_DATA_VIA);
		if (vias.exists()){
			
			File[] ficheros = vias.listFiles();
			
			for (int i=0 ; i < (ficheros.length-1) ; i++){    		
	    		   	
	    		
	    		try {
	    			inputStream = new FileInputStream(PATH_DATA_VIA + ficheros[i].getName());
	    			in = new ObjectInputStream(inputStream);
	    			
	    			resul.add((Via) in.readObject());
	    						
	    		} catch (FileNotFoundException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		} catch (ClassNotFoundException e) {
	    			e.printStackTrace();
	    		} finally {
	    			try {
	    				if (in != null ) in.close();
	    				if (inputStream != null ) inputStream.close();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    	}			
		}
		
		return resul;
	}

	@Override
	public boolean update(Object o) {
		FileOutputStream outputStream = null;
		ObjectOutputStream out =  null;
		
		boolean resul = false;		
		Via v = (Via)o;		
		String file = PATH_DATA_VIA + v.getId() + FILE_EXTENSION;
		
		try {
			outputStream = new FileOutputStream(file);
			out = new ObjectOutputStream(outputStream);
			
			//guardar objeto
			out.writeObject(v);
			
			resul = true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
			try {
				if (out != null ) out.close();
				if (outputStream != null ) outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}	
		
		return resul;
	}

	@Override
	public boolean delete(int id) {
		
		File fBorrar = null;
		
		String file = PATH_DATA_VIA + id + FILE_EXTENSION;
		
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