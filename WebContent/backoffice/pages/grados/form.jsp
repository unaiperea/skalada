<%@page import="com.ipartek.formacion.skalada.bean.Grado"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//Recogemos par�metro "via" de la Clase Via. Lo ha enviado desde el controlador con request.setAttribute("via",via);
	Grado g = (Grado)request.getAttribute("grado");
	String titulo = request.getAttribute("titulo").toString();
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
            
				<form role="form" action="<%=Constantes.CONTROLLER_GRADOS%>" method="post">
				
					<div class="form-group">
            			<!-- Mostramos el input text, pero se submita el hidden -->
                    	<label for="id">ID</label>
                    	<input type="hidden" name="id" value="<%=g.getId()%>"> <!-- Si viaja en el formulario -->
            			<input type="text" class="form-control" value="<%=g.getId()%>" disabled> <!-- No viaja en el formulario -->
            		</div>	
				
					<div class="form-group">
                    	<label for="nombre">Nombre</label>
                        <input class="form-control" name="nombre" type="text" value="<%=g.getNombre()%>">                        
                    </div>
					
					<div class="form-group">
                    	<label for="nombre">Descripcion</label>
                        <textarea class="form-control" name="descripcion"><%=g.getDescripcion()%></textarea>                        
                    </div>

					<!-- Botonera -->
			<div class="form-group">
								
				<% if(g.getId()!= -1){ %>
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
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=g.getNombre().toUpperCase() %></h2>
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
						    			<a href="<%=Constantes.CONTROLLER_GRADOS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=g.getId()%>" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
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
