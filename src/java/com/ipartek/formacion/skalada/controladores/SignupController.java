package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.SendMail;

/**
 * Servlet implementation class RegistroController
 */
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher = null;
	
	private int pAccion = -1;
	
	private String pNombre;
	private String pEmail;
	private String pToken;
	private String pPass;
	private int pId;
	
	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private Mensaje msg = null;
	
	private HashMap<String,Integer> map = null;
    
    @Override
  	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modeloUsuario = new ModeloUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			msg = null;
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
			getParameters(request, response);
			
			switch (pAccion) {
			case Constantes.ACCION_CONFIRM:
				confirmar(request,response);
				break;
			case Constantes.ACCION_MOSTRAR_REGENERAR_PASS:
				mostrar_recuperar_pass(request,response);
				break;
			default:
				signup(request,response);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
			msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error desconocido.");
		}finally{
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
		}
	}
	
	private void mostrar_recuperar_pass(HttpServletRequest request,
			HttpServletResponse response) {
		usuario = (Usuario) modeloUsuario.getByEmail(pEmail);
		request.setAttribute("email", pEmail);
		request.setAttribute("token", usuario.getToken());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_RECUPERAR_PASS);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
			String accion = request.getParameter("action");
			if (accion!=null){
			pAccion = Integer.parseInt(accion);
			}
			if (request.getParameter("id") != null
					&& !"".equalsIgnoreCase(request.getParameter("id"))) {
				pId = Integer.parseInt(request.getParameter("id"));
			}
			pEmail = request.getParameter("email");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void confirmar(HttpServletRequest request,
			HttpServletResponse response) {
	
		map=modeloUsuario.checkEmail(pEmail);
		if (map.get("id")!=null){
			if (map.get("id")!=pId){
				msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al comprobar su correo electronico.");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
			}else{
				if (map.get("validado")!=0){
					msg = new Mensaje(Mensaje.MSG_DANGER, "Su usuario ya esta validado.");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);	
				}
				else{
					if (modeloUsuario.validate(map.get("id"))){
						msg = new Mensaje(Mensaje.MSG_SUCCESS, "Su cuenta ha sido activada. Bienvenid@!");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);	
					}else{
						msg = new Mensaje(Mensaje.MSG_WARNING, "Ha ocurrido un error al validar el usuario, contacte con el administrador de la pagina.");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
					}
				}
			}
		}else{
			msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al comprobar su correo electronico.");
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
		}
		
	}

	private void signup(HttpServletRequest request, HttpServletResponse response) {
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			msg = null;
			request.setCharacterEncoding("UTF-8");
			pAccion=Integer.parseInt((String)request.getParameter("action"));			
			switch (pAccion){
			case Constantes.ACCION_REGISTRAR_USUARIO:
				pNombre = (String)request.getParameter("nombre");
				pEmail = (String)request.getParameter("email");
				pPass = (String)request.getParameter("password");
				pId = -1;
				
				if (!modeloUsuario.checkUser(pNombre, pEmail)){ //Comprobamos si existe el usuario
					//Guardar usuario
					crearUsuario(); //Creamos el objeto Usuario
					if ((pId = modeloUsuario.save(usuario)) == -1){
						msg = new Mensaje( Mensaje.MSG_DANGER , "Ha habido un error al guardar el usuario");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
					}else{
						SendMail mail = new SendMail();
						mail.setDestinatario(usuario.getEmail());
						mail.setAsunto("Confirmacion de registro");
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("{usuario}", usuario.getNombre());
						params.put("{url}", Constantes.URL_VALIDATE+pId+"&email="+pEmail);
						params.put("{contenido}", "Gracias por registrarte. Para activar el usuario y verificar el email, clica en el enlace de debajo.");
						params.put("{txt_btn}", "Activa tu cuenta y logeate");
						mail.setMensaje(mail.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, params)); 
						mail.enviarMail();
						msg = new Mensaje( Mensaje.MSG_SUCCESS , "Por favor revise su correo electronico para validar su usuario");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
					}
				}else{
					msg = new Mensaje(Mensaje.MSG_DANGER, "El usuario ya existe");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);	
				}
				break;
			case Constantes.ACCION_PASS_OLVIDADO:
				pEmail = (String)request.getParameter("email-olvidado");
				usuario=(Usuario) modeloUsuario.getByEmail(pEmail);
				usuario.setToken();
				modeloUsuario.update(usuario);
				if (usuario!=null){
					if (usuario.getValidado()==1){
						SendMail mail = new SendMail();
						mail.setDestinatario(pEmail);
						mail.setAsunto("Recuperacion de password");
						HashMap<String, String> params = new HashMap<String, String>();
						params.put("{usuario}", pEmail);
						params.put("{url}", Constantes.URL_PASS_OLVIDADO+pEmail+"&tkn="+usuario.getToken());
						params.put("{contenido}", "Si has olvidado tu contraseña haz click en el enlace de debajo para cambiarla.");
						params.put("{txt_btn}", "Recupera tu contraseña");
						mail.setMensaje(mail.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, params));
						mail.enviarMail();
						msg = new Mensaje(Mensaje.MSG_INFO, "Se ha enviado un mensaje a su cuenta de correo electronico en el que puede recuperar su contraseña");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
					}else{
						msg = new Mensaje(Mensaje.MSG_DANGER, "El usuario no esta validado");
						dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
					}
				}else{
					msg = new Mensaje(Mensaje.MSG_DANGER, "El usuario no existe");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				}
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				break;
			case Constantes.ACCION_REGENERAR_PASS:
				pEmail = (String)request.getParameter("email");
				pToken = (String)request.getParameter("token");
				pPass = (String)request.getParameter("password");
				
				usuario = (Usuario) modeloUsuario.getByEmail(pEmail);
				if (usuario.getToken().equals(pToken)){
					if (modeloUsuario.resetPass(pEmail, pPass)){
						msg = new Mensaje(Mensaje.MSG_SUCCESS, "Se ha cambiado su contraseña");
					}else{
						msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error al cambiar su contraseña, contacte con el administrador");
					}
				}else{
					msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error.");
				}
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				break;
			default:
				msg = new Mensaje(Mensaje.MSG_DANGER, "Ha ocurrido un error desconocido");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}			
			
		}catch(Exception e){
			e.printStackTrace();
			msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error desconocido.");
		}finally{
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
		}
	}

	private void crearUsuario(){
		rol = new Rol("Usuario");
		rol.setId(2);
		usuario = new Usuario(pNombre, pEmail, pPass, rol);
	}
	
	
	
	
	
	
	
}