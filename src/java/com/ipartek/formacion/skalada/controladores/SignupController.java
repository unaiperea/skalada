package com.ipartek.formacion.skalada.controladores;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Rol;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloRol;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.util.EnviarEmails;

/**
 * Servlet implementation class RegistroController
 */
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher = null;
	
	//Por Post
	private String pNombre;
	private String pEmail;
	private String pPass;
	
	//Valor por defecto de un nuevo usuario (Rol = Usuario)
	private int pIdRol = 2; //Deberíamos ponerlo en Constantes por si cambia un día el id
	
	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloRol modeloRol = null;
	private Mensaje msg = null;
	
	//Por Get
	private Usuario usuarioParaValidar = null;
	private String pEmailParaValidar   = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
  	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modeloUsuario = new ModeloUsuario();
    	modeloRol = new ModeloRol();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			//Recibimos la validación
			pEmailParaValidar = request.getParameter("email");
			
			//Accedemos al usuario por el email. No comprobamos nada ya que el email es único en la tabla usuarios
			usuarioParaValidar = (Usuario)modeloUsuario.getByEmail(pEmailParaValidar);
			if (usuarioParaValidar != null){
				usuarioParaValidar.setValidado(1);
				if ( modeloUsuario.update(usuarioParaValidar) ){
					msg = new Mensaje( Mensaje.MSG_SUCCESS , "Enhorabuena " + usuarioParaValidar.getNombre() + ", has sido validado. Ahora puedes logarte");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}else{
					msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al validar el usuario");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{
				msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al validar el usuario");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al validar el usuario. " + e.getMessage());
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			request.setAttribute("msg", msg);
		}finally{
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			request.setCharacterEncoding("UTF-8");
			
			pNombre = (String)request.getParameter("nombre");
			pEmail = (String)request.getParameter("email");
			pPass = (String)request.getParameter("password");
			
			//Comprobamos si existe el usuario
			if (!modeloUsuario.checkUser(pNombre, pEmail)){
				//Creamos el objeto Usuario
				rol = (Rol)modeloRol.getById(pIdRol);
				usuario = new Usuario(pNombre, pEmail, pPass, rol);
				//Guardar en bbdd
				if (modeloUsuario.save(usuario) == -1){
					msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al guardar el usuario");
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				}else{
					//Enviar email de validación
					if (enviarEmail()){
						msg = new Mensaje( Mensaje.MSG_SUCCESS , "Por favor revisa tu email para validar tu registro");
					}else{
						msg = new Mensaje( Mensaje.MSG_SUCCESS , "Error al enviar email, por favor ponte en contacto con nosotros " + EnviarEmails.direccionOrigen);
					}
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{
				request.setAttribute("msg", "El usuario o email ya existe");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al guardar el usuario. " + e.getMessage());
			request.setAttribute("msg", msg);
		}finally{
			request.setAttribute("msg", msg);
			dispatcher.forward(request, response);
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