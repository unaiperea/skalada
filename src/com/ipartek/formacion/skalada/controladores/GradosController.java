package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

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
	private int pAccion = Constantes.ACCION_LISTAR; //accion por defecto
	private int pID     = -1; //ID no valido.
	private String pNombre;
	private String pDescripcion;
	
	
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al Servlet
     * Se suele usar para crear el modelo
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
			
		
		//recoger parametro ACCION y ID
		getParameters(request, response);
		
		//realizar Accion solicitada
		switch (pAccion) {
			case Constantes.ACCION_DETALLE:
				detalle(request, response);
				break;
			case Constantes.ACCION_NUEVO:
				nuevo(request, response);
				break;
			case Constantes.ACCION_ELIMINAR:
				eliminar(request, response);
				break;		
			default:
				listar(request, response);
				break;
		}
		
		dispatcher.forward(request, response); // dispatcher ya viene dado según donde haya entrado en el switch
	}
	

	/**
	 * Obtiene todas los grados del modelo y carga con dispatch con backoffice/pages/grados/form.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("grados", modelo.getAll() );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_GRADOS_INDEX );
		
	}



	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {

		
		if ( modelo.delete(pID)){
			request.setAttribute("msg", "Registro Eliminado");
		}else{
			request.setAttribute("msg", "ERROR: Registro NO Eliminado " + pID );
		}
		
		listar(request, response);
		
	}



	private void nuevo(HttpServletRequest request, HttpServletResponse response) {

		grado = new Grado("nuevo"); 
		request.setAttribute("grado", grado );
		request.setAttribute("titulo", "Crear nuevo registro" );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_GRADOS_FORM);
	}



	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		
		grado = (Grado)modelo.getById(pID);
		request.setAttribute("grado", grado );
		request.setAttribute("titulo", grado.getNombre() );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_GRADOS_FORM);
		
	}



	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {
		
		try{
			//recoger accion a realizar
			String sAccion = request.getParameter("accion");
			pAccion = Integer.parseInt(sAccion);			
		
			//recoger Identificador del Grado	
			String sID = request.getParameter("id");
			if( sID != null && !"".equalsIgnoreCase(sID)){
				pID = Integer.parseInt(sID);
			}else{
				pID = 1;
			}
			
		}catch(Exception e){
			pAccion = Constantes.ACCION_LISTAR;
			pID = -1;
			e.printStackTrace();
		}	
		
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			getParametersFormulario(request, response); //Recogemos los parámetros que nos han enviado desde el formulario
			
			crearObjeto(); //Le metemos los parámetros al nuevo objeto
			
			if (grado.getId() == -1){
				modelo.save(grado); //guardamos el nuevo reg
				request.setAttribute("msg", "Registro creado con éxito"); //Enviamos un mensaje al cliente en el atributo msg
			}else{
				modelo.update(grado); //hacemos update del existente
				request.setAttribute("msg", "Registro modificado con éxito"); //Enviamos un mensaje al cliente en el atributo msg
			}
			
			listar(request, response);
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("msg", "Error recibiendo parametros");
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_GRADOS_FORM );
		}finally{
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * Crea un objeto {@code Object} con los parámetros recibidos
	 */
	private void crearObjeto() {
		grado = new Grado(pNombre);
		grado.setId(pID);
		grado.setDescripcion(pDescripcion);
	}
	
	/**
	 * Recoger los parámetros enviados desde el formulario:
	 * @see backoffice\pages\vias\form.jsp
	 * @param request
	 * @param response
	 */
	private void getParametersFormulario(HttpServletRequest request,
			HttpServletResponse response) {
		
		pID = Integer.parseInt(request.getParameter("id"));
		pNombre = request.getParameter("nombre"); //Según el atributo name="nombre" del input
		pDescripcion = request.getParameter("descripcion");
		
	}

}