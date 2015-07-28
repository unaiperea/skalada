package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.Grado;
import com.ipartek.formacion.skalada.bean.Via;

/**
 * Servlet implementation class ViasController
 */
public class ViasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestDispatcher dispatcher = null;
	private Via via = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViasController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		
		//recoger parametro id de la Via
		String pID = request.getParameter("id");
		if ( pID != null && !"".equals(pID)){
			int id = Integer.parseInt(pID);
			// nueva via
			if ( id == -1 ){ 
				via = new Via("Nueva");
			//detalle via	
			}else{
				//TODO llamar al modelo para recuperarla por ID
				via = new Via("Almendralejo");
				via.setId(5);
				via.setGrado(Grado.FACIL);
				via.setLongitud(34);
				via.setDescripcion("Lorem ipsum bla bla bla");
			}
			//enviar atributo con la via
			request.setAttribute("via", via);
			//cargarmos el dispatcher
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_FORM);	
		}else{
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_INDEX);	
		}
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
