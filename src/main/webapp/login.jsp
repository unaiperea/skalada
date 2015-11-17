
<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.modelo.ModeloUsuario"%>
<%@page import="com.ipartek.formacion.skalada.modelo.ModeloZona"%>

<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<%
	if (request.getSession().getAttribute(Constantes.KEY_SESSION_USER)!=null){
		request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX).forward(request, response);
	}
%>


<jsp:include page="includes/head.jsp"></jsp:include>


<body>

<div id="tf-login" class="text-center">
<div class="overlay">
<div class="container">
	<ol class="breadcrumb container transparente">
		<li><a href="#">Inicio</a></li>
		<li class="active">Login</li>
	</ol>
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title" style="text-align: center;">- Skalada Login -</h3>
				</div>
				<div class="panel-body">

					<%@include file="includes/mensaje.jsp" %>

					<form role="form" action="<%=Constantes.CONTROLLER_LOGIN%>"
						method="post">
						<fieldset>
							<div class="form-group">
								<input class="form-control" style="margin-top: 25px;" placeholder="E-mail" name="email"
									type="email" value="unaiperea@gmail.com" autofocus>
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Contraseña"
									name="password" type="password" value="aaaaaa">
							</div>
							<div class="checkbox">
								<label style="font-size: 14px;"> <input name="remember" type="checkbox"
									value="Remember Me" onClick="marcar(this)" >Recordar mis datos
								</label>
								<!-- Cambio de color del Checkbox si esta seleccionado -->
								<script>  
								function marcar(check) {  
								    check.parentNode.parentNode.style.color=check.checked?"#21C558":"grey"; 
								}  
								</script> 								
																 
								<br>
							</div>
							<input class="btn btn-lg btn-block btn-primary-login" type="submit"
								value="Login" style="border-radius:25px; margin-bottom:10px">
								<a class="link" href="recupera-pass.jsp" style="margin-left: -125px;">¿Has olvidado tu contraseña?</a>
								<br>
								<br>
							<a href="<%=Constantes.VIEW_SIGNUP%>" class="btn btn-lg btn-block btn-primary-login" style="border-radius:25px;margin-bottom: 25px;">¿Aún no estás registrado?</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Ventana Modal -->
<div class="modal fade col-md-6 col-md-offset-3" id="modal-olvidado" role="dialog">
	<div class="modal-dialog">
  
    <!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h2 class="modal-title text-center text-info"><i class="fa fa-exclamation-triangle"></i> RECUPERAR CONTRASEÑA</h2>
  			</div>
  			<form role="form" action="<%=Constantes.CONTROLLER_SIGNUP%>" method="post">
	  			<div class="modal-body">
	    			<div class="row">
	    				
                                   	<div class="form-group col-md-12">
                                   		<input type="hidden" name="action" id="action" value="4">
                                   		<label for="email-olvidado">Email:</label>
                                   		<input type="email" name="email-olvidado" id="email-olvidado" class="form-control">                                           		    
                                      	</div>
                                  	</div>
	  			</div>
	  			<div class="modal-footer">						    			
	    			<button type="submit" id ="boton-enviar-olvidado" class="btn btn-success btn-l">Enviar</button>
	      			<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
	      		</div>
      		</form>
    	</div> <!-- END Modal content-->
  	</div>
</div> <!-- END Ventana Modal -->
</div>
</div>

<jsp:include page="includes/foot.jsp"></jsp:include>
