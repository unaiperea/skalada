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
                        <h3 class="panel-title">Recuperar contraseña</h3>
                    </div>
                    <div class="panel-body">                    

						<% 
							//Parámetros
						 	String email = (String)request.getParameter("email");
							String token = (String)request.getParameter("token");
							
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
			    		
			    		 <!-- Entrará por POST -->
                        <form role="form" action="<%=Constantes.CONTROLLER_FORGOT_PASS%>" method="post" onSubmit="return validar();"> <!-- Si validar() devuelve true submita -->
                            <fieldset>
                            
                            	<!-- Mensajes error de validación -->
                            	<div id="div-msg" class="alert alert-danger alert-dismissible fade in hide" role="alert">
								    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
								    	<span aria-hidden="true">×</span>
								    </button>
								    <h4>Por favor revisa los campos del formulario</h4>
								    <p>
								    	<span id="span-msg" value=""></span>
								    </p>
								    <!-- <p><button type="button" class="btn btn-danger">Cerrar</button></p> -->
							    </div>
							    
							    <!-- Campos de formulario -->
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="email" id="email" type="hidden" value="<%=email%>">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="token" name="token" id="token" type="hidden" value="<%=token%>">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Contraseña" name="password" id="pw1" type="password" required value="" tabindex="1">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Confirmar Contraseña" name="password2" id="pw2" type="password" required value="" tabindex="2">
                                </div>
                                <input class="btn btn-lg btn-block btn-primary"  type="submit" tabindex="3" value="Enviar"> 
                            </fieldset>
                            
                              <script> 
								function validar(){
									
									var resul = false;
									//Inputs formulario
									var mail = document.getElementById("email");
								   	var p1 = document.getElementById("pw1").value;
								   	var p2 = document.getElementById("pw2").value;
								   	//div mensaje
								   	var dm = document.getElementById("div-msg");
								   	var sm = document.getElementById("span-msg");
								   	
							   		if (mail.value != ""){
								   		if (p1.length >= 6){
								   			if (p1 == p2){
								   				resul = true;
										   	}else{
										   		sm.innerHTML = "Las dos claves han de ser iguales";
										   		dm.classList.remove("hide");
										   	}
								   		}else{
								   			sm.innerHTML = "El password ha de tener 6 caracteres mínimo";
								   			dm.classList.remove("hide");
								   		}
							   		}else{
							   			sm.innerHTML = "Email No puede estar vacío";
							   			dm.classList.remove("hide");
							   		}
								   	
								    return resul;
								}
								
								</script> 
                        </form>
                    </div>                    
                </div>
            </div>
        </div>
    </div>
    


<jsp:include page="includes/foot.jsp"></jsp:include>

