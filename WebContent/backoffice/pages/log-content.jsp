<!-- Para que me salgan los acentos -->
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

    <div class="row embed-responsive embed-responsive-4by3">

    	<h1>Logs</h1>
    	<!-- No permite incluir un jsp de un proyecto externo -->
    	<embed src="<%=Constantes.VIEW_BACK_LOGS%>">

    </div> <!-- end: .row -->
</div> <!-- end: #page-wrapper -->

<jsp:include page="includes/foot.jsp"></jsp:include>