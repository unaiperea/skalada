package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	// AJAX
	private int pIdZona = 0;
	private int pIdSector = 0;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		modeloZona = new ModeloZona();
		modeloUsuario = new ModeloUsuario();
		modeloSector = new ModeloSector();
		modeloVia = new ModeloVia();
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeomapController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


			/**
			 * MOSTRAR LA ZONA/SECTOR ENVIADO DESDE LA HOME
			 */

			/*
			 * // Recuperamos el usuario Administrador para poder recuperar todas las Zonas y Sectores usuario =
			 * modeloUsuario.getById(Constantes.ROLE_ID_ADMIN); ArrayList<Zona> zonas = modeloZona.getAll(usuario); ArrayList<Sector> sectores =
			 * modeloSector.getAll(usuario);
			 */

			// El sector elegido en la pagina principal junto con su zona y todas sus vias
			if (request.getParameter("idSector") != null) {
				pIdSector = Integer.parseInt(request.getParameter("idSector"));

				sectorDestacado = modeloSector.getById(pIdSector);
				zona = modeloZona.getById(sectorDestacado.getZona().getId());
				// Recogemos todos los sectores de esa zona en un ArrayList
				sectores = modeloSector.getAllByZona(zona.getId());
				// Recogemos todas las vias del sector enviado desde la Home
				vias = modeloVia.getAllBySector((Usuario)modeloUsuario.getById(Constantes.ROLE_ID_ADMIN), pIdSector);
				// Devolvemos los resultados
				setearAtributos(request);
			}
			// La zona elegida en la pagina principal junto con todos sus sectores.
			if (request.getParameter("idZona") != null) {
				pIdZona = Integer.parseInt(request.getParameter("idZona"));

				// Recogemos todos los sectores de esa zona en un ArrayList
				sectores = modeloSector.getAllByZona(pIdZona);

				// Guardamos el sector destacado, por defecto el primero del ArrayList
				if (sectores.get(0) != null) {
					sectorDestacado = sectores.get(0);
					// Recogemos todas las vias del sector destacado
					vias = modeloVia.getAllBySector((Usuario)modeloUsuario.getById(Constantes.ROLE_ID_ADMIN), sectores.get(0).getId());
					zona = modeloZona.getById(sectorDestacado.getZona().getId());
				}
				// Devolvemos los resultados
				setearAtributos(request);

			}

			// ENVIA TODAS LAS ZONAS Y SECTORES
			/*
			 * // Enviamos la informacion a mostrar en Geomap request.setAttribute("zonas", zonas); request.setAttribute("sectores", sectores);
			 */

			dispatcher = request.getRequestDispatcher(Constantes.VIEW_FRONT_SECTORESINFO);
			dispatcher.forward(request, response);
			

	}

	private void setearAtributos(HttpServletRequest request) throws ServletException, IOException {
		// Mandamos el detalle
		request.setAttribute("zona", zona);
		request.setAttribute("sectores", sectores);
		request.setAttribute("sectorDestacado", sectorDestacado);
		request.setAttribute("vias", vias);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}