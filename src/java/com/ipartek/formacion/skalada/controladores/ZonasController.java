package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class ZonaController
 *
 * @author Curso
 */
public class ZonasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LOGS
	private static final Logger LOG = Logger.getLogger(ZonasController.class);
	private Usuario usuarioSession = null;

	private RequestDispatcher dispatcher = null;
	private ModeloZona modelo = null;
	private ModeloUsuario mu = null;
	private Zona zona = null;
	private Usuario usuario = null;
	private Usuario admin = null;
	private ArrayList<Usuario> usuarios = null;
	private Usuario creador = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre;
	private int validado;
	private double pLongitud;
	private double pLatitud;
	
	private Mensaje msg = null;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modelo = new ModeloZona();
		this.mu = new ModeloUsuario();
		this.admin = this.mu.getById(Constantes.ROLE_ID_ADMIN);
		this.usuarios = this.mu.getAll(this.admin);
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//recoger usuario de session
		usuarioSession = Utilidades.getSessionUser(req, resp);
		super.service(req, resp);
	}
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		default:
			this.listar(request, response);
			break;
		}

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			this.usuario = (Usuario) request.getSession().getAttribute( Constantes.KEY_SESSION_USER);
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if ((request.getParameter("id") != null) && !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene todas los zonas del modelo y carga dispatch con index.jsp
	 *
	 * @see backoffice/pages/zonas/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("zonas", this.modelo.getAll(this.usuario));
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_ZONAS_INDEX);
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if (this.modelo.delete(this.pID)) {
			this.msg = new Mensaje( Mensaje.MSG_DANGER, "Registro eliminado correctamente");
			LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Elimina la Zona con id: " + pID);
		} else {
			this.msg = new Mensaje( Mensaje.MSG_WARNING, "Error al eliminar el registro [id(" + this.pID + ")]");
			LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al eliminar la Zona con id: " + pID);
		}
		request.getSession().setAttribute("msg", this.msg);
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.zona = new Zona("Crear nuevo Zona", this.usuario);
		this.zona.setFechaCreado();
		this.zona.setFechaModificado();
		request.setAttribute("zona", this.zona);
		request.setAttribute("usuarios", this.usuarios);
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_ZONAS_FORM);

	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		this.zona = this.modelo.getById(this.pID);
		request.setAttribute("zona", this.zona);
		request.setAttribute("usuarios", this.usuarios);
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_ZONAS_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros del formulario
		this.getParametersForm(request);

		// Crear Objeto Zona
		this.crearObjeto();

		// Guardar/Modificar Objeto Via
		if (this.pID == -1) {
			if (this.modelo.save(this.zona) != -1) {
				this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Registro creado con exito");
				LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Crea la Zona: " + zona.getNombre() + "[" + zona.getId() + "].");
			} else {
				this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al guardar el nuevo registro");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al crea la Zona.");
			}
		} else {
			if (this.modelo.update(this.zona)) {
				this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Modificado correctamente el registro [id(" + this.pID + ")]");					
				LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Modifica la Zona: " + zona.getNombre() + "[" + zona.getId() + "].");
			} else {
				this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al modificar el registro [id(" + this.pID + ")]");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al modificar la Zona: " + zona.getNombre() + "[" + zona.getId() + "].");
			}
		}

//		this.listar(request, response);
//		this.dispatcher.forward(request, response);
		
		request.getSession().setAttribute("msg", this.msg);
		response.sendRedirect(request.getContextPath() + "/backoffice/zonas?accion=" + Constantes.ACCION_LISTAR);

	}

	/**
	 * Crea un Objeto {@code Zona} Con los parametros recibidos
	 */
	private void crearObjeto() {
		if (this.creador != null) {
			this.zona = new Zona(this.pNombre, this.creador);
		} else {
			this.zona = new Zona(this.pNombre, this.usuario);
		}
		this.zona.setId(this.pID);
		this.zona.setFechaCreado();
		this.zona.setFechaModificado();
		if (this.validado > 0) {
			this.zona.setValidado(true);
		} else {
			this.zona.setValidado(false);
		}
		this.zona.setLongitud(this.pLongitud);
		this.zona.setLatitud(this.pLatitud);
	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice\pages\zonas\form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private void getParametersForm(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.usuario = (Usuario) request.getSession().getAttribute(Constantes.KEY_SESSION_USER);
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pNombre = request.getParameter("nombre");
		if (request.getParameter("creador") != null) {
			// El creador es un Admin
			this.creador = (this.mu.getById(Integer.parseInt(request.getParameter("creador"))));
		}

		if (request.getParameter("validado") != null) {
			this.validado = 1;
		} else {
			this.validado = 0;
		}

		this.pLongitud = Double.parseDouble(request.getParameter("longitud"));
		this.pLatitud = Double.parseDouble(request.getParameter("latitud"));
	}

}