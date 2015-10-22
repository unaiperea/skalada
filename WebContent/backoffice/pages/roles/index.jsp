<%@page import="com.ipartek.formacion.skalada.bean.Rol"%>s
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

    
	<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Roles
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_ROLES%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
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
	                <th>Descripcion</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "zonas" que nos llegara del Servlet con una coleccion de zonas(ArrayList<Zona>)
	           		ArrayList<Rol> roles = (ArrayList<Rol>)request.getAttribute("roles");
	   				if ( roles == null ){
	   					roles = new ArrayList<Rol>();
	   				}
	           		Rol r = null;
	           		for(int i = 0 ; i < roles.size() ; i++){
	           			r = roles.get(i);
   	           %>
   	                <tr>
		                <td><%=r.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_ROLES%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=r.getId()%>">
		                		<%=r.getNombre()%>
		                	</a>
		                </td>
		                <td><%=r.getDescripcion()%></td>
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            