<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//Comprobar si es Administrado
	boolean isAdmin = false;
	Usuario usuario = (Usuario)session.getAttribute(Constantes.KEY_SESSION_USER);
	if ( usuario != null ){
		if ( Constantes.ROLE_ID_ADMIN == usuario.getRol().getId() ){
			isAdmin = true;
		}	
	}

%>


<div id="page-wrapper">

    
	<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Sectores
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
            		<i class="fa fa-plus"></i> Nuevo
            	</a>
           	</h1>
        </div>        <!-- /.col-lg-12 -->
    </div>    <!-- /.row -->
    
<!-- MUESTRA EL MENSAJE -->
    <div class="row">    
        <%@include file="../includes/mensaje.jsp" %>        
	</div> <!-- /.row -->
<!-- END MUESTRA DE MENSAJE -->

    <div class="row">    
    <!-- tabla -->
    	<table id="tabla" class="display" cellspacing="0" width="100%">
	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>Nombre</th>
	                <th>Zona</th>
	                <th>Validado</th>
	                <%if (isAdmin){%>
	                	<th>Usuario</th>
	                <%}%>
	                <th>Longitud</th>
	                <th>Latitud</th>	
	                
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "sectores" que nos llegara del Servlet con una coleccion de sectores(ArrayList<Sector>)
	           		ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("sectores");
	           		
	           		Sector s = null;
	           		for(int i = 0 ; i < sectores.size() ; i++){
	           			s = sectores.get(i);
   	           %>
   	                <tr>
		                <td><%=s.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=s.getId()%>">
		                		<%=s.getNombre()%>
		                	</a>
		                </td>
		                <td><%=s.getZona().getNombre()%></td>
		                <!-- Validado -->
		                <td>	
		                	<% if (s.isValidado()){%>
		                		<span class="label label-success">Validado</span>	
		                	<%}else{%>
		                		<span class="label label-warning">Sin Validar</span>
		                	<%}%>
		                </td>
		                
		                <!-- Mostrar Usuario solo para Administradores -->
		                 <%if (isAdmin){%>
		                 	
	                		<td>
	                			<a href="<%=Constantes.CONTROLLER_USUARIOS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=s.getUsuario().getId()%>">
	                				<%=s.getUsuario().getNombre()%>
	                			</a>
	                		</td>
	                			
	                	<%}%>	
	                	
	                	<td><%=s.getLongitud()%></td>
		                <td><%=s.getLatitud()%></td>
		                
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            