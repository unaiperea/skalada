package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloUsuario implements Persistable<Usuario> { // <Objeto
																// genérico>
																// previamente
																// determinado
																// en la
																// interfaz

	private static final String SQL_INSERT = "INSERT INTO `usuario` (`email`, `nombre`, `password`, `id_rol`) VALUES (?, ?, ?, ?);";
	private static final String SQL_DELETE = "DELETE FROM `usuario` WHERE `id`= ? ;";
	private static final String SQL_GETALL = "SELECT u.`id`, u.`token`, u.`email`, u.`nombre`, u.`password`, u.`validado`, u.`id_rol`, r.`nombre` AS nombre_rol "
			+ "FROM `usuario` AS u "
			+ "INNER JOIN `rol` as r ON (u.`id_rol` = r.`id`)";
	private static final String SQL_GETONE = SQL_GETALL + " WHERE u.`id`= ?;";
	private static final String SQL_GET_BY_EMAIL = SQL_GETALL
			+ " WHERE u.`email`= ?;";
	private static final String SQL_GET_USER_INVALID = "SELECT COUNT(*) FROM `usuario` WHERE `validado` = 0;";
	private static final String SQL_UPDATE = "UPDATE `usuario` SET `email`= ?, `nombre`= ?, `password`= ?, `validado`= ?, `id_rol`= ?, `token`= ? WHERE `id`= ?;";

	private static final String SQL_CHECK_USER = "SELECT `id`, `token`, `email`, `nombre`, `password`, `validado`, `id_rol` FROM `usuario` WHERE `nombre` = ? OR `email` = ?";

	private static final byte campo1 = 1;
	private static final byte campo2 = 2;
	private static final byte campo3 = 3;
	private static final byte campo4 = 4;
	private static final byte campo5 = 5;
	private static final byte campo6 = 6;
	private static final byte campo7 = 7;

	@Override
	public int save(Usuario usuario) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (usuario != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(campo1, usuario.getEmail());
				pst.setString(campo2, usuario.getNombre());
				pst.setString(campo3, usuario.getPassword());
				pst.setInt(campo4, usuario.getRol().getId());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(campo1);
						usuario.setId(resul);
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
	public Usuario getById(int id) {
		Usuario resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETONE);
			pst.setInt(campo1, id);
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

	/**
	 **** READ BY EMAIL **** Recupera Objeto por su Email
	 * 
	 * @param email
	 *            {@code String} Email del objeto a recuperar
	 * @return {@code Object} objeto encontrado o null en caso contrario
	 */
	public Usuario getByEmail(String email) {
		Usuario resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GET_BY_EMAIL);
			pst.setString(campo1, email);
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
	public ArrayList<Usuario> getAll() {
		ArrayList<Usuario> resul = new ArrayList<Usuario>();
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

	public int getInvalidUsers() {
		int resul = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GET_BY_EMAIL);
			resul = (Integer) pst.executeQuery();
			/*
			 * while (rs.next()) { resul = this.mapeo(rs); }
			 */
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
	public boolean update(Usuario usuario) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (usuario != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(campo1, usuario.getEmail());
				pst.setString(campo2, usuario.getNombre());
				pst.setString(campo3, usuario.getPassword());
				pst.setInt(campo4, usuario.getValidado());
				pst.setInt(campo5, usuario.getRol().getId());
				pst.setString(campo6, usuario.getToken());
				pst.setInt(campo7, usuario.getId());
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
			pst.setInt(campo1, id);
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
	 * Mapea un ResultSet a Usuario
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Usuario mapeo(ResultSet rs) throws SQLException {
		Usuario resul = null;

		Rol rol = new Rol(rs.getString("nombre_rol"));
		rol.setId(rs.getShort("id_rol"));

		resul = new Usuario(rs.getString("nombre"), rs.getString("email"),
				rs.getString("password"), rol);
		resul.setId(rs.getInt("id"));
		resul.setValidado(rs.getInt("validado"));
		resul.setToken(rs.getString("token"));

		return resul;
	}

	/**
	 * Comprueba si existe ese nombre o email en la tabla usuario
	 * 
	 * @param nombre
	 *            {@code String} nombre del {@code Usuario}
	 * @param email
	 *            {@code String} email del {@code Usuario}
	 * @return {@code boolean} true si existe en la tabla, false en caso de que
	 *         este libre
	 */
	public boolean checkUser(String nombre, String email) {
		boolean resul = true;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_CHECK_USER);
			pst.setString(campo1, nombre);
			pst.setString(campo2, email);
			rs = pst.executeQuery();
			if (!rs.next()) {
				resul = false; // Nombre y email libres
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

}
