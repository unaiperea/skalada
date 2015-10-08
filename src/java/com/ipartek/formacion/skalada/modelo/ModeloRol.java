package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Rol;

public class ModeloRol implements Persistable<Rol> { // <Objeto genérico>
														// previamente
														// determinado en la
														// interfaz

	private static final String TABLA = "rol";
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

	private static final byte CAMPO1 = 1;
	private static final byte CAMPO2 = 2;
	private static final byte CAMPO3 = 3;

	@Override
	public int save(Rol rol) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (rol != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(CAMPO1, rol.getNombre());
				pst.setString(CAMPO2, rol.getDescripcion());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						rol.setId(resul);
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

	@Override
	public Rol getById(int id) {
		Rol resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETONE);
			pst.setInt(CAMPO1, id);
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

	@Override
	public ArrayList<Rol> getAll() {
		ArrayList<Rol> resul = new ArrayList<Rol>();
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

	@Override
	public boolean update(Rol rol) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (rol != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(CAMPO1, rol.getNombre());
				pst.setString(CAMPO2, rol.getDescripcion());
				pst.setInt(CAMPO3, rol.getId());
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

	@Override
	public boolean delete(int id) {
		boolean resul = false;
		PreparedStatement pst = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_DELETE);
			pst.setInt(CAMPO1, id);
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
	 * Mapea un ResultSet a Rol
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Rol mapeo(ResultSet rs) throws SQLException {
		Rol resul = null;

		resul = new Rol(rs.getString(COL_NOMBRE));
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));

		return resul;
	}

}