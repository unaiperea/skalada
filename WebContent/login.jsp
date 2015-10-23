<%@include file="includes/head.jsp" %>

<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%
	if (request.getSession().getAttribute(Constantes.KEY_SESSION_USER)!=null){
		request.getRequestDispatcher(Constantes.VIEW_BACK_INDEX).forward(request, response);
	}
%>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><fmt:message key="login.titulo"></fmt:message></h3>
				</div>
				<div class="panel-body">

					<jsp:include page="includes/mensaje.jsp"></jsp:include>

					<form role="form" action="<%=Constantes.CONTROLLER_LOGIN%>"
						method="post">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="E-mail" name="email"
									type="email" autofocus>
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Contraseña"
									name="password" type="password" value="">
							</div>
							<div class="checkbox">
								<label> <input name="remember" type="checkbox"
									value="Remember Me">Recordar
								</label> <a class="btn btn-l btn-info align-right" id="btn-olvidado" data-toggle="modal" data-target="#modal-olvidado">¿Ha
									olvidado su contraseña?</a>
							</div>
							<input class="btn btn-lg btn-block btn-success" type="submit"
								value="login">
							<a href="<%=Constantes.VIEW_SIGNUP%>" class="btn btn-lg btn-block btn-primary">¿No estás registrado?</a>
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

<jsp:include page="includes/foot.jsp"></jsp:include>