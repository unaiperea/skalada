<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>

<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Oferta"%>
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
            <h1 class="page-header">Ofertas
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
            		<i class="fa fa-plus"></i> Nueva
            	</a>
           	</h1>
        </div>        <!-- /.col-lg-12 -->
    </div>    <!-- /.row -->
    
    <div class="row">
        <% 
			Mensaje msg = (Mensaje)request.getAttribute("msg");	
			if (msg != null){
				out.print("<div class='alert alert-"+ msg.getTipo() +" alert-dismissible' role='alert'>");
					out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
						out.print("<span aria-hidden='true'>&times;</span>");
					out.print("</button>");
					out.print("<strong>"+ msg.getTexto() +"</strong>");
				out.print("</div>");
			} 
		%>
		
	</div> <!-- /.row -->

    <div class="row">
    <table id="tabla" class="display" cellspacing="0" width="100%">
    <%if(usuario.isAdmin()){ %>
    <!-- tabla ADMINISTRADOR -->
    	
	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>Título</th>
	                <th>Zona</th>
	                <th>Precio</th>
                	<th>Usuarios</th>
	                <th>Visible</th>                	
	                <th>Fecha alta</th>
	                <th>Fecha baja</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "sectores" que nos llegara del Servlet con una coleccion de sectores(ArrayList<Sector>)
	           		ArrayList<Oferta> ofertas = (ArrayList<Oferta>)request.getAttribute("ofertas");
	           		
	           		Oferta o = null;
	           		for(int i = 0 ; i < ofertas.size() ; i++){
	           			o = ofertas.get(i);
	            %>
   	                <tr>
		                <td><a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=o.getId()%>"><%=o.getId()%></a></td>
		                <td><a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=o.getId()%>"><%=o.getTitulo()%></a>
		                </td>
		                <td><%=o.getZona().getNombre()%></td>
		                <td><%=o.getPrecio()%></td>
                		<td><%=o.getNumeroSuscritos()%></td>
                		<td>
		                	<% if (o.getVisible()==Constantes.OFERTA_VISIBLE){%>
		                		<span class="label label-success">Visible</span>	
		                	<%}else{%>
		                		<span class="label label-warning">No Visible</span>
		                	<%}%>
	                	</td>            	
		                <td><%=o.getFecha_alta()%></td>
		                <td><%=o.getFecha_baja()%></td>
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
  
	<% }else{ %>
  <!-- tabla USUARIO -->

	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>Título</th>
	                <th>Zona</th>
	                <th>Precio</th>
                	<th>Inscrito</th>
                	<th>Fecha inscripción</th>	
	                <th>Fecha alta</th>
	                <th>Fecha baja</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "sectores" que nos llegara del Servlet con una coleccion de sectores(ArrayList<Sector>)
	           		ArrayList<Oferta> ofertas = (ArrayList<Oferta>)request.getAttribute("ofertas");
	           		
	           		Oferta o = null;
	           		for(int i = 0 ; i < ofertas.size() ; i++){
	           			o = ofertas.get(i);
	           			if(o.getVisible()==Constantes.OFERTA_VISIBLE){
	            %>
   	                <tr>
		                <td><a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=o.getId()%>"><%=o.getId()%></a></td>
		                <td><a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=o.getId()%>"><%=o.getTitulo()%></a></td>
		                <td><%=o.getZona().getNombre()%></td>
		                <td><%=o.getPrecio()%></td>
		                	<%
		                	boolean inscrito=false;
		                	Date date = new Date();
	                		Timestamp fechaIns = new Timestamp(date.getTime());
	                		for(int j=0; j<o.getUsuariosInscritos().size();j++){
	                		//recorremos el arraylist para buscar al usuario	
	                			if(usuario.getId()==o.getUsuariosInscritos().get(j).getId()){
	                				inscrito=true;
	                				fechaIns = o.getUsuariosInscritos().get(j).getFechaInscripcion();
	                			}
	                		}               	
	                		if (inscrito){%>
	                			<td><span class="label label-success">Inscrito</span></td>
	                			<td><%=fechaIns%></td>		
	                		<%}else{%>
	                			<td><span class="label label-warning">No inscrito</span></td>
	                			<td></td> 
		                		
		                		<%}%>
		                <td type="date"><%=o.getFecha_alta()%></td>
		                <td type="date"><%=o.getFecha_baja()%></td>
		            </tr>	            
	           <%
	           			} //end del if VISIBLE
	           		} //end for
	           %>	            
           </tbody>

	<%} %> 
	</table> 	
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            