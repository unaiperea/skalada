package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	
	private static final Logger log = Logger.getLogger(LoginController.class);
	
	private static final long serialVersionUID = 1L;
	
	//Key oara guardar el usuario en la session
	public static final String KEY_SESSION_USER = "ss_user";
	private static final ModeloUsuario modeloUsuario = new ModeloUsuario();
       
	private RequestDispatcher dispatcher = null;
	private HttpSession session = null;
	
	private String pEmail = "";
	private String pPassword = "";
	
	private Usuario usuario = null;
	private Mensaje msg = null;
	

		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.info("Entrando....");
		
		//recoger la sesion
		session = request.getSession(true);
		Usuario user_session = (Usuario)session.getAttribute(KEY_SESSION_USER);
		
		//Usuario logeado
		if ( user_session != null && "".equals(user_session.getNombre()) ){		
			//Ir a => index_back.jsp		
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
			
		//Usuario No logeado o caducada session
		} else {
			//recoger parametros del formulario
			getParameters(request);
					
			//validar los datos
			//comprobamos con la BBDD
			
			usuario = (Usuario) modeloUsuario.getByEmail(pEmail);
			if (usuario !=null){
				if(usuario.getEmail().equals(pEmail)&&usuario.getPassword().equals(pPassword)){
					if (usuario.getValidado()!=0){
					//salvar session
					session.setAttribute(KEY_SESSION_USER, usuario );
					//Ir a => index_back.jsp		
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
					}else{
						msg = new Mensaje(Mensaje.MSG_WARNING, "El usuario no ha sido validado todavia, por favor revise su correo electronico");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
					}
				} else {
					//Ir a => login.jsp
					msg = new Mensaje(Mensaje.MSG_WARNING, "El email o la contraseña proporcionados no son validos.");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{
				msg = new Mensaje(Mensaje.MSG_WARNING, "El usuario no existe, si lo desea registrese.");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
			}
			
		}
		
		log.info("Saliendo...");
		
		request.setAttribute("msg", msg);		
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



