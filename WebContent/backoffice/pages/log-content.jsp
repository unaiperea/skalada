<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

<div id="page-wrapper">
    <div class="row">
    
    	<h1>Logs</h1> 
    	<div class="embed-responsive embed-responsive-4by3">   	
    		<embed src="<%=Constantes.VIEW_BACK_LOGS%>">
    	</div>	
    
    </div> <!-- .row -->
</div> <!-- #page-wrapper" -->

<jsp:include page="includes/foot.jsp"></jsp:include>    