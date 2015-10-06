package com.ipartek.formacion.skalada.modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;

/**
 * Clase encargada de persistir los objetos de tipo {@code Via} 
 * en ficheros serializando y des-serializando
 * @author ur00
 *
 */
public class ModeloVia implements Persistable {
	
	
	private static final String SQL_INSERT = "";
	
	private static final String SQL_GETALL = "SELECT v.`id`, v.`nombre`,`longitud`, v.`descripcion`, `id_grado`, g.`nombre` as `nombre_grado` , `id_sector`, s.`nombre` as `nombre_sector`, `id_tipo_escalada`, t.`nombre` as `nombre_tipo_escalada`, s.`id_zona`, z.`nombre` as `nombre_zona` FROM `via` as v INNER JOIN `grado` as g ON `id_grado` = g.`id` INNER JOIN `sector` as s ON `id_sector` = s.`id` INNER JOIN `tipo_escalada` as t ON `id_tipo_escalada` = t.`id` INNER JOIN `zona` as z ON s.`id_zona` = z.`id`";
	/*	  
	  SELECT 
		  v.`id`, v.`nombre`,`longitud`, v.`descripcion`,
		  `id_grado`, g.`nombre` as `nombre_grado` ,
		  `id_sector`, s.`nombre` as `nombre_sector`,
		  `id_tipo_escalada`, t.`nombre` as `nombre_tipo_escalada`,
		  s.`id_zona`, z.`nombre` as `nombre_zona`
  
		FROM

		`via` as v INNER JOIN `grado` as g ON `id_grado` = g.`id`
		 INNER JOIN `sector` as s ON `id_sector` = s.`id`
		 INNER JOIN `tipo_escalada` as t ON `id_tipo_escalada` = t.`id`
		 INNER JOIN `zona` as z ON s.`id_zona` = z.`id`
	  
	 */
	private static final String SQL_GETONE = SQL_GETALL + " where v.`id` = ? ";
	
	private static final String SQL_UPDATE = "";
	private static final String SQL_DELETE = "";
	

	@Override()
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

	@Override()
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
	    		resul = this.mapeo(rs);
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

	@Override()
	public ArrayList<Object> getAll() {
		ArrayList<Object> resul = new ArrayList<Object>();
		PreparedStatement pst = null;
		ResultSet rs = null;		
		try{
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL);
	    	rs = pst.executeQuery();   	   	
	    	while(rs.next()){
	    		resul.add(this.mapeo(rs));
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

	@Override()
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

	@Override()
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
		
		//Tipo Escalada
		TipoEscalada tipoEscalada = new TipoEscalada( rs.getString("nombre_tipo_escalada") );
		tipoEscalada.setId(rs.getInt("id_tipo_escalada"));

		//Zona
		Zona zona = new Zona( rs.getString("nombre_zona")  );
		zona.setId(rs.getInt("id_zona"));
		
		//Sector
		Sector sector = new Sector( rs.getString("nombre_sector") , zona);
		sector.setId(rs.getInt("id_sector"));
		
		//Grado
		Grado grado = new Grado( rs.getString("nombre_grado") );
		grado.setId(rs.getInt("id_grado"));
		
		//nombre
		String nombre = rs.getString("nombre");
		
		//longitud
		int longitud  = rs.getInt("longitud");
		
		//descripcion
		String descripcion = rs.getString("descripcion");
		
		//creamos la Via
		resul = new Via(nombre, longitud, grado, tipoEscalada, sector);
		resul.setId( rs.getInt("id") );
		resul.setDescripcion(descripcion);

		//inicializar a null		
		tipoEscalada = null;
		zona = null;
		sector = null;
		grado = null;
		nombre = null;
		descripcion = null;		
		
		return resul;
	}
	
	
	

}
