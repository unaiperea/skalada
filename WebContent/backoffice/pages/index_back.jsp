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
%>


        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Panel de control</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6 col-md-6">
                	<a href="#">
                    	<div class="panel panel-success">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-users fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">2</div>
	                                    <div>Usuarios Conectados</div>
	                                </div>
	                            </div>
	                        </div>
                    	</div>
                    </a>
                </div>
                <div class="col-lg-6 col-md-6">
                	<a href="<%=Constantes.CONTROLLER_SECTORES+ "?accion="	+ Constantes.ACCION_LISTAR%>">
	                    <div class="panel panel-info">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-picture-o fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge"><%=modeloSector.sectoresPublicados()%></div>
	                                    <div>Sectores Publicados</div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
					</a>
                </div>
                <div class="col-lg-6 col-md-6">
	            	<a href="<%=Constantes.CONTROLLER_USUARIOS+ "?accion="+ Constantes.ACCION_NO_VALIDADOS%>">
	                    <div class="panel panel-danger">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-user-times fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge"><%=modeloUsuario.usuariosNoValidados()%></div>
	                                    <div>Usuarios no validados</div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
					</a>
                </div>
                <div class="col-lg-6 col-md-6">
                	<a href="<%=Constantes.VIEW_BACK_CONTENT_LOGS%>">
	                    <div class="panel panel-warning">
	                        <div class="panel-heading">
	                            <div class="row">
	                                <div class="col-xs-3">
	                                    <i class="fa fa-file-text-o fa-5x"></i>
	                                </div>
	                                <div class="col-xs-9 text-right">
	                                    <div class="huge">LOGS</div>
	                                    <div>Entre para ver los logs</div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
				</a>
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->


<jsp:include page="includes/foot.jsp"></jsp:include>
   

