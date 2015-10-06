package com.ipartek.formacion.skalada.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Zona;

//<Objeto genérico> previamente determinado en la interfaz
public class ModeloSector implements Persistable<Sector> {

	private static final String TABLA_SECTOR = "sector";
	private static final String TABLA_ZONA = "zona";
	private static final String COL_ID = "id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_ZONA_ID = "id_zona";
	private static final String COL_ZONA_NOMBRE = "nombre_zona";
	private static final String COL_IMAGEN = "imagen";

	private static final String SQL_INSERT = "INSERT INTO `" + TABLA_SECTOR
			+ "` (`" + COL_NOMBRE + "`, `" + COL_ZONA_ID + "`, `" + COL_IMAGEN
			+ "`) VALUES (?,?,?);";
	private static final String SQL_DELETE = "DELETE FROM `" + TABLA_SECTOR
			+ "` WHERE `" + COL_ID + "`= ?;";

	// private static final String SQL_GETONE = "SELECT  s." + COL_ID + ", s." +
	// COL_NOMBRE + ", " + COL_ZONA_ID + ", z." + COL_NOMBRE + " AS " +
	// COL_ZONA_NOMBRE
	// + " FROM " + TABLA_SECTOR + " AS s INNER JOIN " + TABLA_ZONA +
	// " AS z ON (s." + COL_ZONA_ID + " = z." + COL_ID
	// + ") WHERE s." + COL_ID + " = ?";
	private static final String SQL_GETONE = "SELECT  s.id, s.nombre, id_zona, s.imagen, z.nombre AS nombre_zona FROM sector AS s INNER JOIN zona AS z ON (s.id_zona = z.id) WHERE s.id = ?";

	private static final String SQL_GETALL = "SELECT  s." + COL_ID + ", s."
			+ COL_IMAGEN + ", s." + COL_ZONA_ID + ", s.nombre , z."
			+ COL_NOMBRE + " AS " + COL_ZONA_NOMBRE + " FROM " + TABLA_SECTOR
			+ " AS s, " + TABLA_ZONA + " AS z WHERE s." + COL_ZONA_ID + "= z."
			+ COL_ID;

	private static final String SQL_UPDATE = "UPDATE `" + TABLA_SECTOR
			+ "` SET `" + COL_NOMBRE + "`= ? , `" + COL_ZONA_ID + "`= ?"
			+ ", `" + COL_IMAGEN + "`= ? WHERE `" + COL_ID + "`= ? ;";

	private static final String SQL_GETALL_BY_ZONA = "select `id`, `nombre`, `zona` from `sector` where `id_zona` = ?";

	private static final byte CAMPO1 = 1;
	private static final byte CAMPO2 = 2;
	private static final byte CAMPO3 = 3;
	private static final byte CAMPO4 = 4;

	@Override
	public int save(Sector sector) {
		int resul = -1;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		if (sector != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				pst = con.prepareStatement(SQL_INSERT,
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(CAMPO1, sector.getNombre());
				pst.setInt(CAMPO2, sector.getZona().getId());
				pst.setString(CAMPO3, sector.getImagen());
				if (pst.executeUpdate() != 1) {
					throw new Exception("No se ha realizado la insercion");
				} else {
					rsKeys = pst.getGeneratedKeys();
					if (rsKeys.next()) {
						resul = rsKeys.getInt(1);
						sector.setId(resul);
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
	public Sector getById(int id) {
		Sector resul = null;
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
	public ArrayList<Sector> getAll() {
		ArrayList<Sector> resul = new ArrayList<Sector>();
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
	public boolean update(Sector sector) {
		boolean resul = false;
		PreparedStatement pst = null;
		if (sector != null) {
			try {
				Connection con = DataBaseHelper.getConnection();
				String sql = SQL_UPDATE;
				pst = con.prepareStatement(sql);
				pst.setString(CAMPO1, sector.getNombre());
				pst.setInt(CAMPO2, sector.getZona().getId());
				pst.setString(CAMPO3, sector.getImagen());
				pst.setInt(CAMPO4, sector.getId());
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
	 * Mapea un ResultSet a Sector
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Sector mapeo(ResultSet rs) throws SQLException {
		Sector resul = null;

		Zona zona = new Zona(rs.getString(COL_ZONA_NOMBRE)); // Guardamos la
																// zona
		zona.setId(rs.getInt(COL_ZONA_ID));

		resul = new Sector(rs.getString(COL_NOMBRE), zona); // Guardamos el
															// sector
		resul.setId(rs.getInt(COL_ID));
		resul.setImagen(rs.getString(COL_IMAGEN));

		return resul;
	}

	/**
	 * Obtiene todos los sectores de una Zona {@code Zona}, <b> Cuidado:
	 * getZona() retorna <code>null</code>, se supone que ya la conocemos</b>
	 * 
	 * @param id_zona
	 *            {@int} identificador de la {@code Zona}
	 * @return ArrayList<Sector> colecci�n de sectores de la Zona, si no existe
	 *         ninguno colecci�n inicializada con new() as� no devolvemos un
	 *         null
	 */
	public ArrayList<Sector> getAllByZona(int id_zona) {

		ArrayList<Sector> resul = new ArrayList<Sector>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			Connection con = DataBaseHelper.getConnection();
			pst = con.prepareStatement(SQL_GETALL_BY_ZONA);
			pst.setInt(CAMPO1, id_zona);
			rs = pst.executeQuery();

			Sector sector = null;
			while (rs.next()) {
				sector = new Sector(rs.getString("nombre"), null); // Le
																	// enviamos
																	// como zona
																	// null para
																	// que no se
																	// complique
																	// AJAX al
																	// rellenar
																	// el combo
				sector.setId(rs.getInt("id"));
				sector.setImagen(rs.getString("imagen"));

				resul.add(sector);
				sector = null;
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
