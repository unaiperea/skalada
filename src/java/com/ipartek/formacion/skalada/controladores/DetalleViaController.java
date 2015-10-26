package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class DetalleViaController
 */
public class DetalleViaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpSession session = null;

	private RequestDispatcher dispatcher = null;
	private ModeloVia modeloVia = null;
	private Via via = null;

	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;

	private Usuario user = null;

	private ArrayList<Zona> zonas = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.modeloVia = new ModeloVia();
		this.modeloUsuario = new ModeloUsuario();
		this.modeloZona = new ModeloZona();

		super.init(config);
	}

	// Parametros
	private int pID = -1; // ID no valido

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.user = (Usuario) this.modeloUsuario.getByEmail("admin@admin.com");
		this.session = request.getSession(true);
		this.session.setAttribute("admin", this.user);
		// recoger parametros
		this.getParameters(request, response);

		this.detalle(request, response);

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
			if ((request.getParameter("id") != null)
					&& !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		this.via = this.modeloVia.getById(this.pID);
		request.setAttribute("via", this.via);
		request.setAttribute("titulo", this.via.getNombre());
		request.setAttribute("grados", this.via.getGrado().getNombre());
		request.setAttribute("tipoEscaladas", this.via.getTipoEscalada()
				.getNombre());
		request.setAttribute("zonas", this.via.getSector().getZona().getNombre());
		request.setAttribute("sectores", this.via.getSector().getNombre());
		this.zonas = this.modeloZona.getAll(this.user);
		request.setAttribute("todo_zonas", this.zonas);
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_FRONT_VIAS_DETALLE);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
