package com.ipartek.formacion.skalada.modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Via;

/**
 * Clase encargada de persistir los objetos de tipo {@code Via} 
 * en ficheros serializando y des-serializando
 * @author ur00
 *
 */
public class ModeloVia implements Persistable {
	
	private static final String TABLA_VIA = "via";
	private static final String TABLA_GRADO = "grado";
	private static final String TABLA_TIPO_ESCALADA = "tipo_escalada";
	private static final String TABLA_ZONA = "zona";
	private static final String TABLA_SECTOR = "sector";
	
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_LONGITUD = "longitud";	
	private static final String COL_GRADO_ID = "id_grado";
	private static final String COL_GRADO_NOMBRE = "nombre_grado";	
	private static final String COL_TIPO_ESCALADA_ID = "id_tipo_escalada";
	private static final String COL_TIPO_ESCALADA_NOMBRE = "nombre_tipo_escalada";	
	private static final String COL_ZONA_ID = "id_zona";
	private static final String COL_ZONA_NOMBRE = "nombre_zona";	
	private static final String COL_SECTOR_ID = "id_sector";
	private static final String COL_SECTOR_NOMBRE = "nombre_sector";
	
	/*
	 * select v.id, v.nombre, v.longitud, v.descripcion, v.id_grado, g.nombre as nombre_grado, v.id_tipo_escalada, te.nombre as nombre_tipo_escalada,v.id_sector, s.nombre as nombre_sector, s.id_zona, z.nombre as nombre_zona
	 * from via as v 
	 * INNER JOIN grado g ON (v.id_grado = g.id) 
	 * INNER JOIN tipo_escalada te ON (v.id_tipo_escalada = te.id)
	 * INNER JOIN sector s ON (v.id_sector = s.id)
	 * INNER JOIN zona z ON (s.id_zona = z.id)
                                       
	 */
	
	
	
	private static final String SQL_INSERT = "";
	private static final String SQL_GETONE = "";
	private static final String SQL_GETALL = "";
	private static final String SQL_UPDATE = "";
	private static final String SQL_DELETE = "";
	

	@Override
	public int save(Object o) {
		int resul = -1;
		Via v = null;	
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if(o != null){
			try{
				v = (Via)o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, v.getNombre());	
		    	if ( pst.executeUpdate() != 1 ){
					throw new Exception("No se ha realizado la insercion");
				} else {		
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						v.setId(resul);
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
		Via v = null;
		PreparedStatement pst = null;
		if (o != null){
			try{
				v = (Via)o;
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, v.getNombre());
				pst.setInt(2, v.getId());				
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
	 * Mapea un ResultSet a Via
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private Via mapeo (ResultSet rs) throws SQLException{
		Via resul = null;    
//		
//		Zona zona = new Zona( rs.getString(COL_ZONA_NOMBRE) );
//		zona.setId(rs.getInt(COL_ZONA_ID));
//		
//		resul = new Via( rs.getString(COL_NOMBRE), zona );
//		resul.setId( rs.getInt(COL_ID));
		
		return resul;
	}
	
	
	

}
