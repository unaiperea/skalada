package com.ipartek.formacion.skalada.controladores;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.ipartek.formacion.skalada.Constantes;
import com.ipartek.formacion.skalada.bean.Mensaje;
import com.ipartek.formacion.skalada.bean.Sector;
import com.ipartek.formacion.skalada.bean.Usuario;
import com.ipartek.formacion.skalada.bean.Zona;
import com.ipartek.formacion.skalada.modelo.ModeloSector;
import com.ipartek.formacion.skalada.modelo.ModeloUsuario;
import com.ipartek.formacion.skalada.modelo.ModeloZona;

/**
 * Servlet implementation class SectoresController
 *
 * @author Curso
 */
public class SectoresController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = null;
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

		this.usuario = (Usuario) request.getSession().getAttribute(
				Constantes.KEY_SESSION_USER);
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
		request.setAttribute("sectores", this.modeloSector.getAll(this.usuario));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_INDEX);
	}

	private void eliminar(HttpServletRequest request,
			HttpServletResponse response) {

		// Check Autorizacion
		if (this.usuario.isAdmin()
				|| (this.sector.getUsuario().getId() == this.usuario.getId())) {

			if (this.modeloSector.delete(this.pID)) {
				request.setAttribute("msg-danger",
						"Registro eliminado correctamente");
			} else {
				request.setAttribute("msg-warning",
						"Error al eliminar el registro [id(" + this.pID + ")]");
			}
			// Usuario sin autorizacion para este Sector
		} else {
			Mensaje msg = new Mensaje(Mensaje.MSG_DANGER,
					"No tienes permisos para ver el detalle");
			request.setAttribute("msg", msg);
		}

		this.listar(request, response);
	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {
		this.zona = new Zona("");
		this.sector = new Sector("", this.zona);
		this.sector.setUsuario(this.usuario);
		request.setAttribute("sector", this.sector);
		request.setAttribute("titulo", "Crear nuevo Sector");
		request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
		request.setAttribute("usuarios", this.modeloUsuario.getAll(this.admin));
		this.dispatcher = request
				.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);

	}

	private void detalle(HttpServletRequest request,
			HttpServletResponse response) {
		this.sector = this.modeloSector.getById(this.pID);

		// check autorizacion
		if (this.usuario.isAdmin()
				|| (this.sector.getUsuario().getId() == this.usuario.getId())) {

			request.setAttribute("sector", this.sector);
			request.setAttribute("titulo", this.sector.getNombre()
					.toUpperCase());
			request.setAttribute("zonas", this.modeloZona.getAll(this.admin));
			request.setAttribute("usuarios",
					this.modeloUsuario.getAll(this.admin));

			this.dispatcher = request
					.getRequestDispatcher(Constantes.VIEW_BACK_SECTORES_FORM);

			// Usuario sin autorizacion
		} else {
			Mensaje msg = new Mensaje(Mensaje.MSG_DANGER,
					"No tienes permisos para ver el detalle");
			request.setAttribute("msg", msg);
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

		Mensaje msg = new Mensaje(Mensaje.MSG_DANGER, "Error sin identificar");

		try {
			// recoger parametros del formulario
			this.getParametersForm(request);

			// Crear Objeto Sector
			this.crearObjeto();

			// Guardar/Modificar Objeto Via
			if (this.pID == -1) {
				if (this.modeloSector.save(this.sector) != -1) {
					msg.setTipo(Mensaje.MSG_SUCCESS);
					msg.setTexto("Registro creado con exito");

				} else {
					msg.setTipo(Mensaje.MSG_DANGER);
					msg.setTexto("Error al guardar el nuevo registro");
				}
			} else {
				if (this.modeloSector.update(this.sector, this.usuario)) {
					msg.setTipo(Mensaje.MSG_SUCCESS);
					msg.setTexto("Modificado correctamente el registro [id("
							+ this.pID + ")]");
				} else {
					msg.setTipo(Mensaje.MSG_DANGER);
					msg.setTexto("Error al modificar el registro [id("
							+ this.pID + ")]");
				}
			}

			request.setAttribute("msg", msg);

		} catch (FileSizeLimitExceededException e) {
			e.printStackTrace();
			msg = new Mensaje(Mensaje.MSG_DANGER,
					"La imagen excede del tama�o maximo permitido "
							+ Constantes.MAX_FILE_SIZE + " bytes");
			request.setAttribute("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Mensaje(Mensaje.MSG_DANGER, e.getMessage());
			request.setAttribute("msg", msg);
		}

		this.listar(request, response);
		this.dispatcher.forward(request, response);

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
				dataParameters
						.put(item.getFieldName(), item.getString("UTF-8"));
				// Imagen
			} else {
				String fileName = item.getName();
				if (!"".equals(fileName)) {
					String fileContentType = item.getContentType();

					if (Constantes.CONTENT_TYPES.contains(fileContentType)) {

						item.getSize();

						// TODO No repetir nombres imagenes

						this.file = new File(Constantes.IMG_UPLOAD_FOLDER
								+ "\\" + fileName);
						item.write(this.file);
					} else {
						throw new Exception("[" + fileContentType
								+ "] extensi�n de imagen no permitida");
					}// end: content-type no permitido
				} else {
					this.file = null;
				}
			}
		}// End: for List<FileItem>

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
			this.pIDusuario = this.usuario.getId();
		}

		this.pValidado = (dataParameters.get("validado") != null) ? true
				: false;

		this.pLongitud = Double.parseDouble(dataParameters.get("longitud"));
		this.pLatitud = Double.parseDouble(dataParameters.get("latitud"));

	}

}