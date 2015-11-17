package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;

public class ModeloSector implements Persistable<Sector> {

	private Connection con = null;

	private static final Logger LOG = Logger.getLogger(ModeloSector.class);

	private static final String TABLA_SECTOR = "sector";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_ZONA_ID = "id_zona";
	private static final String COL_IMAGEN = "imagen";

	private static final String SQL_INSERT = "INSERT INTO `"
			+ TABLA_SECTOR
			+ "` (`"
			+ COL_NOMBRE
			+ "`, `"
			+ COL_ZONA_ID
			+ "` , `"
			+ COL_IMAGEN
			+ "`, `validado`, `id_usuario`, `longitud`, `latitud`) VALUES (?,?,?,?,?,?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA_SECTOR
			+ "` WHERE `" + COL_ID + "`= ?;";

	private static final String SQL_GETALL = "SELECT s.nombre, s.id, s.imagen, s.validado, s.longitud, s.latitud, z.nombre as zona_nombre, z.id as zona_id, z.latitud as zona_latitud, z.longitud as zona_longitud, r.nombre as rol_nombre, r.id as rol_id, u.nombre as usuario_nombre, u.password as usuario_pass, u.email as usuario_email, u.id as usuario_id FROM sector as s INNER JOIN zona as z ON s.id_zona = z.id INNER JOIN usuario as u ON s.id_usuario = u.id INNER JOIN rol as r ON u.id_rol = r.id";


	private static final String SQL_GETALL_BY_USER = SQL_GETALL
			+ " AND s.id_usuario = ? ";
	private static final String SQL_GETONE = SQL_GETALL + " WHERE s.id = ?";

	private static final String SQL_UPDATE = "UPDATE `"
			+ TABLA_SECTOR
			+ "` SET `"
			+ COL_NOMBRE
			+ "`= ? , `"
			+ COL_ZONA_ID
			+ "`= ? , `"
			+ COL_IMAGEN
			+ "`= ? , `validado`= ? , `id_usuario`= ? , `longitud`= ? , `latitud`= ? WHERE `"
			+ COL_ID + "`= ? ";

	private static final String SQL_UPDATE_AUTORIZACION = SQL_UPDATE
			+ " and id_usuario = ?";

	private static final String SQL_GETALL_BY_ZONA = SQL_GETALL + " where s.id_zona = ?";

	private static final String SQL_SECTORES_PUBLICADOS = "SELECT COUNT(`id`) as `sectores` FROM `SECTOR`;";

	private static final String SQL_GET_NO_VALIDADOS = SQL_GETALL
			+ " where s.validado=0 and s.id_usuario like ?";
	private static final String SQL_BUSQUEDA = SQL_GETALL
			+ " WHERE s.nombre like ?";

