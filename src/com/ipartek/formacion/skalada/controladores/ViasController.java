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
	private int pAccion = Constantes.ACCION_LISTAR;		//Accion por defecto
	private int pID	= -1;		//ID no valido
	
	private String pNombre;
	private Grado pGrado;
	private int pLongitud;
	private String pDescripcion;
	
    
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al servlet
     * Se suele usar para creal el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	modelo = new ModeloVia();
    	//TODO quitar estos datos de prueba
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
		via = new Via("");
		request.setAttribute("via", via);
		request.setAttribute("titulo", "Crear nueva Via");
		request.setAttribute("metodo", "Guardar");
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);
		
	}
	
	private void detalle(HttpServletRequest request, HttpServletResponse response) {
		via = (Via)modelo.getById(pID);
		request.setAttribute("via", via);
		request.setAttribute("titulo", via.getNombre());
		request.setAttribute("metodo", "Modificar");
		dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_VIAS_FORM);		
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//recoger parametros del formulario
		getParameters(request);
		
		//Crear Objeto Via
		via = new Via(pNombre);
		via.setId(pID);
		via.setGrado(pGrado);
		via.setLongitud(pLongitud);
		via.setDescripcion(pDescripcion);
		
		//Guardar Objeto Via
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
				request.setAttribute("msg-danger", "Error al modificadar la Via ");
			}
		}
		
		listar(request,response);
		dispatcher.forward(request, response);
		
	}
	
	/**
	* Recoger los parametros enviados
	* @param request
	*/
	private void getParameters(HttpServletRequest request) {
	
		pID = Integer.parseInt(request.getParameter("id"));
		pNombre = request.getParameter("nombre");
		pGrado = Grado.valueOf(request.getParameter("grado"));
		pLongitud = Integer.parseInt(request.getParameter("longitud"));
		pDescripcion = request.getParameter("descripcion");
	
	}
	
	
//    /**
//     * Crea un juego de datos ficticios para las Vias
//     * @param modelo2
//     */
//	private void generateViaMocks(ModeloVia modelo2) {
//		Via v = new Via("No se");
//		v.setDescripcion("Tampoco lo se");
//		v.setGrado(Grado.DIFICIL);
//		v.setLongitud(67);
//		modelo.save(v);
//
//		v = new Via("Arrabalde");
//		v.setDescripcion("Arrabalde es un pueblo situado al noreste de la provincia de Zamora a 25 kilómetros de Benavente. Es conocido culturalmente por su riqueza  arqueológica, ya que en lo alto de su sierra se encuentran los restos del antiguo castro celta de 'Las Labradas'");
//		v.setGrado(Grado.EXTREMO);
//		v.setLongitud(500);
//		modelo.save(v);
//		
//		v = new Via("La Cucala");
//		v.setDescripcion("Escuela de escalada cercana a Castellon y alternativa a Castellet, equipado por el Club Trepa Castellet y el Club de Montaña Villareal.");
//		v.setGrado(Grado.FACIL);
//		v.setLongitud(12);
//		modelo.save(v);
//		
//		v = new Via("Montserrat");
//		v.setDescripcion("El Bruc, Collbato, y Monistrol de Montserrat son los puntos de partida para acceder a los distintos sectores de la zona. Monserrat Sur contiene vías de un largo de las zonas bajas de la cara sur de Montserrat con concesiones de zonas algo más arriba. Los criterios a la hora de seleccionar el contenido de la guía son la cercanía a los aparcamientos (entre 5' y 1 h) y pertenecer a la cara Sur.");
//		v.setGrado(Grado.NORMAL);
//		v.setLongitud(30);
//		modelo.save(v);
//	}
	

}

