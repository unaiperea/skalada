package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloGrado implements Persistable<Grado> {

	private Connection con = null;

	private static final String TABLA = "grado";
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
	public int save(Grado g) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (g != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				pst = this.con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, g.getNombre());
				pst.setString(2, g.getDescripcion());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						g.setId(resul);
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
	public Grado getById(int id) {
		Grado resul = null;
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
	public ArrayList<Grado> getAll(Usuario usuario) {
		ArrayList<Grado> resul = new ArrayList<Grado>();
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
	public boolean update(Grado g) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (g != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = this.con.prepareStatement(sql);
				pst.setString(1, g.getNombre());
				pst.setString(2, g.getDescripcion());
				pst.setInt(3, g.getId());
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
	 * Mapea un ResultSet a Grado
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Grado mapeo(ResultSet rs) throws SQLException {
		Grado resul = null;

		resul = new Grado(rs.getString(COL_NOMBRE));
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));

		return resul;
	}

}
