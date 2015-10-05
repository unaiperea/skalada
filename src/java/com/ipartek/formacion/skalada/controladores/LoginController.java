package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final static Logger log = Logger.getLogger(LoginController.class); //Se pone el nombre de la clase (LOG)
	private static final String ROL_ADMIN = "Administrador";
	
	private RequestDispatcher dispatcher = null;
	private HttpSession session = null;
	
	private Mensaje msg = null;
	
	private ModeloUsuario modeloUsuario = null;
	private Usuario usuario = null;
	
	//LOGIN
	//Key para guardar el usuario en la session
	public static final String KEY_SESSION_USER = "ss_user";
	
	private String pEmail;
	private String pPassword;
		
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	modeloUsuario = new ModeloUsuario();
    	
    	//Inicializamos el logger
    	try {
			//Fichero configuracion de Log4j
			Properties props = new Properties();
			props.load( getClass().getResourceAsStream("/log4j.properties"));
			PropertyConfigurator.configure(props);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		msg = new Mensaje(Mensaje.MSG_WARNING, "");
		log.info("Login entrando"); // (LOG)
		
		//recoger la sesion
		session = request.getSession();
		Usuario sUsuario = (Usuario)session.getAttribute(KEY_SESSION_USER);
		
		//Usuario logeado
		if ( sUsuario != null || "".equals(sUsuario) ){
			
			System.out.println("    Usuario YA logueado");
			
			//Ir a => index_back.jsp
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			
		//Usuario No logeado o caducada session
		} else {
			System.out.println("    Usuario NO logueado");
			
			//recoger parametros del formulario
			pEmail = request.getParameter("email");
			pPassword = request.getParameter("password");
					
			//validar los datoscomprobando en la BBDD
			usuario = modeloUsuario.getByEmail(pEmail); //No hace falta castearlo ya que el método nos devuelve un objeto de tipo Usuario ya porque en la interface lo declaramos como objeto genérico
			if (usuario != null && usuario.getValidado()==1 ){
				if ( pPassword.equals(usuario.getPassword()) && ROL_ADMIN.equals(usuario.getRol().getNombre()) ){
					
					//salvar session
					session.setAttribute(KEY_SESSION_USER, usuario);
					
					//Ir a => index_back.jsp		
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
					
				}else {
					//Ir a => login.jsp
					msg.setTipo(Mensaje.MSG_WARNING);
					msg.setTexto("El email y/o contrase&ntilde;a incorrecta");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{
				//Ir a => login.jsp
				msg.setTipo(Mensaje.MSG_WARNING);
				msg.setTexto("El usuario no existe");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
		
		}
		
		log.info("Login saliendo"); // (LOG)
		
		request.setAttribute("msg", msg);
		dispatcher.forward(request, response);
	}
	
}



