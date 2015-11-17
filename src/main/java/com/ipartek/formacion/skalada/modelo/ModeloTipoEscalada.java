package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloTipoEscalada implements Persistable<TipoEscalada> {

	private Connection con = null;

	private static final String TABLA = "tipo_escalada";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_DESCRIPCION = "descripcion";

	private static final String SQL_INSERT = "INSERT INTO `" + TABLA + "` (`"
			+ COL_NOMBRE + "`, `" + COL_DESCRIPCION + "`) VALUES (?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA
			+ "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETONE = "SELECT `id`, `nombre`, `descripcion` FROM `"
			+ TABLA + "` WHERE `" + COL_ID + "`= ?;";
	private static final String SQL_GETALL = "SELECT `id`, `nombre`, `descripcion` FROM "
			+ TABLA;
	private static final String SQL_UPDATE = "UPDATE `" + TABLA + "` SET `"
			+ COL_NOMBRE + "`= ? , `" + COL_DESCRIPCION + "`= ? WHERE `"
			+ COL_ID + "`= ? ;";

	//@Override()
	public int save(TipoEscalada te) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (te != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				pst = this.con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, te.getNombre());
				pst.setString(2, te.getDescripcion());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						te.setId(resul);
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
	public TipoEscalada getById(int id) {
		TipoEscalada resul = null;
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
	public ArrayList<TipoEscalada> getAll(Usuario usuario) {
		ArrayList<TipoEscalada> resul = new ArrayList<TipoEscalada>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			pst = this.con.prepareStatement(SQL_GETALL);
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
	public boolean update(TipoEscalada te) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (te != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = this.con.prepareStatement(sql);
				pst.setString(1, te.getNombre());
				pst.setString(2, te.getDescripcion());
				pst.setInt(3, te.getId());
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
	 * Mapea un ResultSet a TipoEscalada
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TipoEscalada mapeo(ResultSet rs) throws SQLException {
		TipoEscalada resul = null;

		resul = new TipoEscalada(rs.getString(COL_NOMBRE));
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));

		return resul;
	}

}
