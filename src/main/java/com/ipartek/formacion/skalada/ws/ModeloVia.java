package com.ipartek.formacion.skalada.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.DataBaseHelper;

/**
 * Clase encargada de persistir los objetos de tipo {@code Via} en ficheros
 * serializando y des-serializando
 * 
 * @author ur00
 *
 */
public class ModeloVia implements Persistable<Via> {

	private static final String TABLA_VIA = "via";

	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_LONGITUD = "longitud";
	private static final String COL_DESCRIPCION = "descripcion";
	private static final String COL_GRADO_ID = "id_grado";
	private static final String COL_TIPO_ESCALADA_ID = "id_tipo_escalada";
	private static final String COL_SECTOR_ID = "id_sector";

	private static final String SQL_INSERT = "INSERT INTO `via` (`nombre`, `longitud`, `descripcion`, `id_grado`, `id_tipo_escalada`, `id_sector`) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_GETALL = "SELECT v.id, v.nombre, v.longitud, v.descripcion, v.id_grado, g.nombre AS nombre_grado, "
			+ "v.id_tipo_escalada, te.nombre AS nombre_tipo_escalada, "
			+ "v.id_sector, s.nombre AS nombre_sector, "
			+ "s.id_zona, z.nombre AS nombre_zona "
			+ "FROM via AS v "
			+ "INNER JOIN grado AS g ON (v.id_grado = g.id) "
			+ "INNER JOIN tipo_escalada AS te ON (v.id_tipo_escalada = te.id) "
			+ "INNER JOIN sector AS s ON (v.id_sector = s.id) "
			+ "INNER JOIN zona AS z ON (s.id_zona = z.id)";
	private static final String SQL_GETALL_BY_SECTOR = SQL_GETALL + " WHERE s.`id` = ?";
	private static final String SQL_GETONE = SQL_GETALL + "WHERE v.id = ?";
	private static final String SQL_UPDATE = "UPDATE `via` SET `nombre`=?, `longitud`=?, `descripcion`=?, `id_grado`=?, `id_tipo_escalada`=?, `id_sector`=? WHERE  `id`=?;";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA_VIA
			+ "` WHERE `" + COL_ID + "`= ?;";

	//@Override
	public int save(Via via) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		Connection con = null;
		if (via != null) {
			try {
				con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, via.getNombre());
				pst.setInt(2, via.getLongitud());
				pst.setString(3, via.getDescripcion());
				pst.setString(4, via.getNombreGrado());
				pst.setString(5, via.getNombreTipoEscalada());
				pst.setString(6, via.getNombreSector());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						via.setId(resul);
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
					DataBaseHelper.closeConnection(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	//@Override
	public Via getById(int id) {
		Via resul = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
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
				DataBaseHelper.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	//@Override
	public ArrayList<Via> getAll(Usuario usuario) {
		ArrayList<Via> resul = new ArrayList<Via>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
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
				DataBaseHelper.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	/**
	 * Devuelve todas las vias del sector dado
	 * 
	 * @param usuario
	 *            {@code Usuario} Objeto Usuario
	 * @param sectorId
	 *            {@code int} Id del sector
	 * @return ArrayList<Via>
	 */
	public ArrayList<Via> getAllBySector(Usuario usuario, int sectorId) {
		ArrayList<Via> resul = new ArrayList<Via>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL_BY_SECTOR);
			pst.setInt(1, sectorId);
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
				DataBaseHelper.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}
	
	//@Override
	public boolean update(Via via) {
		boolean resul = false;
		PreparedStatement pst = null;
		Connection con = null;
		if (via != null) {
			try {
				con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(1, via.getNombre());
				pst.setInt(2, via.getLongitud());
				pst.setString(3, via.getDescripcion());
				pst.setString(4, via.getNombreGrado());
				pst.setString(5, via.getNombreTipoEscalada());
				pst.setString(6, via.getNombreSector());
				pst.setInt(7, via.getId());
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
					DataBaseHelper.closeConnection(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resul;
	}

	//@Override
	public boolean delete(int id) {
		boolean resul = false;
		PreparedStatement pst = null;
		Connection con = null;
		try {
			con = DataBaseHelper.getConnection();
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
				DataBaseHelper.closeConnection(con);
				return resul;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resul;
	}

	/**
	 * Mapea un ResultSet a Via
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Via mapeo(ResultSet rs) throws SQLException {
		Via resul = null;

		Grado grado = new Grado(rs.getString("nombre_grado"));
		grado.setId(rs.getInt(COL_GRADO_ID));

		TipoEscalada tipoEscalada = new TipoEscalada(
				rs.getString("nombre_tipo_escalada"));
		tipoEscalada.setId(rs.getInt(COL_TIPO_ESCALADA_ID));

		Zona zona = new Zona(rs.getString("nombre_zona"));
		zona.setId(rs.getInt("id_zona"));

		Sector sector = new Sector(rs.getString("nombre_sector"), zona);
		sector.setId(rs.getInt(COL_SECTOR_ID));

		String nombre = rs.getString(COL_NOMBRE);
		int longitud = rs.getInt(COL_LONGITUD);

		resul = new Via(nombre, longitud, grado, tipoEscalada, sector);
		resul.setId(rs.getInt(COL_ID));
		resul.setDescripcion(rs.getString(COL_DESCRIPCION));

		// inicializar a null
		grado = null;
		tipoEscalada = null;
		zona = null;
		sector = null;
		nombre = null;

		return resul;
	}

}