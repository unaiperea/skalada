package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;

/**
 * Servlet implementation class LogoutControler
 * 
 * @author Curso
 */
public class LogoutControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Logs
	private final static Logger LOG = Logger.getLogger(LogoutControler.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutControler() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute(Constantes.KEY_SESSION_USER);
		
		LOG.info("Usuario: " + usuario.getNombre() + "[id:" + usuario.getId() + "] Cierra sesion.");

		request.getSession().invalidate();
		request.getRequestDispatcher("home").forward(request, response);

	}

}