package com.ipartek.formacion.skalada.controladores;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloZona;
import com.mysql.jdbc.EscapeTokenizer;

/**
 * Servlet implementation class SectoresController
 */
public class SectoresController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	   
		private RequestDispatcher dispatcher = null;
		private ModeloSector modeloSector = null;
		private Sector sector = null;
		private ModeloZona modeloZona = null;
		private Zona zona = null;
		
		//parametros
		private int pAccion = Constantes.ACCION_LISTAR;		//Accion por defecto
		private int pID	= -1;		//ID no valido	
		private String pNombre;
		private int pIDZona;
		

		//Imágen File
		private File file ;
	    
	    /**
	     * Este metodo se ejecuta solo la primera vez que se llama al servlet
	     * Se usa para crear el modeloSector
	     */
	    @Override
	    public void init(ServletConfig config) throws ServletException {
	    	super.init(config);
	    	modeloSector = new ModeloSector();  
	    	modeloZona = new ModeloZona();
	    	//filePath = getServletContext().getInitParameter("file-upload");
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
		 * Obtiene todas los sectores del modeloSector y carga dispatch con index.jsp
		 * @see backoffice/pages/sectores/index.jsp
		 * @param request
		 * @param response
		 */
		private void listar(HttpServletRequest request, HttpServletResponse response) {
			request.setAttribute("sectores", modeloSector.getAll());
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_INDEX);		
		}

		private void eliminar(HttpServletRequest request, HttpServletResponse response) {
			if(modeloSector.delete(pID)){
				request.setAttribute("msg-danger", "Registro eliminado correctamente");
			} else {
				request.setAttribute("msg-warning", "Error al eliminar el registro [id(" + pID + ")]");
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
		
		private void detalle(HttpServletRequest request, HttpServletResponse response) {
			sector = (Sector)modeloSector.getById(pID);
			request.setAttribute("sector", sector);
			request.setAttribute("titulo", sector.getNombre().toUpperCase());
			request.setAttribute("zonas", modeloZona.getAll());
						
			dispatcher = request.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);		
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
			Mensaje msg = new Mensaje(Mensaje.MSG_DANGER,  "Excepción al modificar");
			
			try{
			//recoger parametros del formulario y subida de imágen
			getParametersForm(request);

			//Subida de imágen
			uploadFile(request);
			
			//Crear Objeto Sector
			crearObjeto();
			
			//Guardar/Modificar Objeto Via
			if (pID == -1) {
				if (modeloSector.save(sector) != -1) {
					msg.setTipo(Mensaje.MSG_SUCCESS);
					msg.setTexto("Registro creado con exito");
					
				} else {
					msg.setTipo(Mensaje.MSG_DANGER);
					msg.setTexto("Error al guardar el nuevo registro");														
				}
			} else {
				if (modeloSector.update(sector)) {
					msg.setTipo(Mensaje.MSG_SUCCESS);
					msg.setTexto("Modificado correctamente el registro [id(" + pID
							+ ")]");						
				} else {
					msg.setTipo(Mensaje.MSG_DANGER);
					msg.setTexto("Error al modificar el registro [id(" + pID + ")]");			
				}
			}
			
			request.setAttribute("msg", msg);
			
			}catch( FileSizeLimitExceededException e){		
				e.printStackTrace();
				msg = new Mensaje( Mensaje.MSG_DANGER , "La imágen excede del tamaño maximo permitido " + Constantes.IMG_MAX_FILE_SIZE + " bytes" );
				request.setAttribute("msg", msg);	
			}catch(Exception e){
				e.printStackTrace();
				msg = new Mensaje( Mensaje.MSG_DANGER , e.getMessage() );
				request.setAttribute("msg", msg);
			}	
			
			listar(request, response);
			dispatcher.forward(request, response);

		}
		
		private void uploadFile(HttpServletRequest request) {
			// TODO Realizar comprobaciones y guardar imágen en PC
			
		}

		/**
		 * Se encarga de guardar la imágen del formulario en la carpeta de subidas
		 * @param request
		 */
		//private void uploadFile(HttpServletRequest request) {
			
			
			
		//}
		
		/**
		 * Crea un Objeto {@code Sector} Con los parametros recibidos
		 */
		private void crearObjeto() {
			
			//zona = new Zona("");
			//zona.setId(pIDZona);
			zona = (Zona)modeloZona.getById(pIDZona);
			
			//TODO controlar si cambiamos el sector pero no la imágen
			
			//existe sector
			if ( pID != -1 ){
				
				sector = (Sector)modeloSector.getById(pID);
				sector.setNombre(pNombre);
				sector.setZona(zona);
				if(file!=null){
					sector.setImagen(file.getName());
				}
				
			//nuevo sector	
			}else{
				sector = new Sector(pNombre, zona);
				sector.setId(pID);
				if(file!=null){
					sector.setImagen(file.getName());
				}
			}	
			
		}


		/**
		* Recoger los parametros enviados desde el formulario
		* @see backoffice\pages\sectores\form.jsp
		* @param request
		 * @throws UnsupportedEncodingException 
		*/
		private void getParametersForm(HttpServletRequest request) throws Exception {

				request.setCharacterEncoding("UTF-8");
				
				DiskFileItemFactory factory = new DiskFileItemFactory(); //Factoría para trabajar con ficheros
			    // maximum size that will be stored in memory
				//TODO Cambiar este valor para que falle
			    factory.setSizeThreshold(Constantes.IMG_MAX_MEM_SIZE); //Memoria para trabajar con ficheros. 
			    // Location to save data that is larger than maxMemSize.
			    //TODO Comprobar si no existe carpeta
			    factory.setRepository(new File(Constantes.IMG_UPLOAD_TEMP_FOLDER)); //Directorio temporal del propio Tomcat para guardarlo ahí
				
			    // Create a new file upload handler
			    ServletFileUpload upload = new ServletFileUpload(factory); //Objeto para la gestión de estos datos
			    // maximum file size to be uploaded.
			    //TODO Cambiar valor no dejar subir más de 1MB
			    upload.setSizeMax(Constantes.IMG_MAX_FILE_SIZE);
			    
			    //Parámetros de la request del formulario, No la imágen. Creamos un array de dos dimensiones para guardar todos los items de la página con la key su valor 
			    HashMap<String, String> dataParameters = new HashMap<String, String>();
			    
			    // Parse the request to get file items.
			    List<FileItem> items = upload.parseRequest(request); //Todo lo que se ha subido se parsea. Imágenes y parámetros que se han subido
			    // Process the uploaded file items
			    for(FileItem item : items){ //Recorro  todos los items de la página form.jsp para buscar la imágen
			    
			    	//Parámetro formulario
			    	if(item.isFormField()){ //CampoDeFormulario se refiere a los parámetros. Los metemos al HashMap
			    		//Nombre del item y valor del item, uséase key y value
			    		dataParameters.put(item.getFieldName(), item.getString("UTF-8") ); //UTF-8 para solucionar al cojer los string con acentos
			    	
			    	//Si no es campoDeFormulario, uséase imágen
			    	}else{
				    	//Atributos de la imágen
			            String fileName = item.getName();
			            if (!"".equals(fileName)){ //Si está vacío subo la imágen. Qué no modifique la imágen cuando está
				            String fileContentType = item.getContentType();
				            if (Constantes.IMG_CONTENT_TYPES.contains(fileContentType)){ //Si contiene los tipos de archivo jpg o png
					            boolean isInMemory = item.isInMemory();
					            long sizeInBytes = item.getSize();
					            
					            //comprobar 'size' y 'contentType' (tipo de extensión)

					            //No repetir nombre imágenes
				            	File carpetaUploads = new File(Constantes.IMG_UPLOAD_FOLDER);
				            	if (carpetaUploads.exists()){
					                File[] ficherosUploads = carpetaUploads.listFiles();
					                
					                //Recorro los ficheros y compruebo su nombre por si es igual que el nombre del fichero a subir
					                for (int i=0; i<ficherosUploads.length; i++ ){
					                	if (ficherosUploads[i].isFile()){
					                		//Si el nombre es igual
					                		if (item.getName().equalsIgnoreCase(ficherosUploads[i].getName())){
					                			//Añadimos la fecha
					                			Date fecha = new Date();
					                			SimpleDateFormat formato = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss");
					                			
					                			int indicePunto = fileName.indexOf(".");
					                			if (indicePunto != -1);
					                				String nombre = fileName.substring(0, indicePunto);
					                				String extension = fileName.substring(indicePunto + 1);
					                				fileName =  nombre + "_" + formato.format(fecha).toString().replace(" ", "-").replace(":", "_") + "." + extension;
					                			break;
					                			
					                			
					                		}
					                	}
					                } //End for: ficherosUploads
					                
					                file = new File(Constantes.IMG_UPLOAD_FOLDER + "\\" + fileName);
							        item.write( file );
							        
				            	} //End: exists()
				            }else{
				            	throw new Exception("[" + fileContentType + "] Extensión de imágen no permitida");
				            } //End: fileContentType
			            }else{
			            	file = null;
			            }//End: fileName vacío
				    
			    	} //End: fileName
			    } //End: for items<FileItem>
		            
			    //Se comprueba el tamaño del fichero desde el form.jsp en javascript
			    
			    //Cojo los parámetros pero del propio HashMap dataParameters
			    pID = Integer.parseInt(dataParameters.get("id"));
				pNombre = dataParameters.get("nombre");	
				pIDZona = Integer.parseInt(dataParameters.get("zona"));
		            
		        //TODO actualizar modelo

		}


	}