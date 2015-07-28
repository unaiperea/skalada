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
	
	private Via via = null;
	private ModeloVia modelo = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViasController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Este método se ejecuta sólo la primera vez que se llama al servlet
     * Se suele usar para crear el modelo
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// Inicializo el objeto de la Clase Java
    	super.init(config);
    	modelo = new ModeloVia();
    	
    	//Datos de prueba
    	//generateViaMocks(modelo);
    }
    
    
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recoger parámetro id de la Via
		String pID = request.getParameter("id");
		if (pID != null && !"".equals(pID)){
			int id = Integer.parseInt(pID);
			//Nueva Via
			if (id == -1){
				via = new Via("Nueva");
			//Detalle Via
			}else{
				//TODO Llamar al modelo para recuperarla por el ID
				via = (Via)modelo.getById(id);
			}
			
			//Enviar atributo con la via
			request.setAttribute("via", via);
			
			//Cargamos el dispatcher
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_FORM);
		//Listar todas las vias
		}else{
			//Si no tiene ID que quiero listar todos useasé ir al index
			request.setAttribute("vias", modelo.getAll());
			dispatcher = request.getRequestDispatcher( Constantes.VIEW_BACK_VIAS_INDEX);
		}
		
		dispatcher.forward(request, response);
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
    private void generateViaMocks(ModeloVia modelo2){
    	
    	Via v = new Via("No se");
    	v.setDescripcion("Tampoco lo se");
    	v.setLongitud(12);
    	v.setGrado(Grado.FACIL);
    	modelo.save(v);
    	
    	v = new Via("Filibusteros");
    	v.setDescripcion("Esta es otra zona de escalada deportiva de la provincia de Zamora, en la localidad de la Granja de Moreruela, en el paraje conocido como Puente Quintos.");
    	v.setLongitud(20);
    	v.setGrado(Grado.NORMAL);
    	modelo.save(v);
    	
    	v = new Via("Ekain");
    	v.setDescripcion("Zona ubicada en la playa del mismo nombre, con caliza marmórea algo jabonosa. Lo mejor es su zona de búlder. Orientación: N,O,S,E.");
    	v.setLongitud(35);
    	v.setGrado(Grado.DIFICIL);
    	modelo.save(v);
    }
    
}
