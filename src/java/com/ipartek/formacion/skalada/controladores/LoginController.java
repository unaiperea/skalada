package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class LoginController
 *
 * @author Curso
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LOGS
	private static final Logger LOG = Logger.getLogger(LoginController.class);

	// Key para guardar el usuario en la session
	private static final ModeloUsuario MODELOUSUARIO = new ModeloUsuario();

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

	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// recoger la sesion
		this.session = request.getSession(true);
		Usuario user_session = (Usuario) this.session.getAttribute(Constantes.KEY_SESSION_USER);

		// Usuario logeado
		if ((user_session != null) && "".equals(user_session.getNombre())) {
			// Ir a => index_back.jsp
			this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);

			// Usuario No logeado o caducada session
		} else {
			// recoger parametros del formulario
			this.getParameters(request);

			// validar los datos
			// comprobamos con la BBDD

			this.usuario = MODELOUSUARIO.getByEmail(this.pEmail);
			if (this.usuario != null) {
				if (this.usuario.getEmail().equals(this.pEmail) && this.usuario.getPassword().equals(this.pPassword)) {
					if (this.usuario.isValidado()) {
						// salvar session
						this.session.setAttribute(Constantes.KEY_SESSION_USER, this.usuario);
						// Ir a => index_back.jsp
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);						
						LOG.info("Usuario: " + usuario.getNombre() + "[id:" + usuario.getId() + "]. Inicia sesión");
					} else {
						this.msg = new Mensaje(Mensaje.MSG_WARNING, "El usuario no ha sido validado todavia, por favor revise su correo electronico");
						this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);						
						LOG.warn("Usuario: " + usuario.getNombre() + "[id:" + usuario.getId() + "] no esta validado.");
					}
				} else {
					// Ir a => login.jsp
					this.msg = new Mensaje(Mensaje.MSG_WARNING, "El email o la contraseña proporcionados no son validos.");
					this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_LOGIN);
					LOG.warn("Usuario: " + usuario.getNombre() + "[id:" + usuario.getId() + "] Contraseña incorrecta.");
				}
			} else {
				this.msg = new Mensaje(Mensaje.MSG_WARNING, "El usuario no existe, si lo desea registrese.");
				this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_SIGNUP);
				LOG.warn("Intento de inicio de sesion de usuario no registrado.");
			}

		}

		request.setAttribute("msg", this.msg);
		this.dispatcher.forward(request, response);

	}

	/**
	 * Recoger los parametros enviados
	 *
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private void getParameters(HttpServletRequest request)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.pEmail = request.getParameter("email");
		this.pPassword = request.getParameter("password");

	}

}