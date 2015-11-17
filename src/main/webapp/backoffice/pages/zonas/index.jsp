<%@page import="java.text.SimpleDateFormat"%>
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
            <h1 class="page-header">Zonas
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
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
	                <th>Creado por</th>
	                <th>Creado el</th>
	                <th>Modificado el</th>
	                <th>Validado</th>
	                <th>Longitud</th>
	                <th>Latitud</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "zonas" que nos llegara del Servlet con una coleccion de zonas(ArrayList<Zona>)
	           		ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("zonas");
	           		
	           		Zona z = null;
	           		for(int i = 0 ; i < zonas.size() ; i++){
	           			z = zonas.get(i);
   	           %>
   	                <tr>
		                <td><%=z.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=z.getId()%>">
		                		<%=z.getNombre()%>
		                	</a>
		                </td>
		                <td><%=z.getUsuario().getNombre() %></td>
		                <%
		                	String fechaCreado = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(z.getFechaCreado());
		                	String fechaModificado = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(z.getFechaModificado());
		                %>
		                <td><%=fechaCreado %></td>
		                <td><%=fechaModificado %></td>
		                <%
		                String validado = "";
		                String val_class ="";
		                if (z.isValidado()){
		                	validado="Validado";
		                	val_class="success";
		                }else{
		                	validado="No Validado";
		                	val_class="danger";
		                }
		                %>
		                <td><span class="label label-<%=val_class%>"><%=validado %></span></td>
		                <td><%=z.getLongitud()%></td>
		                <td><%=z.getLatitud()%></td>
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            