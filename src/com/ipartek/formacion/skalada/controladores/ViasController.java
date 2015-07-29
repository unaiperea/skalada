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
	
	
    /**
     * Este metodo se ejecuta solo la primera vez que se llama al Servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {    	
    	super.init(config);
    	modelo = new ModeloVia();    	
    	//TODO quitar estos datos de prueba
    	//generateViaMocks(modelo);    	
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
			pID = Integer.parseInt(request.getParameter("id"));
			
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
		
		
	}

	
	 /**
     * Crea un juego de datos ficticio para las Vias
     * @param modelo2
     */
	private void generateViaMocks(ModeloVia modelo2) {

		Via v = new Via("No se");
		v.setDescripcion("Tampoco lo se");
		v.setLongitud(12);
		v.setGrado(Grado.FACIL);
		modelo.save(v);
		
		v = new Via("Arrebalde");
		v.setDescripcion("Arrabalde es un pueblo situado al noreste de la provincia de Zamora a 25 kilómetros de Benavente. Es conocido culturalmente por su riqueza  arqueológica, ya que en lo alto de su sierra se encuentran los restos del antiguo castro celta de “Las Labradas” El castro de Las Labradas fue emplazado en la Sierra de Carpurias a 1.000 metros de altitud. Por su extensión, esta considerado como el más grande de todo el noroeste peninsular, con asentamientos en la Edad del Bronce (1400 a 900 a.C.) y finales de la Edad del Hierro (siglos I a.C. al Id.C.). Destaca la aparición de dos ocultaciones de joyas prerromanas conocidas genéricamente como “El Tesoro de Arrabalde”. En las antiguas escuelas del pueblo de Arrabalde encontramos el Aula Arqueológica, donde tendremos una amable visita guiada por la reproducción de una calle del Castro. Así mismo dispondremos de información sobre toda la Ruta Arqueológica de los Valles.En las inmediaciones del Castro existen unas franjas rocosas de cuarcitas conocidas como Peña la Pipa y Peña Sorda . En estas paredes el Club Montañero Benaventano ha equipado 40 vías de Escalada Deportiva que van desde el tercero hasta el séptimo grado. Seis de estas rutas componen un sector de iniciación llamado Jóvenes Guerreros.Para alojarnos, Benavente dispone de gran oferta, también hay un camping en Mózar de Valverde. Además el pueblo de Arrabalde, dispone de dos panaderías con productos tradicionales, dos bares, una tienda y una farmacia.");
		v.setLongitud(32);
		v.setGrado(Grado.DIFICIL);
		modelo.save(v);
		
		v = new Via("La Cabeza");
		v.setDescripcion("No hay descripción disponible para esta zona");
		v.setLongitud(142);
		v.setGrado(Grado.EXTREMO);
		modelo.save(v);
		
		v = new Via("Uxola (Alcoy)");
		v.setDescripcion("Pequeña escuela situada a escasos cinco minutos del casco urbano de Alcoy. Cuenta con vías difíciles y explosivas y en ella se han forjado los potentes escaladores locales.");
		v.setLongitud(142);
		v.setGrado(Grado.NORMAL);
		modelo.save(v);
		
		
	}
}
