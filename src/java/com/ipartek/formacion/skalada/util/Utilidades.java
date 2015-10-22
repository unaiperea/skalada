package com.ipartek.formacion.skalada.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;

public class Utilidades {
	
	private static final int TAM_MAX = 255;

	/**
	 * Constructor privado de Utilidades
	 */
	private Utilidades() {
		super();
	}

	public static String getCadenaAlfanumAleatoria (int longitud){
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
			char c = (char)r.nextInt(TAM_MAX);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
	/**
	 * Metodo que devuelbe el usuario que tiene iniciada la sesion.
	 * @param request
	 * @param response
	 * @return {@code Usuario} usuario guardado en la session.
	 */
	public static Usuario getSessionUser(HttpServletRequest request, HttpServletResponse response){
		Usuario sessionUser = null;						
		HttpSession session = request.getSession(true);
		sessionUser = (Usuario) session.getAttribute(Constantes.KEY_SESSION_USER);		
		return sessionUser;
	}
	
}
