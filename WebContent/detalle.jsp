<%@page import="com.ipartek.formacion.skalada.Grado"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>

<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="nav.jsp"></jsp:include>
<%
	//Recogemos parámetro "via" de la Clase Via. Lo ha enviado desde el controlador con request.setAttribute("via",via);
	Via via = (Via)request.getAttribute("objeto_Via");
%>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><%=via.getNombre()%></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            
					<div class="form-group">
                    	<label for="id">ID</label>
            			<input type="text" class="form-control" value="<%=via.getId()%>" disabled>
            		</div>	
				
					<div class="form-group">
                    	<label for="nombre">Nombre</label>
                        <input name="nombre" class="form-control" type="text" value="<%=via.getNombre()%>">                        
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
                    	<label for="nombre">Descripcion</label>
                        <textarea class="form-control" name="descripcion"><%=via.getDescripcion()%></textarea>                        
                    </div>
            			
			</div>

<jsp:include page="foot.jsp"></jsp:include>            
