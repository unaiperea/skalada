package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La usuarán los DAOs 
 * @author Curso
 *
 */
public class DataBaseHelper {
	
	//parámetros configuración base datos
	static final public String DRIVER    = "com.mysql.jdbc.Driver";
	static final public String SERVER    = "localhost";
	static final public String DATA_BASE = "eskalada";
	static final public String USER      = "root";
	static final public String PASS      = "";
	
	//Conexión
	private static Connection con;
	
	/**
	 * Retornar la conexión abierta
	 * implementa un patrón singleton (Sólo existe un objeto, en este caso de tipo conexión)
	 * @return {@code Connection} conexión
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		
		if ( con == null ){ //si no existe que la abra
			Class.forName(DRIVER);
			//TODO cambiar parámetros
			con = DriverManager.getConnection ("jdbc:mysql://localhost/eskalada","root", "");
		}
		return con;
	}
	
	/**
	 * Cierra la conexión, tener cuidado porque al cerrar una conexión con el método .close no la pone a null
	 * @return
	 */
	public static boolean closeConnection(){
		boolean resul = false;
		try{
			con.close();
			con=null;
			resul = true;
		}catch ( SQLException e){
			con=null;
			e.printStackTrace();
			resul = false;
		}
		return resul;
	}
	
	/**
	 * Crea la bbdd ejecutando un Script
	 */
	void crear(){
		
	}
	
	/**
	 * Elimina bbdd con sentencia drop
	 */
	void eliminar(){
		
	}
	
	/**
	 * Crea las tablas:
	 * <ol>
	 * 	<li>test</li>
	 * </ol>
	 */
	void crearTablas(){
		
	}
	
	/**
	 * Insertar en las tablas un juego de datos para testear
	 */
	void insertarDatosPrueba(){
		
	}
	
	
	
}