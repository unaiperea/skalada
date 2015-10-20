<jsp:include page="../../includes/head.jsp"></jsp:include>
<jsp:include page="../../includes/nav.jsp"></jsp:include>


<section>

	<!-- HEADER -->
	<header>
		<div class="container-fluid">
				<h1>GeoMap</h1>
		</div>
		
		<!-- BUSCAR -->
		<div class="pull-right">
			<form role="form"
				  method="get"> <!-- action="< %=Constantes.CONTROLLER_LOGIN%>"-->
				<input type="search" id="buscar-zona" name="buscar-zona" class="form-control" placeholder="escribe zona, sector o via a buscar">
			</form>
		</div>
	</header>

	<!-- MAPA -->
	<article id="article-mapa">
		<div id="div-mapa"> <!-- Container lo centra -->
			<!-- Div sobre el que se cargará el mapa de google -->
			<div id="mapa"> </div>
		</div>
		
		<style>
			/* Mapa a pantalla coompleta*/
			html { 
			  height: 100% 
			}
			
			body { 
			   height: 100%;
			   background-size: cover;
			   background-repeat: no-repeat;
			}
			
			 #mapa {
			    width: 100%; 
			    height: 100% !important;
			}
      		/*#mapa {
			    height: 800px;
			    width:100%;
			    margin: 0px;
			    padding: 0px;
	      		}*/
    	</style>
		
		<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		
		<script>
			//Por orden de ejecución pero se escribe así		
			//1.-
			var map;
			var lat = 0;
			var lng = 0;
			var zoom = 15;
			
			//4.-
			function show_map(localizacion) {
				console.debug('estamos geolocalizados');
				
				//Obtener Geolocalización
				lat = localizacion.coords.latitude;
				lng = localizacion.coords.longitude;
				console.debug('latitud : '+ lat);
				console.debug('longitud : '+ lng);
				var myLatlng = new google.maps.LatLng(lat,lng); //La var con la que trabaja Google Maps
				
				//Inicializar mapa
			  	map = new google.maps.Map(document.getElementById('mapa'), {
			    	zoom: zoom,
			    	center: {lat: lat, lng: lng},
			    	zoomControlOptions: { style: google.maps.ZoomControlStyle.LARGE },
			    	mapTypeId: google.maps.MapTypeId.HYBRID
			  	});
			
			
			//marcador
			var marker = new google.maps.Marker({
			      position: myLatlng,
			      map: map,
			      title: 'Aquí estoy yo'
			  });
			
			
			//circulo
		    var circuloOptions = {
		    	      strokeColor: '#0000FF',
		    	      strokeOpacity: 0.5,
		    	      strokeWeight: 2,
		    	      fillColor: '#0000FF',
		    	      fillOpacity: 0.35,
		    	      map: map,
		    	      center: myLatlng,
		    	      radius: 500 
		    	    };
		   // Add the circle for this city to the map.
		   cityCircle = new google.maps.Circle(circuloOptions);
			
		}
			
			//3.-
			//Geolocalizarme
			function geolocalizarme(){
				if (navigator.geolocation){
					console.debug('Geolocalizando ...');
					navigator.geolocation.getCurrentPosition(show_map);
				}else{
					console.error('Geolocation NO Soportado');
				}
			}

			//2.-
			google.maps.event.addDomListener(window, 'load', geolocalizarme);
			
		</script>
		
		<!-- DETALLE ZONA, SECTOR, VIAS -->
		<article id="detalle">
			<div class="row">
				<div class="col-xs-6">
					<form action="<%=Constantes.CONTROLLER_SECTORES%>" method="get" role="form">
					</div>
					<div class="col-xs-6">
					
				</div>
			</div>
		</article>
	
	</article>
	

</section>


<footer>
	<ul>
		<li><a href="http://diveintohtml5.info/geolocation.html target="_blank"">Art&iacute;culo sobre Geolocalizaci&oacute;n </a></li>
		<li><a href="" target="_blank">API Google Maps</a></li>
	</ul>
</footer>

<jsp:include page="../../includes/foot.jsp"></jsp:include>