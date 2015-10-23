package com.ipartek.formacion.skalada;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase con constantes
 *
 * @author Curso
 *
 */
public final class Constantes {

	/**
	 * Constructor Privado
	 */
	private Constantes() {
		super();
	}

	public static final String SERVER = "http://localhost:8080";
	public static final String ROOT_APP = "/skalada/";
	public static final String APP_NAME = "skalada";

	// vistas publicas
	public static final String VIEW_PUBLIC_INDEX = "index.jsp";
	public static final String VIEW_LOGIN = "login.jsp";
	public static final String VIEW_SIGNUP = "signup.jsp";
	public static final String VIEW_RECUPERAR_PASS = "recupera-pass.jsp";

	public static final String VIEW_PUBLIC_VIA = "/pages/via_detalle.jsp";

	// Vistas frontoffice
	public static final String ROOT_FRONT = ROOT_APP + "frontoffice/";
	public static final String FRONT_MAPA = ROOT_FRONT + "pages/geomap.jsp";

	// vistas backoffice
	public static final String ROOT_BACK = ROOT_APP + "backoffice/";

	public static final String VIEW_BACK_CONTENT_LOGS = "pages/log-content.jsp";
	public static final String VIEW_BACK_LOGS = "/logs/trazas.jsp";

	public static final String VIEW_BACK_INDEX = "backoffice/pages/index_back.jsp";
	public static final String VIEW_BACK_DASHBOARD = "pages/index_back.jsp";

	public static final String VIEW_BACK_VIAS_INDEX = "pages/vias/index.jsp";
	public static final String VIEW_BACK_VIAS_FORM = "pages/vias/form.jsp";

	public static final String VIEW_BACK_GRADOS_INDEX = "pages/grados/index.jsp";
	public static final String VIEW_BACK_GRADOS_FORM = "pages/grados/form.jsp";

	public static final String VIEW_BACK_TIPO_ESCALADA_INDEX = "pages/tipo-escalada/index.jsp";
	public static final String VIEW_BACK_TIPO_ESCALADA_FORM = "pages/tipo-escalada/form.jsp";

	public static final String VIEW_BACK_ZONAS_INDEX = "pages/zonas/index.jsp";
	public static final String VIEW_BACK_ZONAS_FORM = "pages/zonas/form.jsp";

	public static final String VIEW_BACK_SECTORES_INDEX = "pages/sectores/index.jsp";
	public static final String VIEW_BACK_SECTORES_FORM = "pages/sectores/form.jsp";

	public static final String VIEW_BACK_ROLES_INDEX = "pages/roles/index.jsp";
	public static final String VIEW_BACK_ROLES_FORM = "pages/roles/form.jsp";

	public static final String VIEW_BACK_USUARIOS_INDEX = "pages/usuarios/index.jsp";
	public static final String VIEW_BACK_USUARIOS_FORM = "pages/usuarios/form.jsp";
	public static final String VIEW_BACK_PERFIL_FORM = "pages/usuarios/perfil.jsp";

	// controladores
	public static final String CONTROLLER_LOGIN = ROOT_APP + "login";
	public static final String CONTROLLER_SIGNUP = ROOT_APP + "signup";
	public static final String CONTROLLER_LOGOUT = ROOT_APP + "logout";
	public static final String CONTROLLER_VIAS = ROOT_BACK + "vias";
	public static final String CONTROLLER_HOME = ROOT_APP + "home";
	public static final String CONTROLLER_GRADOS = ROOT_BACK + "grados";
	public static final String CONTROLLER_TIPO_ESCALADA = ROOT_BACK
			+ "tipo-escalada";
	public static final String CONTROLLER_ZONAS = ROOT_BACK + "zonas";
	public static final String CONTROLLER_ZONAS_JSON = ROOT_APP + "zonas-json";
	public static final String CONTROLLER_SECTORES = ROOT_BACK + "sectores";
	public static final String CONTROLLER_ROLES = ROOT_BACK + "roles";
	public static final String CONTROLLER_USUARIOS = ROOT_BACK + "usuarios";
	public static final String CONTROLLER_PERFIL = ROOT_BACK + "perfil";

	public static final String CONTROLLER_GEOMAP = ROOT_FRONT + "geomap";

	// acciones
	public static final int ACCION_NUEVO = 0;
	public static final int ACCION_DETALLE = 1;
	public static final int ACCION_LISTAR = 2;
	public static final int ACCION_ELIMINAR = 3;
	public static final int ACCION_NO_VALIDADOS = 4;
	public static final int ACCION_CONECTADOS = 5;

	// acciones signup
	public static final int ACCION_CONFIRM = 1;
	public static final int ACCION_MOSTRAR_REGENERAR_PASS = 2;
	public static final int ACCION_REGISTRAR_USUARIO = 3;
	public static final int ACCION_PASS_OLVIDADO = 4;
	public static final int ACCION_REGENERAR_PASS = 5;

	// Imagenes y Ficheros
	public static final String IMG_UPLOAD_FOLDER = "C:\\desarrollo\\apache-tomcat-6.0.44\\webapps\\uploads";
	public static final String IMG_UPLOAD_TEMP_FOLDER = "C:\\desarrollo\\apache-tomcat-6.0.44\\temp";
	public static final String IMG_WEP_PATH = SERVER + "/uploads/";
	public static final int MAX_FILE_SIZE = 1000 * 1024;
	public static final int MAX_MEM_SIZE = 40 * 1024;
	public static final String IMG_DEFAULT_SECTOR = "default_sector.jpg";

	public static final ArrayList<String> CONTENT_TYPES = new ArrayList<String>(
			Arrays.asList("image/jpeg", "image/png"));

	// ROLES
	public static final String ROLE_USER = "usuario";
	public static final String ROLE_ADMIN = "administrador";
	public static final int ROLE_ID_USER = 2;
	public static final int ROLE_ID_ADMIN = 1;

	// Usuarios
	public static final int USER_VALIDATE = 1;
	public static final int USER_NO_VALIDATE = 0;
	public static final String EMAIL_TEMPLATE_REGISTRO = "file/plantilla-mail.html";
	public static final String URL_VALIDATE = SERVER
			+ "/skalada/signup?action=1&id=";
	public static final String URL_PASS_OLVIDADO = SERVER
			+ "/skalada/signup?action=2&email=";

	public static final String VALIDACION = "validacion";
	public static final String RECUPERACION = "recuperacion";

	public static final String KEY_SESSION_USER = "ss_user";

	public static final int VALIDADO = 1;
	public static final int NO_VALIDADO = 0;

}
