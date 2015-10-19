package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class GeomapController
 */
public class GeomapController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;
	private ModeloSector modeloSector = null;
	
	private Usuario usuario = null;

	// AJAX
	private int pIdZona = 0;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		modeloZona = new ModeloZona();
		modeloUsuario = new ModeloUsuario();
		modeloSector = new ModeloSector();
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
		 * MOSTRAR TODAS LAS ZONAS EN GEOMAP Recuperamos todas las Zonas y todos los Sectores para pintarlos en GEOMAP
		 */
		// Recuperamos el usuario Administrador para poder recuperar todas las Zonas y Sectores
		usuario = (Usuario)modeloUsuario.getById(Constantes.ROLE_ID_ADMIN);
		ArrayList<Zona> zonas = modeloZona.getAll(usuario);
		ArrayList<Sector> sectores = modeloSector.getAll(usuario);


		/**
		 * JSON PARA CARGAR EL COMBO DE SECTORES EN EL DETALLE SEGUN LA ZONA ELEGIDA
		 */
		// Recoger parametros
		pIdZona = Integer.parseInt(request.getParameter("idZona"));
		// Recuperar Sectores en combo segun Zona
		modeloSector.getAllByZona(pIdZona);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
