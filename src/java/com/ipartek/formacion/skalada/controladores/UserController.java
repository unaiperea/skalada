package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import com.ipartek.formacion.skalada.listener.ListenerSession;
import com.ipartek.formacion.skalada.modelo.ModeloRol;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;

/**
 * Servlet implementation class UserController
 *
 * @author Curso
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloRol modeloRol = null;
	private Usuario usuario = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre = "";
	private String pEmail = "";
	private String pPassword = "";
	private int pValidado = Constantes.USER_NO_VALIDATE;
	private int pRolId = -1; // Identificador del Rol

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloUsuario = new ModeloUsuario();
		this.modeloRol = new ModeloRol();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros
		this.getParameters(request, response);

		// realizar accion solicitada
		switch (this.pAccion) {
		case Constantes.ACCION_NUEVO:
			this.nuevo(request, response);
			break;
		case Constantes.ACCION_DETALLE:
			this.detalle(request, response);
			break;
		case Constantes.ACCION_ELIMINAR:
			this.eliminar(request, response);
			break;
		case Constantes.ACCION_NO_VALIDADOS:
			this.noValidados(request, response);
			break;
		case Constantes.ACCION_CONECTADOS:
			this.conectados(request, response);
			break;
		default:
			this.listar(request, response);
			break;
		}

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if ((request.getParameter("id") != null)
					&& !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene todas los usuarios del modelo y carga dispatch
	 *
	 * @see
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("usuarios", this.modeloUsuario.getAll(null));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_USUARIOS_INDEX);
	}

	private void noValidados(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("usuarios", this.modeloUsuario.getNoValidados());
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_USUARIOS_INDEX);
	}

	private void conectados(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("usuarios", ListenerSession.session_users);
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_USUARIOS_INDEX);
	}

	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {
		Mensaje msg = new Mensaje(Mensaje.MSG_DANGER, "Error sin determinar");
		if (this.modeloUsuario.delete(this.pID)) {
			msg.setTipo(Mensaje.MSG_SUCCESS);
			msg.setTexto("Registro eliminado correctamente");
		} else {
			msg.setTexto("Error al eliminar el registro [id(" + this.pID + ")]");
		}
		request.setAttribute("msg", msg);
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {

		Rol rol = new Rol(Constantes.ROLE_USER);
		this.usuario = new Usuario(this.pNombre, this.pEmail, this.pPassword,
				rol);
		request.setAttribute("usuario", this.usuario);
		request.setAttribute("titulo", "Crear nuevo Usuario");

		request.setAttribute("roles", this.modeloRol.getAll(null));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_USUARIOS_FORM);

	}

	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		this.usuario = (Usuario) this.modeloUsuario.getById(this.pID);
		request.setAttribute("usuario", this.usuario);
		request.setAttribute("titulo", this.usuario.getNombre().toUpperCase());

		request.setAttribute("roles", this.modeloRol.getAll(null));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_USUARIOS_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros del formulario
		this.getParametersForm(request);

		// Crear Objeto Grado
		this.crearObjeto();

		// Guardar/Modificar Objeto Via
		Mensaje msg = new Mensaje(Mensaje.MSG_DANGER, "Erro sibn determinar");
		if (this.pID == -1) {
			if (this.modeloUsuario.save(this.usuario) != -1) {
				msg.setTipo(Mensaje.MSG_SUCCESS);
				msg.setTexto("Registro creado con exito");
			} else {
				msg.setTexto("Error al guardar el nuevo registro");
			}
		} else {
			if (this.modeloUsuario.update(this.usuario)) {
				msg.setTipo(Mensaje.MSG_SUCCESS);
				msg.setTexto("Modificado correctamente el registro [id("
						+ this.pID + ")]");
			} else {
				msg.setTexto("Error al modificar el registro [id(" + this.pID
						+ ")]");
			}
		}
		request.setAttribute("msg", msg);
		this.listar(request, response);

		this.dispatcher.forward(request, response);

	}

	/**
	 * Crea un Objeto {@code Grado} Con los parametros recibidos
	 */
	private void crearObjeto() {

		Rol rol = (Rol) this.modeloRol.getById(this.pRolId);
		this.usuario = new Usuario(this.pNombre, this.pEmail, this.pPassword,
				rol);
		this.usuario.setValidado(this.pValidado);
		this.usuario.setId(this.pID);
	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice/pages/usuarios/form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private void getParametersForm(HttpServletRequest request)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pNombre = request.getParameter("nombre");
		this.pRolId = Integer.parseInt(request.getParameter("rol"));

		if (request.getParameter("validado") != null) {
			this.pValidado = Integer.parseInt(request.getParameter("validado"));
		} else {
			this.pValidado = Constantes.USER_NO_VALIDATE;
		}

		this.pEmail = request.getParameter("email");
		this.pPassword = request.getParameter("password");

	}
}