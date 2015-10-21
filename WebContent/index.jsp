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


    <!-- Home Page
    ==========================================-->
    <div id="tf-home" class="text-center">
        <div class="overlay">
            <div class="content">
                <h1>Busca lo que quieras</h1>
                <form class="form-horizontal" action="#" method="post" onsubmit="return false();">
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
            %>
            <div class="categories">
                <ul class="cat">
                    <li class="pull-left">
                        <ol class="type">
                        	<%
                        	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("ultimas_zonas");
	           				Zona z = null;
	           				for(int i = 0 ; i < zonas.size() ; i++){
	           					z = zonas.get(i);            	
                        	%>
                           	 	<li><a href="#" data-filter=".<%=z.getNombre() %>" class="active"><%=z.getNombre() %></a></li>
                           	<%} %>
                        </ol>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div id="lightbox" class="row">		
			<%
			ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("ultimos_sectores");            	
			Sector s = null;
			for(int i = 0 ; i < zonas.size() ; i++){
					z = zonas.get(i);
	  				for(int j = 0 ; j < sectores.size() ; j++){
	  					s = sectores.get(j);
	  					String img_path = Constantes.IMG_DEFAULT_SECTOR;	      			
	  				if ( !img_path.equals( s.getImagen())){
	  					img_path = Constantes.IMG_WEP_PATH + s.getImagen();	      				
	  				}else{
	  					img_path = "img/" + img_path;
  					}
             %>
              <div class="col-sm-6 col-md-3 col-lg-3 <%=z.getNombre()%>">
                    <div class="portfolio-item">
                        <div class="hover-bg">
                            <a href="#">
                                <div class="hover-text">
                                    <h4><%=s.getNombre() %></h4>
                                    <small>Branding</small>
                                    <div class="clearfix"></div>
                                    <i class="fa fa-plus"></i>
                                </div>
                                <img src="<%=img_path %>" class="img-responsive" alt="...">
                            </a>
                        </div>
                    </div>
                </div>
            <%} //for j %>
            <%} //for zonas %>
            </div>
		</div>
	</div>
    <!-- Testimonials Section
    ==========================================-->
    <div id="tf-testimonials" class="text-center">
        <div class="overlay">
            <div class="container">
                <div class="section-title center">
                    <h2><strong>Our clients’</strong> testimonials</h2>
                    <div class="line">
                        <hr>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div id="testimonial" class="owl-carousel owl-theme">
                            <div class="item">
                                <h5>This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</h5>
                                <p><strong>Dean Martin</strong>, CEO Acme Inc.</p>
                            </div>

                            <div class="item">
                                <h5>This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</h5>
                                <p><strong>Dean Martin</strong>, CEO Acme Inc.</p>
                            </div>

                            <div class="item">
                                <h5>This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</h5>
                                <p><strong>Dean Martin</strong>, CEO Acme Inc.</p>
                            </div>
                        </div>
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
                        <h2>Feel free to <strong>contact us</strong></h2>
                        <div class="line">
                            <hr>
                        </div>
                        <div class="clearfix"></div>
                        <small><em>Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.</em></small>            
                    </div>

                    <form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Email address</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Message</label>
                            <textarea class="form-control" rows="3"></textarea>
                        </div>
                        
                        <button type="submit" class="btn tf-btn btn-default">Submit</button>
                    </form>

                </div>
            </div>

        </div>
    </div>
	<nav id="footer">
        <div class="container">
            <div class="pull-left fnav">
                <p>ALL RIGHTS RESERVED. COPYRIGHT © 2014. Designed by <a href="https://dribbble.com/shots/1817781--FREEBIE-Spirit8-Digital-agency-one-page-template">Robert Berki</a> and Coded by <a href="https://dribbble.com/jennpereira">Jenn Pereira</a></p>
            </div>
        </div>
    </nav>

<jsp:include page="includes/foot.jsp"></jsp:include>
    