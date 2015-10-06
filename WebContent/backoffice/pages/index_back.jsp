<%@page import="com.ipartek.formacion.skalada.listener.ListenerUserCounter"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.listener;"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Dashboard2</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-5x"> <i
								class="fa fa-users fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=ListenerUserCounter.contadorSession%></div>
							<div class="huge">Usuarios conectados</div>
						</div>
					</div>
				</div>
				<a href="#">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-green">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-5x"> <i
								class="fa fa-picture-o fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("num_sectores")%></div>
							<div class="huge">Sectores publicados</div>
						</div>
					</div>
				</div>
				<a href="#">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-red">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-5x"> <i
								class="fa fa-user fa-stack-1x"></i> <i
								class="fa fa-ban fa-stack-2x text-danger"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("num_user_invalid")%></div>
							<div class="huge">Usuarios sin validar</div>
						</div>
					</div>
				</div>
				<a href="#">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							  class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-yellow">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-5x"> 
								<i class="fa fa-file-text-o fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge">.</div>
							<div class="huge">Logs</div>
						</div>
					</div>
				</div>
				<a href="<%=Constantes.VIEW_BACK_CONTENT_LOGS%>">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>
			</div>
		</div>
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

<jsp:include page="includes/foot.jsp"></jsp:include>