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
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.modelo.ModeloVia;

/**
 * Servlet implementation class ViasController
 */
public class ViasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestDispatcher dispatcher = null;
	private ModeloVia modelo = null;
	private Via via = null;
	
	//parametros
	private int pAccion = Constantes.ACCION_LISTAR;		//Accion por defecto
	private int pID	= -1;		//ID no valido	
	private String pNombre;
	private Grado pGrado;
	private int pLongitud;
	private String pDescripcion;
	private String pImagen;
	
    
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modelo = new ModeloVia();
    	
    	// datos de prueba
//    	generateViaMocks(modelo);
    	
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
	 * Obtiene todas las vias del modelo y carga dispatch con index.jsp
	 * @see backoffice/pages/via/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("vias", modelo.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_INDEX);		
		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if(modelo.delete(pID)){
			request.setAttribute("msg-danger", "Se a eliminado la Via correctamente");
		} else {
			request.setAttribute("msg-warning", "Error al eliminar la Via [id(" + pID + ")]");
		}
		listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
//		via = new Via("");
		request.setAttribute("via", via);
		request.setAttribute("titulo", "Crear nueva Via");
		request.setAttribute("metodo", "Guardar");
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);
		
	}
	
	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		via = (Via)modelo.getById(pID);
		request.setAttribute("via", via);
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recoger parametros del formulario
		getParametersForm(request);
		
		//Crear Objeto Via
		crearObjetoVia();
		
		//Guardar/Modificar Objeto Via
		if (pID == -1){
			if( modelo.save(via) != -1){	
				request.setAttribute("msg-success", "A&ntilde;adida correctamente la nueva via");
			} else {
				request.setAttribute("msg-danger", "Error al guardar la nueva Via");
			}
		} else {
			if(modelo.update(via)){
				request.setAttribute("msg-success", "Modificada correctamente la Via ");
			} else {
				request.setAttribute("msg-danger", "Error al modificar la Via ");
			}
		}
		
		listar(request,response);
		
		dispatcher.forward(request, response);
		
	}
	
	/**
	 * Crea un Objeto {@code Via} Con los parametros recibidos
	 */
	private void crearObjetoVia() {
//		via = new Via(pNombre);
//		via.setId(pID);
//		via.setGrado(pGrado);
//		via.setLongitud(pLongitud);
//		via.setDescripcion(pDescripcion);
	}


	/**
	* Recoger los parametros enviados desde el formulario
	* @see backoffice\pages\vias\form.jsp
	* @param request
	 * @throws UnsupportedEncodingException 
	*/
	private void getParametersForm(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		pID = Integer.parseInt(request.getParameter("id"));
		pNombre = request.getParameter("nombre");
//		pGrado = Grado.valueOf(request.getParameter("grado"));
		if(request.getParameter("longitud") != null && !"".equals(request.getParameter("longitud"))){
			pLongitud = Integer.parseInt(request.getParameter("longitud"));
		} else {
			pLongitud = 0;
		}		
		pDescripcion = request.getParameter("descripcion");
		pImagen = request.getParameter("imagen");
	
	}


}

