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
	private static final String SQL_GETALL = "select v.`id`, v.`nombre`, v.`longitud`, v.`descripcion`, v.`id_grado`, g.`nombre` as `nombre_grado`, v.`id_sector`, s.`nombre` as `nombre_sector`, v.`id_tipo_escalada`, tp.`nombre` as `nombre_tipo_escalada`, s.`id_zona`, z.`nombre` as `nombre_zona` from `via` as v INNER JOIN `grado` as g ON v.`id_grado` = g.`id` INNER JOIN  `sector` as s ON v.`id_sector` = s.`id` INNER JOIN  `tipo_escalada` as tp ON v.`id_tipo_escalada` = tp.`id` INNER JOIN  `zona` as z ON s.`id_zona` = z.`id`";

	/*select 
	v.`id`, v.`nombre`, v.`longitud`, v.`descripcion`,
	v.`id_grado`, g.`nombre` as `nombre_grado`,
	v.`id_sector`, s.`nombre` as `nombre_sector`,
 	v.`id_tipo_escalada`, tp.`nombre` as `nombre_tipo_escalada`,
 	s.`id_zona`, z.`nombre` as `nombre_zona`

from
	`via` as v INNER JOIN `grado` as g ON v.`id_grado` = g.`id`
	INNER JOIN  `sector` as s ON v.`id_sector` = s.`id`
	INNER JOIN  `tipo_escalada` as tp ON v.`id_tipo_escalada` = tp.`id`
	INNER JOIN  `zona` as z ON s.`id_zona` = z.`id`*/
	
	private static final String SQL_GETONE = SQL_GETALL + " WHERE v.`id` = ? ";
	private static final String SQL_UPDATE = "UPDATE `via` SET `nombre`= ?, `longitud`= ?, `descripcion`= ?, `id_grado`= ?, `id_tipo_escalada`= ?, `id_sector`= ? WHERE  `id`= ?;";
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
				//UPDATE `via` SET `nombre`= ?, `longitud`= ?, `descripcion`= ?, `id_grado`= ?, `id_tipo_escalada`= ?, `id_sector`= ? WHERE  `id`= ?;
				pst.setString(1, v.getNombre());
				pst.setInt(2, v.getLongitud());
				pst.setString(3, v.getDescripcion());
				pst.setInt(4, v.getGrado().getId());
				pst.setInt(5, v.getTipoEscalada().getId());
				pst.setInt(6, v.getSector().getId());
				//pst.setInt(7, v.getSector().getZona().getId()); //Zona no está en la tabla. Habrá que hacer update directamente en la zona
				pst.setInt(8, v.getId());
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
		
		//Vamos creando objetos según recorramos la bbdd y rellenándolos.
		//Así lo tenemos en memoria y accederemos en cualquier momento
		
		//Tipo Escalada
		TipoEscalada tipoEscalada = new TipoEscalada(rs.getString("nombre_tipo_escalada")); //Creamos el objeto tipoEscalada con su constructor dándole el campo del resulSet
		tipoEscalada.setId(rs.getInt("id_tipo_escalada")); //Seteamos el id del tipo_Escalada
		
		//Zona
		Zona zona = new Zona(rs.getString("nombre_zona"));
		zona.setId(rs.getInt("id_zona"));
		
		//Sector
		Sector sector = new Sector(rs.getString("nombre_sector"), zona); //Creamos el objeto Sector con el nombre de la consulta y el objeto zona creado antes
		sector.setId(rs.getInt("id_sector")); //Seteamos el id del sector
		
		//Grado
		Grado grado = new Grado(rs.getString("nombre_grado"));
		grado.setId(rs.getInt("id_grado"));
		
		//nombre
		String nombre = rs.getString("nombre");
		
		//longitud
		int longitud = rs.getInt("longitud");
		
		//Descripcion
		String descripcion = rs.getString("descripcion");
		
		resul = new Via(nombre, longitud, grado, tipoEscalada, sector);
		resul.setId(rs.getInt("id"));
		resul.setDescripcion(descripcion);
		
		//inicializar a null
		tipoEscalada = null;
		zona = null;
		sector = null;
		grado = null;
		nombre = null;
		descripcion = null;
		
		
		
		
//		
//		Zona zona = new Zona( rs.getString(COL_ZONA_NOMBRE) );
//		zona.setId(rs.getInt(COL_ZONA_ID));
//		
//		resul = new Via( rs.getString(COL_NOMBRE), zona );
//		resul.setId( rs.getInt(COL_ID));
		
		return resul;
	}
	
	
	

}
