<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.skalada.Constantes"%>


<jsp:include page="includes/head.jsp"></jsp:include>
<div id="tf-pass" class="text-center">
<div class="overlay">
<div class="container">
	<ol class="breadcrumb container transparente">
		<li><a href="#">Inicio</a></li>
		<li class="active">Recuperar Password</li>
	</ol>
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">- Recupera tu password -</h3>
				</div>
				<div class="panel-body">


					<div class='alert alert-danger hidden'
						id="alerta" role='alert'>
						<button type='button' class='close' data-dismiss='alert'
							aria-label='Close'>
							<span aria-hidden='true'>&times;</span>
						</button>
						<p id="mensaje">
							<strong>Error al registrarse</strong>
						</p>
					</div>


					<%
						String email = (String)request.getParameter("email");
						String token = (String)request.getParameter("tkn");
						Mensaje msg = (Mensaje) request.getAttribute("msg");
						if (msg != null) {
							out.print("<div class='alert alert-" + msg.getTipo()
									+ " alert-dismissible' role='alert'>");
							out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
							out.print("<span aria-hidden='true'>&times;</span>");
							out.print("</button>");
							out.print("<strong>" + msg.getTexto() + "</strong>");
							out.print("</div>");
						}
					%>

					<form role="form" id="f1" action="<%=Constantes.CONTROLLER_SIGNUP%>?action=<%=Constantes.ACCION_REGENERAR_PASS%>" method="post"
						onSubmit="return validateForm()">
						<input type="hidden" name="email" value="<%=email%>">
						<input type="hidden" name="token" value="<%=token%>">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="Contraseña"
									name="password" id="password" type="password" required value=""
									tabindex="3">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Confirmar Contraseña"
									name="password2" id="pw2" type="password" required value=""
									tabindex="4">
							</div>
							<input class="btn btn-lg btn-block btn-primary" type="submit"
								tabindex="5" value="Cambia tu contraseña">
						</fieldset>

						<script>
							function validateForm() {
								if (!document.getElementById("alerta").classList
										.contains("hidden")) {
									document.getElementById("alerta").classList
											.add("hidden");
								}
								var validado = true;
								var mensaje = "";
								var p1 = document.getElementById("password").value;
								var p2 = document.getElementById("pw2").value;
								if (p1 != p2) {
									mensaje = "<strong>Las password no coinciden</strong>";
									document.getElementById("mensaje").innerHTML = mensaje;
									document.getElementById("alerta").classList
											.toggle('hidden');
									validado = false;
									return validado;
								}
								if (p1.length < 6) {
									mensaje = "<strong>El password es demasiado corto (Minimo 6 caracteres)</strong>";
									document.getElementById("mensaje").innerHTML = mensaje;
									document.getElementById("alerta").classList
											.toggle('hidden');
									validado = false;
									return validado;
								}
								return validado;
							}
						</script>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>


<jsp:include page="includes/foot.jsp"></jsp:include>