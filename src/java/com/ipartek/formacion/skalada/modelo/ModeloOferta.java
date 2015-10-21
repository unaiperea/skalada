package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.ipartek.formacion.skalada.bean.Oferta;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.UsuarioInscrito;
import com.ipartek.formacion.skalada.bean.Zona;

public class ModeloOferta implements Persistable<Oferta> {

	private static final String TABLA = "oferta";
	private static final String COL_ID = "id";
	private static final String COL_TITULO = "titulo";
	private static final String COL_DESCRIPCION = "descripcion";
	private static final String COL_PRECIO = "precio";
	private static final String COL_FECHA_ALTA = "fecha_alta";
	private static final String COL_FECHA_BAJA = "fecha_baja";

	
	
	private static final String SQL_INSERT = "INSERT INTO `oferta` (`titulo`, `descripcion`, `precio`,`fecha_alta`,`fecha_baja`,`visible`,`zona_id`) VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "DELETE FROM `oferta` WHERE `id`= ?;";
	private static final String SQL_GETALL = "select o.id, o.titulo, o.descripcion, o.precio, o.fecha_alta, o.fecha_baja, o.visible, z.id as zona_id, z.nombre as zona_nombre from oferta as o inner join zona as z on z.id = o.zona_id";
	private static final String SQL_GETONE = SQL_GETALL + " where o.id=?";
	private static final String SQL_UPDATE ="UPDATE `oferta` SET `titulo`=?, `descripcion`=?, `precio`=?, `fecha_alta`=?, `fecha_baja`=?, `visible`=?, `zona_id`=? WHERE `id`=?";
	
	private static final String SQL_INSCRIBIR = "INSERT INTO `ofertausuario` (`oferta_id`, `usuario_id`, `fecha_inscripcion`) VALUES (?,?,?)";
	private static final String SQL_DESINSCRIBIR = "DELETE FROM `ofertausuario` WHERE  `oferta_id`=? AND `usuario_id`=?";

