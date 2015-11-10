<%@page import="com.ipartek.formacion.skalada.modelo.ModeloSector"%>
<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.bean.Usuario"%>
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="includes/head.jsp"></jsp:include>
<jsp:include page="includes/nav.jsp"></jsp:include>

	<li class="active"><a href="#">Inicio</a></li> 
	  </ol>
    </nav>  
    <!-- Home Page
    ==========================================-->
    <div id="tf-home" class="text-center">	
        <div class="overlay">
            <div class="content">
                <h1>Busca lo que quieras</h1>
                <form class="form-horizontal" action="<%=Constantes.VIEW_SEARCH%>" method="post" onsubmit="return false();">
                	<input type="text" class="form-control form-centrado" placeholder="Busca tu zona, vía, sector..." name="buscador">
                	<input type="submit" class="btn btn-outline btn-primary btn-dch" value="Buscar">
                </form>
            </div>
        </div>
    </div>

    <!-- Portfolio Section
    ==========================================-->
    <div id="tf-about">
        <div class="container"> <!-- Container -->
            <div class="section-title text-center center">
                <h2>Echa un vistazo a nuestras <strong>ZONAS</strong></h2>
                <div class="line">
                    <hr>
                </div>
            </div>
            <%
            	Usuario user = (Usuario) session.getAttribute("admin");
            	ModeloSector modeloSector = new ModeloSector();
            	ArrayList<Zona> zonas = new ArrayList<Zona>();
            	ArrayList<Sector> sectores = new ArrayList<Sector>();
            %>
            <div class="categories">
                <ul class="cat">
                    <li class="pull-left">
                        <ol class="type">
                        	<li><a href="#" data-filter="*" class="active">Todas</a></li>
                        	<%
                        	//Recoger todas las zonas de la BBDD (TODO SQL con vias solo publicadas)
                        	zonas = (ArrayList<Zona>)request.getAttribute("todo_zonas");
	           				Zona z = null;
	           				for(int i = 0 ; i < zonas.size() ; i++){
	           					z = zonas.get(i);            
                        	%>
                           	 	<li><a href="<%=Constantes.CONTROLLER_GEOMAP%>?idZona=<%=z.getId()%>" data-filter=".<%=z.getId()%>"><%=z.getNombre()%></a></li>
                       	<%} %>
                        </ol>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div id="lightbox" class="row">		
			<%
			//Volvemos a recorrer todas las zonas para recoger los sectores de cada una (TODO SQL con solo publicas)
			for(int i = 0 ; i < zonas.size() ; i++){
				z = zonas.get(i);
				sectores = (ArrayList<Sector>)modeloSector.getAllByZona(z.getId());
				Sector s = null;
				//Recorremos los sectores por zona
				for(int j = 0 ; j < sectores.size() ; j++){
					s = sectores.get(j);
					String img_path = Constantes.IMG_DEFAULT_SECTOR;	      			
				if ( !img_path.equals( s.getImagen())){
					img_path = Constantes.IMG_WEP_PATH + s.getImagen();	      				
				}else{
					img_path = "img/" + img_path;
				}
             %>
              <div class="col-sm-6 col-md-3 col-lg-3 <%=z.getId()%>">
                    <div class="portfolio-item">
                        <div class="hover-bg">
                            <a href="<%=Constantes.CONTROLLER_GEOMAP%>?idSector=<%=s.getId()%>">
                                <div class="hover-text">
                                    <h4><%=s.getNombre() %></h4>
                                    <small><%=z.getNombre() %></small>
                                    <div class="clearfix"></div>
                                    <i class="fa fa-plus"></i>
                                </div>
                                <img src="<%=img_path %>" class="img-responsive" alt="...">
                            </a>
                        </div>
                    </div>
                </div>
            <%} //for j %>
            <%} //for i %>
            </div>
		</div>
	</div>
    <!-- Testimonials Section
    ==========================================-->
    <div id="tf-testimonials" class="text-center">
        <div class="overlay">
            <div class="container">
                <div class="section-title center">
                    <h2><strong>CREA TU RUTA</strong></h2>
                    <div class="line">
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div id="testimonial" class="owl-carousel owl-theme">
                            <div class="item">
                                <h5>Puedes crear tu propia ruta de escalada, completa, parcial, como quieras.</h5>
                            </div>
                            <div class="item">
                                <h5>Crea tu zona de escalada, con los sectores que pueda haber, y dibuja tus sectores y vías en ella.</h5>
                            </div>
                            <div class="item">
                                <h5>Podrás compartir todas tus rutas completas, zonas, sectores o vías con demás gente.</h5>
                            </div>
                        </div>
                        <br>
                        <a href="<%=Constantes.VIEW_FRONT_HORMASTUDIO%>">Horma Studio</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Contact Section
    ==========================================-->
    <div id="tf-contact" class="text-center">
        <div class="container">

            <div class="row">
                <div class="col-md-8 col-md-offset-2">

                    <div class="section-title center">
                        <h2>Registrate en <strong>SKALADA</strong></h2>
                        <div class="line">
                            <hr>
                        </div>
                        <div class="clearfix"></div>
                        <small><em>En esta seccion podrás crear, actualizar, buscar y borrar tus propias Zonas, Sectores y Vías</em></small>            
                    </div>

                    <form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Correo Electrónico</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Inserta tu E-Mail">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Contraseña</label>
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Números y Letras (Entre 6-16 caracteres)">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Mensaje</label>
                            <textarea class="form-control" rows="3" placeholder="Introduce tu mensaje (Opcional)"></textarea>
                        </div>
                        
                        <button type="submit" class="btn tf-btn btn-default">Registrate</button>
                    </form>

                </div>
            </div>

        </div>
    </div>
	<nav id="footer">
        <div class="container">
            <div class="pull-left fnav">
                <p>TODOS LOS DERECHOS RESERVADOS. COPYRIGHT © 2015. Diseñado y codeado por <a href="https://plus.google.com/u/0/102797013868480815021/posts" target="_blank">Unai Perea</a></p>
            </div>
        </div>
    </nav>

<jsp:include page="includes/foot.jsp"></jsp:include>
    