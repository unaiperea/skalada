package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.basedatosProfe.bean.Persona;
import com.ipartek.formacion.basedatosProfe.modelo.DataBaseHelper;

public class ModeloGrado implements Persistable {

	private static final String TABLA = "grado";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	
	private static final String SQL_INSERT = "INSERT INTO `" + TABLA + "` (`"+COL_NOMBRE+ ; //******copiar la sentencia*******************
	private static final String SQL_DELETE = "DELETE FROM `grado` WHERE  `id`= ?;"; //******copiar la sentencia*******************
	
	@Override
	public int save(Object o) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		
		try{
			Grado grado = (Grado)o;
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS); //Ejecuta la sql y devuelve la key generada, useasé un 1

			pst.setString(1, p.getNombre());
			pst.setFloat(2, p.getNota());
			pst.setString(3, p.getTelefono());
			
			if (pst.executeUpdate() == 1){//Si ha creado un nuevo registro
				//Para devolver la id
				rsKeys = pst.getGeneratedKeys(); //Guardamos en un ResulSet la tabla que el método getGeneratedKeys() ha generado del nuevo registro
				if (rsKeys.next()){ //si hay registros
					resul = rsKeys.getInt(1); //coge el valor de la primera columna del nuevo registro generado
				}else{
					throw new SQLException("No se ha podido generar ID");
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (rsKeys != null){rsKeys.close();}
				if (pst != null){pst.close();}
				DataBaseHelper.closeConnection();
			}catch(Exception e){e.printStackTrace();}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		
		boolean resul = false;
		PreparedStatement pst = null;
		
		try{
	        Connection conexion = DataBaseHelper.getConnection();
	        //String sql = "DELETE FROM `test` WHERE  `id`=" + id;
	        
	        pst = conexion.prepareStatement(SQL_DELETE); //No permite la inyección de SQL y es más rápido
	        pst.setInt(1,  id);
	        
	        if (pst.executeUpdate() == 1){
	        	resul = true;
	        }

		}catch  (Exception e){ //Si falla que vuelva al form. S�lo se pueden enviar Atributos, par�metros no
			e.printStackTrace();
		}finally{
			try{
				if (pst != null){pst.close();}
				DataBaseHelper.closeConnection();
			}catch(Exception e){e.printStackTrace();}
		}
		
		return resul;
	}

}
