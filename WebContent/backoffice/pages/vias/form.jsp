<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.Grado"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recoger parametro (Objeto Via)"via" y (String)"titulo"
	Via via = (Via)request.getAttribute("via");
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
           		<input type="text" class="form-control" name="nombre" value="<%=via.getNombre()%>">
          	</div>

			<div class="form-group">
                <label for="grado">Dificultad </label>
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
	        
	        <div class="form-group">
	            <label for="imagen">Ruta de la imagen</label>
	            <input type="text" class="form-control" name="imagen" value="<%=via.getImagen()%>">
	        </div>

			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(via.getId()!= -1){ %>
						<input type="submit" class="btn btn-outline btn-primary" value="Modificar / Guardar">
  						<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-outline btn-danger" data-toggle="modal" data-target="#myModal">Eliminar</button>
						
						<!-- Ventana Modal -->
						<div class="modal fade col-md-6 col-md-offset-3" id="myModal" role="dialog">
							<div class="modal-dialog">
						  
						    <!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR VIA: <%=via.getNombre().toUpperCase() %></h2>
						  			</div>
						  			<div class="modal-body">
						    			<div class="row checkbox">
                                        	<div class="form-group col-md-12">
                                        		<label>
                                            		<input type="checkbox" id="check_eliminar">Marca la casilla para eliminar
                                           		</label>
                                           		    <!-- Habilitar eliminacion mediante checkbox -->
												    <script>
														document.getElementById('check_eliminar').onclick = function () {
															document.getElementById('boton_eliminar').classList.toggle("disabled");				
														}	
													</script>
                                           	</div>
                                       	</div>
						  			</div>
						  			<div class="modal-footer">						    			
						    			<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=via.getId()%>&accion=eliminar" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
						      			<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						      		</div>
						    	</div> <!-- END Modal content-->
						  	</div>
						</div> <!-- END Ventana Modal -->
			
				
				<% } else { %>
						<input type="submit" class="btn btn-outline btn-primary" value="Crear / Guardar">
						<button type='reset' class='btn btn-outline btn-warning'>Limpiar</button>
				<% } %>
	
				
			</div>
			
		</form>
   
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            