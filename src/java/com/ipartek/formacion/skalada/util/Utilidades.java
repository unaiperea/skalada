package com.ipartek.formacion.skalada.util;

import java.util.Random;

public class Utilidades {

	/**
	 * Genera una cadena aleatoria de caracteres para crear el token
	 * @param longitud
	 * @return
	 */
	//Al poner static no hace falta instanciarlo desde donde se llama. NombreDeLaClase.nombreDelMétodo (parámetros si los hay);
	public static String getCadenaAlfanumAleatoria (int longitud){
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
			char c = (char)r.nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
}
