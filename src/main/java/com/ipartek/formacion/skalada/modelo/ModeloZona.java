package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;

public class ModeloZona implements Persistable<Zona> {
	private Connection con = null;

	private static final String TABLA = "zona";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CREADOR = "id_usuario";
	private static final String COL_PUBLICADO = "validado";
	private static final String COL_FECHA_CREADO = "fecha_creado";
	private static final String COL_FECHA_MODIFICADO = "fecha_modificado";

	private static final String SQL_INSERT = "INSERT INTO `" + TABLA + "` (`"
			+ COL_NOMBRE + "`, `" + COL_CREADOR + "`, `" + COL_PUBLICADO
			+ "`, `longitud`, `latitud`) VALUES (?,?,?,?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA
			+ "` WHERE `" + COL_ID + "`= ?;";

	private static final String SQL_GETALL = "SELECT `id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado`, `longitud`, `latitud` FROM "
			+ TABLA;
	private static final String SQL_GETONE = SQL_GETALL + " WHERE `" + COL_ID
			+ "`= ?";
	private static final String SQL_ZONAS_USER = SQL_GETALL + " WHERE `"
			+ COL_CREADOR + "`= ?";

	private static final String SQL_UPDATE = "UPDATE `" + TABLA + "` SET `"
			+ COL_NOMBRE + "`= ?, `" + COL_CREADOR + "`=?, `" + COL_PUBLICADO

			+ "`=?, `" + COL_FECHA_MODIFICADO
			+ "`=?, `longitud`= ? , `latitud`= ? WHERE `" + COL_ID + "`= ? ;";
	private static final String SQL_BUSQUEDA = SQL_GETALL
			+ " WHERE nombre like ?";


	//@Override()
	public int save(Zona z) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (z != null) {
			try {

				this.con = DataBaseHelper.getConnection();
				pst = this.con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);

				pst.setString(1, z.getNombre());
				pst.setInt(2, z.getUsuario().getId());
				if (z.isValidado()) {
					pst.setInt(3, Constantes.VALIDADO);
				} else {
					pst.setInt(3, 0);
				}
				pst.setDouble(4, z.getLongitud());
				pst.setDouble(5, z.getLatitud());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						z.setId(resul);
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
	public Zona getById(int id) {
		Zona resul = null;
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
	public ArrayList<Zona> getAll(Usuario u) {
		ArrayList<Zona> resul = new ArrayList<Zona>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();

			// si el usuario es admin se recogen todos los registros
			// si el usuario no es admin solo se recogen sus zonas
			if (u.getRol().getId() == 1) {
				// Usuario admin
				pst = this.con.prepareStatement(SQL_GETALL);
			} else if (u.getRol().getId() == 2) {
				// Usuario no admin
				pst = this.con.prepareStatement(SQL_ZONAS_USER);
				pst.setInt(1, u.getId());
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

	//@Override()
	public boolean update(Zona z) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (z != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = this.con.prepareStatement(sql);
				pst.setString(1, z.getNombre());
				pst.setInt(2, z.getUsuario().getId());
				pst.setBoolean(3, z.isValidado());
				pst.setTimestamp(4, z.getFechaModificado());
				pst.setDouble(5, z.getLongitud());
				pst.setDouble(6, z.getLatitud());
				pst.setInt(7, z.getId());
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
	 * Mapea un ResultSet a Zona
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Zona mapeo(ResultSet rs) throws SQLException {
		Zona resul = null;
		Usuario creador = null;
		ModeloUsuario mu = new ModeloUsuario();
		int validado = 0;
		validado = rs.getInt(COL_PUBLICADO);
		creador = mu.getById(rs.getInt(COL_CREADOR));
		resul = new Zona(rs.getString(COL_NOMBRE));
		resul.setId(rs.getInt(COL_ID));
		resul.setUsuario(creador);
		if (validado > 0) {
			resul.setValidado(true);
		} else {
			resul.setValidado(false);
		}
		resul.setFechaCreado(rs.getTimestamp(COL_FECHA_CREADO));
		resul.setFechaModificado(rs.getTimestamp(COL_FECHA_MODIFICADO));
		resul.setLongitud(rs.getDouble("longitud"));
		resul.setLatitud(rs.getDouble("latitud"));

		return resul;
	}


	// TODO OBTENER SECTORES DE UNA VIA
	public ArrayList<Zona> getSectores(int id) {
		ArrayList<Zona> resul = new ArrayList<Zona>();

		return resul;
	}

	public ArrayList<Zona> busqueda(String texto) {
		ArrayList<Zona> resul = new ArrayList<Zona>();

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
