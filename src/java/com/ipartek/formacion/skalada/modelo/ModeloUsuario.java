package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloUsuario implements Persistable{

	private static final String SQL_INSERT = "INSERT INTO `usuario` (`email`, `nombre`, `password`, `id_rol`) VALUES (?, ?, ?, ?);";
	private static final String SQL_DELETE = "DELETE FROM `usuario` WHERE `id`= ? ;";
	private static final String SQL_GETALL = "SELECT u.`id`, u.`email`, u.`nombre`, u.`password`, u.`validado`, u.`id_rol`, r.`nombre` AS nombre_rol "
											+ "FROM `usuario` AS u "
											+ "INNER JOIN `rol` as r ON (u.`id_rol` = r.`id`)";
	private static final String SQL_GETONE  = SQL_GETALL + " WHERE u.`id`= ?;";
	private static final String SQL_UPDATE = "UPDATE `usuario` SET `email`= ?, `nombre`= ?, `password`= ?, `validado`= ?, `id_rol`= ? WHERE `id`= ?;";
	
	private static final String SQL_CHECK_USER  = "SELECT * FROM `usuario` WHERE `nombre` = ? OR `email` = ?";
	
	
	@Override
	public int save(Object o) {
		int resul = -1;
		Usuario usuario = null;	
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if(o != null){
			try{
				usuario = (Usuario)o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, usuario.getEmail());
				pst.setString(2, usuario.getNombre());
				pst.setString(3, usuario.getPassword());
				pst.setInt(4, usuario.getRol().getId());
		    	if ( pst.executeUpdate() != 1 ){
					throw new Exception("No se ha realizado la insercion");
				} else {		
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						usuario.setId(resul);
					} else {
						throw new Exception("No se ha podido generar ID");
					}
				}	    		    		
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					if(rsKeys != null){
						rsKeys.close();
					}
					if(pst != null){
						pst.close();
					}
					DataBaseHelper.closeConnection();			
				}catch(Exception e){
					e.printStackTrace();
				}			
			}	
		}
		return resul;
	}

	@Override
	public Object getById(int id) {
		Object resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;		
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETONE);
			pst.setInt(1, id);
	    	rs = pst.executeQuery();	      	   	
	    	while(rs.next()){
	    		resul = mapeo(rs);
	    	}	
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(pst != null){
					pst.close();
				}
				DataBaseHelper.closeConnection();			
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		return resul;		
	}

	@Override
	public ArrayList<Object> getAll() {
		ArrayList<Object> resul = new ArrayList<Object>();
		PreparedStatement pst = null;
		ResultSet rs = null;		
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL);
	    	rs = pst.executeQuery();   	   	
	    	while(rs.next()){
	    		resul.add(mapeo(rs));
	    	}	
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(pst != null){
					pst.close();
				}
				DataBaseHelper.closeConnection();			
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		return resul;				
	}

	@Override
	public boolean update(Object o) {
		boolean resul = false;
		Usuario usuario = null;
		PreparedStatement pst = null;
		if (o != null){
			try{
				usuario = (Usuario)o;
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, usuario.getEmail());
				pst.setString(2, usuario.getNombre());
				pst.setString(3, usuario.getPassword());
				pst.setInt(4, usuario.getValidado());
				pst.setInt(5, usuario.getRol().getId());
				pst.setInt(6, usuario.getId());				
		    	if ( pst.executeUpdate() == 1 ){
		    		resul = true;	    		
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					if(pst != null){
						pst.close();
					}				
					DataBaseHelper.closeConnection();									
				}catch(Exception e){
					e.printStackTrace();
				}			
			}	
		}
		return resul;
	}

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		PreparedStatement pst = null;
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_DELETE);
			pst.setInt(1, id);			
			if ( pst.executeUpdate() == 1 ){
				resul = true;
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(pst != null){
					pst.close();
				}
				DataBaseHelper.closeConnection();	
				return resul;
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		return resul;
	}
	
	/**
	 * Mapea un ResultSet a Usuario
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private Usuario mapeo (ResultSet rs) throws SQLException{
		Usuario resul = null;    
		
		Rol rol = new Rol(rs.getString("nombre_rol"));
		rol.setId(rs.getShort("id_rol"));
		
		resul = new Usuario( rs.getString("nombre"), rs.getString("email"), rs.getString("password"), rol);
		resul.setId( rs.getInt("id"));
		resul.setValidado(rs.getInt("validado"));
		
		return resul;
	}
	
	
	/**
	 * Comprueba si existe ese nombre o email en la tabla usuario
	 * @param nombre {@code String} nombre del {@code Usuario}
	 * @param email {@code String} email del {@code Usuario}
	 * @return {@code boolean} true si existe en la tabla, 
	 * 						   false en caso de que este libre
	 */
	public boolean checkUser(String nombre, String email){
		boolean resul = true;
		PreparedStatement pst = null;
		ResultSet rs = null;		
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_CHECK_USER);
			pst.setString(1, nombre);
			pst.setString(2, email);
	    	rs = pst.executeQuery(); 
	    	if (!rs.next()){
	    		resul = false;	//Nombre y email libres
	    	}	    	
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(pst != null){
					pst.close();
				}
				DataBaseHelper.closeConnection();			
			}catch(Exception e){
				e.printStackTrace();
			}			
		}	
		return resul;
	}
	

}

