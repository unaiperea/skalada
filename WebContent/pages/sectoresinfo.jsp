<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page import="com.ipartek.formacion.skalada.bean.Via"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

<%
	Zona zona = (Zona)request.getAttribute("zona");
	ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("sectores");
	Sector sectorDestacado = (Sector)request.getAttribute("sectorDestacado");
	ArrayList<Via> vias = (ArrayList<Via>)request.getAttribute("vias");
	ArrayList<Zona> zonas = (ArrayList<Zona>)request.getAttribute("todo_zonas");
%>

		<li><a href="#">Inicio</a></li>
		<li class="active"><a href="#">Sectores</a></li>
	  </ol>
    </nav>
    
  	<link rel="stylesheet" type="text/css" href="css/sectores.css" media="screen" />  
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
			<!-- <iframe width="100%" height="100%" frameborder="0" style="border:0;" allowfullscreen>-->
				<div id="mapa" style="width:100%;height:100%;" ></div>
			<!-- </iframe>-->
          </div>
          <br>
          
              <div class="sector_detalle">
			
			<p>Nombre: <%=sectorDestacado.getNombre()%><a href="#"><i class="fa fa-question-circle"></i></a></p>
			<p>Zona: <%=zona.getNombre()%></p>
						
			</div>
          
          <div class="row">
          <br>
          	<%
          		System.out.print(vias.size());
          		for (int i=0;i<vias.size();i++){
          			System.out.print("Entrando en vias: " + vias.get(i).getNombre());
          	%>
          			<div class="col-xs-6 col-lg-4">
                    <br>
                      <h2><%=vias.get(i).getNombre()%><a href="#"><i class="fa fa-question-circle"></i></a></h2>
                      <br>
                      <p>Longitud: <%=vias.get(i).getLongitud()%> m</p>
                      <p>Grado: <%=vias.get(i).getGrado().getNombre()%> <a href="#"><i class="fa fa-question-circle"></i></a></p>
                      <p>Tipo Escalada: <%=vias.get(i).getTipoEscalada().getNombre()%><a href="#"><i class="fa fa-question-circle"></i></a> </p>
                      <p><a class="btn btn-default" href="pages/viaH.jsp" role="button">Leer Más &raquo;</a></p>
                    </div><!--/.col-xs-6.col-lg-4-->
            <%
          		}
          	
          	%>
     
          </div><!--/row-->
        </div><!--/.col-xs-12.col-sm-9-->

      </div><!--/row-->
