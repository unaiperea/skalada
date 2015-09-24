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
	private int pAccion = -1;
	
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
	private String pPassRecuperar = "";
		
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); //Cuando se cojan Strings desde un formulario hay que decirle que sea en formato UTF8
		
		pAccion = Integer.parseInt(request.getParameter("accion"));
		
		switch (pAccion){
		case Constantes.ACCION_RECUPERAR:
			recuperarPassword(request);
			
			break;
		case Constantes.ACCION_LOGIN:
			login(request, response); //Quitar response???
			
			break;
		}
		
		dispatcher.forward(request, response);

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
	
	private void recuperarPassword(HttpServletRequest request){
		pEmailRecuperar = (String)request.getParameter("email");
		pPassRecuperar = (String)request.getParameter("password");
		
		//Accedo al usuario mediante el email recibido
		usuario = (Usuario)modeloUsuario.getByEmail(pEmailRecuperar);
		if (pPassRecuperar == usuario.getPassword()){
			if (enviarEmail()){
				msg = new Mensaje( Mensaje.MSG_SUCCESS , "Por favor revisa tu email para confirmar tu cambio de contraseña");
			}else{
				msg = new Mensaje( Mensaje.MSG_SUCCESS , "Error al enviar email, por favor ponte en contacto con nosotros " + EnviarEmails.direccionOrigen);
			}
			request.setAttribute("msg", msg);
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
		}
		
	}
	
	private boolean enviarEmail(){
		boolean resul = false;
		File archivo = null;
		String linea   = "";
		String cuerpo  = "";
		
		EnviarEmails correo = new EnviarEmails();
		correo.setDireccionFrom("skalada.ipartek@gmail.com"); //Sin espacios
		correo.setDireccionDestino(usuario.getEmail()); //unaiperea@gmail.com
		correo.setMessageSubject("Por favor valida tu email");
		
		//Leemos la plantilla del fichero registro.html (email)
		try {		
			archivo = new File (Constantes.TEST_EMAIL_TEMPLATE_REGISTRO);
			//fr = new FileReader(archivo);
			//br = new BufferedReader(fr);
			
			cuerpo = FileUtils.readFileToString(archivo, "UTF-8");
			//while( (linea=br.readLine()) != null){
			//	cuerpo += linea;
			//}
		}catch (Exception e) {
			e.printStackTrace();
			resul = false;
			return resul;
		}
		
		//Creamos un hashmap para sustituir todos los campos que queramos. Un array de dos dimensiones para guardar todos los items de la p�gina con la key su valor
	    HashMap<String, String> mapa = new HashMap<String, String>();
	    mapa.put("{usuario}", usuario.getNombre());
	    mapa.put("{url}", Constantes.SERVER + Constantes.CONTROLLER_SIGNUP+"?email="+usuario.getEmail());
		
	    //recogemos los valores de las keys metidas en el hashmap
	    Iterator it = mapa.entrySet().iterator();
	    while(it.hasNext()){
	    	Map.Entry entrada = (Map.Entry)it.next();
	    	cuerpo = cuerpo.replace(entrada.getKey().toString(), entrada.getValue().toString()); //Los {} pueden ser $, &, ... cualquier símbolo
	    }

		correo.setMessageContent( cuerpo ); //Para html y texto plano
		//correo.setMessageText(cuerpo); //Para texto plano
		
		resul = correo.enviar();
		
		//Validar lo recogeremos en este mismo controlador y por GET
		return resul;
	}
	
}



