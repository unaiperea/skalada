package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Oferta;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloOferta;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;
import com.ipartek.formacion.skalada.util.UtilidadesFecha;

/**
 * Servlet implementation class OfertasController
 */
public class OfertasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(OfertasController.class);
	private RequestDispatcher dispatcher = null;
	private ModeloOferta modeloOferta = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;
	private Oferta oferta = null;
	private Usuario usuario = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pTitulo;
	private String pDescripcion;
	private float pPrecio;
	private Timestamp pFecha_alta;
	private Timestamp pFecha_baja;
	private int pVisible;
	private Zona pZona;
	private int pOferta;
	private int pUser;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloOferta = new ModeloOferta();
		this.modeloUsuario = new ModeloUsuario();
		this.modeloZona = new ModeloZona();
		LOG.trace("Entrando....");
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.usuario = (Usuario) request.getSession().getAttribute(
				Constantes.KEY_SESSION_USER);
		super.service(request, response);
		LOG.trace("Cogiendo usuario de session... (ID)=" + usuario.getId());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.trace("Entrando de doGet");

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
		case Constantes.ACCION_INSCRIBIR:
			this.inscribir(request, response);
			break;
		case Constantes.ACCION_DESINSCRIBIR:
			this.desinscribir(request, response);
			break;
		default:
			this.listar(request, response);
			break;
		}
		LOG.trace("Saliendo de doGet");
		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Cogiendo parámetros de GET...");
		try {
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if (request.getParameter("id") != null
					&& !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
				LOG.debug("Cogiendo parámetro de GET: id="+pID);
			}
			if (request.getParameter("oferta") != null) {
				this.pOferta = Integer.parseInt(request.getParameter("oferta"));
				LOG.debug("Cogiendo parámetro de GET: oferta="+pOferta);
			}
			if (request.getParameter("user") != null) {
				this.pUser = Integer.parseInt(request.getParameter("user"));
				LOG.debug("Cogiendo parámetro de GET: user="+pUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("Cogiendo parámetros de GET " + e.getMessage());
		}

	}

	/**
	 * Obtiene todas las ofertas del modelo y carga dispatch con index.jsp
	 *
	 * @see backoffice/pages/ofertas/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("ofertas", this.modeloOferta.getAll(this.usuario));
		LOG.trace("Listando ofertas");
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_INDEX);
	}

	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Eliminando oferta");
		if (this.modeloOferta.delete(this.pID)) {
			request.setAttribute("msg-danger",
					"Registro eliminado correctamente");
			LOG.info("Eliminando oferta correctamente (ID)="+pID + " " + pTitulo);
		} else {
			request.setAttribute("msg-warn",
					"Error al eliminar el registro [id(" + this.pID + ")]" + " " + pTitulo);
			LOG.warn("Error eliminando oferta (ID)="+pID  + " " + pTitulo);
		}
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		LOG.trace("Creando oferta");
		this.oferta = new Oferta("");
		request.setAttribute("oferta", this.oferta);
		request.setAttribute("titulo", "Crear nueva Oferta");
		request.setAttribute("usuarios",
				this.modeloUsuario.getAll(this.usuario));
		request.setAttribute("zonas", this.modeloZona.getAll(this.usuario));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_FORM);

	}

	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Detalle de oferta");
		this.oferta = this.modeloOferta.getById(this.pID);
		request.setAttribute("oferta", this.oferta);
		request.setAttribute("titulo", this.oferta.getTitulo().toUpperCase());
		request.setAttribute("usuarios",
				this.modeloUsuario.getAll(this.usuario));
		request.setAttribute("zonas", this.modeloZona.getAll(this.usuario));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_FORM);
	}

	private void inscribir(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Inscribiendo usuario en oferta");
		if (this.modeloOferta.inscribir(this.pOferta, this.usuario.getId())) {
			request.setAttribute("msg-danger",
					"Registro insertado correctamente");
			LOG.info("Usuario inscrito (OFERTA)="+pOferta+", (USUARIO)="+this.usuario.toString());
		} else {
			request.setAttribute("msg-warn",
					"Error al insertar el registro [id(" + this.pID + ")]");
			LOG.warn("Error inscribiendo a usuario en oferta (OFERTA)="+pOferta+", (USUARIO)="+this.usuario.toString());
		}
		this.listar(request, response);
	}

	private void desinscribir(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.trace("Desinscribiendo usuario en oferta");
		if (this.modeloOferta.desInscribir(this.pOferta, this.pUser)) {
			request.setAttribute("msg-danger",
					"Registro eliminado correctamente");
			LOG.info("Usuario DES-inscrito (OFERTA)="+pOferta+", (USUARIO)="+this.usuario.toString());
		} else {
			request.setAttribute("msg-warn",
					"Error al eliminar el registro [id(" + this.pID + ")]");
			LOG.warn("Error desinscribiendo a usuario en oferta (OFERTA)="+pOferta+", (USUARIO)="+this.usuario.toString());
		}
		this.listar(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.trace("Entrando a doPost");

		// recoger parametros del formulario
		this.getParametersForm(request);

		// Crear Objeto Oferta
		this.crearObjeto();

		// Guardar/Modificar Objeto Oferta
		if (this.pID == -1) {
			if (this.modeloOferta.save(this.oferta) != -1) {
				request.setAttribute("msg-success", "Registro creado con exito");
				LOG.info("Oferta salvada con exito " + this.oferta.toString() );
			} else {
				request.setAttribute("msg-danger",
						"Error al guardar el nuevo registro");
				LOG.warn("Error al salvar oferta " + this.oferta.toString() );
			}
		} else {
			if (this.modeloOferta.update(this.oferta)) {
				request.setAttribute("msg-success",
						"Modificado correctamente el registro [id(" + this.pID
						+ ")]");
				LOG.info("Oferta Modificada " + this.oferta.toString() );
				} else {
				request.setAttribute("msg-danger",
						"Error al modificar el registro [id(" + this.pID + ")]");
				LOG.warn("Error al modificar " + this.oferta.toString() );
			}
		}

		this.listar(request, response);
		LOG.trace("Saliendo de doPost");
		this.dispatcher.forward(request, response);

	}

	/**
	 * Crea un Objeto {@code Oferta} Con los parametros recibidos
	 */
	private void crearObjeto() {
		LOG.trace("Entrando a crearObjeto");
		this.oferta.setId(this.pID);
		this.oferta.setDescripcion(this.pDescripcion);
		this.oferta.setPrecio(this.pPrecio);
		this.oferta.setZona(this.pZona);
		this.oferta.setVisible(this.pVisible);
		this.oferta.setFecha_alta(this.pFecha_alta);
		this.oferta.setFecha_baja(this.pFecha_baja);
		this.oferta.setTitulo(this.pTitulo);
		LOG.trace("Saliendo de crearObjeto");
	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice\pages\ofertas\form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	private void getParametersForm(HttpServletRequest request)
			throws UnsupportedEncodingException {
		LOG.trace("Recogiendo parametros del formulario");
		request.setCharacterEncoding("UTF-8");
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pTitulo = request.getParameter("titulo");
		this.pDescripcion = request.getParameter("descripcion");
		this.pPrecio = Float.parseFloat(request.getParameter("precio"));
		this.pZona = this.modeloZona.getById(Integer.parseInt(request
				.getParameter("zona")));
		this.pVisible = (request.getParameter("visible") != null) ? 1 : 0;

		this.pFecha_alta = UtilidadesFecha.convFechaATimestamp(request
				.getParameter("fecha_alta"));
		this.pFecha_baja = UtilidadesFecha.convFechaATimestamp(request
				.getParameter("fecha_baja"));
	}

	
}