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
 * 
 * @author Curso
 */
public class ZonasJsonController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ModeloSector modeloSectores = null;

	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloSectores = new ModeloSector();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// recoger parametros
		int id_zona = Integer.parseInt(request.getParameter("id_zona"));

		// llamar al modelo
		ArrayList<Sector> sectores = this.modeloSectores.getAllByZona(id_zona);

		// responder
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		String jsonReponse = new Gson().toJson(sectores);
		out.print(jsonReponse);
		out.flush();

	}

}
