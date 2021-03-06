<!doctype html>

<!-- Para que salgan acentos -->
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<html lang="es">

	<head>
	
		<base href="<%=request.getContextPath()%>/">
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<title>Canvas</title>
		<meta name="description" content="Horma Studio, crea tus propias vï¿½as de escalada">
		<meta name="author" content="Unai Perea Cruz">
		
		<!-- Estilos CSS -->
		<link rel='stylesheet' type="text/css" href="/skalada/css/spectrum.css" />
		<link rel='stylesheet' type="text/css" href="/skalada/css/hormastudio.css" />
		
		<!-- Responsive Design -->
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		
		<!-- Bootstrap minified CSS -->
		<link rel="stylesheet" href="/skalada/css/bootstrap.min.css">
		
		<!-- Font Awesome -->		
		<link rel="stylesheet" type="text/css" href="/skalada/font-awesome/css/font-awesome.min.css">
		
		<!-- TODO crear style.css -->
		<style>
			body{background-color: purple;}
	  		#control_pincel, #control_reunion, #control_borrar{ cursor: pointer;} /*QuÃ© pasa aquÃ­iii? ***********************/
	  		header{background-color: lime;}
	  		#cabecera{background-color: yellow;}
	  		.container{background-color: grey;}
	  		.menu{background-color: brown;}
	  		/*#barra-menus{background-color: olive;}*/
	  		#controles{background-color: #343434;}
	  		/* #herramientas-izda{background-color: cyan;} */
	  		#herramientas-dcha{background-color: #424242;}
	  		footer{background-color: silver;}
	  		
		</style>
	  
	</head>
	
	<body>
	<!-- for others: use <body oncontextmenu="return false;"> to prevent browser context menus from appearing on right click. -->
	
		<header id="cabecera">
			<div class="container clearfix">
				<span id="titulo" class="">
					<h1>Horma Studio</h1>
				</span>
			</div>
		</header>
		
		<nav class="menu-wrapper">
			<div class="menu">
				<h2>menu</h2>
			</div>
		</nav>
		
		<div class="container-fluid">
			
			<section id="entorno" class="main row">
			
				<aside id="herramientas-izda" class="col-xs-2">
					<!-- SUBIR FICHEROS -->
					<!-- Formulario -->
					<!-- @see: http://www.tutorialspoint.com/servlets/servlets-file-uploading.htm -->
					<form action="/HormaStudio/imagen" enctype="multipart/form-data" method="post" role="form">
						<div id="control-archivo">
							<ul>
								<li>
									<!-- images/* o image/jpeg, image/bmp, image/png, image/gif y atributo disabled -->
									<input type="file" id="control_imagen" name="control_imagen" accept="image/jpeg"> <!-- onchange="abrirImagen();"> -->
									<input type="submit" id="btn_submit" class="btn btn-outline btn-primary disabled" disabled="" value="Subir">
								</li>
								<li><span id="control_guardar" class="btn btn-default">Guardar imágen</span></li>
								<li><a id="descargar" href="#" target="_blank" class="invisible">Descargar imágen</a></li>
							</ul>
						</div>
					</form>
					<!-- CONTROLES -->
					<div class="clearfix">
						<div id="control-dibujo" class="pull-right">
							<ul class="iconos-dibujo">
								<li><span id="control_pincel" class="glyphicon glyphicon-pencil boton_pulsado"></span></li>
								<!-- <li><span id="control_pincel" class="fa fa-paint-brush boton_pulsado fa-2x"></span></li> -->
								<li><span id="control_reunion" class="fa fa-dot-circle-o boton_hover boton_no_pulsado lista-margen-arriba"></span></li>
								<li><span id="control_borrar" class="fa fa-eraser boton_hover boton_no_pulsado lista-margen-arriba"></span></li>
								<!-- <li><span id="control_mover" class="fa fa-arrows boton_hover boton_no_pulsado fa-2x lista-margen-arriba"></span></li> -->
								<!-- <li><span class="icon-image" style="font-size: 30px"></span></li> -->
							</ul>
						</div>
					</div>
					<!-- PROPIEDADES -->
					<div class="clearfix">
						<div id="control-propiedades" class="pull-right">
							<ul class="iconos-propiedades">
								<li><input type="color" id="control_color" name="control_color" class="pull_right" name="control_color" onchange="getColor();"></li>
								<!-- 
								<li><input type="range" id="control_grosor" name="control_grosor" class="range vertical-lowest-first round zoom-range" min="2" max="50" onchange="setGrosor();" style="margin-top: 1px"></li>
								-->
								<li>
									<div id="grosor_container" class="inputDiv clearfix">
										<div class=flotar_dcha>
										  	<!-- <span id="grosor_menos" class="fa fa-minus-circle fa-1x flotar_izda" style="color: #FFFFFF;" onclick="moverGrosor('abajo');"></span> -->
										  	<div id="etiqueta"></div>
										  	<input id="control_grosor" type="range" value="25" min="2" max="50" autocomplete="off" onchange="setGrosor();">
										  	<!-- <span id="grosor_mas" class="fa fa-plus-circle fa-1x flotar_izda" style="color: #FFFFFF;" onclick="moverGrosor('arriba');"></span> -->
										</div>
									</div>
								</li>
	
							</ul>
						</div>
					</div>
				</aside>
				
				<!-- 
				<div id="control-propiedades" class="clearfix">
					<input type="range" id="control_grosor" name="control_grosor" class="range vertical-lowest-first round zoom-range s" min="2" max="50" onchange="setGrosor();" style="margin-top: 1px"></li>
				</div>
				-->
				
				<!-- <div class="clearfix visible-sm-block"></div> -->
				<!-- CANVAS -->
				<article id="centro" class="col-xs-8">
					<div id="barra-menus">
							<div id="control_select">
							   <select>
							      <option>Here is the first option</option>
							      <option>The second option</option>
							   </select>
							</div>
					</div>
					
					<div id="canvas-container">
						<canvas id="canvas_croquis" class="cursor_none" name="imagen">Su navegador no soporta Canvas. Instale la ultima version de Chrome</canvas>
					</div>
				</article>

				<!-- UTILIDADES -->
				<!-- colocar a la izda -->
				<aside id="herramientas-dcha" class="col-xs-2">
					<div id="herramientas-dcha-container" style="width: 47px;">
						<div style="width: 20px; margin:auto;">
							<span id="zoom_menos" class="fa fa-minus-circle cursor_hand" style="font-size: 20px; color: #FFFFFF; padding-left: 1px;" onclick="moverZoom('arriba');"></span>
							<!-- <input type="range" id="control_zoom" name="control_zoom" class="bar cursor_hand" onchange="setZoom();" style="margin-top: 1px;"/> -->
							<!-- <input type="range" id="control_zoom" name="control_zoom" min="0" max="10" step="1" onchange="setZoom();"/> ERA EL BUENO -->
							<!-- <input id="control_zoom" type="text" id="control_zoom" name="control_zoom" onchange="setZoom();"/> ES EL MEJOR -->
							<div id="zoom_container" class="inputDiv clearfix">
								<div class=flotar_dcha>
								  	<!-- <span id="grosor_menos" class="fa fa-minus-circle fa-1x flotar_izda" style="color: #FFFFFF;" onclick="moverGrosor('abajo');"></span> -->
								  	<div id="etiqueta"></div>
								  	<input id="control_zoom" type="range" value="0" min="0" max="10" autocomplete="off"  onchange="setZoom();"> <!-- onchange="setGrosor();"> -->
								  	<!-- <span id="grosor_mas" class="fa fa-plus-circle fa-1x flotar_izda" style="color: #FFFFFF;" onclick="moverGrosor('arriba');"></span> -->
								</div>
							</div>
							<!-- <input id="control_zoom" type="range" min=0 max=10 step=1 value=0> -->
							<!-- <div id="zoom-slider">
								<input class="bar" type="range" id="rangeinput" value="10" onchange="rangevalue.value=value"/>
								<span class="highlight"></span>
								<output id="rangevalue">50</output>
							</div> -->
							<span id="zoom_mas" class="fa fa-plus-circle cursor_hand" style="font-size: 20px; color: #FFFFFF; padding-left: 1px;" onclick="moverZoom('abajo');"></span>
						</div>
						<div id="zoom-1-1" class="cursor_hand">
							<!-- <input type="button" id="zoom_restaurar" name="zoom_restaurar" class="btn btn-primary" onclick="resetZoom();" value="1:1"/> -->
							<span id="control_mover" class="fa fa-compress boton_hover boton_no_pulsado cursor_hand"></span>
						</div>
						
						<div id="mover-container" class="text-center">
							<span id="control_mover" class="fa fa-arrows boton_hover boton_no_pulsado cursor_hand"></span>
						</div>
					</div>

					<!-- TODO Incluir un video explicativo -->
				</aside>
			</section>
			
			<footer>
				<div class="container">
					<h3>pie de pagina</h3>
				</div>
			</footer>
		
		</div>
				
		
		<!--  jQuery -->
		<script src="/skalada/js/jquery.1.11.1.js"></script>
		
		<!-- JQuery MouseWheel -->
		<script type='text/javascript' src="/skalada/js/jquery.mousewheel.min.js"></script>
		
		<!-- Bootstrap minified JavaScript -->
	  	<script src="/skalada/js/bootstrap.min.js"></script>
		
		<!-- Spectrum Color Picker  - http://bgrins.github.io/spectrum/-->
		<script src="/skalada/js/spectrum.js"></script>
		
		<!-- Paper.js dibujo en Canvas -->
		<script type="text/javascript" src="/skalada/js/paper-full.min.js" canvas="canvas_croquis"></script>
		
		<!-- Horma Studio -->
		<script type="text/javascript" src="/skalada/js/hormastudio.js"></script>

	</body>
</html>