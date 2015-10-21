<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<body>
    <!-- Navigation
    ==========================================-->
    <nav id="tf-menu" class="navbar navbar-default navbar-inverse navbar-fixed-top">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<%=Constantes.CONTROLLER_HOME%>">Skalada</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-left">
         	<li><a href="#tf-about" class="page-scroll">Zonas</a></li>
         	<li><a href="#tf-about" class="page-scroll">Crea tu Ruta</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="<%=Constantes.VIEW_LOGIN%>">Login</a></li>
            <li>
           		<ul class="footer-social">
                	<li><a href="#"><i class="fa fa-facebook"></i></a></li>
                	<li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                	<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                	<li><a href="#"><i class="fa fa-twitter"></i></a></li>
            	</ul>
            </li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>