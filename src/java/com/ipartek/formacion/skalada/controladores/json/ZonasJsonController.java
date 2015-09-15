package com.ipartek.formacion.skalada.controladores.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.modelo.ModeloSector;

/**
 * Servlet implementation class ZonasJsonController
 */
public class ZonasJsonController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static ModeloSector modeloSectores = null;
   	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		modeloSectores = new ModeloSector();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//recoger parametros
		
		//llamar al modelo
		
		//responder
		response.setContentType("application/json");		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();		
		String jsonReponse = "{\"my_key\": \"my_value\"}";		
		out.print(jsonReponse);
		out.flush();
		
		
		
	}

}
