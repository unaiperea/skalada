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
import com.ipartek.formacion.skalada.bean.Grado;
import com.ipartek.formacion.skalada.modelo.ModeloGrado;

/**
 * Servlet implementation class GradosController
 *
 * @author Curso
 */
public class GradosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RequestDispatcher dispatcher = null;
	private ModeloGrado modelo = null;
	private Grado grado = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre;
	private String pDescripcion;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modelo
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modelo = new ModeloGrado();
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

	/**
	 * Recupera los parametros
	 *
	 * @param request
	 * @param response
	 */
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
	 * Obtiene todas los grados del modelo y carga dispatch con index.jsp
	 *
	 * @see backoffice/pages/grados/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("grados", this.modelo.getAll(null));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_INDEX);
	}

	/**
	 * Eliminar
	 *
	 * @param request
	 * @param response
	 */
	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {
		if (this.modelo.delete(this.pID)) {
			request.setAttribute("msg-danger",
					"Registro eliminado correctamente");
		} else {
			request.setAttribute("msg-warning",
					"Error al eliminar el registro [id(" + this.pID + ")]");
		}
		this.listar(request, response);
	}

	/**
	 * Nuevo grado
	 *
	 * @param request
	 * @param response
	 */
	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.grado = new Grado("");
		request.setAttribute("grado", this.grado);
		request.setAttribute("titulo", "Crear nuevo Grado");
		request.setAttribute("metodo", "Guardar");
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);

	}

	/**
	 * Ver detalle del grado
	 *
	 * @param request
	 * @param response
	 */
	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		this.grado = (Grado) this.modelo.getById(this.pID);
		request.setAttribute("grado", this.grado);
		request.setAttribute("titulo", this.grado.getNombre().toUpperCase());
		request.setAttribute("metodo", "Modificar");
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);
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
		if (this.pID == -1) {
			if (this.modelo.save(this.grado) != -1) {
				request.setAttribute("msg-success", "Registro creado con exito");
			} else {
				request.setAttribute("msg-danger",
						"Error al guardar el nuevo registro");
			}
		} else {
			if (this.modelo.update(this.grado)) {
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
	private void getParametersForm(HttpServletRequest request)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		this.pID = Integer.parseInt(request.getParameter("id"));
		this.pNombre = request.getParameter("nombre");
		this.pDescripcion = request.getParameter("descripcion");
	}

}