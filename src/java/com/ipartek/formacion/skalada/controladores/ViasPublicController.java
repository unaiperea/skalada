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
 * Servlet implementation class ViasPublicController
 * @author Curso
 */
public class ViasPublicController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ModeloVia modelo = null;	
	private Via via = null;
	
	private int pID;
    
    /**
	 * @see Servlet#init(ServletConfig)
	 * @param config Configuracion del servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modelo = new ModeloVia();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getParameters(request);
		
		this.via = (Via)this.modelo.getById(this.pID);
		request.setAttribute("via", this.via);
		
		request.getRequestDispatcher(Constantes.VIEW_PUBLIC_VIA).forward(request, response);
	}

	private void getParameters(HttpServletRequest request) {
		try {
			if(request.getParameter("id") != null && !"".equalsIgnoreCase(request.getParameter("id"))){
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
