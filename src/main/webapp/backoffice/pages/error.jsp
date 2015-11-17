<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page isErrorPage="true"%>
<%@page contentType="text/html; charset=UTF-8"%>

<head>
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="../css/error.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>Oops! ${pageContext.errorData.statusCode} Error</h1>
					<div class="error-details">Perdon por las molestias, algo ha
						fallado en el servidor.</div>
					<div class="error-actions">
						<a href="<%=Constantes.ROOT_APP + Constantes.VIEW_BACK_INDEX%>"
							class="btn btn-primary btn-lg"><span
							class="glyphicon glyphicon-home"></span> Volver al inicio </a><a
							href="" class="btn btn-default btn-lg"><span
							class="glyphicon glyphicon-envelope"></span> Contactar con el
							administrador</a>
					</div>
				</div>
			</div>
			<div class="col-md-8 col-md-offset-2">
				<div class="panel-group">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<button class="btn btn-primary center-block" data-toggle="collapse" data-target="#collapse1">Abrir traza
									completa</button>
							</h4>
						</div>
						<div id="collapse1" class="panel-collapse collapse">
							<div class="panel-body">Traza del Error</div>
							<div class="panel-footer">
								<c:forEach var="trace"
									items="${pageContext.exception.stackTrace}">
									<p>${trace}</p>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>


<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

