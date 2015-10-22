package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.modelo.ModeloGrado;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloTipoEscalada;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class ViasController
 *
 * @author Curso
 */
public class ViasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LOGS
	private static final Logger LOG = Logger.getLogger(ViasController.class);
	private Usuario usuarioSession = null;

	private RequestDispatcher dispatcher = null;
	private ModeloVia modeloVia = null;
	private Via via = null;
	private Usuario admin = null;

	private ModeloGrado modeloGrado = null;
	private Grado grado = null;
	private ModeloTipoEscalada modeloTipoEscalada = null;
	private TipoEscalada tipoEscalada = null;
	private ModeloSector modeloSector = null;
	private Sector sector = null;
	private ModeloZona modeloZona = null;
	private ModeloUsuario modeloUsuario = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre;
	private int pLongitud;
	private String pDescripcion;
	private int pIDGrado;
	private int pIDTipoEscalada;
	private int pIDSector;
	private Usuario pUsuario;
	private boolean pValidado;

	private Mensaje msg;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se
	 * suele usar para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloVia = new ModeloVia();
		this.modeloGrado = new ModeloGrado();
		this.modeloTipoEscalada = new ModeloTipoEscalada();
		this.modeloSector = new ModeloSector();
		this.modeloZona = new ModeloZona();
		this.modeloUsuario = new ModeloUsuario();
		this.admin = this.modeloUsuario.getById(Constantes.ROLE_ID_ADMIN);
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
		default:
			this.listar(request, response);
			break;
		}

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if ((request.getParameter("id") != null) && !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene todas las vias del modelo y carga dispatch con index.jsp
	 *
	 * @see backoffice/pages/via/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("vias", this.modeloVia.getAll(this.usuarioSession));
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_INDEX);
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if (this.modeloVia.delete(this.pID)) {
			this.msg = new Mensaje( Mensaje.MSG_DANGER, "Registro eliminado correctamente");
			LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Elimina la Via con id: " + pID);
		} else {
			this.msg = new Mensaje( Mensaje.MSG_WARNING, "Error al eliminar el registro [id(" + this.pID + ")]");
			LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al eliminar la Via con id: " + pID);
		}
		request.getSession().setAttribute("msg", this.msg);
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.via = new Via("Crear nueva Via");
		this.via.setGrado(new Grado(""));
		this.via.setTipoEscalada(new TipoEscalada(""));
		this.via.setSector(this.modeloSector.getById(1));
		// Al crear nuevo registro se muestra el primer sector
		this.via.setUsuario(this.usuarioSession);
		this.via.setValidado(false);
		this.via.setDescripcion("");
		request.setAttribute("via", this.via);
		request.setAttribute("grados", this.modeloGrado.getAll(null));
		request.setAttribute("tipoEscaladas", this.modeloTipoEscalada.getAll(null));
		request.setAttribute("sectores", this.modeloSector.getAllByZona(1));
		request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
		request.setAttribute("usuarios", this.modeloUsuario.getAll(this.admin));
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);

	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		this.via = this.modeloVia.getById(this.pID);
		request.setAttribute("via", this.via);
		request.setAttribute("grados", this.modeloGrado.getAll(null));
		request.setAttribute("tipoEscaladas", this.modeloTipoEscalada.getAll(null));
		request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
		request.setAttribute("sectores", this.modeloSector.getAllByZona(this.via.getSector().getZona().getId()));
		request.setAttribute("usuarios", this.modeloUsuario.getAll(this.admin));
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// recoger parametros del formulario
		this.getParametersForm(request);

		// Si el pIDSector es -1 es porque el sector es null,
		// mandamos mensaje de error advirtiendolo
		if (this.pIDSector == -1) {
			if (this.pID == -1) {
				this.msg = new Mensaje(Mensaje.MSG_DANGER, "Error: No se puede crear una Via sin un Sector");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al crear una Via sin Sector.");
			} else {
				this.msg = new Mensaje(Mensaje.MSG_DANGER, "Error: No se puede modificar una Via sin un Sector");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al modificar una Via sin Sector.");
			}
		} else {
			// si la via tiene un sector, se realiza la creacion o la
			// modificacion de la misma

			// Crear Objeto Via
			this.crearObjetoVia();

			// Guardar/Modificar Objeto Via
			if (this.pID == -1) {
				if (this.modeloVia.save(this.via) != -1) {
					this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Registro creado con exito");
					LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Crea la Via: " + via.getNombre() + "[" + via.getId() + "].");
				} else {
					this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al guardar el nuevo registro");
					LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al crea la Via.");
				}
			} else {
				if (this.modeloVia.update(this.via)) {
					this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Modificado correctamente el registro [id(" + this.pID + ")]");					
					LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Modifica la Via: " + via.getNombre() + "[" + via.getId() + "].");
				} else {
					this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al modificar el registro [id(" + this.pID + ")]");
					LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al modificar la Via: " + via.getNombre() + "[" + via.getId() + "].");
				}
			}

		}// end if: this.pIDSector == -1

		// this.listar(request, response);

		request.getSession().setAttribute("msg", this.msg);
		response.sendRedirect(request.getContextPath() + "/backoffice/vias?accion=" + Constantes.ACCION_LISTAR);

	}

	/**
	 * Crea un Objeto {@code Via} Con los parametros recibidos
	 */
	private void crearObjetoVia() {
		this.grado = this.modeloGrado.getById(this.pIDGrado);
		this.tipoEscalada = this.modeloTipoEscalada
				.getById(this.pIDTipoEscalada);
		this.sector = this.modeloSector.getById(this.pIDSector);

		if (this.pID != -1) {
			this.via = this.modeloVia.getById(this.pID);
			this.via.setGrado(this.grado);
			this.via.setTipoEscalada(this.tipoEscalada);
			this.via.setSector(this.sector);
			this.via.setLongitud(this.pLongitud);
			this.via.setDescripcion(this.pDescripcion);
			this.via.setNombre(this.pNombre);
			this.via.setUsuario(this.pUsuario);
			this.via.setValidado(this.pValidado);
		} else {
			this.via = new Via(this.pNombre, this.pLongitud, this.grado,
					this.tipoEscalada, this.sector, this.pUsuario);
			this.via.setId(this.pID);
			this.via.setDescripcion(this.pDescripcion);
			this.via.setValidado(this.pValidado);
		}
	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice\pages\vias\form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private void getParametersForm(HttpServletRequest request)
			throws UnsupportedEncodingException {

		request.setCharacterEncoding("UTF-8");

		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pNombre = request.getParameter("nombre");
		if ((request.getParameter("longitud") != null)
				&& !"".equals(request.getParameter("longitud"))) {
			this.pLongitud = Integer.parseInt(request.getParameter("longitud"));
		} else {
			this.pLongitud = 0;
		}
		this.pDescripcion = request.getParameter("descripcion");
		this.pIDGrado = Integer.parseInt(request.getParameter("grado"));
		this.pIDTipoEscalada = Integer.parseInt(request
				.getParameter("tipo_escalada"));

		try {
			this.pIDSector = Integer.parseInt(request.getParameter("sector"));
		} catch (Exception e) {
			this.pIDSector = -1;
		}

		if (request.getParameter("creador") != null) {
			this.pUsuario = (this.modeloUsuario.getById(Integer
					.parseInt(request.getParameter("creador"))));
		} else {
			// "usuario" es el user cogido de la sesion
			this.pUsuario = this.usuarioSession;
		}

		if (request.getParameter("validado") != null) {
			this.pValidado = true;
		} else {
			this.pValidado = false;
		}
	}

}
