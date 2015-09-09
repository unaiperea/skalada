package com.ipartek.formacion.skalada.controladores;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.Grado;
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
	private int pAccion = Constantes.ACCION_LISTAR; //accion por defecto
	private int pID     = -1; //ID no valido
	private String pNombre;
	private int    pLongitud;
	private Grado  pGrado;
	private String pDescripcion;
	
	
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al Servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {    	
    	super.init(config);
    	modelo = new ModeloVia();
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
		
		dispatcher.forward(request, response);
	}
	

	/**
	 * Obtiene todas las vias del modelo y carga con dispatch con backoffice/pages/vias/form.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("vias", modelo.getAll() );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_INDEX );
		
	}



	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {

		
		if ( modelo.delete(pID)){
			request.setAttribute("msg", "Via Eliminada");
		}else{
			request.setAttribute("msg", "Via NO Eliminada " + pID );
		}
		
		listar(request, response);
		
	}



	private void nuevo(HttpServletRequest request, HttpServletResponse response) {

		via = new Via("nueva"); 
		request.setAttribute("via", via );
		request.setAttribute("titulo", "Crear nueva Via" );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_FORM);
	}



	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		
		via = (Via)modelo.getById(pID);
		request.setAttribute("via", via );
		request.setAttribute("titulo", via.getNombre() );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_FORM);
		
	}



	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {
		
		try{
			//recoger accion a realizar
			String sAccion = request.getParameter("accion");
			pAccion = Integer.parseInt(sAccion);			
		
			//recoger Identificador de la Via	
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
			
			crearObjetoVia(); //Le metemos los parámetros al nuevo objeto
			
			if (via.getId() == -1){
				modelo.save(via); //guardamos el nuevo reg
				request.setAttribute("msg", "Nueva vía creada con éxito"); //Enviamos un mensaje al cliente en el atributo msg
			}else{
				modelo.update(via); //hacemos update del existente
				request.setAttribute("msg", "Nueva vía modificada con éxito"); //Enviamos un mensaje al cliente en el atributo msg
			}
			
			listar(request, response);
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("msg", "Error recibiendo parametros");
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_FORM );
		}finally{
			dispatcher.forward(request, response);
		}
		
	}

	/**
	 * Crea un objeto {@code Via} con los parámetros recibidos
	 */
	private void crearObjetoVia() {
		via = new Via(pNombre);
		via.setId(pID);
		via.setLongitud(pLongitud);
		via.setGrado(pGrado);
		via.setDescripcion(pDescripcion);

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
		pNombre = request.getParameter("nombre");
		pLongitud = Integer.parseInt(request.getParameter("longitud"));
		pGrado = Grado.valueOf(request.getParameter("grado")); //Casteamos el String a un objeto de tipo Grado
		pDescripcion = request.getParameter("descripcion");
		
	}

}