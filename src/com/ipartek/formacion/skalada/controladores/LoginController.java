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
	
	//Key para guardar el usuario en session
	public final static String KEY_SESSION_USER = "ss_user";
	
	HttpSession session; 
	private RequestDispatcher dispatcher = null;
	
	private String pEmail;
	private String pPassword;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();       
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
		
		System.out.println("Login entrando...");
		
		session = request.getSession();
		
		String usuario = (String)session.getAttribute(KEY_SESSION_USER);
		
		//Usuario Logeado
		if ( usuario != null && !"".equals(usuario) ){	
			System.out.println("    usuario YA logeado");
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
		//Usuario NO Logeado o caducada session	
		}else{
			System.out.println("    usuario NO logeado");
			//recoger parametros del formulario
			getParameters(request);		
			
			//validar los datos		
			if(EMAIL.equals(pEmail)&&PASS.equals(pPassword)){
				
				//salver session
				session.setAttribute(KEY_SESSION_USER, pEmail );
				
				//Ir a => index_back.jsp		
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			} else {
				//Ir a => login.jsp
				request.setAttribute("msg", "El email y/o contrase&ntilde;a incorrecta");			
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
			
		}
		
		System.out.println("Login forward o saliendo");
		dispatcher.forward(request, response);
		
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



