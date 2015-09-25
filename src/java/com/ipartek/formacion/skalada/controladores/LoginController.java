package com.ipartek.formacion.skalada.controladores;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.EnviarEmails;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher = null;
	private HttpSession session = null;
	
	private Mensaje msg = null;
	
	private ModeloUsuario modeloUsuario = null;
	private Usuario usuario = null;
	
	//LOGIN
	//Key para guardar el usuario en la session
	public static final String KEY_SESSION_USER = "ss_user";
	
	private final String EMAIL = "admin@admin.com";
	private final String PASS = "admin";
	
	private String pEmail;
	private String pPassword;

	//RECUPERAR CONTRASEÑA
	private String pEmailRecuperar = "";
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        modeloUsuario = new ModeloUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}
	
	
	private void login(HttpServletRequest request, HttpServletResponse response){
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
			System.out.println("    Usuario NO logueado");
			
			//recoger parametros del formulario
			pEmail = request.getParameter("email");
			pPassword = request.getParameter("password");
					
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
	}
	
	
	
	
	
	
	
}



