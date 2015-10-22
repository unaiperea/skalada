
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.bean.TipoEscalada"%>
<%@page import="com.ipartek.formacion.skalada.bean.Grado"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	//recojer atributos de la request
	String titulo = request.getAttribute("titulo").toString();

	Via via = (Via)request.getAttribute("via");
	ArrayList<Grado> grados = (ArrayList<Grado>)request.getAttribute("grados");
	ArrayList<TipoEscalada> tipoEscaladas = (ArrayList<TipoEscalada>)request.getAttribute("tipoEscaladas");
 	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("zonas");
	ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("sectores");
	
%>

    <!-- Detalle via
    ========================================== -->
    <div id="tf-home" class="text-left">
        <div class="overlay">
            <div class="content">
                 <div class="row">
	                <div class="col-md-4">
	                    <img src="img/via_del_nino2.jpg" class="img-responsive">
	                </div>
	                <div class="col-md-8">
	                    <div class="about-text">
	                        <div class="section-title">
	                            <h1><strong><%=via.getNombre()%></strong></h1>
	                            <h5>Zona <strong>zona</strong></h5>
	                            <hr>
	                            <div class="clearfix"></div>
	                        </div>
	                        
	                         <ul class="about-list">
	                            <li>
	                                <span class="fa fa-dot-circle-o"></span>
	                                <strong>Longitud</strong> - <em><%=via.getLongitud()%></em><em> metros</em> </li>
	                            <li>
	                                <span class="fa fa-dot-circle-o"></span>
	                                <strong>Dificultad</strong> - <em><%=via.getGrado()%></em>
	                                <a class="fa fa-info-circle" data-toggle="modal" data-target=".bs-example-modal-sm"></a>
	                                <!-- Small modal -->
	                                	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
										  <div class="modal-dialog modal-sm">
										    <div class="modal-content">
										      <p><%=via.getDescripcion()%></p>
										    </div>
										  </div>
										</div>
	                            </li>
	                            <li>
	                                <span class="fa fa-dot-circle-o"></span>
	                                <strong>Tipo Escalada</strong> - <em>Deportiva</em>
	                                <a class="fa fa-info-circle" data-toggle="modal" data-target=".bs-example-modal-sm"></a>
	                                <!-- Small modal -->
	                                	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
										  <div class="modal-dialog modal-sm">
										    <div class="modal-content">
										      <p>Este tipo de escalada se caracteriza por que en la pared podemos encontrar anclajes fijos colocados estratégicamente para asegurar nuestros pasos. Podemos encontrar anclajes basados en sistemas mecánicos -de expansión- o químicos -resinas-. Por lo general, estas vías al equiparse, se limpian de maleza y de piedras sueltas o susceptibles de romperse, para ganar en la seguridad del escalador deportivo.</p>
										    </div>
										  </div>
										</div>
	                            </li>
	                        </ul>
	                        
	                        <p class="intro">Descripcion: Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ornare mi eu rhoncus consequat. Nunc varius, orci eget consequat dapibus, justo metus cursus erat, et auctor erat ligula ut massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras eget enim rhoncus, eleifend lectus quis, iaculis nibh. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec nec viverra nunc, ac faucibus augue. Vestibulum egestas iaculis mauris, et scelerisque ex consequat eu. Praesent sodales nisi in efficitur volutpat. Quisque cursus sodales odio, finibus ultricies orci feugiat sit amet. Praesent molestie iaculis libero eu dictum. Vivamus a nisi erat.</p>
	                       
	                    </div>
	                </div>
	            </div>
            </div>
        </div>
    </div>

<jsp:include page="../includes/foot.jsp"></jsp:include>
    