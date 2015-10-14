<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recoger atributos (Objeto Zona)"zona" y (String)"titulo"
	Zona zona = (Zona)request.getAttribute("zona");
	String titulo = request.getAttribute("titulo").toString();
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
	Usuario usuario = (Usuario)session.getAttribute(Constantes.KEY_SESSION_USER);
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
	
		<form action="<%=Constantes.CONTROLLER_ZONAS%>" method="post" role="form">
			
			<div class="row">
				<div class="form-group col-lg-1">			
					<!-- Mostramon el input text, pero se submita el hidden -->
					<label for="id">ID</label>
					<input type="hidden" name="id" value="<%=zona.getId()%>">
					<input type="text"  class="form-control" value="<%=zona.getId()%>" disabled >
				</div>
				<div class="form-group col-lg-3">
	           		<label for="nombre">Nombre</label>
	           		<input type="text" class="form-control" name="nombre" value="<%=zona.getNombre()%>">
	          	</div>
	          		<!-- Fechas -->
	          	<%
	           		String fechaCreado = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(zona.getFechaCreado());
                	String fechaModificado = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(zona.getFechaModificado());
	           	%>
	          	<div class="form-group col-lg-4">
	           		<label for="fecha_creacion">Fecha de Creacion</label>
	           		<input type="datetime-local" disabled class="form-control" name="fecha_creacion" value="<%=fechaCreado%>">
	          	</div>
	          	<div class="form-group col-lg-4">
	           		<label for="fecha_modificacion">Fecha de Modificacion</label>
	           		<input type="datetime-local" disabled class="form-control" name="fecha_modificacion" value="<%=fechaModificado%>">
	          	</div>         	
	      	</div><!-- end row -->
	      	<%
		        String validado = "";
	            String val_class ="";
	            if (zona.isValidado()){
	            	validado="checked";
	            }else{
	            	validado="";
	            }
				if (usuario.getRol().getId()==Constantes.ROLE_ID_ADMIN){
					out.print("<div class='row'><div class='form-group col-lg-3'><label for='creador'>Creado por</label>");
							
					out.print("<select class='form-control' name='creador'>");
					for (int i = 0 ; i < usuarios.size() ; i++){
						if( usuarios.get(i).getId() == zona.getUsuario().getId() ){
							out.print("<option selected value='"+usuarios.get(i).getId()+"'>"+usuarios.get(i).getNombre()+"</option>");
						}else{
							out.print("<option value='"+usuarios.get(i).getId()+"'>"+usuarios.get(i).getNombre()+"</option>");
						}//end else  						
					}//end for
					out.print("</select></div>");
					out.print("<div class='form-group col-lg-2'><label for='validado'>Validado</label><br><input type='checkbox' "+validado+" name='validado' data-toggle='toggle' data-on='Validado' data-off='No Validado' value=1></div></div>");
				}
			%>
			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(zona.getId()!= -1){ %>
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
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=zona.getNombre().toUpperCase() %></h2>
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
						    			<a href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=zona.getId()%>&accion=eliminar" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
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