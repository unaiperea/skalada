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
                    <h1 class="page-header">Edici&oacute;n Vias</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            
				<form role="form" action="#" method="post">
				
					<div class="form-group">
						<!-- Mostramos el input text, pero se submita el hidden -->
						<label for="id">ID</label>
						<input type="hidden" name="id" value="">
						<input type="text" class="form-control" value="69" name="id" disabled>
					</div>
				
					<div class="form-group">
					    <label for="nombre">Nombre</label>
					    <input class="form-control" name="nombre" type="text" value="<%=via.getNombre()%>">
					</div>
					
					<div class="form-group">
					    <label for="grado">Grado</label>
					    <input class="form-control" name="grado" type="text" value="<%=via.getGrado()%>">
					</div>
					
					<div class="form-group">
					    <label for="longitud">Longitud</label>
					    <input class="form-control" name="longitud" type="text" value="<%=via.getLongitud()%>">
					</div>
					
					<div class="form-group">
					    <label for="descripcion">Descripcion</label>
					    <input class="form-control" name="descripcion" type="text" value="<%=via.getDescripcion()%>">
					</div>
					
					<!-- TODO Resto de inputs -->
					
					<div class="form-group">
						<input type="submit" class="btn btn-outline btn-primary" value="Modificar">
						<input type="button" class="btn btn-outline btn-danger" value="Eliminar">
					</div>
				</form>
            
            </div>
            
        </div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            