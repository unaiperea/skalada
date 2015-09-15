<!-- Para que me salgan los acentos -->
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.skalada.bean.Grado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

    
	<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Grados
            	<!-- TODO llamar al Servlet, nunca a una JSP -->
            	<a href="<%=Constantes.CONTROLLER_GRADOS%>?accion=<%=Constantes.ACCION_NUEVO%>" class="btn btn-outline btn-success btn-lg">
            		<i class="fa fa-plus"></i> Nuevo
            	</a>
           	</h1>
        </div>        <!-- /.col-lg-12 -->
    </div>    <!-- /.row -->
    
    <div class="row">
        <% 
			String msg = (String)request.getAttribute("msg-success");	
			if (msg != null){
				out.print("<div class='alert alert-success alert-dismissible' role='alert'>");
					out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
						out.print("<span aria-hidden='true'>&times;</span>");
					out.print("</button>");
					out.print("<strong>"+ msg +"</strong>");
				out.print("</div>");
			} 
		%>
		
		<%	
			msg = (String)request.getAttribute("msg-warning");	
			if (msg != null){
				out.print("<div class='alert alert-warning alert-dismissible' role='alert'>");
					out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
						out.print("<span aria-hidden='true'>&times;</span>");
					out.print("</button>");
					out.print("<strong>"+ msg +"</strong>");
				out.print("</div>");
			} 
		%>
		
		<%	
			msg = (String)request.getAttribute("msg-danger");	
			if (msg != null){
				out.print("<div class='alert alert-danger alert-dismissible' role='alert'>");
					out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
						out.print("<span aria-hidden='true'>&times;</span>");
					out.print("</button>");
					out.print("<strong>"+ msg +"</strong>");
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
	                <th>DescripciÃ³n</th>
	            </tr>
	        </thead> 
	        	 
	        <tbody>	           
	           <%
	           		// recoger el atributo "grados" que nos llegara del Servlet con una coleccion de grados(ArrayList<Grado>)
	           		ArrayList<Grado> grados = (ArrayList<Grado>)request.getAttribute("grados");
	           		
	           		Grado g = null;
	           		for(int i = 0 ; i < grados.size() ; i++){
	           			g = grados.get(i);
   	           %>
   	                <tr>
		                <td><%=g.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_GRADOS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=g.getId()%>">
		                		<%=g.getNombre()%>
		                	</a>
		                </td>
		                <td><%=g.getDescripcion()%></td>
		            </tr>	            
	           <%
	           		} //end for
	           %>	            
           </tbody>
		</table>    
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            