<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%
	/**
	 *    Gestion de los Mensajes para el Usuario
	 *    Primero busca mensaje en "Request",
	 *    si no lo encuentra busca en "Session" y luego lo elimina de session
	 *    Finalmente muestra el Mensaje si no es null
	 */
	Mensaje msg = (Mensaje) request.getAttribute("msg");
	if (msg == null) {
		msg = (Mensaje)session.getAttribute("msg");
		session.setAttribute("msg", null);
	}

	if (msg != null) {
		out.print("<div class='alert alert-" + msg.getTipo() + " alert-dismissible' role='alert'>");
		out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
		out.print("<span aria-hidden='true'>&times;</span>");
		out.print("</button>");
		out.print("<strong>" + msg.getTexto() + "</strong>");
		out.print("</div>");
	}
%>