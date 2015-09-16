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
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.TipoEscalada;
import com.ipartek.formacion.skalada.bean.Via;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloGrado;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloTipoEscalada;
import com.ipartek.formacion.skalada.modelo.ModeloVia;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class ViasController
 */
public class ViasController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestDispatcher dispatcher = null;
	private ModeloVia modeloVia = null;
	private Via via = null;
	
	private ModeloGrado modeloGrado = null;
	private Grado grado = null;
	private ModeloTipoEscalada modeloTipoEscalada = null;
	private TipoEscalada tipoEscalada = null;
	private ModeloSector modeloSector = null;
	private Sector sector = null;
	private ModeloZona modeloZona = null;

	
	//parametros
	private int pAccion = Constantes.ACCION_LISTAR;		//Accion por defecto
	private int pID	= -1;		//ID no valido	
	private String pNombre;
	private int pLongitud;
	private String pDescripcion;
	private int pIDGrado;
	private int pIDTipoEscalada;
	private int pIDSector;
	
	private Mensaje msg;
	
    
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modeloVia = new ModeloVia();
    	modeloGrado = new ModeloGrado();
    	modeloTipoEscalada = new ModeloTipoEscalada();
    	modeloSector = new ModeloSector();
    	modeloZona = new ModeloZona();
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
			request.setCharacterEncoding("UTF-8");
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
		request.setAttribute("vias", modeloVia.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_INDEX);		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		if(modeloVia.delete(pID)){			
			msg = new Mensaje(Mensaje.MSG_DANGER, "Registro eliminado correctamente");			
		} else {
			msg = new Mensaje(Mensaje.MSG_WARNING, "Error al eliminar el registro [id(" + pID + ")]");
		}
		request.setAttribute("msg", msg);
		listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		via = new Via("");
		via.setGrado(new Grado(""));
		via.setTipoEscalada(new TipoEscalada(""));
		via.setSector(new Sector("", new Zona("")));
		request.setAttribute("via", via);
		request.setAttribute("titulo", "Crear nueva Via");
		request.setAttribute("metodo", "Guardar");
		request.setAttribute("grados", modeloGrado.getAll());
		request.setAttribute("tipoEscaladas", modeloTipoEscalada.getAll());
		request.setAttribute("sectores", modeloSector.getAll());
		request.setAttribute("zonas", modeloZona.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);
		
	}
	
	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		via = (Via)modeloVia.getById(pID);
		request.setAttribute("via", via);
		request.setAttribute("titulo", via.getNombre().toUpperCase());
		request.setAttribute("metodo", "Modificar");
		request.setAttribute("grados", modeloGrado.getAll());
		request.setAttribute("tipoEscaladas", modeloTipoEscalada.getAll());
		request.setAttribute("sectores", modeloSector.getAllByZona( via.getSector().getZona().getId() )); //De la vía saco el sector, del sector saco la zona y de la zona el id
		request.setAttribute("zonas", modeloZona.getAll());
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recoger parametros del formulario
		getParametersForm(request);
		
		//Crear Objeto Via con el Grado, TipoEscalada, Zona y Sector
		crearObjetoVia();
		
		//Guardar/Modificar Objeto Via
		if (pID == -1){
			if( modeloVia.save(via) != -1){
				msg = new Mensaje(Mensaje.MSG_SUCCESS, "Registro creado con exito");
			} else {
				msg = new Mensaje(Mensaje.MSG_DANGER, "Error al guardar el nuevo registro");
			}
		} else {
			if(modeloVia.update(via)){
				msg = new Mensaje(Mensaje.MSG_SUCCESS, "Modificado correctamente el registro [id(" + pID + ")]");
			} else {
				msg = new Mensaje(Mensaje.MSG_DANGER, "Error al modificar el registro [id(" + pID + ")]");
			}
		}
		
		listar(request,response);
		
		request.setAttribute("msg", msg);
		dispatcher.forward(request, response);
		
	}
	
	/**
	 * Crea un Objeto {@code Via} Con los parametros recibidos
	 */
	private void crearObjetoVia() {
		grado = (Grado)modeloGrado.getById(pIDGrado);
		tipoEscalada = (TipoEscalada)modeloTipoEscalada.getById(pIDTipoEscalada);
		sector = (Sector)modeloSector.getById(pIDSector);		
		
		if (pID != -1) {
			via = (Via)modeloVia.getById(pID);
			via.setNombre(pNombre);
			via.setLongitud(pLongitud);
			via.setGrado(grado);
			via.setTipoEscalada(tipoEscalada);
			via.setSector(sector);
		}else{			
			via = new Via(pNombre, pLongitud, grado, tipoEscalada, sector);
			via.setId(pID);
			via.setDescripcion(pDescripcion);
		}
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
		if(request.getParameter("longitud") != null && !"".equals(request.getParameter("longitud"))){
			pLongitud = Integer.parseInt(request.getParameter("longitud"));
		} else {
			pLongitud = 0;
		}		
		pDescripcion = request.getParameter("descripcion");
		pIDGrado= Integer.parseInt(request.getParameter("grado"));
		pIDTipoEscalada = Integer.parseInt(request.getParameter("tipo_escalada"));
		pIDSector = Integer.parseInt(request.getParameter("sector"));
	
	}


}