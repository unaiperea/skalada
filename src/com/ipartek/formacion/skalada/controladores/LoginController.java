package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.skalada.Constantes;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	//comprobamos con la BBDD	
	private final String EMAIL = "admin@admin.com";
	private final String PASS  = "admin";
	
	HttpSession session; 
	private RequestDispatcher dispatcher = null;
	
	private String pEmail;
	private String pPassword;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
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

		session = request.getSession();
		
		String usuario = (String)session.getAttribute("user");
		
		//Usuario Logeado
		if ( usuario != null && !"".equals(usuario) ){		
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
		//Usuario NO Logeado o caducada session	
		}else{
			//recoger parametros del formulario
			getParameters(request);		
			
			//validar los datos		
			if(EMAIL.equals(pEmail)&&PASS.equals(pPassword)){
				
				saveSession(request);
				
				//Ir a => index_back.jsp		
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			} else {
				//Ir a => login.jsp
				request.setAttribute("msg", "El email y/o contrase&ntilde;a incorrecta");			
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
			
		}
		
		
		dispatcher.forward(request, response);
		
	}

	private void saveSession(HttpServletRequest request) {

		
		session.setAttribute("user", pEmail );
		
		
	}

	/**
	* Recoger los parametros enviados
	* @param request
	*/
	private void getParameters(HttpServletRequest request) {
	
		pEmail = request.getParameter("email");
		pPassword = request.getParameter("password");
	}
	
}



