package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;

public class ModeloUsuario implements Persistable<Usuario> {

	Connection con = null;

	// Logs
	private static final Logger LOG = Logger.getLogger(ModeloUsuario.class);

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
	private static final String SQL_BUSQUEDA = SQL_GETALL
			+ "WHERE u.nombre COLLATE UTF8_GENERAL_CI like ? OR u.email COLLATE UTF8_GENERAL_CI like ?";

	//@Override()
	public int save(Usuario u) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (u != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				// LOG.debug("Obtenemos conexion BBDD.");
				pst = this.con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, u.getEmail());
				pst.setString(2, u.getNombre());
				pst.setString(3, u.getPassword());
				pst.setInt(4, u.getRol().getId());
				pst.setString(5, u.getToken());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						u.setId(resul);
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
	public Usuario getById(int id) {
		Usuario resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
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

	public Usuario getByEmail(String email) {
		Usuario resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			pst = this.con.prepareStatement(SQL_GET_BY_EMAIL);
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
				DataBaseHelper.closeConnection(this.con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	//@Override()
	public ArrayList<Usuario> getAll(Usuario u) {
		ArrayList<Usuario> resul = new ArrayList<Usuario>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
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

	public ArrayList<Usuario> getNoValidados() {
		ArrayList<Usuario> resul = new ArrayList<Usuario>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			pst = this.con.prepareStatement(SQL_GETNOVALIDADOS);
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
	public boolean update(Usuario u) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (u != null) {
			try {
				this.con = DataBaseHelper.getConnection();
				// LOG.debug("Obtenemos conexion BBDD.");
				String sql = SQL_UPDATE;
				pst = this.con.prepareStatement(sql);
				pst.setString(1, u.getEmail());
				pst.setString(2, u.getNombre());
				pst.setString(3, u.getPassword());
				if (u.isValidado()) {
					pst.setInt(4, Constantes.VALIDADO);
				} else {
					pst.setInt(4, Constantes.NO_VALIDADO);
				}
				pst.setInt(5, u.getRol().getId());
				pst.setString(6, u.getToken());
				pst.setInt(7, u.getId());
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
			// LOG.debug("Obtenemos conexion BBDD.");
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
		if (rs.getInt("validado") == 1) {
			resul.setValidado(true);
		} else {
			resul.setValidado(false);
		}

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
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			pst = this.con.prepareStatement(SQL_CHECK_USER);
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
				DataBaseHelper.closeConnection(this.con);
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
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			pst = this.con.prepareStatement(SQL_CHECK_EMAIL);
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
				DataBaseHelper.closeConnection(this.con);
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
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			String sql = SQL_VALIDATE;
			pst = this.con.prepareStatement(sql);
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
				DataBaseHelper.closeConnection(this.con);
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
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			String sql = SQL_RESET_PASS;
			pst = this.con.prepareStatement(sql);
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
				DataBaseHelper.closeConnection(this.con);
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
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
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
				DataBaseHelper.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	public ArrayList<Usuario> busqueda(String texto) {
		ArrayList<Usuario> resul = new ArrayList<Usuario>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.con = DataBaseHelper.getConnection();
			// LOG.debug("Obtenemos conexion BBDD.");
			pst = this.con.prepareStatement(SQL_BUSQUEDA);
			pst.setString(1, "%" + texto + "%");
			pst.setString(2, "%" + texto + "%");
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