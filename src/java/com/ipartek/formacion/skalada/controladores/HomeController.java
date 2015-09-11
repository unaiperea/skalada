package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.modelo.ModeloVia;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ModeloVia modelo = null;
	
       
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		modelo = new ModeloVia();
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
		
//		/*
//		 * Se puede dibujar html a pelo
//		 */
//		PrintWriter out = response.getWriter();		
//		out.println("<h1>HOME</h1>");		
//		out.flush();
		
		
//		if ( modelo.getAll().size() > 6 ){
//			request.setAttribute("ultimas_vias", modelo.getAll().subList(0, 6));
//		}
		
		//recuperar las ultimas 6 vias del modelo
//		ArrayList<Object> vias = modelo.getAll();
//		if ( vias.size() > 6 ){
//			vias = new ArrayList<Object>(vias.subList(0, 6));
//		}
//		
//		//enviarlas como atributo en la request
//		request.setAttribute("ultimas_vias", vias);
//		
//		//ir a index
		request.getRequestDispatcher(Constantes.VIEW_PUBLIC_INDEX).forward(request, response);
		
		
		
		
		

		
		
		
		
	}

}