	//@Override()
	public int save(Sector s) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (s != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				pst = this.con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, s.getNombre());
				pst.setInt(2, s.getZona().getId());
				pst.setString(3, s.getImagen());
				if (s.isValidado()) {
					pst.setInt(4, Constantes.VALIDADO);
				} else {
					pst.setInt(4, 0);
				}
				pst.setInt(5, s.getUsuario().getId());
				pst.setDouble(6, s.getLongitud());
				pst.setDouble(7, s.getLatitud());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						s.setId(resul);
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
					DataBaseHelper.closeConnection(this.con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	//@Override()
	public Sector getById(int id) {
		Sector resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_GETONE);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				resul = this.mapeo(rs);
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
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	//@Override()
	public ArrayList<Sector> getAll(Usuario usuario) {
		ArrayList<Sector> resul = new ArrayList<Sector>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			if (usuario != null) {
				if (usuario.isAdmin()) {
					pst = this.con.prepareStatement(SQL_GETALL);
				} else {
					pst = this.con.prepareStatement(SQL_GETALL_BY_USER);
					pst.setInt(1, usuario.getId());
				}
			} else {
				throw new Exception("Peticion sin autorizacion: Usuario nulo");
			}

			rs = pst.executeQuery();
			while (rs.next()) {
				resul.add(this.mapeo(rs));
			}
		} catch (Exception e) {
			LOG.error("Peticion sin autorizacion: Usuario nulo");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				LOG.error("Excepcion cerrando recursos");
				e.printStackTrace();
			}
		}
		return resul;
	}

	//@Override()
	public boolean update(Sector s) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (s != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = this.con.prepareStatement(sql);
				pst.setString(1, s.getNombre());
				pst.setInt(2, s.getZona().getId());
				pst.setString(3, s.getImagen());
				if (s.isValidado()) {
					pst.setInt(4, Constantes.VALIDADO);
				} else {
					pst.setInt(4, 0);
				}
				pst.setInt(5, s.getUsuario().getId());
				pst.setDouble(6, s.getLongitud());
				pst.setDouble(7, s.getLatitud());
				pst.setInt(8, s.getId());
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
					DataBaseHelper.closeConnection(this.con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	/**
	 * Modificar Sector comprobando Autorizacion del Usuario
	 *
	 * @param s
	 *            {@code Sector} a modificar
	 * @param usuario
	 *            {@code Usuario} logeado en Session
	 * @return true si modifica, false en caso contrario
	 */
	public boolean update(Sector s, Usuario usuario) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (s != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				if (!usuario.isAdmin()) {
					sql = SQL_UPDATE_AUTORIZACION;
				}
				pst = this.con.prepareStatement(sql);
				pst.setString(1, s.getNombre());
				pst.setInt(2, s.getZona().getId());
				pst.setString(3, s.getImagen());
				if (s.isValidado()) {
					pst.setInt(4, Constantes.VALIDADO);
				} else {
					pst.setInt(4, 0);
				}
				pst.setInt(5, s.getUsuario().getId());
				pst.setDouble(6, s.getLongitud());
				pst.setDouble(7, s.getLatitud());
				pst.setInt(8, s.getId());
				// comprobar que le pertenezca el Sector al usuario
				if (!usuario.isAdmin()) {
					pst.setInt(9, usuario.getId());
				}
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
					DataBaseHelper.closeConnection(this.con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	//@Override()
	public boolean delete(int id) {
		boolean resul = false;
		PreparedStatement pst = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_DELETE);
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
				DataBaseHelper.closeConnection(this.con);
				return resul;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	/**
	 * Mapea un ResultSet a Sector
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Sector mapeo(ResultSet rs) throws SQLException {
		Sector resul = null;

		// Zona
		Zona zona = new Zona(rs.getString("zona_nombre"));
		zona.setId(rs.getInt("zona_id"));
		zona.setLatitud(rs.getDouble("zona_latitud"));
		zona.setLongitud(rs.getDouble("zona_longitud"));

		// Usuario
		Rol rol = new Rol(rs.getString("rol_nombre"));
		rol.setId(rs.getInt("rol_id"));

		Usuario user = new Usuario(rs.getString("usuario_nombre"),
				rs.getString("usuario_email"), rs.getString("usuario_pass"),
				rol);
		user.setId(rs.getInt("usuario_id"));

		// Sector
		resul = new Sector(rs.getString("nombre"), zona);
		resul.setId(rs.getInt("id"));
		resul.setImagen(rs.getString("imagen"));
		resul.setLongitud(rs.getDouble("longitud"));
		resul.setLatitud(rs.getDouble("latitud"));

		if (rs.getInt("validado") == Constantes.VALIDADO) {
			resul.setValidado(true);
		}
		resul.setUsuario(user);
		resul.setLatitud(rs.getDouble("latitud"));
		resul.setLongitud(rs.getDouble("longitud"));

		return resul;
	}

	/**
	 * Obtiene todos los sectores de una {@code Zona}, <b> Cuidado: getZona()
	 * retorna <code>null</code>, se supone que ya la conocemos </b>
	 *
	 * @param id_zona
	 *            {@code int} identificador de la {@code Zona}
	 * @return ArrayList<Sector> coleccion de sectores de la {@code Zona}, si no
	 *         existe ninguno coleccion inicializada con new()
	 *
	 */
	public ArrayList<Sector> getAllByZona(int id_zona) {
		ArrayList<Sector> resul = new ArrayList<Sector>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_GETALL_BY_ZONA);
			pst.setInt(1, id_zona);
			rs = pst.executeQuery();

			Sector sector = null;
			while (rs.next()) {
				resul.add(this.mapeo(rs));
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
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	public static int sectoresPublicados() {
		int resul = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_SECTORES_PUBLICADOS);
			rs = pst.executeQuery();
			while (rs.next()) {
				resul = rs.getInt("sectores");
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
				DataBaseHelper.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	public ArrayList<Sector> sectoresNoValidados(Usuario usuario) {
		ArrayList<Sector> resul = new ArrayList<Sector>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_GET_NO_VALIDADOS);
			if (usuario.isAdmin()) {
				pst.setString(1, "%");
			} else {
				pst.setInt(1, usuario.getId());
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				resul.add(this.mapeo(rs));
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
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}


	public ArrayList<Sector> busqueda(String texto) {
		ArrayList<Sector> resul = new ArrayList<Sector>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_BUSQUEDA);
			pst.setString(1, "%" + texto + "%");
			rs = pst.executeQuery();
			while (rs.next()) {
				resul.add(this.mapeo(rs));
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
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resul;
	}

}


