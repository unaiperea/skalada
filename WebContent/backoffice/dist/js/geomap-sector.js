var map;
var latSector = document.getElementsByName('latitud')[0].value;
var lngSector = document.getElementsByName('longitud')[0].value;
var zoom = 14;
var marker;

function show_map(localizacion) {
	
	//obtener localizacion	
	var selectZonas = document.getElementById("selecZonas");
    var selectedOption = selectZonas.options[selectZonas.selectedIndex];	
	var latZona = selectedOption.dataset.lat;
	var lngZona = selectedOption.dataset.lng;
	
	if(latSector == 0.0 && lngSector == 0.0){
		latSector = latZona;
		lngSector = lngZona;
	}	
	var center = new google.maps.LatLng(latZona,lngZona);
	var sectorPosition = new google.maps.LatLng(latSector,lngSector);
	
	//inicializar Mapa
	map = new google.maps.Map(document.getElementById('map'), {
		zoom: zoom,
	   	center: center,
	   	mapTypeId: google.maps.MapTypeId.HYBRID,
		disableDefaultUI: true	   
	});
	
	//marcador
	marker = new google.maps.Marker({
		position: sectorPosition,
		map: map,
		draggable: true,
	 	title: document.getElementsByName('nombre')[0].value
	});
		
	marker.addListener('dragend', function() {
		document.getElementsByName('longitud')[0].value = marker.getPosition().lng();
		document.getElementsByName('latitud')[0].value = marker.getPosition().lat();
	});							
}			
    

/**
 * Centrar el mapa en la posicion seleccionada
 * @param select con el option, el cual tiene la localizacion
 */
function changeMapPosition(){
	
	var selectZonas = document.getElementById("selecZonas");
    var selectedOption = selectZonas.options[selectZonas.selectedIndex];	
	var lat = selectedOption.dataset.lat;
	var lng = selectedOption.dataset.lng;
	
	console.debug(" Moviendo mapa {" + lat + "," + lng +"}" );
	var newLatLng = new google.maps.LatLng( lat, lng);
	map.setZoom(zoom);
	map.setCenter(newLatLng);
	
	marker.setPosition(newLatLng);
}

function geolocalizarme(){				
	if ( navigator.geolocation ){
		console.debug('Geolocalizando...');
		navigator.geolocation.getCurrentPosition(show_map);				
	}else{
		console.error('Geolocation NO soportado');
	}
}	

google.maps.event.addDomListener(window, 'load', geolocalizarme);
