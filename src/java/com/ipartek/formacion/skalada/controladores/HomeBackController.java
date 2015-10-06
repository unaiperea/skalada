package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class HomeBackController
 */
public class HomeBackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Key oara guardar el usuario en la session
	public static final String KEY_SESSION_USER = "ss_user";

	private RequestDispatcher dispatcher = null;
	private ModeloUsuario modeloUsuario = null;

	private ModeloSector modeloSector = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloUsuario = new ModeloUsuario();
		this.modeloSector = new ModeloSector();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("num_user_invalid",
				this.modeloUsuario.getInvalidUsers());

		/*
		 * request.setAttribute("num_sectores",
		 * this.modeloSector.getSectorCant());
		 */

		this.dispatcher = request.getRequestDispatcher("pages/index_back.jsp");
		this.dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}