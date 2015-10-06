package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

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
 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// @Unai: Se pone el nombre de la clase (LOG)
	private final static Logger LOG = Logger.getLogger(LoginController.class);

	private static final String ROL_ADMIN = "Administrador";

	private RequestDispatcher dispatcher = null;
	private HttpSession session = null;

	private Mensaje msg = null;

	private ModeloUsuario modeloUsuario = null;
	private Usuario usuario = null;

	private String pEmail;
	private String pPassword;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		this.modeloUsuario = new ModeloUsuario();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.msg = new Mensaje(Mensaje.MSG_WARNING, "");
		LOG.info("Login entrando"); // (LOG)

		// recoger la sesion
		this.session = request.getSession();
		this.usuario = (Usuario) this.session
				.getAttribute(Constantes.KEY_SESSION_USER);

		// Usuario logeado
		if (this.usuario != null && this.usuario instanceof Usuario) {
			LOG.info("Usuario YA logueado");

			// Ir a => index_back.jsp
			this.dispatcher = request
					.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);

			// Usuario No logeado o caducada session
		} else {
			LOG.info("Usuario NO logueado");

			// recoger parametros del formulario
			this.pEmail = request.getParameter("email");
			this.pPassword = request.getParameter("password");

			// validar los datos comprobando en la BBDD
			// @Unai: No hace falta castearlo ya que el método nos devuelve un
			// objeto de tipo Usuario ya porque en la interface lo declaramos
			// como objeto genérico
			this.usuario = this.modeloUsuario.getByEmail(this.pEmail);
			if (this.usuario != null && this.usuario.getValidado() == 1
					&& this.pPassword.equals(this.usuario.getPassword())) {

				// salvar session
				this.session.setAttribute(Constantes.KEY_SESSION_USER,
						this.usuario);

				// Ir a => index_back.jsp
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);

			} else {
				// Ir a => login.jsp
				this.msg.setTipo(Mensaje.MSG_WARNING);
				this.msg.setTexto("El email y/o contrase&ntilde;a incorrecta");
				this.dispatcher = request
						.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
			}
		}

		LOG.info("Login saliendo"); // (LOG)

		request.setAttribute("msg", this.msg);
		this.dispatcher.forward(request, response);
	}

}
