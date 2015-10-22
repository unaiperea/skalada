<%@page import="com.ipartek.formacion.skalada.bean.Rol"%>
<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
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
            <h1 class="page-header">Usuarios
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_USUARIOS%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
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
	                <th>Email</th>
	                <th>Validado</th>
	                <th>Rol</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "zonas" que nos llegara del Servlet con una coleccion de zonas(ArrayList<Zona>)
	           		ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
	           		Usuario u = null;
	           		for(int i = 0 ; i < usuarios.size() ; i++){
	           			u = usuarios.get(i);
   	           %>
   	                <tr>
		                <td><%=u.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_USUARIOS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=u.getId()%>">
		                		<%=u.getNombre()%>
		                	</a>
		                </td>
		                <td><%=u.getEmail()%></td>
		                <%
		                String validado = "";
		                String val_class ="";
		                if (u.isValidado()){
		                	validado="Validado";
		                	val_class="success";
		                }else{
		                	validado="No Validado";
		                	val_class="danger";
		                }
		                %>
		                <td><span class="label label-<%=val_class%>"><%=validado %></span></td>
		               	<td><%=u.getRol().getNombre()%></td>
		            </tr>           
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>     
