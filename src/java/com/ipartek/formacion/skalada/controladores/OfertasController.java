package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Oferta;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.UsuarioInscrito;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloOferta;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class OfertasController
 */
public class OfertasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;
	private ModeloOferta modeloOferta = null;
	private ModeloUsuario modeloUsuario = null;
	private ModeloZona modeloZona = null;
	private Oferta oferta = null;
	private Zona zona = null;
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
	private ArrayList<UsuarioInscrito> pUsuariosInscritos;
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
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.usuario = (Usuario) request.getSession().getAttribute(
				Constantes.KEY_SESSION_USER);
		super.service(request, response);
	}
	/**
	 * @param request
	 *            a
	 * @param response
	 *            a
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws ServletException
	 *             a
	 * @throws IOException
	 *             a
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

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if (request.getParameter("id") != null && !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
			if (request.getParameter("oferta") != null ) {
				this.pOferta = Integer.parseInt(request.getParameter("oferta"));
			}
			if (request.getParameter("user") != null ) {
				this.pUser = Integer.parseInt(request.getParameter("user"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene todas los grados del modelo y carga dispatch con index.jsp
	 *
	 * @see backoffice/pages/grados/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("ofertas", this.modeloOferta.getAll(usuario));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_INDEX);
	}

	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {
		if (this.modeloOferta.delete(this.pID)) {
			request.setAttribute("msg-danger",
					"Registro eliminado correctamente");
		} else {
			request.setAttribute("msg-warning",
					"Error al eliminar el registro [id(" + this.pID + ")]");
		}
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.oferta = new Oferta("");
		request.setAttribute("oferta", this.oferta);
		request.setAttribute("titulo", "Crear nuevo Oferta");
		request.setAttribute("usuarios", this.modeloUsuario.getAll(usuario));
		request.setAttribute("zonas", this.modeloZona.getAll(usuario));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_FORM);

	}

	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		this.oferta = this.modeloOferta.getById(this.pID);
		request.setAttribute("oferta", this.oferta);
		request.setAttribute("titulo", this.oferta.getTitulo().toUpperCase());
		request.setAttribute("usuarios", this.modeloUsuario.getAll(usuario));
		request.setAttribute("zonas", this.modeloZona.getAll(usuario));		
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_OFERTAS_FORM);
	}
	
	private void inscribir(HttpServletRequest request,
			HttpServletResponse response) {
		if (this.modeloOferta.inscribir(pOferta,usuario.getId() )) {
			request.setAttribute("msg-danger",
					"Registro insertado correctamente");
		} else {
			request.setAttribute("msg-warning",
					"Error al insertar el registro [id(" + this.pID + ")]");
		}	
		//TODO no mandarlo a listar sino dejarle en el form
		this.listar(request, response);
	}

	private void desinscribir(HttpServletRequest request,
			HttpServletResponse response) {
		if (this.modeloOferta.desInscribir(pOferta,pUser )) {
			request.setAttribute("msg-danger",
					"Registro eliminado correctamente");
		} else {
			request.setAttribute("msg-warning",
					"Error al eliminar el registro [id(" + this.pID + ")]");
		}
		//TODO no mandarlo a listar sino dejarle en el form
		this.listar(request, response);
	}

	/**
	 * @param request
	 *            a
	 * @param response
	 *            a
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws ServletException
	 *             a
	 * @throws IOException
	 *             a
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros del formulario
		this.getParametersForm(request);

		// Crear Objeto Oferta
		this.crearObjeto();

		// Guardar/Modificar Objeto Oferta
		if (this.pID == -1) {
			if (this.modeloOferta.save(this.oferta) != -1) {
				request.setAttribute("msg-success", "Registro creado con exito");
			} else {
				request.setAttribute("msg-danger",
						"Error al guardar el nuevo registro");
			}
		} else {
			if (this.modeloOferta.update(this.oferta)) {
				request.setAttribute("msg-success",
						"Modificado correctamente el registro [id(" + this.pID
								+ ")]");
			} else {
				request.setAttribute("msg-danger",
						"Error al modificar el registro [id(" + this.pID + ")]");
			}
		}

		this.listar(request, response);

		this.dispatcher.forward(request, response);

	}

	/**
	 * Crea un Objeto {@code Oferta} Con los parametros recibidos
	 */
	private void crearObjeto() {
		//this.oferta = new Oferta();
		this.oferta.setId(this.pID);
		this.oferta.setDescripcion(this.pDescripcion);
		oferta.setPrecio(pPrecio);
		oferta.setZona(pZona);
		oferta.setVisible(pVisible);
		oferta.setFecha_alta(pFecha_alta);
		oferta.setFecha_baja(pFecha_baja);
		oferta.setTitulo(pTitulo);
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
		request.setCharacterEncoding("UTF-8");
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pTitulo = request.getParameter("titulo");
		this.pDescripcion = request.getParameter("descripcion");
		this.pPrecio = Float.parseFloat(request.getParameter("precio"));
		this.pZona = modeloZona.getById(Integer.parseInt(request.getParameter("zona")));
		this.pVisible = (request.getParameter("visible")!=null)?1:0;
		
		this.pFecha_alta = convFechaATimestamp(request.getParameter("fecha_alta"));
		this.pFecha_baja = convFechaATimestamp(request.getParameter("fecha_baja")); 
	}
	
	/**
	 * Convierte una fecha pasada como argumento en Timestamp
	 * @param strFecha: string con la fecha en formato "yyyy-MM-dd"
	 * @return la fecha en Timestamp
	 */
	public Timestamp convFechaATimestamp(String strFecha){
		Timestamp resul = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date;
			date = sdf.parse(strFecha);
			resul = new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return resul;
	}
}