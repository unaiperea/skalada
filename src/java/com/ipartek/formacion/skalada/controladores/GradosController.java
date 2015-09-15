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
 */
public class GradosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RequestDispatcher dispatcher = null;
	private ModeloGrado modelo = null;
	private Grado grado = null;
	
	//parametros
	private int pAccion = Constantes.ACCION_LISTAR;		//Accion por defecto
	private int pID	= -1;		//ID no valido	
	private String pNombre;
	private String pDescripcion;
	
    
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al servlet
     * Se usa para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modelo = new ModeloGrado();   	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recoger parametros
		getParameters(request,response);
		
		//realizar accion solicitada
		switch (pAccion) {
		case Constantes.ACCION_NUEVO:
			nuevo(request,response);
			break;
		case Constantes.ACCION_DETALLE:
			detalle(request,response);
			break;
		case Constantes.ACCION_ELIMINAR:
			eliminar(request,response);
			break;
		default:
			listar(request,response);
			break;
		}
			
		dispatcher.forward(request, response);
	}
		
	private void getParameters(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			pAccion = Integer.parseInt(request.getParameter("accion"));		
			if(request.getParameter("id") != null && !"".equalsIgnoreCase(request.getParameter("id"))){
				pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * Obtiene todas los grados del modelo y carga dispatch con index.jsp
	 * @see backoffice/pages/grados/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("grados", modelo.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_INDEX);		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if(modelo.delete(pID)){
			request.setAttribute("msg-danger", "Registro eliminado correctamente");
		} else {
			request.setAttribute("msg-warning", "Error al eliminar el registro [id(" + pID + ")]");
		}
		listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		grado = new Grado("");
		request.setAttribute("grado", grado);
		request.setAttribute("titulo", "Crear nuevo Grado");
		request.setAttribute("metodo", "Guardar");
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);
		
	}
	
	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		grado = (Grado)modelo.getById(pID);
		request.setAttribute("grado", grado);
		request.setAttribute("titulo", grado.getNombre().toUpperCase());
		request.setAttribute("metodo", "Modificar");
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_GRADOS_FORM);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recoger parametros del formulario
		getParametersForm(request);
		
		//Crear Objeto Grado
		crearObjeto();
		
		//Guardar/Modificar Objeto Via
		if (pID == -1){
			if( modelo.save(grado) != -1){	
				request.setAttribute("msg-success", "Registro creado con exito");
			} else {
				request.setAttribute("msg-danger", "Error al guardar el nuevo registro");
			}
		} else {
			if(modelo.update(grado)){
				request.setAttribute("msg-success", "Modificado correctamente el registro [id(" + pID + ")]");
			} else {
				request.setAttribute("msg-danger", "Error al modificar el registro [id(" + pID + ")]");
			}
		}
		
		listar(request,response);
		
		dispatcher.forward(request, response);
		
	}
	
	/**
	 * Crea un Objeto {@code Grado} Con los parametros recibidos
	 */
	private void crearObjeto() {
		grado = new Grado(pNombre);
		grado.setId(pID);
		grado.setDescripcion(pDescripcion);
	}


	/**
	* Recoger los parametros enviados desde el formulario
	* @see backoffice\pages\grados\form.jsp
	* @param request
	*/
	private void getParametersForm(HttpServletRequest request) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("UTF-8"); //Cuando se cojan Strings desde un formulario hay que decirle que sea en formato UTF8
		
		pID = Integer.parseInt(request.getParameter("id"));
		pNombre = request.getParameter("nombre");	
		pDescripcion = request.getParameter("descripcion");
	}


}