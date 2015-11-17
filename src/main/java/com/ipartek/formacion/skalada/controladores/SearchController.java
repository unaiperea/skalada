package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class SearchController
 */
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;

	private ModeloVia mv = null;
	private ModeloZona mz = null;
	private ModeloSector ms = null;
	private ModeloUsuario mu = null;

	private ArrayList<Via> vias_busqueda = null;
	private ArrayList<Zona> zonas_busqueda = null;
	private ArrayList<Sector> sectores_busqueda = null;
	private ArrayList<Usuario> usuarios_busqueda = null;

	private String pInputSearch = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		this.mv = new ModeloVia();
		this.mz = new ModeloZona();
		this.ms = new ModeloSector();
		this.mu = new ModeloUsuario();
		super.init();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Comprobar sesion
		if (!request.isRequestedSessionIdValid()) {
			Mensaje msg = new Mensaje(Mensaje.MSG_WARNING, "La sesion del usuario ha caducado");
			request.setAttribute("msg", msg);
			this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
		} else {
			// Recoger parametros
			request.setCharacterEncoding("UTF-8");
			this.pInputSearch = request.getParameter("search_input");

			// TODO Llamar a los modelos para realizar la busqueda y cargar los
			// ArrayList

			this.vias_busqueda = this.mv.busqueda(this.pInputSearch);
			this.zonas_busqueda = this.mz.busqueda(this.pInputSearch);
			this.sectores_busqueda = this.ms.busqueda(this.pInputSearch);
			this.usuarios_busqueda = this.mu.busqueda(this.pInputSearch);

			// TODO guardar HashMaps como atributos
			request.setAttribute("vias_busqueda", this.vias_busqueda);
			request.setAttribute("zonas_busqueda", this.zonas_busqueda);
			request.setAttribute("sectores_busqueda", this.sectores_busqueda);
			request.setAttribute("usuarios_busqueda", this.usuarios_busqueda);

			// TODO Lanzar el forward a la pagina de resultados
			this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SEARCH);
		}
		this.dispatcher.forward(request, response);

	}

}
