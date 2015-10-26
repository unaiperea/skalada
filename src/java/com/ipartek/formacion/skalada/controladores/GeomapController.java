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
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class GeomapController
 */
public class GeomapController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;

	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;
	private ModeloSector modeloSector = null;
	private ModeloVia modeloVia = null;

	private Usuario usuario = null;
	private Sector sectorDestacado = null;
	private Zona zona = null;
	private ArrayList<Via> vias = null;
	private ArrayList<Sector> sectores = null;

	private ArrayList<Zona> zonas = null;
	private HttpSession session = null;

	// AJAX
	private int pIdZona = 0;
	private int pIdSector = 0;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloZona = new ModeloZona();
		this.modeloUsuario = new ModeloUsuario();
		this.modeloSector = new ModeloSector();
		this.modeloVia = new ModeloVia();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeomapController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * MOSTRAR LA ZONA/SECTOR ENVIADO DESDE LA HOME
		 */
		this.usuario = (Usuario) this.modeloUsuario.getByEmail("admin@admin.com");
		this.session = request.getSession(true);
		this.session.setAttribute("admin", this.usuario);
		this.zonas = this.modeloZona.getAll(this.usuario);
		request.setAttribute("todo_zonas", this.zonas);

		/*
		 * // Recuperamos el usuario Administrador para poder recuperar todas las
		 * Zonas y Sectores usuario =
		 * modeloUsuario.getById(Constantes.ROLE_ID_ADMIN); ArrayList<Zona> zonas =
		 * modeloZona.getAll(usuario); ArrayList<Sector> sectores =
		 * modeloSector.getAll(usuario);
		 */

		// El sector elegido en la pagina principal junto con su zona y todas sus
		// vias
		if (request.getParameter("idSector") != null) {
			this.pIdSector = Integer.parseInt(request.getParameter("idSector"));

			this.sectorDestacado = this.modeloSector.getById(this.pIdSector);
			this.zona = this.modeloZona.getById(this.sectorDestacado.getZona().getId());
			// Recogemos todos los sectores de esa zona en un ArrayList
			this.sectores = this.modeloSector.getAllByZona(this.zona.getId());
			// Recogemos todas las vias del sector enviado desde la Home
			this.vias = this.modeloVia.getAll(
					(Usuario) this.modeloUsuario.getById(Constantes.ROLE_ID_ADMIN));
			// Devolvemos los resultados
			this.setearAtributos(request);
		}
		// La zona elegida en la pagina principal junto con todos sus sectores.
		if (request.getParameter("idZona") != null) {
			this.pIdZona = Integer.parseInt(request.getParameter("idZona"));

			// Recogemos todos los sectores de esa zona en un ArrayList
			this.sectores = this.modeloSector.getAllByZona(this.pIdZona);

			// Guardamos el sector destacado, por defecto el primero del ArrayList
			if (this.sectores.get(0) != null) {
				this.sectorDestacado = this.sectores.get(0);
				// Recogemos todas las vias del sector destacado
				this.vias = this.modeloVia.getAll(
						(Usuario) this.modeloUsuario.getById(Constantes.ROLE_ID_ADMIN));
				this.zona = this.modeloZona.getById(this.sectorDestacado.getZona()
						.getId());
			}
			// Devolvemos los resultados
			this.setearAtributos(request);

		}

		// ENVIA TODAS LAS ZONAS Y SECTORES
		/*
		 * // Enviamos la informacion a mostrar en Geomap
		 * request.setAttribute("zonas", zonas); request.setAttribute("sectores",
		 * sectores);
		 */

		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_FRONT_SECTORESINFO);
		this.dispatcher.forward(request, response);

	}

	private void setearAtributos(HttpServletRequest request)
			throws ServletException, IOException {
		// Mandamos el detalle
		request.setAttribute("zona", this.zona);
		request.setAttribute("sectores", this.sectores);
		request.setAttribute("sectorDestacado", this.sectorDestacado);
		request.setAttribute("vias", this.vias);

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