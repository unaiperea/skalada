package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
	
	//Key oara guardar el usuario en la session
	public static final String KEY_SESSION_USER = "ss_user";
       
	private RequestDispatcher dispatcher = null;
	private HttpSession session = null;
	
	private final String EMAIL = "admin@admin.com";
	private final String PASS = "admin";
	
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

		//
		System.out.println("Login entrando....");
		
		//recoger la sesion
		session = request.getSession();
		String usuario = (String)session.getAttribute(KEY_SESSION_USER);
		
//Usuario logeado
		if ( usuario != null && "".equals(usuario) ){
			
			//
			System.out.println("    Usuario YA logueado");
			
			//Ir a => index_back.jsp		
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			
//Usuario No logeado o caducada session
		} else {
			
			//
			System.out.println("    Usuario NO logueado");
			
			//recoger parametros del formulario
			getParameters(request);
					
			//validar los datos

			//comprobamos con la BBDD			
			if(EMAIL.equals(pEmail)&&PASS.equals(pPassword)){
				
				//salvar session
				session.setAttribute(KEY_SESSION_USER, pEmail);
				
				//Ir a => index_back.jsp		
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			} else {
				//Ir a => login.jsp
				request.setAttribute("msg", "El email y/o contrase&ntilde;a incorrecta");			
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
			
		}
		
		//
		System.out.println("login forward o saliendo....");
				
		dispatcher.forward(request, response);
		
	}
	
	/**
	* Recoger los parametros enviados
	* @param request
	 * @throws UnsupportedEncodingException 
	*/
	private void getParameters(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		pEmail = request.getParameter("email");
		pPassword = request.getParameter("password");
		
	}
	
}



