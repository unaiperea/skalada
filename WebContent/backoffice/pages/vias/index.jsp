<!-- Para que me salgan los acentos --> 
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
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
    
    <div class="row">
        <% 
			Mensaje msg = (Mensaje)request.getAttribute("msg");	
			if (msg != null){
				out.print("<div class='alert " + msg.getTipo() + " alert-dismissible' role='alert'>");
					out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
					out.print("<span aria-hidden='true'>&times;</span>");
					out.print("</button>");
					out.print("<strong>"+ msg.getTexto() +"</strong>");
				out.print("</div>");
			} 
		%>
		
	</div> <!-- /.row -->

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
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "vias" que nos llegara del Servlet con una coleccion de vias(ArrayList<Via>)
	           		ArrayList<Via> vias = (ArrayList<Via>)request.getAttribute("vias");
	           		
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
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            