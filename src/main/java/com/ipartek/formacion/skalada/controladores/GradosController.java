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
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.modelo.ModeloGrado;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class GradosController
 *
 * @author Curso
 */
public class GradosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LOGS
	private static final Logger LOG = Logger.getLogger(GradosController.class);
	private Usuario usuarioSession = null;

	private RequestDispatcher dispatcher = null;
	private ModeloGrado modelo = null;
	private Grado grado = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre;
	private String pDescripcion;
	
	private Mensaje msg = null;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modelo = new ModeloGrado();
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
	
	/**
	 * Recupera los parametros
	 *
	 * @param request
	 * @param response
	 */
	private void getParameters(HttpServletRequest request, HttpServletResponse response) {
		try {
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if ((request.getParameter("id") != null) && !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
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
		request.setAttribute("grados", this.modelo.getAll(null));
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_INDEX);
	}

	/**
	 * Eliminar
	 *
	 * @param request
	 * @param response
	 */
	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if (this.modelo.delete(this.pID)) {
			this.msg = new Mensaje( Mensaje.MSG_DANGER, "Registro eliminado correctamente");			
			LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Elimina el Grado con id: " + pID);
		} else {
			this.msg = new Mensaje( Mensaje.MSG_WARNING, "Error al eliminar el registro [id(" + this.pID + ")]");
			LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al eliminar el Grado con id: " + pID);
		}
		request.getSession().setAttribute("msg", this.msg);
		this.listar(request, response);
	}

	/**
	 * Nuevo grado
	 *
	 * @param request
	 * @param response
	 */
	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.grado = new Grado("Crear nuevo Grado");
		request.setAttribute("grado", this.grado);
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);

	}

	/**
	 * Ver detalle del grado
	 *
	 * @param request
	 * @param response
	 */
	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		this.grado = (Grado) this.modelo.getById(this.pID);
		request.setAttribute("grado", this.grado);
		this.dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros del formulario
		this.getParametersForm(request);

		// Crear Objeto Grado
		this.crearObjeto();

		// Guardar/Modificar Objeto Via
		if (this.pID == -1) {
			if (this.modelo.save(this.grado) != -1) {
				this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Registro creado con exito");
				LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Crea el Grado: " + grado.getNombre() + "[" + grado.getId() + "].");
			} else {
				this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al guardar el nuevo registro");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al crea el Grado.");
			}
		} else {
			if (this.modelo.update(this.grado)) {
				this.msg = new Mensaje( Mensaje.MSG_SUCCESS, "Modificado correctamente el registro [id(" + this.pID + ")]");					
				LOG.info("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Modifica el Grado: " + grado.getNombre() + "[" + grado.getId() + "].");
			} else {
				this.msg = new Mensaje( Mensaje.MSG_DANGER, "Error al modificar el registro [id(" + this.pID + ")]");
				LOG.error("Usuario: '" + usuarioSession.getNombre() + "[" + usuarioSession.getId() + "]' - Error al modificar el Grado: " + grado.getNombre() + "[" + grado.getId() + "].");
			}
		}

//		this.listar(request, response);
//		this.dispatcher.forward(request, response);
		
		request.getSession().setAttribute("msg", this.msg);
		response.sendRedirect(request.getContextPath() + "/backoffice/grados?accion=" + Constantes.ACCION_LISTAR);

	}

	/**
	 * Crea un Objeto {@code Grado} Con los parametros recibidos
	 */
	private void crearObjeto() {
		this.grado = new Grado(this.pNombre);
		this.grado.setId(this.pID);
		this.grado.setDescripcion(this.pDescripcion);
	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice\pages\grados\form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	private void getParametersForm(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pNombre = request.getParameter("nombre");
		this.pDescripcion = request.getParameter("descripcion");
	}

}