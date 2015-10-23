<%@page import="com.ipartek.formacion.skalada.modelo.ModeloVia"%>
<%@page import="com.ipartek.formacion.skalada.modelo.ModeloSector"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="css/sectores.css" media="screen" />

<%  
	Usuario user = (Usuario) session.getAttribute("admin");
	Via via = (Via)request.getAttribute("via");
	
	ModeloVia modeloVia = new ModeloVia();
	
	ModeloSector modeloSector = new ModeloSector();
	ArrayList<Zona> zonas = new ArrayList<Zona>();
	ArrayList<Sector> sectores = new ArrayList<Sector>();
%>

    <!-- Home Page
    ========================================== -->
    <div id="tf-home" class="text-left">
        <div class="overlay">
            <div class="content">
                 
               <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div width="100%">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="jumbotron">
			<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d11602.36431235352!2d-2.9566062887177496!3d43.36466354088925!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd4e5b5eddac7f85%3A0x22edfcf4e7542f38!2sAtxarte+Kalea%2C+48600+Sopela%2C+Bizkaia!5e0!3m2!1ses!2ses!4v1445415863785" width="100%" height="100%" frameborder="0" style="border:0" allowfullscreen></iframe>
          </div>
          <br>
          
              <div class="sector_detalle">
			
			<p>Nombre: Primer Espolón<a href="#"><i class="fa fa-question-circle"></i></a></p>
			<p>Zona: Atxarte</p>
						
			</div>

          <div class="row">
          <br>
            <div class="col-xs-6 col-lg-4">
            <br>
              <h2>IRENTXO<a href="#"><i class="fa fa-question-circle"></i></a></h2> 
              <input type="hidden" name="id" value="1">
              <br>
              <p>Longitud: 30m </p>
              <p>Grado: 6a <a href="#"><i class="fa fa-question-circle"></i></a></p>
              <p>Tipo Escalada: Deportiva <a href="#"><i class="fa fa-question-circle"></i></a> </p>
              <p><a class="btn btn-default" href="<%=Constantes.CONTROLLER_FRONT_VIAS%>?id=1" role="button">Leer Más &raquo;</a></p>
            </div><!--/.col-xs-6.col-lg-4-->
            
            <div class="col-xs-6 col-lg-4">
            <br>
              <h2>BOSQUE DE LOS INURIOS<a href="#"><i class="fa fa-question-circle"></i></a></h2>
              <input type="hidden" name="id" value="2">
              <br>
              <p>Longitud: 30m </p>
              <p>Grado: 6a <a href="#"><i class="fa fa-question-circle"></i></a></p>
              <p>Tipo Escalada: Deportiva <a href="#"><i class="fa fa-question-circle"></i></a></p>
              <p><a class="btn btn-default" href="<%=Constantes.CONTROLLER_FRONT_VIAS%>?id=2" role="button">Leer Más &raquo;</a></p>
            </div><!--/.col-xs-6.col-lg-4-->
            <div class="col-xs-6 col-lg-4">
            <br>
              <h2>NORMAL<a href="#"><i class="fa fa-question-circle"></i></a></h2>
              <input type="hidden" name="id" value="3">
              <br>
              <p>Longitud: 30m </p>
              <p>Grado: 6a <a href="#"><i class="fa fa-question-circle"></i></a></p>
              <p>Tipo Escalada: Deportiva<a href="#"><i class="fa fa-question-circle"></i></a> </p>
              <p><a class="btn btn-default" href="<%=Constantes.CONTROLLER_FRONT_VIAS%>?id=3" role="button">Leer Más &raquo;</a></p>
            </div><!--/.col-xs-6.col-lg-4-->                  
          </div><!--/row-->
        </div><!--/.col-xs-12.col-sm-9-->

      </div><!--/row-->
<br>
<br>
		
    </div><!--/.container-->   
                 

<jsp:include page="../includes/foot.jsp"></jsp:include>    