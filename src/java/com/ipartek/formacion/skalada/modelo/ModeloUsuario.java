package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloUsuario implements Persistable {

	private static final String SQL_INSERT = "INSERT INTO `usuario` (`email`, `nombre`, `password`, `id_rol`,`token`) VALUES (?, ?, ?, ?,?);";
	private static final String SQL_DELETE = "DELETE FROM `usuario` WHERE `id`= ? ;";
	private static final String SQL_GETALL = "SELECT u.`id`, u.`email`, u.`nombre`, u.`password`, u.`validado`, u.`token`, u.`id_rol`, r.`nombre` AS nombre_rol "
			+ "FROM `usuario` AS u "
			+ "INNER JOIN `rol` as r ON (u.`id_rol` = r.`id`)";
	private static final String SQL_GETNOVALIDADOS = SQL_GETALL
			+ "WHERE `validado`=0";
	private static final String SQL_GETONE = SQL_GETALL + " WHERE u.`id`= ?;";
	private static final String SQL_UPDATE = "UPDATE `usuario` SET `email`= ?, `nombre`= ?, `password`= ?, `validado`= ?, `id_rol`= ?, `token`=? WHERE `id`= ?;";

	private static final String SQL_CHECK_USER = "SELECT `id`, `email`, `nombre`, `password`, `id_rol`, `validado`, `token` FROM `usuario` WHERE `nombre` = ? OR `email` = ?;";
	private static final String SQL_CHECK_EMAIL = "SELECT id, validado FROM `usuario` WHERE `email` like ?;";
	private static final String SQL_VALIDATE = "UPDATE `usuario` SET `validado`= ? WHERE `id`= ?;";
	private static final String SQL_RESET_PASS = "UPDATE `usuario` SET `password`= ? WHERE `email`= ?;";
	private static final String SQL_GET_BY_EMAIL = SQL_GETALL
			+ " WHERE u.`EMAIL` LIKE ?;";
	private static final String SQL_USUARIOS_NO_VALIDADOS = "SELECT COUNT(`id`) AS `noValidados` FROM `usuario` WHERE `validado`=0;";

	@Override()
	public int save(Object o) {
		int resul = -1;
		Usuario usuario = null;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (o != null) {
			try {
				usuario = (Usuario) o;
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, usuario.getEmail());
				pst.setString(2, usuario.getNombre());
				pst.setString(3, usuario.getPassword());
				pst.setInt(4, usuario.getRol().getId());
				pst.setString(5, usuario.getToken());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
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

	public Object getByEmail(String email) {
		Object resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GET_BY_EMAIL);
			pst.setString(1, email);
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

	public ArrayList<Object> getNoValidados() {
		ArrayList<Object> resul = new ArrayList<Object>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETNOVALIDADOS);
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
		Usuario usuario = null;
		PreparedStatement pst = null;
		if (o != null) {
			try {
				usuario = (Usuario) o;
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, usuario.getEmail());
				pst.setString(2, usuario.getNombre());
				pst.setString(3, usuario.getPassword());
				pst.setInt(4, usuario.getValidado());
				pst.setInt(5, usuario.getRol().getId());
				pst.setString(6, usuario.getToken());
				pst.setInt(7, usuario.getId());
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
			pst.setString(1, nombre);
			pst.setString(2, email);
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

	/**
	 * Funcion para comprobar la activacion de usuarios. Busca en la BBDD si
	 * existe un email dado.
	 * 
	 * @param email
	 *            Email a buscar en la BBDD
	 * @return Devuelve un HashMap con el id y el validao correspondiente al
	 *         usuario con el email buscado. En caso de no existir el email
	 *         devuelve un HashMap vacio
	 */

	public HashMap<String, Integer> checkEmail(String email) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_CHECK_EMAIL);
			pst.setString(1, email);
			rs = pst.executeQuery();
			if (rs.next()) {
				map.put("id", rs.getInt("id"));
				map.put("validado", rs.getInt("validado"));
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

		return map;
	}

	public boolean validate(int id) {
		boolean resul = false;

		PreparedStatement pst = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			String sql = SQL_VALIDATE;
			pst = con.prepareStatement(sql);
			pst.setInt(1, 1);
			pst.setInt(2, id);
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

		return resul;
	}

	public boolean resetPass(String email, String pass) {
		boolean resul = false;

		PreparedStatement pst = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			String sql = SQL_RESET_PASS;
			pst = con.prepareStatement(sql);
			pst.setString(1, pass);
			pst.setString(2, email);
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

		return resul;
	}

	public static int usuariosNoValidados() {
		int resul = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_USUARIOS_NO_VALIDADOS);
			rs = pst.executeQuery();
			while (rs.next()) {
				resul = rs.getInt("noValidados");
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
