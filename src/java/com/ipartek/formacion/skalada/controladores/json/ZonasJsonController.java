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
	
	private static  ModeloSector modeloSectores = null;

	@Override // Escribimos ini y pulsamos CTRL + SPAC y elegimos la segunda
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		modeloSectores = new ModeloSector();
		
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZonasJsonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recoger parámetros
		
		//Llamar al modelo
		
		//Responder
		response.setContentType("application/son");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); //Objeto para escribir la respuesta
		
		String jsonResponse = "{\"my_key\": \"my_value\"}";
		
		out.print(jsonResponse);
		out.flush(); //Liberamos el buffer
	}

}
