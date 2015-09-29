<%@page import="com.ipartek.formacion.skalada.bean.Mensaje"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.Constantes"%>


<jsp:include page="includes/head.jsp"></jsp:include>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Entrar</h3>
                    </div>
                    <div class="panel-body">                    
                		
                		<!-- Podríamos meterlo en un include para no copiar y pegarlo siempre -->
  						<% 
				            Mensaje msg = (Mensaje)request.getAttribute("msg");	
							if (msg != null){
								out.print("<div class='alert alert-"+ msg.getTipo() +" alert-dismissible' role='alert'>");
									out.print("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
										out.print("<span aria-hidden='true'>&times;</span>");
									out.print("</button>");
									out.print("<strong>"+ msg.getTexto() +"</strong>");
								out.print("</div>");
							} 
						%>              
                    
                        <form role="form" action="<%=Constantes.CONTROLLER_LOGIN%>" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="email" type="email" value="unaiperea@gmail.com" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Contraseña" name="password" type="password" value="aaaaaa">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Recordar
                                    </label>
                                </div>
                                <input class="btn btn-lg btn-block btn-success" type="submit" value="login">
                                <a href="pages/signup.jsp" class="btn btn-lg btn-block btn-primary">¿No estás registrado?</a>
                                <a href="#" data-toggle="modal" data-target="#myModal">¿Has olvidado la contraseña?</a>
                            </fieldset>
                        </form>
                        
                        <div class="bs-example">
						    <!-- Button HTML (to Trigger Modal) -->
						    <!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" data-title="Feedback">¿Has olvidado la contraseña?</button> -->
						    
						    <!-- Modal HTML -->
						    <div id="myModal" class="modal fade">
						        <div class="modal-dialog">
						            <div class="modal-content">
						                <div class="modal-header">
						                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						                    <h4 class="modal-title">Recuperar contraseña</h4>
						                </div>
						                <div class="modal-body">
						                    <form role="form" action="<%=Constantes.CONTROLLER_FORGOT_PASS%>" method="get"> <!-- Lo enviamos por GET queriendo -->
						                        <div class="form-group">
						                            <label for="recipient-name" class="control-label">Email:</label>
						                            <input class="form-control" placeholder="E-mail" name="email-forget" type="email" tabindex="1" required autofocus>
						                        </div>
						                        <div class="modal-footer">
								                    <button type="button" class="btn btn-default" data-dismiss="modal" tabindex="3">Cancelar</button>
								                    <input class="btn btn-primary"  type="submit" tabindex="2" value="Enviar">
						                		</div>
						                    </form>
						                </div>
						            </div>
						        </div>
						    </div>
						</div>

                    </div>                    
                </div>
            </div>
        </div>
    </div>

<jsp:include page="includes/foot.jsp"></jsp:include>
