package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;

/**
 * Servlet implementation class LogoutControler
 */
public class LogoutControler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// @Unai: Se pone el nombre de la clase (LOG)
	private final static Logger LOG = Logger.getLogger(LoginController.class);

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

		//
		LOG.info("Logout.... "); // LOG

		request.getSession().removeAttribute(Constantes.KEY_SESSION_USER);

		request.getSession().invalidate();
		request.getRequestDispatcher("home").forward(request, response);

	}

}
