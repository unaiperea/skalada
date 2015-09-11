package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.TipoEscalada;

public class ModeloSector implements Persistable {

	private static final String TABLA           = "sector";
	private static final String COL_ID          = "id";
	private static final String COL_NOMBRE      = "nombre";
	private static final String COL_IDZONA = "id_Zona";
	
	private static final String SQL_INSERT = "INSERT INTO `" + TABLA + "` (`" + COL_NOMBRE + "`, `" + COL_IDZONA + "`) VALUES (?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA + "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETBYID = "SELECT * FROM `" + TABLA + "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETALL = "SELECT * FROM " + TABLA;
	private static final String SQL_UPDATE = "UPDATE `" + TABLA + "` SET `" + COL_NOMBRE + "`= ? , `" + COL_IDZONA + "`= ? WHERE `" + COL_ID + "`= ? ;";
	
	
	@Override
	public int save(Object o) {
		int resul = -1;
		TipoEscalada tp = null;	
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		
		if(o != null){
			try{
				tp = (TipoEscalada)o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, tp.getNombre());
				pst.setString(2, tp.getDescripcion());		
		    	if ( pst.executeUpdate() != 1 ){
					throw new Exception("No se ha realizado la insercion");
				} else {		
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						tp.setId(resul);
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
		}//End if; Objeto null
		return resul;
	}

	@Override
	public Object getById(int id) {
		Object resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;		
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETBYID);
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
	    		resul.add(mapeo(rs)); //a—ADE oBJETOS AL aRRAYlIST
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
		TipoEscalada tp = null;
		PreparedStatement pst = null;
		
		if (o != null){
			try{
				tp = (TipoEscalada) o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_UPDATE);
				pst.setString(1, tp.getNombre());
				pst.setString(2, tp.getDescripcion());
				pst.setInt(3, tp.getId());
				
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
		}//End if; Objeto null
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
	 * Mapea un ResultSet a Grado
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private TipoEscalada mapeo (ResultSet rs) throws SQLException{
		TipoEscalada resul = null;    
		
		resul = new TipoEscalada( rs.getString(COL_NOMBRE) );
		resul.setId( rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));
		
		return resul;
	}
	
	
	

}