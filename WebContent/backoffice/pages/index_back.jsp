<%@page import="com.ipartek.formacion.skalada.listener.ListenerUserCounter"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Panel de administración</h1>
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
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-red">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-5x"> <i
								class="fa fa-user-times fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("totalInvalidados")%></div>
							<div class="huge">Usuarios sin validar</div>
						</div>
					</div>
				</div>
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							  class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
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
						<span class="pull-left">Detalles</span> <span
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
								class="fa fa-heart fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("totalLikes")%></div>
							<div class="huge">Likes</div>
						</div>
					</div>
				</div>
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
			</div>
		</div>
		<div class="col-md-4">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-3x"> <i
								class="fa fa-map fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("totalZonas")%></div>
							<div>Zonas publicadas</div>
						</div>
					</div>
				</div>
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
			</div>
		</div>
		<div class="col-md-4">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-3x"> <i
								class="fa fa-map-signs fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("totalSectores")%></div>
							<div>Sectores publicados</div>
						</div>
					</div>
				</div>
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
			</div>
		</div>
		<div class="col-md-4">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<span class="fa-stack fa-3x"> <i
								class="fa fa-random fa-rotate-270 fa-stack-2x"></i>
							</span>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge"><%=request.getAttribute("totalVias")%></div>
							<div>Vías publicadas</div>
						</div>
					</div>
				</div>
				<!-- <a href="#"> -->
					<div class="panel-footer">
						<span class="pull-left">Detalles</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				<!-- </a> -->
			</div>
		</div>
		
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

<jsp:include page="includes/foot.jsp"></jsp:include>