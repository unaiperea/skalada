<%@page import="com.ipartek.formacion.skalada.Grado"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//Recogemos parámetro "via" de la Clase Via. Lo ha enviado desde el controlador con request.setAttribute("via",via);
	Via via = (Via)request.getAttribute("via");
%>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><%=request.getAttribute("titulo")%></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            
				<form role="form" action="<%=Constantes.CONTROLLER_VIAS%>" method="post">
				
					<div class="form-group">
            			<!-- Mostramos el input text, pero se submita el hidden -->
                    	<label for="id">ID</label>
                    	<input type="hidden" name="id" value="<%=via.getId()%>">
            			<input type="text" class="form-control" value="<%=via.getId()%>" disabled>
            		</div>	
				
					<div class="form-group">
                    	<label for="nombre">Nombre</label>
                        <input class="form-control" name="nombre" type="text" value="<%=via.getNombre()%>">                        
                    </div>
					
					<div class="form-group">
					    <label for="grado">Grado</label>
					    <!-- <input class="form-control" name="grado" type="text" value="<%=via.getGrado()%>"> -->
						<select name="grado">
							<!-- Lo que manda al Servlet es el value -->
							<%
							//Iteramos
							//Grado[] grados = new Grado.values(); Podríamos utilizar una variable intermedia
							for (int i=0; i<Grado.values().length; i++){
								//Si mientras insertamos conincide con el que tiene la vía en sí que se quede seleccionada
								//Operador ternario (todo junto) -> variable = ( condición )? SI_TRUE : SI_FALSE ;
								String selected = (Grado.values()[i] == via.getGrado())? "selected" : "" ;%>
								
								<option <%=selected%> value="<%=Grado.values()[i]%>"><%=Grado.values()[i]%></option>
							<%}%>
						</select>
					
					<div class="form-group">
					    <label for="longitud">Longitud</label>
					    <input class="form-control" name="longitud" type="text" value="<%=via.getLongitud()%>">
					</div>
					
					<div class="form-group">
                    	<label for="nombre">Descripcion</label>
                        <textarea class="form-control" name="descripcion"><%=via.getDescripcion()%></textarea>                        
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
                                            		<input type="checkbox" id="check_eliminar">Marque la casilla para eliminar:
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

<jsp:include page="../includes/foot.jsp"></jsp:include>            
