<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%>

<%@include file="../includes/head.jsp"%>
<%@include file="../includes/nav.jsp"%>

<%
	String texto_busqueda = request.getParameter("search_input");
	//HashMap con datos de busqueda
	ArrayList<Via> vias_busqueda = new ArrayList<Via>();
	ArrayList<Zona> zonas_busqueda = new ArrayList<Zona>();
	ArrayList<Sector> sectores_busqueda = new ArrayList<Sector>();
	ArrayList<Usuario> usuarios_busqueda= new ArrayList<Usuario>();
	//Cargamos los HashMap
	vias_busqueda = (request.getAttribute("vias_busqueda")!=null)?(ArrayList<Via>)request.getAttribute("vias_busqueda"):vias_busqueda;
	zonas_busqueda = (request.getAttribute("zonas_busqueda")!=null)?(ArrayList<Zona>)request.getAttribute("zonas_busqueda"):zonas_busqueda;
	sectores_busqueda = (request.getAttribute("sectores_busqueda")!=null)?(ArrayList<Sector>)request.getAttribute("sectores_busqueda"):sectores_busqueda;
	usuarios_busqueda=(request.getAttribute("usuarios_busqueda")!=null)?(ArrayList<Usuario>)request.getAttribute("usuarios_busqueda"):usuarios_busqueda;
%>



<div id="page-wrapper">
	<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><i class="fa fa-search"></i> Buscando... "<%=texto_busqueda %>"</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    
<!-- Grupos de resultados -->
	<!-- Vias -->
	<%if (!vias_busqueda.isEmpty()){ %>
    <div class="panel panel-default">
    	<div class="panel-heading"><h3 class="margin-top-bottom-0"><i
		class="fa fa-map-signs"></i> Vias</h3></div>
    	<div class="panel-body">
    	
    	<ul class="list-group">
    		<%
    		Iterator it = vias_busqueda.iterator();
    		Via via = null;
    		while (it.hasNext()){
    			via = (Via)it.next();
    		%>
    			<li class="list-group-item hover"><a href="<%=Constantes.CONTROLLER_VIAS+"?accion="+Constantes.ACCION_DETALLE+"&id="+via.getId()%>"><%=via.getNombre().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>")%>[<%=String.valueOf(via.getLongitud()) %> metros] (<%=via.getDescripcion().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>") %>)</a></li>
    		<%
    		}
    		%>
    		</ul>
    	
    	</div>
    </div>
    <%} %>
    
    
    <!-- Zonas -->
    <%if (!zonas_busqueda.isEmpty()){ %>
    <div class="panel panel-default">
    	<div class="panel-heading"><h3 class="margin-top-bottom-0"><i
		class="fa fa-globe"></i> Zonas</h3></div>
    	<div class="panel-body">
    	
    	<ul class="list-group">
    		<%
    		Iterator it = zonas_busqueda.iterator();
    		Zona zona = null;
    		while (it.hasNext()){
    			zona = (Zona)it.next();
    		%>
    			<li class="list-group-item hover"><a href="<%=Constantes.CONTROLLER_ZONAS+"?accion="+Constantes.ACCION_DETALLE+"&id="+zona.getId()%>"><%=zona.getNombre().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>")%></a></li>
    		<%
    		}
    		%>
    		</ul>
    	
    	</div>
    </div>
    <%} %>
    
    <!-- Sectores -->
    <%if (!sectores_busqueda.isEmpty()){ %>
    <div class="panel panel-default">
    	<div class="panel-heading"><h3 class="margin-top-bottom-0"><i
		class="fa fa-map-o"></i> Sectores</h3></div>
    	<div class="panel-body">
    	
    	<ul class="list-group">
    		<%
    		Iterator it = sectores_busqueda.iterator();
    		Sector sector = null;
    		while (it.hasNext()){
    			sector = (Sector)it.next();
    		%>
    			<li class="list-group-item hover"><a href="<%=Constantes.CONTROLLER_SECTORES+"?accion="+Constantes.ACCION_DETALLE+"&id="+sector.getId()%>"><%=sector.getNombre().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>")%></a></li>
    		<%
    		}
    		%>
    		</ul>
    	
    	</div>
    </div>
    <%} %>
    
    <!-- Usuarios -->
    <%if (!usuarios_busqueda.isEmpty()){ %>
    <div class="panel panel-default">
    	<div class="panel-heading"><h3 class="margin-top-bottom-0"><i
		class="fa fa-users"></i> Usuarios</h3></div>
    	<div class="panel-body">
    		<ul class="list-group">
    		<%
    		Iterator it = usuarios_busqueda.iterator();
    		Usuario usu = null;
    		while (it.hasNext()){
    			usu = (Usuario)it.next();
    		%>
    			<li class="list-group-item hover"><a href="<%=Constantes.CONTROLLER_USUARIOS+"?accion="+Constantes.ACCION_DETALLE+"&id="+usu.getId()%>"><%=usu.getNombre().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>")%> (<%=usu.getEmail().replaceAll(texto_busqueda, "<mark>"+texto_busqueda+"</mark>") %>)</a></li>
    		<%
    		}
    		%>
    		</ul>
    	</div>
    </div>
    <%} %>
    
</div>

<%@include file="../includes/foot.jsp"%>