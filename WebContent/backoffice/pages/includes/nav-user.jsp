<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page
	import="com.ipartek.formacion.skalada.controladores.LoginController"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>



<%	
	Usuario usuario=(Usuario) session.getAttribute(Constantes.KEY_SESSION_USER);
	request.setAttribute("usuario", usuario);
	%>

<li><a href="<%=Constantes.CONTROLLER_HOME%>"><i
		class="fa fa-home fa-fw"></i> Web Publica</a></li>
<li><a href="<%=Constantes.VIEW_BACK_DASHBOARD%>"><i
		class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>
<li><a
	href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_LISTAR%>"><i
		class="fa fa-map-signs fa-fw"></i> Vias<span class="badge right">3</span></a></li>
<li><a
	href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_LISTAR%>"><i
		class="fa fa-globe fa-fw"></i> Zonas<span class="badge right">2</span></a></li>
<li><a
	href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_LISTAR%>"><i
		class="fa fa-map-o fa-fw"></i> Sectores<span class="badge right">1</span></a></li>
		
<li><a href="<%=Constantes.CONTROLLER_PERFIL%>"><i
		class="fa fa-map-o fa-fw"></i> Perfil<span class="badge right">1</span></a></li>