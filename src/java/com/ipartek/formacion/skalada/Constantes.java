package com.ipartek.formacion.skalada;

public class Constantes {

	public static final String ROOT_APP = "/skalada/";
	public static final String APP_NAME = "skalada";
	
	//vistas publicas
	public static final String VIEW_PUBLIC_INDEX = "index.jsp";
	public static final String VIEW_PUBLIC_VIA = "/pages/via_detalle.jsp";
	
	//vistas backoffice
	public static final String ROOT_BACK = ROOT_APP + "backoffice/";
	
	public static final String VIEW_BACK_LOGIN = "backoffice/pages/login.jsp";
	public static final String VIEW_BACK_INDEX = "backoffice/pages/index_back.jsp";
	
	public static final String VIEW_BACK_VIAS_INDEX = "backoffice/pages/vias/index.jsp";
	public static final String VIEW_BACK_VIAS_FORM = "backoffice/pages/vias/form.jsp";
	
	public static final String VIEW_BACK_GRADOS_INDEX = "backoffice/pages/grados/index.jsp";
	public static final String VIEW_BACK_GRADOS_FORM = "backoffice/pages/grados/form.jsp";
	
	public static final String VIEW_BACK_TIPO_ESCALADA_INDEX = "backoffice/pages/tipo-escalada/index.jsp";
	public static final String VIEW_BACK_TIPO_ESCALADA_FORM = "backoffice/pages/tipo-escalada/form.jsp";
	
	public static final String VIEW_BACK_ZONAS_INDEX = "backoffice/pages/zonas/index.jsp";
	public static final String VIEW_BACK_ZONAS_FORM = "backoffice/pages/zonas/form.jsp";
	
	public static final String VIEW_BACK_SECTORES_INDEX = "backoffice/pages/sectores/index.jsp";
	public static final String VIEW_BACK_SECTORES_FORM = "backoffice/pages/sectores/form.jsp";
	
	//controladores
	public static final String CONTROLLER_LOGIN           = ROOT_APP + "login";
	public static final String CONTROLLER_LOGOUT          = ROOT_APP + "logout";	
	public static final String CONTROLLER_VIAS            = ROOT_APP + "vias";
	public static final String CONTROLLER_HOME            = ROOT_APP + "home";
	public static final String CONTROLLER_GRADOS          = ROOT_APP + "grados";
	public static final String CONTROLLER_TIPO_ESCALADA   = ROOT_APP + "tipo-escalada";
	public static final String CONTROLLER_ZONAS           = ROOT_APP + "zonas";
	public static final String CONTROLLER_ZONAS_JSON      = ROOT_APP + "zonas-json";
	public static final String CONTROLLER_SECTORES        = ROOT_APP + "sectores";
	
	//acciones
	public static final int ACCION_NUEVO	= 0;
	public static final int ACCION_DETALLE	= 1;
	public static final int ACCION_LISTAR	= 2;
	public static final int ACCION_ELIMINAR = 3;
	
	//Imagenes y Ficheros
	public static final String IMG_UPLOAD_FOLDER        = "C:\\desarrollo\\java\\apache-tomcat-6.0.43\\webapps\\uploads";
	public static final String IMG_UPLOAD_TEMP_FOLDER   = "C:\\desarrollo\\java\\apache-tomcat-6.0.43\\temp";
	public static final String IMG_WEP_PATH             = "http://localhost:8080/uploads/"; 
	public static final int    MAX_FILE_SIZE            = 1000 * 1024;
	public static final int    MAX_MEM_SIZE             = 40 * 1024;
	public static final String IMG_DEFAULT_SECTOR       = "default_sector.jpg";
	
}
