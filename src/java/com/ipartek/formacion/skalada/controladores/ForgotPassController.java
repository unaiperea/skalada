package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloRol;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.EnviarEmails;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class ForgotPassController
 */
public class ForgotPassController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher = null;
	
	String pEmail;
	String pPass;
	private String pToken;
	
	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloRol modeloRol = null;
	private Mensaje msg = null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPassController() {
        super();
        modeloUsuario = new ModeloUsuario();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Recibo parámetros del mail para reenviarle al formulario de 'Cambio de Pass'
		msg.setTipo(Mensaje.MSG_DANGER);
		msg.setTexto("Error sin definir");

		try{
			pEmail = (String)request.getParameter("email-forget");
			
			//Accedo al usuario mediante el email recibido
			usuario = (Usuario)modeloUsuario.getByEmail(pEmail);
			if (usuario != null){
				//Generar token para el usuario
				String token = Utilidades.getCadenaAlfanumAleatoria(250);
				usuario.setToken(token);
				modeloUsuario.update(usuario);
				if ( enviarEmail() ){
					//Le envío un email de validación para vaya al formulario de cambiar la contraseña
					msg = new Mensaje( Mensaje.MSG_SUCCESS , "Por favor revisa tu Email para reestablecer la contraseña");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}else{
					msg = new Mensaje( Mensaje.MSG_DANGER , "Error al enviar email, por favor ponte en contacto con nosotros " + EnviarEmails.direccionOrigen);
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{ //Si no existe
				msg.setTexto("Email no registrado: "+ pEmail);
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Recibo la Pass del formulario recuperar.jsp y la cambio en la bbdd
		
		msg = new Mensaje( Mensaje.MSG_DANGER , "Error sin definir");
		
		try{
			pEmail = (String)request.getParameter("email");
			pPass = (String)request.getParameter("password");
			pToken = (String)request.getParameter("token");
			
			//Accedo al usuario mediante el email recibido
			usuario = (Usuario)modeloUsuario.getByEmail(pEmail);
			
			if(usuario!=null){
				//Comprobar no se haya cambiado el email. Luego se podría coger la IP del usuario y guardarla, etc, ...
				if ( pToken.equals(usuario.getToken()) ){
					//Guardo Pass y modifico en la bbdd
					usuario.setPassword(pPass);
					if (modeloUsuario.update(usuario)){
						//Enviar email de validación de pass
							msg.setTexto("Contraseñas modificadas correctamente");
							msg.setTipo(Mensaje.MSG_SUCCESS);
						}else{
							//usuario no existe
							msg.setTexto("usuario no existe");
							msg.setTipo(Mensaje.MSG_WARNING);
						}
				}
			}else{
				//usuario no existe
				msg.setTexto("usuario no existe");
				msg.setTipo(Mensaje.MSG_WARNING);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			request.setAttribute("msg", msg);
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN );
			dispatcher.forward(request, response);
		}
			
	}
	
	private boolean enviarEmail(){
		boolean resul = false;
		//url para validar el registro del usuario. Llamará a este mismo controlador por GET pasando el email del usuario y el churro de token
		String url = Constantes.SERVER + Constantes.ROOT_BACK + Constantes.VIEW_BACK_RECUPERAR_PASS+"?email="+usuario.getEmail()+"&token="+usuario.getToken();
		String contenido = "Has solicitado cambiar la contraseña, si no ha sido Ud por favor póngase en contacto con nosotros, clica en el enlace de debajo.";
		String submitButtonText = "Cambiar contraseña";
		
		try{
			EnviarEmails correo = new EnviarEmails();
			
			//Parámetros para la plantilla
			HashMap<String, String> parametros = new HashMap<String, String>(); //Creamos un hashmap para sustituir todos los campos que queramos. Un array de dos dimensiones para guardar todos los items de la p�gina con la key su valor

			parametros.put("{usuario}", usuario.getNombre());
			parametros.put("{url}", url);
			parametros.put("{contenido}", contenido);
			parametros.put("{btn_submit_text}", submitButtonText);
			
			//Configurar correo electrónico
			correo.setDireccionFrom("skalada.ipartek@gmail.com"); //Sin espacios
			correo.setDireccionDestino(usuario.getEmail()); //unaiperea@gmail.com
			correo.setMessageSubject("Confirmar registro usuario");
			correo.setMessageContent( //Para HTML y texto plano
					correo.generarPlantilla(Constantes.EMAIL_TEMPLATE_REGISTRO, parametros)
					); //Le paso la ruta de la plantilla con formato HTML y un HashMap y me lo devuelve ya montado
			//correo.setMessageText(cuerpo); //Para texto plano			
			
			/*	O DE ESTA FORMA leyendo el fichero directamente desde una ubicación dentro de Webcontent
			archivo = new File (Constantes.EMAIL_TEMPLATE_REGISTRO);
			cuerpo = FileUtils.readFileToString(archivo, "UTF-8");
			cuerpo = cuerpo.replace("{usuario}", usuario); //Los {} pueden ser $ &, cualquier símbolo
			cuerpo = cuerpo.replace("{url}", url);*/
			
			
			//Enviar correo correo electrónico. Va por GET
			resul = correo.enviar();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resul;
	}
	
	
}