<br>
<br>
    </div><!--/.container-->   
                 
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		
<script>
	//Por orden de ejecuci�n pero se escribe as�		
	//1.-
	var map;
	
	//mi ubicacion
	var miLat = 0.0;
	var miLng = 0.0;
	
	//Ubicacion de las zonas
	var lat = 0.0;
	var lng = 0.0;
	
	var markerZona;
	var markerSector;
	var zoom = 10;
	var icon = "";
	var infowindow = null;

		
	/*	
		Recibimos un array en Java desde el Controlador y lo queremos utilizar en JavaScript para ello:
		el controlador devuelve el codigo Java traducido a HTML por lo que lo utilizamos para crear el Array en JavaScript  
	*/
        	
	/* Ejemplo datos que necesitamos
	var zonas =  [
	                 ['Title A', 3.180967,101.715546],
	                 ['Title B', 3.200848,101.616669],
	                 ['Title C', 3.147372,101.597443],
	                 ['Title D', 3.19125,101.710052]
	            ];
	*/
	
	/*
	var zonas =  [
					< % for (int z=0; z < zonas.size(); z++){
						out.print("[" + zonas.get(z).getId() + ", '" + zonas.get(z).getNombre() + "', " + zonas.get(z).getLatitud() + ", " + zonas.get(z).getLongitud() + "]");
						if (z != (zonas.size() - 1)){
			       			out.print(", ");
			       	  	}
			         } % >
	            ];
	*/

	var sectores = [
	                <%
	                for (int z=0; z < sectores.size(); z++){
	                	out.print("[" + sectores.get(z).getId() + ", '" + sectores.get(z).getNombre() + "'," + sectores.get(z).getZona().getId() + ", " + sectores.get(z).getLatitud() + ", " + sectores.get(z).getLongitud() + "]");
	                	if (z != (sectores.size() - 1)){
			       			out.print(", ");
			       	  	}
	                }
	                %>
	                ];

	function show_map(localizacion) {
		//Mi Ubicacion
		miLat = localizacion.coords.latitude;
		miLng = localizacion.coords.longitude;
		var myLatlng = new google.maps.LatLng(miLat, miLng); //La var con la que trabaja Google Maps
		
		var markerUbicacion = new google.maps.Marker({
		      position: myLatlng,
		      map: map,
		      title: 'Mi ubicacion'
		});
		
		//Inicializar mapa
	  	map = new google.maps.Map(document.getElementById('mapa'), {
	    	zoom: zoom,
	    	center: {lat: miLat, lng: miLng},
	    	zoomControlOptions: { style: google.maps.ZoomControlStyle.LARGE },
	    	mapTypeId: google.maps.MapTypeId.HYBRID
	  	});
		
		//Inicializamos ventana de info de los marcadores
	  	var contentwindow = '<div>your point</div>'
  		infowindow = new google.maps.InfoWindow({
  		    content: contentwindow
  		 });
		
		//Setear mi Ubicacion
		markerUbicacion.setMap(map);
		
		//Cargar Zona
		loadZona();
		
		//Cargar Sectores
		loadSectores();
		
		//circulo
	    var circuloOptions = {
	    	      strokeColor: '#0000FF',
	    	      strokeOpacity: 0.5,
	    	      strokeWeight: 2,
	    	      fillColor: '#0000FF',
	    	      fillOpacity: 0.1,
	    	      map: map,
	    	      center: myLatlng,
	    	      radius: 25000 
	    	    };
	   // Add the circle for this city to the map.
	   cityCircle = new google.maps.Circle(circuloOptions);
	
	}
	
	/*function loadZonas(){
		//Zonas
		console.debug("Zonas Loading.....");
		var zonaLatlng;
		var zona;
		
		for (i = 0; i < zonas.length; i++) {					
			zona = zonas[i];				
			console.debug("    Creando ZONA " + zona[1]);
			zonaLatlng = new google.maps.LatLng( zona[2],zona[3]);					
			markerZona = new google.maps.Marker({
			      position: zonaLatlng,
			      map: map,
			      title: zona[1]
			  });
			console.debug("Marcador Zona " + markerZona[1]);
			markerZona.setMap(map);
		}
		
		console.debug("Zonas cargadas");
	}*/
	
	function loadZona(){
		console.debug("Zona loading.....");
		var zonaLatlng;
		<% double lat = zona.getLatitud(); %>
		<% double lng = zona.getLongitud(); %>
		<% String titulo = zona.getNombre(); %>
		zonaLatlng = new google.maps.LatLng( <%=lat%>,<%=lng%>);					
		markerZona = new google.maps.Marker({
		      position: zonaLatlng,
		      map: map,
		      title: "Atxarte"
		  });
		console.debug("Marcador Zona " + markerZona);
		markerZona.setMap(map);
	
		console.debug("Zonas cargadas");
	}
	
	function loadSectores(){
		//Sectores
		console.debug("Sectores Loading.....");
		var sectorLatlng;
		var sector;
		var icon = "orange";
		var markerSector = [];
		
		icon = "http://maps.google.com/mapfiles/ms/icons/" + icon + ".png";
		for (i=0; i<sectores.length; i++){
			sector = sectores[i];					
			sectorLatlng = new google.maps.LatLng( sector[3],sector[4]);					
			markerSector[i] = new google.maps.Marker({
				  position: sectorLatlng,
			      map: map,
			      animation: google.maps.Animation.DROP,
		          icon: new google.maps.MarkerImage(icon),
			      title: sector[1]
			});
			console.debug("    Creando SECTOR " + sector[1] + "{" + sector[3] + "," + sector[4] +"}");
			console.debug("    Marcador SECTOR " + markerSector[i]);
			markerSector[i].setMap(map);

			// Funcion que abre una ventana con el contenido del sector 
			
			function setInfoWindow(event, marcador){
				// Create content  
				var contentString = "<br /><br /><hr />Coordinate: " + marcador.lng +"," + marcador.lat; 
		  		// Replace our Info Window's content and position 
				infowindow.setContent(contentString);
				infowindow.setPosition(marcador.position); 
				infowindow.open(map)
				//infowindow.open(map, markerSector)
			}
			
			// Funcion que se llama al hacer click sobre un marcador
			
			markerSector[i].addListener('click', function() {
			   	map.setCenter(new google.maps.LatLng(this.position.lat(), this.position.lng())); 
			    map.setZoom(15);
			    setInfoWindow(event, this);
		 	});
		}
		console.debug("Sectores cargados");
		
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
		
<jsp:include page="../includes/foot.jsp"></jsp:include>    