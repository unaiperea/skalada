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
                        <h3 class="panel-title">Registrate</h3>
                    </div>
                    <div class="panel-body">                    

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
			    
                        <form role="form" name="f1"  method="post">
                            <fieldset>
                            	<div class="alert alert-danger alert-dismissible fade in" role="alert">
								    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
								    	<span aria-hidden="true">×</span>
								    </button>
								    <h4>Error !!</h4>
								    <p>
								    	<span id="msg" value=""></span>
								    </p>
								    <p>
								      <button type="button" class="btn btn-danger">Take this action</button>
								    </p>
							    </div>
		                        <button id="alerta" type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
                            	<div class="form-group">
                                    <input class="form-control" placeholder="Nombre" name="nombre" id="nombre" type="text" required autofocus tabindex="1">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="email" id="email" type="email" required tabindex="2">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Contraseña" name="password" id="pw1" type="password" required value="" tabindex="3">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Confirmar Contraseña" name="password2" id="pw2" type="password" required value="" tabindex="4">
                                </div>
                                <input class="btn btn-lg btn-block btn-primary"  onClick="return validar();" type="submit" tabindex="5" value="Registrate"> 
                            </fieldset>
                            
                              <script> 
								function validar(){
									
									var resul = false;
									
									var nombre = document.getElementById("nombre");
									var mail = document.getElementById("email");
								   	var p1 = document.getElementById("pw1").value;
								   	var p2 = document.getElementById("pw2").value;
								   	var m = document.getElementById("msg");
								   	
								   	
								   	//$('#alerta').on('closed.bs.alert', function () {}
								   	
								   	if ( nombre.value.length >= 4  ){
								   		if (mail.value != ""){
									   		if (p1.length >= 6){
									   			if (p1 == p2){
											      	m.value = "Las dos claves son iguales...";
											   	}else{
											   		resul = true;
											   		m.value = "Las dos claves son distintas...";
											   	}
									   		}else{
									   			m.value = "El password ha de tener 6 caracteres mínimo";
									   		}
								   		}else{
								   			m.value = "Email No puede estar vacío";
								   		}
								   	}else{
								   		m.value = "El campo Nombre ha de tener 4 caracteres mínimo";
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

