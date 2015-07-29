<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.Grado"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recoger parametro "via" de la clase Via
	Via via = (Via)request.getAttribute("via");
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=request.getAttribute("titulo") %></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	
	<!-- Formulario -->
	
		<form action="<%=Constantes.CONTROLLER_VIAS%>" method="post" role="form">
			
			<div class="form-group">			
				<!-- Mostramon el input text, pero se submita el hidden -->
				<label for="id">ID</label>
				<input type="hidden" name="id" value="<%=via.getId()%>">
				<input type="text"  class="form-control" value="<%=via.getId()%>" disabled >
			</div>
			
			<div class="form-group">
           		<label for="nombre">Nombre</label>
           		<input class="form-control" name="nombre" type="text" value="<%=via.getNombre()%>">
          	</div>

			<div class="form-group">
                <label for="grado">Grado </label>
                <select class="form-control" name="grado" >
                	
                	<%	
                		Grado[] grados = Grado.values();
                		String selected = "";
                		for (int i = 0; i < grados.length ; i++){ 
                			
//                 			String selected = ( grados[i] == via.getGrado() )?"selected":"";		//operador ternario
                			
                			if( grados[i] == via.getGrado() ){
                				selected = "selected";  
                			} else {
                				selected = "";
                			}
                	%>
                		<option value="<%=grados[i]%>" <%=selected%> ><%=grados[i]%></option>
                	<% 	} %>

             	</select>
          	</div>
          	
          	<div class="form-group">
           		<label for="longitud">Longitud</label>
           		<input type="number" class="form-control" name="longitud" value="<%=via.getLongitud()%>">
          	</div>
          	
          	<div class="form-group">
	            <label for="descripcion">Descripcion</label>
	            <textarea class="form-control" rows="3" name="descripcion"><%=via.getDescripcion()%></textarea>
	        </div>
		
			<!-- TODO resto de inputs -->
			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(via.getId()!= -1){ %>
						<input type="submit" class="btn btn-outline btn-primary" value="Modificar / Guardar">
						<div id="fade" class="overlay"></div>
							<div id="light" class="modal">
						  		<p>Lorem ipsum dolor sit.....</p>
						  	</div>
						  	
							<a href="" class="btn btn-outline btn-danger" onclick="myFunction()">Eliminar</a>
					
					
				
<%-- 				href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=via.getId()%>&accion=eliminar" --%>
				
				
				<% } else { %>
						<input type="submit" class="btn btn-outline btn-primary" value="Crear / Guardar">
						<button type='reset' class='btn btn-outline btn-warning'>Limpiar</button>
				<% } %>
	
				
			</div>
			
		</form>
   
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            