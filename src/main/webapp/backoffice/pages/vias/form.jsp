<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="com.ipartek.formacion.skalada.bean.TipoEscalada"%>
<%@page import="com.ipartek.formacion.skalada.bean.Grado"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recojer atributos de la request
	Usuario usuario = (Usuario)session.getAttribute(Constantes.KEY_SESSION_USER);
	Via via = (Via)request.getAttribute("via");
	ArrayList<Grado> grados = (ArrayList<Grado>)request.getAttribute("grados");
	ArrayList<TipoEscalada> tipoEscaladas = (ArrayList<TipoEscalada>)request.getAttribute("tipoEscaladas");
 	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("zonas");
	ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("sectores");
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=via.getNombre().toUpperCase()%></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
   
    	
	<!-- Formulario -->
	
		<form action="<%=Constantes.CONTROLLER_VIAS%>" method="post" role="form">
 			<div class="row">
				<div class="form-group col-lg-1">
					<!-- Mostramon el input text, pero se submita el hidden -->
					<label for="id">ID</label>
					<input type="hidden" name="id" value="<%=via.getId()%>">
					<input type="text"  class="form-control" value="<%=via.getId()%>" disabled >
				</div>
				<div class="form-group col-lg-3">
	           		<label  for="nombre">Nombre</label>
           			<input type="text" class="form-control" name="nombre" value="<%=via.getNombre()%>">
	          	</div>
				<div class="form-group col-lg-4">
	           		<label for="zona">Zona</label>
           			<select class="form-control" name="zona" id="zona">
	 					<%
	 					for (int i = 0 ; i < zonas.size() ; i++){
	 						if( zonas.get(i).getId() == via.getSector().getZona().getId() ){ %>
							    <option selected value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
						  <%} else { %>
								<option value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
						  <%}//end else  						
	 					}//end for
						%>
					</select>
           		</div>
	           	<div class="form-group col-lg-4">
	           		<label for="sector">Sector</label>
	           		<select class="form-control" id="sector" name="sector">
	 					<%
	 					for (int i = 0 ; i < sectores.size() ; i++){
	 						if( sectores.get(i).getId() == via.getSector().getId() ){ %>
							    <option selected value="<%=sectores.get(i).getId()%>"><%=sectores.get(i).getNombre()%></option>
						  <%} else { %>
								<option value="<%=sectores.get(i).getId()%>"><%=sectores.get(i).getNombre()%></option>
						  <%}//end else  						
	 					}//end for
						%>
					</select>
				</div>
			</div><!-- end row -->
			<div class="row">
	          	<div class="form-group col-lg-2">
	           		<label class="control-label" for="longitud">Longitud</label>
           			<input type="number" class="form-control" name="longitud" value="<%=via.getLongitud()%>">
	          	</div>
	          	<div class="form-group col-lg-2">
	           		<label class="control-label" for="grado">Grado</label>
	           		<select class="form-control" name="grado">
 					<%
 					for (int i = 0 ; i < grados.size() ; i++){
 						if( grados.get(i).getId() == via.getGrado().getId() ){ %>
						    <option selected value="<%=grados.get(i).getId()%>"><%=grados.get(i).getNombre()%></option>
					  <%} else { %>
							<option value="<%=grados.get(i).getId()%>"><%=grados.get(i).getNombre()%></option>
					  <%}//end else  						
 					}//end for
					%>
					</select>	
          		</div>
	          	<div class="form-group col-lg-3">	
	           		<label class="control-label" for="tipo_escalada">Tipo Escalada</label>
	           		<select class="form-control" name="tipo_escalada">
					<%
					for (int i = 0 ; i < tipoEscaladas.size() ; i++){
						if( tipoEscaladas.get(i).getId() == via.getTipoEscalada().getId() ){ %>
						    <option selected value="<%=tipoEscaladas.get(i).getId()%>"><%=tipoEscaladas.get(i).getNombre()%></option>
					  <%} else { %>
							<option value="<%=tipoEscaladas.get(i).getId()%>"><%=tipoEscaladas.get(i).getNombre()%></option>
					  <%}//end else  						
					}//end for
					%>
					</select>
				</div>
				<%
			        String validado = "";
		            String val_class ="";
		            if (via.isValidado()){
		            	validado="checked";
		            }else{
		            	validado="";
		            }
					if (usuario.isAdmin()){
						out.print("<div class='row'><div class='form-group col-lg-3'><label for='creador'>Creado por</label>");
						out.print("<select class='form-control' name='creador'>");
						for (int i = 0 ; i < usuarios.size() ; i++){
							if( usuarios.get(i).getId() == via.getUsuario().getId() ){
								out.print("<option selected value='"+usuarios.get(i).getId()+"'>"+usuarios.get(i).getNombre()+"</option>");
							}else{
								out.print("<option value='"+usuarios.get(i).getId()+"'>"+usuarios.get(i).getNombre()+"</option>");
							}//end else  						
						}//end for
						out.print("</select></div>");
						out.print("<div class='form-group col-lg-2'><label for='validado'>Validado</label><br><input type='checkbox' "+validado+" name='validado' data-toggle='toggle' data-on='Validado' data-off='No Validado' value=1></div></div>");
					}
				%>
			</div>
		    <div class="row"> 	
		        <div class="form-group col-lg-10 col-lg-offset-1">
		            <label class="control-label" for="descripcion">Descripción</label>
	            	<textarea class="form-control" rows="3" name="descripcion"><%=via.getDescripcion()%></textarea>
	       		</div>
	        </div>
		            
	
				
				<!-- Botonera -->
				<div class="form-group">
					<div class="col-md-4">				
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
												<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=via.getNombre().toUpperCase() %></h2>
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
				</div>
			</div>
		</form>
   
    
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            