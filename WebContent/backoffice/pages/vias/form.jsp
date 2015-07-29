<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.Grado"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recoger parametro "via" de la clase Via
	Via via = (Via)request.getAttribute("via");
%>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><%=request.getAttribute("titulo") %></h1>
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
           		<input class="form-control" name="nombre" type="text" value="<%=via.getNombre()%>">
          	</div>

			<div class="form-group">
                <label for="grado">Grado <% Grado grado = via.getGrado(); %></label>
                <select class="form-control" name="grado" > 
                    <option value="FACIL" <% if( grado.equals(Grado.FACIL) ){ out.print("selected");  }%>>FACIL</option>
					<option value="NORMAL" <% if( grado.equals(Grado.NORMAL) ) out.print("selected"); %> >NORMAL</option>
					<option value="DIFICIL" <% if( grado.equals(Grado.DIFICIL) ) out.print("selected"); %> >DIFICIL</option>
					<option value="EXTREMO" <% if( grado.equals(Grado.EXTREMO) ) out.print("selected"); %> >EXTREMO</option>             
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
		
			<!-- TODO resto de inputs -->
			
			<!-- Botonera -->
			<div class="form-group">
				<input type="submit" class="btn btn-outline btn-primary" value="<%=request.getAttribute("metodo")%>">
				
				<%
					if ("Modificar".equals(request.getAttribute("metodo"))){
						out.print("<a href='"+ Constantes.CONTROLLER_VIAS + "?accion=" + Constantes.ACCION_ELIMINAR + "&id=" + via.getId() + "&accion=eliminar' class='btn btn-outline btn-danger'>Eliminar</a>");
					}

					if ("Guardar".equals(request.getAttribute("metodo"))){
						out.print("<button type='reset' class='btn btn-outline btn-danger'>Reset</button>");
					}
				
				
				%>

<!-- 				<button type='reset' class='btn btn-outline btn-danger'>Reset</button> -->

<%-- 				<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_ELIMINAR%>&id=<%=via.getId()%>&accion=eliminar" class="btn btn-outline btn-danger"> --%>
<!-- 					Eliminar -->
<!-- 				</a> -->
				
			</div>
			
		</form>
   
    </div>
    
</div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            