package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.modelo.ModeloSector;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final byte MAX_SECTORES = 6;

	private ModeloSector modeloSector = null;
       
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		modeloSector = new ModeloSector();
	}

	/**
	 * Se puentea al doPost()
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Recuperar los 6 �ltimos 6 sectores del modelo
		//Usar limit 6 en la select y order by id desc
		ArrayList<Sector> sectores = modeloSector.getAll();
		if (sectores.size() > MAX_SECTORES){
			sectores = new ArrayList<Sector>(sectores.subList(0, MAX_SECTORES));
		}
		
		//Enviarlas como atributo en la request
		request.setAttribute("ultimos_sectores", sectores);
		
		//Ir a index
		request.getRequestDispatcher(Constantes.VIEW_PUBLIC_INDEX).forward(request, response);
		
	}

}
