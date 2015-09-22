package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

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

/**
 * Servlet implementation class RegistroController
 */
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestDispatcher dispatcher = null;
	
	private String pNombre;
	private String pEmail;
	private String pPass;
	
	private Usuario usuario = null;
	private Rol rol = null;
	private ModeloUsuario modeloUsuario = null;
	private Mensaje msg = null;
	
	
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			request.setCharacterEncoding("UTF-8");
			
			pNombre = (String)request.getAttribute("nombre");
			pEmail = (String)request.getAttribute("email");
			pPass = (String)request.getAttribute("password");
			
			if (!modeloUsuario.checkUser(pNombre, pEmail)){ //Comprobamos si existe el usuario
				//Guardar usuario
				crearUsuario(); //Creamos el objeto Usuario
				if (modeloUsuario.save(usuario) == -1){
					msg = new Mensaje( Mensaje.MSG_DANGER , "Ha habido un error al guardar el usuario");
					request.setAttribute("msg", msg);
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				}else{
					//TODO enviar email de confirmación
					msg = new Mensaje( Mensaje.MSG_SUCCESS , "Te has dado de alta con �xito");
					request.setAttribute("msg", msg);
					dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
				}
			}else{
				request.setAttribute("msg-warning", "El usuario ya existe");
				dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SIGNUP);
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			msg = new Mensaje( Mensaje.MSG_WARNING , "Ha habido un error al guardar el usuario. " + e.getMessage());
			request.setAttribute("msg", msg);
		}finally{
			dispatcher.forward(request, response);
		}
	}

	private void crearUsuario(){
		rol = new Rol("Usuario");
		usuario = new Usuario(pNombre, pEmail, pPass, rol);
	}


	
	
	
	
	
	
	
	
	
}