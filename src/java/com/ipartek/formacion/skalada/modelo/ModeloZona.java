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

public class ModeloZona implements Persistable {

	private static final String TABLA = "zona";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CREADOR = "id_usuario";
	private static final String COL_PUBLICADO = "validado";
	private static final String COL_FECHA_CREADO = "fecha_creado";
	private static final String COL_FECHA_MODIFICADO = "fecha_modificado";

	private static final String SQL_INSERT = "INSERT INTO `" + TABLA + "` (`"
			+ COL_NOMBRE + "`, `" + COL_CREADOR + "`, `" + COL_PUBLICADO
			+ "`) VALUES (?,?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA
			+ "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETONE = "SELECT `id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado` FROM `"
			+ TABLA + "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETALL = "SELECT `id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado` FROM "
			+ TABLA;
	private static final String SQL_UPDATE = "UPDATE `" + TABLA + "` SET `"
			+ COL_NOMBRE + "`= ?, `" + COL_CREADOR + "`=?, `" + COL_PUBLICADO
			+ "`=?, `" + COL_FECHA_MODIFICADO + "`=? WHERE `" + COL_ID
			+ "`= ? ;";

	@Override()
	public int save(Object o) {
		int resul = -1;
		Zona z = null;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (o != null) {
			try {
				z = (Zona) o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, z.getNombre());
				pst.setInt(2, z.getUsuario().getId());
				if (z.isValidado()) {
					pst.setInt(3, Constantes.VALIDADO);
				} else {
					pst.setInt(3, 0);
				}
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
					DataBaseHelper.closeConnection();
				} catch (Exception e) {
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
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETONE);
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
				DataBaseHelper.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	@Override()
	public ArrayList<Object> getAll(Usuario usuario) {
		ArrayList<Object> resul = new ArrayList<Object>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL);
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
				DataBaseHelper.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	@Override()
	public boolean update(Object o) {
		boolean resul = false;
		Zona z = null;
		PreparedStatement pst = null;
		if (o != null) {
			try {
				z = (Zona) o;
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, z.getNombre());
				pst.setInt(2, z.getUsuario().getId());
				pst.setBoolean(3, z.isValidado());
				pst.setTimestamp(4, z.getFechaModificado());
				pst.setInt(5, z.getId());
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
		creador = (Usuario) mu.getById(rs.getInt(COL_CREADOR));
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
		return resul;
	}

	// TODO OBTENER SECTORES DE UNA VIA
	public ArrayList<Object> getSectores(int id) {
		ArrayList<Object> resul = new ArrayList<Object>();

		return resul;
	}

}