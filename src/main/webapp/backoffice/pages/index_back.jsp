<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.listener.ListenerSession"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.modelo.ModeloUsuario"%>
<%@page import="com.ipartek.formacion.skalada.modelo.ModeloSector"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

<%
	ModeloSector modeloSector = new ModeloSector();
	ModeloUsuario modeloUsuario = new ModeloUsuario();
	Usuario usuario = null;
	usuario = (Usuario) session.getAttribute(Constantes.KEY_SESSION_USER);
	int rol = 0;
	if (usuario != null) {
		rol = usuario.getRol().getId();
	}
%>


<div id="page-wrapper">

	<%
		if (rol == Constantes.ROLE_ID_ADMIN) {
	%>
		<jsp:include page="dashboard-admin.jsp"></jsp:include>
	<%
		} else {
	%>
		<jsp:include page="dashboard-user.jsp"></jsp:include>
	<%
		}
	%>
</div>
<!-- /#page-wrapper -->


<jsp:include page="includes/foot.jsp"></jsp:include>


