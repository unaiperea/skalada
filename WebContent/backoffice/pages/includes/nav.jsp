<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page
	import="com.ipartek.formacion.skalada.controladores.LoginController"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<%	
	Usuario usuario = null;
	usuario = (Usuario) session.getAttribute(Constantes.KEY_SESSION_USER);
	%>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation"
	style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand"
			href="<%=Constantes.ROOT_APP + Constantes.VIEW_BACK_INDEX%>">
			Backoffice </a>
	</div>
	<!-- /.navbar-header -->

	<ul class="nav navbar-top-links navbar-right">

		<!-- Perfil del usuario -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"> ${sessionScope.ss_user.nombre}
				(${sessionScope.ss_user.rol.nombre}) <i class="fa fa-user fa-fw"></i>
				<i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="<%=Constantes.CONTROLLER_PERFIL%>"><i class="fa fa-user fa-fw"></i> User
						Profile</a></li>
				<li><a href="<%=Constantes.CONTROLLER_LOGOUT%>"><i
						class="fa fa-sign-out fa-fw"></i> Logout</a></li>
			</ul> <!-- /.dropdown-user --></li>
		<!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->

	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li class="sidebar-search">
				<form action="<%=Constantes.CONTROLLER_SEARCH%>" method="post">
					<div class="input-group custom-search-form">
							<input type="text" name="search_input" id="search_input" class="form-control" placeholder="Search...">
							<span class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="fa fa-search"></i>
								</button>
							</span>
					</div> <!-- /input-group -->
				</form>
				</li>
				<%	if (usuario.isAdmin()) {	%>
				<%@include file="nav-admin.jsp"%>
				<%	} else { %>
				<%@include file="nav-user.jsp"%>
				<%} %>
			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>
