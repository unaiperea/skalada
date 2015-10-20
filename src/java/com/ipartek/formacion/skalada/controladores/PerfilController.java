package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class PerfilController
 */
public class PerfilController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(PerfilController.class);

	private int pID = -1; // ID no valido
	private String pNombre = "";
	private String pEmail = "";
	private String pPassword = "";

	Mensaje msg = new Mensaje(Mensaje.MSG_DANGER, "Error sin determinar");

	private Usuario perfil = null;

	// private Usuario usuario = null;
	ModeloUsuario modeloUsuario = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloUsuario = new ModeloUsuario();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOG.trace("Entramos");
		super.service(req, resp);
		LOG.trace("Salimos");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger usuario de session
		this.perfil = (Usuario) request.getSession().getAttribute(
				Constantes.KEY_SESSION_USER);

		// pasarlo como atributo
		request.setAttribute("perfil", this.perfil);
		request.setAttribute("titulo", "Tu Perfil");

		// ir jsp perfil
		request.getRequestDispatcher(Constantes.VIEW_BACK_PERFIL_FORM).forward(
				request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.pEmail = request.getParameter("email");
		this.pNombre = request.getParameter("nombre");
		this.pPassword = request.getParameter("password");

		this.perfil.setPassword(this.pPassword);
		this.perfil.setNombre(this.pNombre);
		this.perfil.setEmail(this.pEmail);

		if (this.modeloUsuario.update(this.perfil)) {
			this.msg.setTipo(Mensaje.MSG_SUCCESS);
			this.msg.setTexto("Modificado correctamente el registro ");

		} else {
			this.msg.setTexto("Error al modificar el registro ");
		}

		// pasarlo como atributo
		request.setAttribute("perfil", this.perfil);
		request.setAttribute("titulo", "Tu Perfil");
		request.setAttribute("msg", this.msg);

		// ir jsp perfil
		request.getRequestDispatcher(Constantes.VIEW_BACK_PERFIL_FORM).forward(
				request, response);
	}

}
