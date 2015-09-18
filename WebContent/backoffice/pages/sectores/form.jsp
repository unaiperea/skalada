<!-- Para que me salgan los acentos -->
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%

	Sector sector = (Sector)request.getAttribute("sector");
	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("zonas");
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
	<!-- @see: http://www.tutorialspoint.com/servlets/servlets-file-uploading.htm -->
	
		<form action="<%=Constantes.CONTROLLER_SECTORES%>"
			enctype="multipart/form-data" //Para subir ficheros
			method="post"
			role="form">
			
			<div class="row col-md-offset-1 col-md-10">

					<div class="form-group">			
						<!-- Mostramon el input text, pero se submita el hidden -->
						<label for="id">ID</label>
						<input type="hidden" name="id" value="<%=sector.getId()%>">
						<input type="text"  class="form-control" value="<%=sector.getId()%>" disabled >
					</div>
					
					<div class="form-group">
		           		<label for="nombre">Nombre</label>
		           		<input type="text" class="form-control" name="nombre" value="<%=sector.getNombre()%>">
		          	</div>
	
		          	<div class="form-group">
			            <label for="zona">Zona</label>
			            <select class="form-control" name="zona">
	  					<%
	  					for (int i=0 ; i<zonas.size() ; i++){
	  					%>
	  						
	  						    <% if( zonas.get(i).getId() == sector.getZona().getId() ){ %>
	  						    	<option selected value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
	  						    <%}else{ %>
	  								<option value="<%=zonas.get(i).getId()%>"><%=zonas.get(i).getNombre()%></option>
	  							<%}//end else  						
	  					}//end for
						%>
						</select>
			        </div>
			        <div class="form-group">			
						<!-- Mostramon el input text, pero se submita el hidden -->
						<label for="imagen">Imágen</label>
						<input id="imagen" type="file"  class="form-control" name="imagen" onchange="showFileSize();">
						
						<%
							String img_path = Constantes.IMG_DEFAULT_SECTOR;
							if ( !img_path.equals(sector.getImagen()) ){ //Si la imágen es diferente a la de por defecto personalizar
								img_path = Constantes.IMG_WEB_PATH + sector.getImagen();
							}else{
								img_path = "../img/" + img_path; //Carga la imágen por defecto
							}
						%>
						<img src="<%=img_path%>" 
							 alt="Imágen del sector "<%=sector.getNombre()%>
							 class="img-responsive img-thumbnail"></a>
					</div>
				</div>
	        </div>
	        
			<script type='text/javascript'>
			function showFileSize() {
			    var input, file;
			    // (Can't use `typeof FileReader === "function"` because apparently
			    // it comes back as "object" on some browsers. So just see if it's there
			    // at all.)
			    if (!window.FileReader) {
			        bodyAppend("p", "The file API isn't supported on this browser yet.");
			        return;
			    }
			
			    input = document.getElementById('imagen');
			    
			    if (input.files[0]){
			    	file = input.files[0];
			    		if(file.size > <%=Constantes.IMG_MAX_FILE_SIZE%>){
			    			alert("Demasiado grande la imágen");
			    			//También se puede crear un alert dinámicamente y darle formato con Bootstrap
			    			document.getElementById('btn_submit').classList.toggle("disabled"); //Si está quitada la pone y sino la quita 
			    		}else{
			    			document.getElementById('btn_submit').classList.remove("disabled");
			    		}
			    }
			    
			    /*if (!input) {
			        bodyAppend("p", "Um, couldn't find the fileinput element.");
			    }
			    else if (!input.files) {
			        bodyAppend("p", "This browser doesn't seem to support the `files` property of file inputs.");
			    }
			    else if (!input.files[0]) {
			        bodyAppend("p", "Please select a file before clicking 'Load'");
			    }
			    else {
			        file = input.files[0];
			        bodyAppend("p", "File " + file.name + " is " + file.size + " bytes in size");
			    }*/
			}
			
			/*
			* Crea dinámicamente un elemento dentro del documento
			*/
			function bodyAppend(tagName, innerHTML) {
			    var elm;
			    
				//Crea dinámicamente un elemento dentro del documento
			    elm = document.createElement(tagName); //Crea un elemento con el tag que lo paso
			    elm.innerHTML = innerHTML; //Meto un texto
			    document.body.appendChild(elm); //Lo mete dentro del documento
			}
			</script>
			
			<!-- Botonera -->
			<div class="form-group">
								
				<% if(sector.getId()!= -1){ %>
						<input type="submit" id="btn_submit" class="btn btn-outline btn-primary" value="Modificar">
  						<!-- Trigger the modal with a button -->
						<button type="button" class="btn btn-outline btn-danger" data-toggle="modal" data-target="#myModal">Eliminar</button>
						
						<!-- Ventana Modal -->
						<div class="modal fade col-md-6 col-md-offset-3" id="myModal" role="dialog">
							<div class="modal-dialog">
						  
						    <!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h2 class="modal-title text-center text-danger"><i class="fa fa-exclamation-triangle"></i> ELIMINAR: <%=sector.getNombre().toUpperCase() %></h2>
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
						    			<a href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=sector.getId()%>&accion=eliminar" id ="boton_eliminar" class="btn btn-danger btn-xs disabled">Eliminar</a>
						      			<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						      		</div>
						    	</div> <!-- END Modal content-->
						  	</div>
						</div> <!-- END Ventana Modal -->
			
				
				<% } else { %>
						<input type="submit" id="btn_submit" class="btn btn-outline btn-primary" value="Guardar">
						<button type='reset' class='btn btn-outline btn-warning'>Limpiar</button>
				<% } %>
	
				
			</div>
			
		</form>
   
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            