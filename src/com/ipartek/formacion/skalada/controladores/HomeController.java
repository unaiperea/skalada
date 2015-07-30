package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.modelo.ModeloVia;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ModeloVia modelo = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//Sólo se ejecuta una sóla vez.
		
		super.init(config); //Llama al padre y construya la configuración del servlet
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
		
		//Recuperar las últimas 6 vías del modelo
		ArrayList<Object> vias = modelo.getAll();
		if (vias.size() > 6){
			vias = new ArrayList<Object>(vias.subList(0, 6));
		}
		
		//Enviarlas como atributo request
		request.setAttribute("ultimas_vias", vias);
		
		//Ir a index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		
		//Forma antigua de enviar datos a la página, lo escribía desde aquí
		//Devuelve a la página este objeto para que lo escriba en pantalla. Es mejor ejecutar <%=***JAVA***%>
		/*PrintWriter out = response.getWriter();
		out.println("<html doctype>");
		out.println("<head");
			out.println("<title>Estamos en la Home</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Home</h1>");
		out.println("</body>");
		
		out.flush(); //Lo envía para que lo escriba en la página*/
		
	}

}
