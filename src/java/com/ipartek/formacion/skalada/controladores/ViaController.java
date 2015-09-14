package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.modelo.ModeloVia;

/**
 * Servlet implementation class Via
 */
public class ViaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModeloVia modelo = null;
	private Via via = null;
	
	private int pId = 0;
	private int pAccion = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		modelo = new ModeloVia();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getParametrosUltimasVias(request,response);
		
		via = (Via)modelo.getById(pId);
		request.setAttribute("objeto_Via", via);
		request.getRequestDispatcher("detalle.jsp").forward(request, response);
	}

	
	private void getParametrosUltimasVias(HttpServletRequest request, HttpServletResponse response){
		
	    pId = Integer.parseInt(request.getParameter("id"));
		pAccion = Integer.parseInt(request.getParameter("accion"));

	}
	
}

