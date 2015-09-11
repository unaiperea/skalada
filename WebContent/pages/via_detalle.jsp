<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	Via via = (Via)request.getAttribute("via");

%>

<div class="container">
	<div class="col-md-7 col-md-offset-2">
<%--      	<h1 class="col-md-offset-2"><%=via.getNombre().toUpperCase()%></h1> --%>
       		<div class="col-md-12 well well-sm">
       			<img class="img-responsive" src="" alt="">
           	</div>                
      	
          	<div class="col-md-5 col-md-offset-1 well">
<%--                 <strong>Dificultad: </strong><%=via.getGrado()%>           --%>
           	</div>
         	<div class="col-md-5 well">
<%--             	<strong>Longitud: </strong><%=via.getLongitud()%> --%>
          	</div>
    	
    		<div class="col-md-12 well">
    			<p><strong>Descripcion:</strong></p>
<%--     			<%=via.getDescripcion()%>   			 --%>
    		</div>    	
	</div>
</div>







<jsp:include page="../includes/foot.jsp"></jsp:include>