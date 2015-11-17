package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class ValidarController
 * @author Curso
 */
public class ValidarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LOGS
	private static final Logger LOG = Logger.getLogger(ValidarController.class);
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ValidarController() {
    	// TODO Auto-generated constructor stub
        super();
    }
    
    @Override()
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	new ModeloUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}