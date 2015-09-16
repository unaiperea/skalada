package com.ipartek.formacion.skalada.controladores.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ipartek.formacion.skalada.bean.Sector;
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
		
		//recoger par�metros
		int id_zona = Integer.parseInt(request.getParameter("id_zona"));
		
		//Llamar al modelo
		ArrayList<Sector> sectores = modeloSectores.getAllByZona(id_zona);
		
		//Responder
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); //Objeto para escribir la respuesta
		
		/*Le damos un arraylist para que lo pase a formato Json (librer�a de Google).
		Si le damos un objeto para que lo pase a Json ha de tener la cl�usula Serializable en la Class*/
		String jsonResponse = new Gson().toJson(sectores);
		//String jsonResponse = "{\"my_key\": \"my_value\"}";
		
		out.print(jsonResponse);
		out.flush(); //Liberamos el buffer
	}

}
