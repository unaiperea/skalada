package com.ipartek.formacion.skalada.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class UtilidadesFecha {
	private static final Logger LOG = Logger.getLogger(UtilidadesFecha.class);

	/**
	 * Convierte una fecha pasada como argumento en Timestamp
	 *
	 * @param strFecha
	 *            : string con la fecha en formato "yyyy-MM-dd"
	 * @return la fecha en Timestamp
	 */
	public static Timestamp convFechaATimestamp(String strFecha) {

		LOG.trace("Entrando a conversion de fechas Fecha->Timestamp");
		Timestamp resul = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date;
			date = sdf.parse(strFecha);
			resul = new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			LOG.warn("Fecha no parseable " + strFecha + " e: " + e.getMessage() );
		}
		LOG.trace("Saliendo de conversion de fechas Fecha->Timestamp");
		return resul;
	}

	/**
	 * Convierte una fecha pasada como argumento en Timestamp
	 *
	 * @param strFecha
	 *            : string con la fecha en formato "yyyy-MM-dd"
	 * @return la fecha en Timestamp
	 * @throws ParseException
	 */
	public static String convTimestampAFecha(Timestamp time)
			throws ParseException {
		LOG.trace("Entrando a conversion de fechas Timestamp->Fecha");
		String resul = "";
		
		if(time!=null){	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			resul = dateFormat.format(time);						
			LOG.trace("Saliendo de conversion de fechas Timestamp->Fecha");
		}
		return resul;

	}
}
