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
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class GradosController
 */
public class SectoresController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestDispatcher dispatcher = null;
	private ModeloSector modeloSector = null;
	private ModeloZona modeloZona = null;
	private Sector sector = null;
	private Zona zona = null;
	
	//parametros
	private int pAccion = Constantes.ACCION_LISTAR; //accion por defecto
	private int pID     = -1; //ID no valido.
	private String pNombre;
	private int pIDZona;
	
	
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al Servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {    	
    	super.init(config);
    	modeloSector = new ModeloSector();  
    	modeloZona = new ModeloZona();
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
		
		request.setAttribute("sectores", modeloSector.getAll() );
		
		dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_SECTORES_INDEX );
		
	}



	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {

		
		if ( modeloSector.delete(pID)){
			request.setAttribute("msg", "Registro Eliminado");
		}else{
			request.setAttribute("msg", "ERROR: Registro NO Eliminado " + pID );
		}
		
		listar(request, response);
		
	}



	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		zona = new Zona("");
		sector = new Sector("", zona);
		request.setAttribute("sector", sector);
		request.setAttribute("titulo", "Crear nuevo Sector");			
		request.setAttribute("zonas", modeloZona.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);
	}



	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		
		sector = (Sector)modeloSector.getById(pID);
		
		request.setAttribute("sector", sector);
		request.setAttribute("titulo", sector.getNombre().toUpperCase());			
		request.setAttribute("zonas", modeloZona.getAll());
					
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);	
		
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

			getParametersFormulario(request); //Recogemos los parámetros que nos han enviado desde el formulario
			
			crearObjeto(); //Le metemos los parámetros al nuevo objeto
			
			//Guardar/Modificar Objeto Sector
			if (pID == -1){
				if( modeloSector.save(sector) != -1){	
					request.setAttribute("msg", "Registro creado con exito");
				} else {
					request.setAttribute("msg", "Error al guardar el nuevo registro");
				}
			} else {
				if(modeloSector.update(sector)){
					request.setAttribute("msg", "Modificado correctamente el registro [id(" + pID + ")]");
				} else {
					request.setAttribute("msg", "Error al modificar el registro [id(" + pID + ")]");
				}
			}
			
			listar(request,response);
			
			dispatcher.forward(request, response);
		
	}

	/**
	 * Crea un objeto {@code Object} con los parámetros recibidos
	 */
	private void crearObjeto() {
		zona = (Zona)modeloZona.getById(pIDZona); //Se habrá elejido previamente de un combo por lo que pregunta antes si existe en la bbdd antes de nada
		
		//existe sector
		if (pID != -1){
			sector = (Sector)modeloSector.getById(pID);
			sector.setZona(zona);
			
		//nuevo sector
		}else{
			sector = new Sector(pNombre, zona);
			sector.setId(pID);
		}
		
	}
	
	/**
	 * Recoger los parámetros enviados desde el formulario:
	 * @see backoffice\pages\vias\form.jsp
	 * @param request
	 * @param response
	 */
	private void getParametersFormulario(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		pID = Integer.parseInt(request.getParameter("id"));
		pNombre = request.getParameter("nombre"); //Según el atributo name="nombre" del input
		pIDZona  = Integer.parseInt(request.getParameter("zona"));
		
	}

}