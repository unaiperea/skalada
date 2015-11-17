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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;
import com.ipartek.formacion.skalada.util.Utilidades;

/**
 * Servlet implementation class SectoresController
 *
 * @author Curso
 */
public class SectoresController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// LOGS
	private static final Logger LOG = Logger.getLogger(SectoresController.class);
	private Usuario usuarioSession = null;

	private Usuario admin = null;
	private RequestDispatcher dispatcher = null;
	private ModeloSector modeloSector = null;
	private ModeloUsuario modeloUsuario = null;
	private Sector sector = null;
	private ModeloZona modeloZona = null;
	private Zona zona = null;

	// parametros
	private int pAccion = Constantes.ACCION_LISTAR; // Accion por defecto
	private int pID = -1; // ID no valido
	private String pNombre;
	private int pIDZona;
	private int pIDusuario;
	private boolean pValidado;
	private double pLongitud;
	private double pLatitud;

	private Mensaje msg = null;

	// Imagen File
	private File file;

	/**
	 * Este metodo se ejecuta solo la primera vez que se llama al servlet Se usa
	 * para crear el modeloSector
	 */
	@Override()
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.modeloSector = new ModeloSector();
		this.modeloZona = new ModeloZona();
		this.modeloUsuario = new ModeloUsuario();
		this.admin = this.modeloUsuario.getById(Constantes.ROLE_ID_ADMIN);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger usuario de session
		this.usuarioSession = Utilidades.getSessionUser(request, response);
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// recoger parametros
		this.getParameters(request, response);

		// realizar accion solicitada
		switch (this.pAccion) {
		case Constantes.ACCION_NUEVO:
			this.nuevo(request, response);
			break;
		case Constantes.ACCION_DETALLE:
			this.detalle(request, response);
			break;
		case Constantes.ACCION_ELIMINAR:
			this.eliminar(request, response);
			break;
		default:
			this.listar(request, response);
			break;
		}

		this.dispatcher.forward(request, response);
	}

	private void getParameters(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			this.pAccion = Integer.parseInt(request.getParameter("accion"));
			if ((request.getParameter("id") != null)
					&& !"".equalsIgnoreCase(request.getParameter("id"))) {
				this.pID = Integer.parseInt(request.getParameter("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene todas los sectores del modeloSector y carga dispatch con
	 * index.jsp
	 *
	 * @see backoffice/pages/sectores/index.jsp
	 * @param request
	 * @param response
	 */
	private void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("sectores",
				this.modeloSector.getAll(this.usuarioSession));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_INDEX);
	}

	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {
		// Check Autorizacion
		if (this.usuarioSession.isAdmin()
				|| (this.sector.getUsuario().getId() == this.usuarioSession
						.getId())) {
			if (this.modeloSector.delete(this.pID)) {
				this.msg = new Mensaje(Mensaje.MSG_DANGER,
						"Registro eliminado correctamente");
				LOG.info("Usuario: '" + this.usuarioSession.getNombre() + "["
						+ this.usuarioSession.getId()
						+ "]' - Elimina el Sector con id: " + this.pID);
			} else {
				this.msg = new Mensaje(Mensaje.MSG_WARNING,
						"Error al eliminar el registro [id(" + this.pID + ")]");
				LOG.error("Usuario: '" + this.usuarioSession.getNombre() + "["
						+ this.usuarioSession.getId()
						+ "]' - Error al eliminar el Sector con id: "
						+ this.pID);
			}
			// Usuario sin autorizacion para este Sector
		} else {
			this.msg = new Mensaje(Mensaje.MSG_DANGER,
					"No tienes permisos para ver el detalle");
			LOG.warn("Usuario: '" + this.usuarioSession.getNombre() + "["
					+ this.usuarioSession.getId()
					+ "]' - No tiene permisos para ver el Sector con id: "
					+ this.pID);
		}
		request.getSession().setAttribute("msg", this.msg);
		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.zona = new Zona("");
		this.sector = new Sector("Crear nuevo Sector", this.zona);
		this.sector.setUsuario(this.usuarioSession);
		request.setAttribute("sector", this.sector);
		request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
		request.setAttribute("usuarios", this.modeloUsuario.getAll(this.admin));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);

	}

	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		this.sector = this.modeloSector.getById(this.pID);

		// check autorizacion
		if (this.usuarioSession.isAdmin()
				|| (this.sector.getUsuario().getId() == this.usuarioSession
						.getId())) {
			request.setAttribute("sector", this.sector);
			request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
			request.setAttribute("usuarios",
					this.modeloUsuario.getAll(this.admin));

			this.dispatcher = request
					.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);

			// Usuario sin autorizacion
		} else {
			this.msg = new Mensaje(Mensaje.MSG_DANGER,
					"No tienes permisos para ver el detalle");
			request.getSession().setAttribute("msg", this.msg);
			this.listar(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.msg = new Mensaje(Mensaje.MSG_DANGER, "Error sin identificar");

		try {
			// recoger parametros del formulario
			this.getParametersForm(request);

			// Crear Objeto Sector
			this.crearObjeto();

			// Guardar/Modificar Objeto Via
			if (this.pID == -1) {
				if (this.modeloSector.save(this.sector) != -1) {
					this.msg = new Mensaje(Mensaje.MSG_SUCCESS,
							"Registro creado con exito");
					LOG.info("Usuario: '" + this.usuarioSession.getNombre()
							+ "[" + this.usuarioSession.getId()
							+ "]' - Crea el Sector: " + this.sector.getNombre()
							+ "[" + this.sector.getId() + "].");
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER,
							"Error al guardar el nuevo registro");
					LOG.error("Usuario: '" + this.usuarioSession.getNombre()
							+ "[" + this.usuarioSession.getId()
							+ "]' - Error al crear el Sector.");
				}
			} else {
				if (this.modeloSector.update(this.sector, this.usuarioSession)) {
					this.msg = new Mensaje(Mensaje.MSG_SUCCESS,
							"Modificado correctamente el registro [id(" + this.pID + ")]");
					LOG.info("Usuario: '" + this.usuarioSession.getNombre()
							+ "[" + this.usuarioSession.getId()
							+ "]' - Modifica el Sector: "
							+ this.sector.getNombre() + "["
							+ this.sector.getId() + "].");
				} else {
					this.msg = new Mensaje(Mensaje.MSG_DANGER,
							"Error al modificar el registro [id(" + this.pID
									+ ")]");
					LOG.error("Usuario: '" + this.usuarioSession.getNombre()
							+ "[" + this.usuarioSession.getId()
							+ "]' - Error al modificar el Sector: "
							+ this.sector.getNombre() + "["
							+ this.sector.getId() + "].");
				}
			}

			request.getSession().setAttribute("msg", this.msg);

		} catch (FileSizeLimitExceededException e) {
			e.printStackTrace();
			this.msg = new Mensaje(Mensaje.MSG_DANGER, "La imagen excede del tamaño maximo permitido " + Constantes.MAX_FILE_SIZE + " bytes");
			LOG.warn("Usuario: '" + this.usuarioSession.getNombre() + "[" + this.usuarioSession.getId() + "]' - La imagen excede el tamaño");
			request.getSession().setAttribute("msg", this.msg);
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = new Mensaje(Mensaje.MSG_DANGER, e.getMessage());
			request.getSession().setAttribute("msg", this.msg);
		}

		// this.listar(request, response);
		// this.dispatcher.forward(request, response);

		request.getSession().setAttribute("msg", this.msg);
		response.sendRedirect(request.getContextPath() + "/backoffice/sectores?accion=" + Constantes.ACCION_LISTAR);

	}

	/**
	 * Se encarga de guardar la imagen del formulario en la carpeta de subidas
	 *
	 * @param request
	 */
	private void uploadFile(HttpServletRequest request) {
		// TODO realizar comprobaciones y guardar imagen en PC
	}

	/**
	 * Crea un Objeto {@code Sector} Con los parametros recibidos
	 */
	private void crearObjeto() {

		this.zona = this.modeloZona.getById(this.pIDZona);
		Usuario uSector = this.modeloUsuario.getById(this.pIDusuario);

		// TODO controlar si cambiamos el sector pero no la imagen

		// existe sector
		if (this.pID != -1) {

			this.sector = this.modeloSector.getById(this.pID);
			this.sector.setNombre(this.pNombre);
			this.sector.setZona(this.zona);
			if (this.file != null) {
				this.sector.setImagen(this.file.getName());
			}

			// nuevo sector
		} else {
			this.sector = new Sector(this.pNombre, this.zona);
			this.sector.setId(this.pID);
			if (this.file != null) {
				this.sector.setImagen(this.file.getName());
			}
		}

		this.sector.setValidado(this.pValidado);
		this.sector.setUsuario(uSector);
		this.sector.setLongitud(this.pLongitud);
		this.sector.setLatitud(this.pLatitud);

	}

	/**
	 * Recoger los parametros enviados desde el formulario
	 *
	 * @see backoffice\pages\sectores\form.jsp
	 * @param request
	 * @throws UnsupportedEncodingException
	 * @throws FileUploadException
	 */
	private void getParametersForm(HttpServletRequest request) throws Exception {

		request.setCharacterEncoding("UTF-8");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		// TODO cambiar este valor para que falle
		factory.setSizeThreshold(Constantes.MAX_MEM_SIZE);
		// Location to save data that is larger than maxMemSize.
		// TODO comprobar si no existe carpeta
		factory.setRepository(new File(Constantes.IMG_UPLOAD_TEMP_FOLDER));
		System.out.println(Constantes.SERVER + "/uploads/");

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		// TODO cambiar valor no dejar subir mas 1Mb
		upload.setSizeMax(Constantes.MAX_FILE_SIZE);

		// Parametros de la request del formulario, NO la imagen
		HashMap<String, String> dataParameters = new HashMap<String, String>();
		// Parse the request to get file items.
		List<FileItem> items = upload.parseRequest(request);
		for (FileItem item : items) {
			// parametro formulario
			if (item.isFormField()) {
				dataParameters.put(item.getFieldName(), item.getString("UTF-8"));
				// Imagen
			} else {
				String fileName = item.getName();
				if (!"".equals(fileName)) {
					String fileContentType = item.getContentType();

					if (Constantes.CONTENT_TYPES.contains(fileContentType)) {

						//TODO borrar del servidor la imagen anterior en el caso de que ya tuviera

						//No repetir nombre imagenes
		            	File carpetaUploads = new File(Constantes.IMG_UPLOAD_FOLDER);
		            	if (carpetaUploads.exists()){

		            		//Añadimos la fecha
                			Date fecha = new Date();
                			SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                			
                			int indicePunto = fileName.indexOf(".");
                			if (indicePunto != -1){
                				String nombre = fileName.substring(0, indicePunto);
                				String extension = fileName.substring(indicePunto + 1);
                				fileName =  nombre + "_" + formato.format(fecha).toString().replace(" ", "-").replace(":", "_") + "." + extension;
                			}
			                			
		                file = new File(Constantes.IMG_UPLOAD_FOLDER + "\\" + fileName);
				        item.write( file );
					        
		            	} //End: exists()
						
					} else {
						throw new Exception("[" + fileContentType + "] extension de imagen no permitida");
					}// end: content-type no permitido
				} else {
					this.file = null;
				}
			} //End: if isFormField
		} //End: for List<FileItem>

		this.pID = Integer.parseInt(dataParameters.get("id"));
		this.pNombre = dataParameters.get("nombre");
		this.pIDZona = Integer.parseInt(dataParameters.get("zona"));

		// Si es null, es un Usuario 'normal'
		// puesto que el formulario no existe el parametro "creador"
		if (dataParameters.get("creador") != null) {
			// Usuario 'admin' coger id_usuario seleccionado del combo
			this.pIDusuario = Integer.parseInt(dataParameters.get("creador"));
		} else {
			// Usuario 'normal' coger id de session
			this.pIDusuario = this.usuarioSession.getId();
		}

		this.pValidado = (dataParameters.get("validado") != null) ? true
				: false;

		this.pLongitud = Double.parseDouble(dataParameters.get("longitud"));
		this.pLatitud = Double.parseDouble(dataParameters.get("latitud"));

	}

}