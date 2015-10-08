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
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

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
	private ModeloZona modeloZona = null;
	private ModeloSector modeloSector = null;
	private ModeloVia modeloVia = null;

	private Usuario usuario = null;

	private String pEmail;
	private String pPassword;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		modeloUsuario = new ModeloUsuario();
		modeloSector = new ModeloSector();
		modeloZona = new ModeloZona();
		modeloVia = new ModeloVia();

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

		msg = new Mensaje(Mensaje.MSG_WARNING, "");
		LOG.info("Login entrando"); // (LOG)

		// recoger la sesion
		session = request.getSession();
		usuario = (Usuario) session.getAttribute(Constantes.KEY_SESSION_USER);

		// Usuario ya logeado, está en sesión
		if (usuario != null && usuario instanceof Usuario) {
			LOG.info("Usuario YA logueado");
			// Enviar datos a la siguiente pantalla (Index de BackOffice)
			prepararAtributosDashboard(request, response);
			// Ir a => index_back.jsp
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);

		} else {// Usuario No logeado o caducada session
			LOG.info("Usuario NO logueado");
			// recoger parametros del formulario
			getParameters(request, response);
			// Comprobar si existe en la bbdd y cumple las condiciones
			comprobarUsuarioEnBBDD(request, response);
		}

		LOG.info("Login saliendo"); // (LOG)

		request.setAttribute("msg", msg);
		dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request, HttpServletResponse response) {
		pEmail = request.getParameter("email");
		pPassword = request.getParameter("password");
	}

	private void comprobarUsuarioEnBBDD(HttpServletRequest request, HttpServletResponse response) {

		// validar los datos comprobando en la BBDD
		// @Unai: No hace falta castearlo ya que el método nos devuelve un objeto de tipo Usuario ya porque en la interface lo declaramos
		// como objeto genérico
		usuario = modeloUsuario.getByEmail(pEmail);
		if (usuario != null && usuario.getValidado() == 1 && pPassword.equals(usuario.getPassword())) {
			// salvar session
			session.setAttribute(Constantes.KEY_SESSION_USER, usuario);
			request.setAttribute("usuario", usuario);
			// Enviar datos a la siguiente pantalla (Index de BackOffice)
			prepararAtributosDashboard(request, response);
			// Ir a => index_back.jsp
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX);
		} else {
			// Ir a => login.jsp
			msg.setTipo(Mensaje.MSG_WARNING);
			msg.setTexto("El email y/o contrase&ntilde;a incorrecta");
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_LOGIN);
		}

	}

	private void prepararAtributosDashboard(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("totalSectores", modeloSector.getTotal());
		request.setAttribute("totalInvalidados", modeloUsuario.getTotalInvalidados());
		request.setAttribute("totalZonas", modeloZona.getTotal());
		request.setAttribute("totalVias", modeloVia.getTotal());
	}

}