	private static final String SQL_GETALL_LISTAUSUARIO = "select o.oferta_id, o.usuario_id, o.fecha_inscripcion, u.email as usuario_email, u.nombre as usuario_nombre, u.password as usuario_password, u.id_rol, u.validado, u.token, r.nombre as rol_nombre from ofertausuario as o inner join usuario as u on o.usuario_id = u.id inner join rol as r on u.id_rol = r.id where o.oferta_id = ?";
	
	
	@Override()
	public int save(Oferta oferta) {
		int resul = -1;

		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (oferta != null) {
			try {

				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, oferta.getTitulo());
				pst.setString(2, oferta.getDescripcion());
				pst.setFloat(3, oferta.getPrecio());
				pst.setTimestamp(4, oferta.getFecha_alta());
				pst.setTimestamp(5, oferta.getFecha_baja());
				pst.setInt(6, oferta.getVisible());
				pst.setInt(7, oferta.getZona().getId());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						oferta.setId(resul);
					} else {
						throw new Exception("No se ha podido generar ID");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rsKeys != null) {
						rsKeys.close();
					}
					if (pst != null) {
						pst.close();
					}
					DataBaseHelper.closeConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	@Override()
	public Oferta getById(int id) {
		Oferta resul = null;
		ArrayList<UsuarioInscrito> usuariosInscritos=new  ArrayList<UsuarioInscrito>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETONE);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				usuariosInscritos = buscarSuscritos(id);
				resul = this.mapeo(rs,usuariosInscritos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	@Override()
	public ArrayList<Oferta> getAll(Usuario usuario) {
		ArrayList<Oferta> resul = new ArrayList<Oferta>();
		ArrayList<UsuarioInscrito> usuariosInscritos=new  ArrayList<UsuarioInscrito>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				usuariosInscritos = buscarSuscritos(rs.getInt("id"));
				resul.add(this.mapeo(rs,usuariosInscritos));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	@Override()
	public boolean update(Oferta oferta) {
		boolean resul = false;

		PreparedStatement pst = null;
		if (oferta != null) {
			try {

				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, oferta.getTitulo());
				pst.setString(2, oferta.getDescripcion());
				pst.setFloat(3, oferta.getPrecio());
				pst.setTimestamp(4, oferta.getFecha_alta());
				pst.setTimestamp(5, oferta.getFecha_baja());
				pst.setInt(6, oferta.getVisible());
				pst.setInt(7, oferta.getZona().getId());				
				pst.setInt(8, oferta.getId());
				if (pst.executeUpdate() == 1) {
					resul = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pst != null) {
						pst.close();
					}
					DataBaseHelper.closeConnection();
				} catch (Exception e) {
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
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_DELETE);
			pst.setInt(1, id);
			if (pst.executeUpdate() == 1) {
				resul = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection();
				return resul;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	/**
	 * Mapea un ResultSet a Oferta
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Oferta mapeo(ResultSet rs) throws SQLException {
		Oferta resul = null;

		resul = new Oferta();
		resul.setTitulo(rs.getString(COL_TITULO));
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));
		resul.setPrecio(rs.getFloat(COL_PRECIO));
		resul.setFecha_alta(rs.getTimestamp(COL_FECHA_ALTA));
		resul.setFecha_baja(rs.getTimestamp(COL_FECHA_BAJA));
		resul.setVisible(rs.getInt("visible"));
		
		//mapeo de la zona
		Zona z = new Zona(rs.getString("zona_nombre"));
		z.setId(rs.getInt("zona_id"));
		resul.setZona(z);
		
		return resul;
		
		
	}
	
	private Oferta mapeo(ResultSet rs, ArrayList<UsuarioInscrito> listaSuscritos ) throws SQLException {
		Oferta resul = null;

		resul = new Oferta();
		resul.setTitulo(rs.getString(COL_TITULO));
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));
		resul.setPrecio(rs.getFloat(COL_PRECIO));
		resul.setFecha_alta(rs.getTimestamp(COL_FECHA_ALTA));
		resul.setFecha_baja(rs.getTimestamp(COL_FECHA_BAJA));
		resul.setVisible(rs.getInt("visible"));
		
		//mapeo de la zona
		Zona z = new Zona(rs.getString("zona_nombre"));
		z.setId(rs.getInt("zona_id"));
		resul.setZona(z);
		
		resul.setUsuariosInscritos(listaSuscritos);
		
		return resul;
		
		
	}

	private ArrayList<UsuarioInscrito> buscarSuscritos(int idOferta){
		ArrayList<UsuarioInscrito> resul = new ArrayList<UsuarioInscrito>();
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		
		
		try {
			Connection con = DataBaseHelper.getConnection();
			pst1 = con.prepareStatement(SQL_GETALL_LISTAUSUARIO);
			pst1.setInt(1, idOferta);
			
			rs1 = pst1.executeQuery();
			System.out.println("Oferta: "+idOferta);
			while (rs1.next()){				
				Rol rol = new Rol(rs1.getString("rol_nombre"));
				rol.setId(rs1.getInt("u.id_rol"));
		
				UsuarioInscrito ui = new UsuarioInscrito(
						rs1.getString("usuario_nombre"),
						rs1.getString("usuario_email"),
						rs1.getString("usuario_password"),
						rol);
				ui.setId(rs1.getInt("o.usuario_id"));
				resul.add(ui);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
				}
				if (pst1 != null) {
					pst1.close();
				}
				DataBaseHelper.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}
	
	/**
	 * Elimina al usuario idUsuario de la oferta idOferta
	 * @param idOferta
	 * @param idUsuario: id del usuario inscrito en la oferta
	 * @return true si elimina, false en caso contrario
	 */
	public boolean desInscribir(int idOferta, int idUsuario){
		boolean resul = false;
		PreparedStatement pst = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_DESINSCRIBIR);
			pst.setInt(1, idOferta);
			pst.setInt(2, idUsuario);
			if (pst.executeUpdate() == 1) {
				resul = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection();
				return resul;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return resul;
	}
		
	/**
	 * Inscribe al usuario idUsuario de la oferta idOferta
	 * @param idOferta
	 * @param idUsuario: id del usuario inscrito en la oferta
	 * @return true si elimina, false en caso contrario
	 */
	public boolean inscribir(int idOferta, int idUsuario){
		boolean resul = false;
		PreparedStatement pst = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_INSCRIBIR);
			pst.setInt(1, idOferta);
			pst.setInt(2, idUsuario);
			Date date = new Date();
			pst.setTimestamp(3, new Timestamp(date.getTime()));
			if (pst.executeUpdate() == 1) {
				resul = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection();
				return resul;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		return resul;
	}
}