package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.modelo.ModeloVia;

/**
 * Servlet implementation class ViasPublicController
 */
public class ViasPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ModeloVia modelo = null;	
	Via via = null;
	
	private int pID;
    
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
		
		getParameters(request);
		
		via = (Via)modelo.getById(pID);
		request.setAttribute("via", via);
		
		request.getRequestDispatcher(Constantes.VIEW_PUBLIC_VIA).forward(request, response);
	}

	private void getParameters(HttpServletRequest request) {
		try {
			if(request.getParameter("id") != null && !"".equalsIgnoreCase(request.getParameter("id"))){
				pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
