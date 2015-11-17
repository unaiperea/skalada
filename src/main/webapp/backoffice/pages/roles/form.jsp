<%@page import="com.ipartek.formacion.skalada.bean.Rol"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
// 	recoger atributos (Objeto Rol) "rol" 
	Rol rol = (Rol)request.getAttribute("rol");
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=rol.getNombre().toUpperCase() %></h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <div class="row">
    	
	<!-- Formulario -->
	
		<form action="<%=Constantes.CONTROLLER_ROLES%>" method="post" role="form">
			
			<div class="row">
				
				<div class="form-group col-lg-1">			
					<!-- Mostramon el input text, pero se submita el hidden -->
					<label for="id">ID</label>
					<input type="hidden" name="id" value="<%=rol.getId()%>">
					<input type="text"  class="form-control" value="<%=rol.getId()%>" disabled >
				</div>
				
				<div class="form-group col-lg-4">
	           		<label for="nombre">Nombre</label>
	           		<input type="text" class="form-control" name="nombre" value="<%=rol.getNombre()%>">
	          	</div>
	          	
	          	<div class="form-group col-lg-5">
	           		<label for="desc">Descripcion</label>
	           		<input type="text" class="form-control" name="desc" value="<%=rol.getDescripcion()%>">
	          	</div>
	        </div>
	        

			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(rol.getId()!= -1){ %>
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
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=rol.getNombre().toUpperCase() %></h2>
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
						    			<a href="<%=Constantes.CONTROLLER_ROLES%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=rol.getId()%>&accion=eliminar" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
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