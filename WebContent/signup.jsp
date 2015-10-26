<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="com.ipartek.formacion.skalada.Constantes"%>


<jsp:include page="includes/head.jsp"></jsp:include>
<div id="tf-signup" class="text-center">
<div class="overlay">
<div class="container">
	<ol class="breadcrumb container transparente">
		<li><a href="#">Inicio</a></li>
		<li class="active">Registrate</li>
	</ol>
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">- Registrate -</h3>
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

					<form role="form" action="<%=Constantes.CONTROLLER_SIGNUP%>" method="post"
						onSubmit="return validateForm()">
						<fieldset>
							<div class="form-group">
								<input type="hidden" name="action" id="action" value="3">
								<input class="form-control" placeholder="Nombre" name="nombre"
									id="nombre" type="text" required autofocus tabindex="1">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="E-mail" name="email"
									id="email" type="email" required tabindex="2">
							</div>
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
							<input class="btn btn-lg btn-block btn-primary login" type="submit"
								tabindex="5" value="Registrate" style="border-radius: 25px;">
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
								var p1 = document.f1.password.value;
								var p2 = document.f1.pw2.value;
								var nombre = document.getElementById("nombre").value;
								if (nombre.length < 4) {
									mensaje = "<strong>El nombre es demasiado corto (Minimo 4 caracteres)</strong>";
									document.getElementById("mensaje").innerHTML = mensaje;
									document.getElementById("alerta").classList
											.toggle('hidden');
									validado = false;
									return validado;
								}
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