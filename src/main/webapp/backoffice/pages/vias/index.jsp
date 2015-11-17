<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

    
	<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Vias
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
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

	<% 
		Usuario usuario = (Usuario)session.getAttribute(Constantes.KEY_SESSION_USER);
	%>
    <div class="row">
    
    <!-- tabla -->
    	<table id="tabla" class="display" cellspacing="0" width="100%">
	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>Nombre</th>
	                <th>Longitud</th>
	                <th>Dificultad</th>	                
	                <th>Tipo escalada</th>
	                <th>Sector</th>
	                <%if (usuario.isAdmin()){ %> 
	                <th>Usuario</th>
	                <%} %>
	                <th>Validado</th>             
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "vias" que nos llegara del Servlet con una coleccion de vias(ArrayList<Via>)
	           		ArrayList<Via> vias = (ArrayList<Via>)session.getAttribute("vias");
	           		
	           		Via v = null;
	           		for(int i = 0 ; i < vias.size() ; i++){
	           			v = vias.get(i);
   	           %>
   	                <tr>
		                <td><%=v.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=v.getId()%>">
		                		<%=v.getNombre()%>
		                	</a>
		                </td>
		                <td><%=v.getLongitud()%></td>
		                <td><%=v.getGrado().getNombre()%></td>
		                <td><%=v.getTipoEscalada().getNombre()%></td>
		                <td><%=v.getSector().getNombre()%> (<%=v.getSector().getZona().getNombre()%>)</td>	                
		               	<!-- Mostrar Usuario solo para Administradores -->
		                 <%if (usuario.isAdmin()){%>
		                 	
	                		<td>
	                			<a href="<%=Constantes.CONTROLLER_USUARIOS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=v.getUsuario().getId()%>">
	                				<%=v.getUsuario().getNombre()%>
	                			</a>
	                		</td>
	                	<%}%>
	                	<!-- end Usuario -->
	                	<!-- Validado -->
		                <td>	
		                	<% if (v.isValidado()){%>
		                		<span class="label label-success">Validado</span>	
		                	<%}else{%>
		                		<span class="label label-warning">Sin Validar</span>
		                	<%}%>
		                </td>	
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            