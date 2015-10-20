<%@page import="com.ipartek.formacion.skalada.bean.Rol"%>
<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	String titulo = request.getAttribute("titulo").toString();
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=titulo%></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    
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
 	
	<!-- Formulario -->
	
		<form action="<%=Constantes.CONTROLLER_PERFIL%>" method="post" role="form">
			
			<div class="row col-lg-12">
				
				
				<div class="form-group">
	           		<label for="nombre">Nombre</label>
	           		<input type="text" required class="form-control" name="nombre" value="${requestScope.perfil.nombre}">
	          	</div>
	          	
	        	<div class="form-group">
	           		<label for="email">Correo Electronico</label>
	           		<input type="email" required class="form-control" name="email" value="${requestScope.perfil.email}">
	          	</div>
	        	
	        	<div class="form-group">
	           		<label for="password">Contraseña</label>
	           		<input type="password" required class="form-control" name="password" value="${requestScope.perfil.password}">
	          	</div>
	        </div>
	        
			<input type="submit" class="btn btn-outline btn-primary" value="Modificar / Guardar">
		
		</form>
   
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            