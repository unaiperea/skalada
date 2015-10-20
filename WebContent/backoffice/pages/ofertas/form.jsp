<%@page import="com.ipartek.formacion.skalada.bean.Rol"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 
<%@page import="com.ipartek.formacion.skalada.bean.Oferta"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
// 	recoger atributos (Objeto Zona)"zona" y (String)"titulo"
	Ofertas oferta = (Oferta)request.getAttribute("oferta");
	String titulo = request.getAttribute("titulo").toString();
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("zonas");
	Usuario usuario = (Usuario)session.getAttribute(Constantes.KEY_SESSION_USER);	
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=titulo %></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	
	<!-- Formulario -->
	
		<form action="<%=Constantes.CONTROLLER_OFERTAS%>" method="post" role="form">
			
			<div class="row col-lg-12">
				
				<div class="form-group">			
					<!-- Mostramon el input text, pero se submita el hidden -->
					<label for="id">ID</label>
					<input type="hidden" name="id" value="<%=oferta.getId()%>">
					<input type="text"  class="form-control" value="<%=oferta.getId()%>" disabled >
				</div>
				
				<div class="form-group">
	           		<label for="nombre">Titulo</label>
	           		<input type="text" class="form-control" name="titulo" value="<%=oferta.getTitulo()%>">
	          	</div>
	          	
	          	<div class="form-group">
	           		<label for="desc">Descripcion</label>
	           		<input type="text" class="form-control" name="descripcion" value="<%=oferta.getDescripcion()%>">
	          	</div>
	          	<div class="form-group">
	           		<label for="precio">Precio</label>
	           		<input type="text" class="form-control" name="precio" value="<%=oferta.getPrecio()%>">
	          	</div>
	          				
	          	<div class="form-group col-lg-3">
					<label for="zona">Zona</label> <select class="form-control"
						name="zona">
						<%
							for (int i = 0 ; i < zonas.size() ; i++){
						%>
	
						<%
							if( zonas.get(i).getId() == sector.getZona().getId() ){
						%>
						<option selected value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
						<%
							}else{
						%>
						<option value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
						<%
							}//end else  						
															  					}//end for
						%>
					</select>
				</div>
				 <%if (isAdmin){
					  if (oferta.getVisible()==Constantes.OFERTA_VISIBLE){
			            	visible="checked";
			            }else{
			            	visible="";
			            }
				 %>
						<div class='form-group col-lg-2'>
							<label for='visible'>Visible</label><br>
							<input type="checkbox" <%=visible%> name="visible" data-toggle='toggle' data-on='Visible' data-off='No Visible' value=1>
						</div>
	                	<%} else {%>
						<div class='form-group col-lg-2'>
							<button type="button" class="btn btn-outline btn-danger" data-toggle="modal" data-target="#modalInscribirse">Apúntate</button>
						</div>
				<% } %>
				
	          	<div class="form-group">
	           		<label for="fecha_alta">Fecha_alta</label>
	           		<input type="date" class="form-control" name="fecha_alta" value="<%=oferta.getFecha_alta()%>">
	          	</div>
	          	<div class="form-group">
	           		<label for="fecha_alta">Fecha_baja</label>
	           		<input type="date" class="form-control" name="fecha_baja" value="<%=oferta.getFecha_baja()%>">
	          	</div>
	        </div>
	        
						<!-- Ventana Modal #modalInscribirse -->
						<div class="modal fade col-md-6 col-md-offset-3" id="modalInscribirse" role="dialog">
							<div class="modal-dialog">
						  
						    <!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> Condiciones de la oferta</h2>
											 <p>bla bla bla
						  			</div>
						  			<div class="modal-body">
						    			<div class="row checkbox">
                                        	<div class="form-group col-md-12">
                                        		<label>
                                            		<input type="checkbox" id="check_eliminar">Marca la casilla para apuntarte
                                           		</label>
                                           		    <!-- Habilitar eliminacion mediante checkbox -->
												    <script>
														document.getElementById('check_inscribir').onclick = function () {
															document.getElementById('boton_inscripcion').classList.toggle("disabled");				
														}	
													</script>
                                           	</div>
                                       	</div>
						  			</div>
						  			<div class="modal-footer">						    			
						    			<a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_INSCRIBIR%>&user=<%=usuario%>&accion=eliminar" id ="boton_inscripcion" class="btn btn-danger btn-xs disabled">Apuntar</a>
						      			<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						      		</div>
						    	</div> <!-- END Modal content-->
						  	</div>
						</div> <!-- END Ventana Modal -->
			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(oferta.getId()!= -1){ %>
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
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=oferta.getTitulo().toUpperCase() %></h2>
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
						    			<a href="<%=Constantes.CONTROLLER_OFERTAS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=oferta.getId()%>&accion=eliminar" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
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