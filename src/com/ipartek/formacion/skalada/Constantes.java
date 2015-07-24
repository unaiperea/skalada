package com.ipartek.formacion.skalada;

public class Constantes {

	public static final String ROOT_APP = "/skalada/";
	
	//vistas publicas
	public static final String VIEW_PUBLIC_INDEX = ROOT_APP + "index.jsp";
	
	//vistas backoffice
	public static final String ROOT_BACK = ROOT_APP + "backoffice/";
	
	public static final String VIEW_BACK_LOGIN = "backoffice/pages/login.jsp";
	public static final String VIEW_BACK_INDEX = "backoffice/pages/index_back.jsp";
	
	public static final String VIEW_BACK_VIAS_INDEX = "backoffice/pages/vias/index.jsp";
	
	
	//controladores
	public static final String CONTROLLER_LOGIN  = ROOT_APP + "login";
	public static final String CONTROLLER_LOGOUT = ROOT_APP + "logout";
	public static final String CONTROLLER_VIAS   = ROOT_APP + "vias";
	
	
}
