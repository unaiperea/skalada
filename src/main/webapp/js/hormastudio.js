/**
 * 
 * Aplicación de dibujo vectorial sobre un canvas
 * A partir de una imágen es posible dibujar vías de escalada
 * 
 * @author: Unai Perea Cruz
 * @date: 
 * @version: 1.0 Beta
 * 
 */

	  	//Variables para que se pueda interactuar entre los botones y el canvas
	  	var canvas; //Para getMousePos(); sino meterlo dentro de onload
	  	var contexto;
		var imagenRaster;
		
		//controles de ....
		var controlPincel;
		var controlReunion;
		var controlBorrar;
		var controlMover;
		
		var reunionColor;
		var reunionRadio;
		var vectorColor;
		var vectorGrosor;
		
		var cursorTamanoPincel;
		var posicionRaton;
		
		var upperZoomLimit;
	    var lowerZoomLimit;
	    //var porcentajeZoom;
	    var originalZoom;
	    var originalCentro;
	    var originalMoveFactor;
	    var ratioZoomFactor;
	    var diferenciaZoom;
	    
	    var controlImagen; //input type=file
	    var hayImagen = false; //Para poder pintar sobre la imagen
	    
	    var capaImagen;
		var capaVectorial;
		
		//TODO No se si es paper.project. ....
		
	  	paper.install(window);
	  	console.info('paper.js instalado');
	  	
		// With JQuery
	    /*$("#ex17a").slider({min  : 0, max  : 10, value: 0, tooltip_position:'bottom'});
	    $("#control_zoom").slider({min  : 0, max  : 10, value: 5, orientation: 'vertical', tooltip_position:'left'});
	    */
	  	
		// Without JQuery
	    //new Slider("#ex17a", {min  : 0, max  : 10, value: 0, tooltip_position:'bottom'});
	    //new Slider("#ex17b", {min  : 0, max  : 10, value: 0, orientation: 'vertical', tooltip_position:'left'});
	  	
		//Only executed our code once the DOM is ready.
		window.onload = function() {
	
			/**
			 * input type=range
			 */
			var elInput = document.querySelector('#control_grosor');
			if (elInput) {
			  var etiqueta = document.querySelector('#etiqueta');
			  if (etiqueta) {
			    etiqueta.innerHTML = "Tamaño pincel: " + elInput.value;

			    elInput.addEventListener('input', function() {
			      etiqueta.innerHTML = "Tamaño pincel: " + elInput.value;
			    }, false);
			  }
			}
			
			
			/**
			 * input type=color - Spectrum Color Picker
			 */
			$("#control_color").spectrum({
			    showPaletteOnly: true,
			    togglePaletteOnly: true,
			    togglePaletteMoreText: 'más',
			    togglePaletteLessText: 'menos',
			    chooseText: "Ok",
			    cancelText: "Cancelar",
			    color: 'blue',
			    palette: [
			        ["#000","#444","#666","#999","#ccc","#eee","#f3f3f3","#fff"],
			        ["#f00","#f90","#ff0","#0f0","#0ff","#00f","#90f","#f0f"],
			        ["#f4cccc","#fce5cd","#fff2cc","#d9ead3","#d0e0e3","#cfe2f3","#d9d2e9","#ead1dc"],
			        ["#ea9999","#f9cb9c","#ffe599","#b6d7a8","#a2c4c9","#9fc5e8","#b4a7d6","#d5a6bd"],
			        ["#e06666","#f6b26b","#ffd966","#93c47d","#76a5af","#6fa8dc","#8e7cc3","#c27ba0"],
			        ["#c00","#e69138","#f1c232","#6aa84f","#45818e","#3d85c6","#674ea7","#a64d79"],
			        ["#900","#b45f06","#bf9000","#38761d","#134f5c","#0b5394","#351c75","#741b47"],
			        ["#600","#783f04","#7f6000","#274e13","#0c343d","#073763","#20124d","#4c1130"]
			    ]
			});
			
			     console.info('window loaded');	
				//Atributos de hitTest (eventos provocados por el rat�n al clickar sobre un item/Path/Segmento/Stroke
				var hitOptions = null;
				
				//Atributos de los vectores
				var vectorRedondezPunta;
				var cursorColor;					/*** CURSOR ***/
				var hitTestTolerancia;
				var nodoTamano;
			
				//Declaramos variables
				//var capaImagen;
				//var capaVectorial;
				var capaGenerica; //Para cualquier otro objeto que afecte al dibujo
				var capaCursor;					/*** CURSOR ***/
				
				//var contexto;
				
				var segment, path; //variables para saber qu� item y en qu� parte del item se ha clickado
				var moverPath = false; //Controla el movimiento en bloque del item
				var dibujar = false; //Controla si se va a dibujar o no
				var rutaImagen = "/skalada/img/krokis.png";
				
				var circuloReunion; //No s� si es imprescindible
				//var rReunion; //Para si agrupamos el c�rculo con la letra R en el centro 
				//var grupoReunion //circuloReunion y  rReunion agrupados
				
				var rectangulo;
				var esquinaTamano;
				var teclaContorno; 
				var teclaPulsada;
				var grupoTecla; //teclaPulsada y teclaContorno agrupados (Tool Tip Text)
				
				inicializarEntorno();
				inicializarCanvas();
				inicializarCapas(); //Creamos las capas (im�gen, l�neas)
				inicializarDibujoVectorial();
				cargarImagenInicial(rutaImagen);
				inicializarControles();
				inicializarToolTip();
			
				//crearPaths(); //Creamos Paths manualmente
			
				//Modificados desde un control exterior
				//$(#controlvectorColor).onChange(function(){...vectorColor = $(#controlvectorColor).value;...});
				//$(#controlvectorColor).onChange(function(){...vectorGrosor = $(#controlvectorGrosor).value;...});
	
	/**
	* Funciones propias del canvas utilizando la librería paper.js
	*/
		function inicializarEntorno(){
			//document.body.style.cursor = 'none'; //el cursor desaparece
			console.warn('inicializarEntorno: No hacemos nada');
		}
		
		function inicializarCanvas(){
			console.info('inicializarCanvas: entramos');
			//Inicializar
			canvas = document.getElementById('canvas_croquis');//$('#canvas_croquis')[0]; //Obtenemos el id de la etiqueta canvas
			
			controlImagen = document.getElementById('control_imagen');
			controlImagen.addEventListener('change', loadImagen, false);
		    
			paper.setup('canvas_croquis'); //Crea una clase intermedia para poder utilizar el lenguaje javascript en vez de paperscript
		    tool = new paper.Tool(); //Crea una herramienta para manejar los eventos del teclado y rat�n. Machaco la var tool ya existente
		    
		    //TODO Zoom
		    paper.tool.distanceThreshold = 8;
		    paper.tool.mouseStartPos = new paper.Point();
		    paper.tool.zoomFactor = 1.3;
		    lowerZoomLimit = 0;
		    upperZoomLimit = 10;
		    diferenciaZoom = document.getElementById("control_zoom").value;
		    
		    //Creamos un contexto contra la etiqueta canvas
			contexto = canvas.getContext('2d');
			//contexto.fillStyle = "#424242"; //Color de fondo del canvas -- NO FUNCIONA
			
			//project.currentStyle = {
			//		strokeColor: 'black',
			//		strokeWidth: 4,
			//		strokeCap: 'round'
			//	};
			project.strokeCap = 'round'; //COMPROBAAAAR *****
			contexto.lineCap = "round"; //COMPROBAAAAR *****
			contexto.lineJoin = "round"; //COMPROBAAAAR *****
			//project.fillStyle = "#424242";
			console.info('inicializarCanvas:salimos');
		}
		
		function inicializarCapas(){
			capaImagen = new paper.Layer();
			capaImagen.name = "capa de imagen";
			capaGenerica = new paper.Layer();
			capaGenerica.name = "capa generica";
			capaCursor = new paper.Layer();					/*** CURSOR ***/
			capaCursor.name= "capa del cursor";
			capaVectorial = new paper.Layer();
			capaVectorial.name = "capa de lineas";
		}

		function inicializarDibujoVectorial(){
			vectorColor         = '#0000FF';
			vectorGrosor        = 6; // Comprobar 	QUE FUNCIONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA*  **********   IMPORTANTEEEEEEEEEEE
			vectorRedondezPunta = 'round';
			nodoTamano          = vectorGrosor*2;
			
			reunionRadio        = 8;
			reunionColor        = '#ff0000';
			
			cursorColor         = 'black';					/*** CURSOR ***/
			hitTestTolerancia   = 2;
			
			//Tama�o de todos los nodos
			paper.settings.handleSize = nodoTamano;
			
			//Creamos el objeto cursor
			capaCursor.activate();
			cursorTamanoPincel = new paper.Path.Circle ({
				center: [0, 0],
				radius: vectorGrosor/2,
				strokeColor: cursorColor,					/*** CURSOR ***/
				name: 'cursor'});
			cursorTamanoPincel.visible = false;
			capaVectorial.activate();
			
			//Opciones HitTest
			hitOptions = {
					segments: true, //para clickar en los nodos
					stroke: true, //para clickar en las l�neas
					fill: true, //para clickar en las reuniones
					tolerance: hitTestTolerancia
					};
		}
		
		//Creamos Paths manualmente
		//function crarPaths(){
			//path.add(new Point(40, 100));
			//path.add(new Point(150, 10));
			//path.add(new Point(370, 350));
			//path.add(new Point(450, 300));
		//}

		
		/**
		*  Cargamos la imagen/raster en la capa capaImagen
		*/
		function cargarImagenInicial(rutaImagen){ 
			//Activamos la capa de la im�gen y la cargamos
			capaImagen.activate();
			
			contexto.clearRect(0, 0, canvas.width, canvas.height); //Borro todo lo que haya en nuestro lienzo
			hayImagen = false;
			
			/*var puntoInsercion = new paper.Point(paper.view.center);
			imagenRaster = new paper.Raster({
		  		source: rutaImagen,
		  		//position: view.center,
				selected: false}, puntoInsercion);*/
			imagenRaster = new paper.Raster({
		  		source: rutaImagen,
				selected: false});
			hayImagen = false;
			
			//Centra la imagen
			var MAX_WIDTH = $('#canvas_croquis').width();//$('#entorno').width(); //Anchura del div
			var MAX_HEIGHT = $('#canvas_croquis').height();//$('#entorno').height(); //Altura del div
			var puntoCentroImagen = new paper.Point(MAX_WIDTH / 2, MAX_HEIGHT / 2);
			imagenRaster.position = paper.view.viewToProject(puntoCentroImagen);
			
			capaVectorial.activate(); //Activa la capa de los vectores y lista para dibujar
		}
		
		function inicializarControles(){
			controlPincel = true;
			controlReunion = false;
			controlBorrar = false;
			
			//ColorPicker
			document.getElementById("control_color").value = "#0000FF";
			
			//Tama�o pincel
			document.getElementById("control_grosor").value = vectorGrosor;
			document.getElementById("etiqueta").innerHTML = "Tamaño pincel: " + vectorGrosor;
			
			//Zoom
			//document.getElementById("control_zoom").value = lowerZoomLimit;
			//document.getElementById("zoom_texto").value = lowerZoomLimit;
			//$("#control_zoom")[0].min = lowerZoomLimit;
	        //$("#control_zoom")[0].max = upperZoomLimit;
		}

		function inicializarToolTip(){
			//TODO letra R rReunion y name: erre
			//this.position + new point ...
			
			capaGenerica.activate();
		
			rectangulo = new paper.Rectangle(new Point(100, 100), new Size(30, 15));
			esquinaTamano = new Size(5, 5);
			teclaContorno = new paper.Path.RoundRectangle(rectangulo, esquinaTamano);
			teclaContorno.fillColor = 'white';
			teclaContorno.strokeColor = 'black';
			teclaContorno.name = "contornotooltip";
			
			teclaPulsada = new PointText({
			    position: [rectangulo.x + (rectangulo.width/7), rectangulo.y + (rectangulo.height/1.5)],
			    content: 'null',
			    fillColor: 'black',
			    fontFamily: 'Arial',
			    fontWeight: 'bold',
			    fontSize: 10,
			    name: "textotooltip"
			});
			
			grupoTecla = new Group({
				children: [teclaContorno, teclaPulsada]
			});
			grupoTecla.name = "tooltiptext";
			grupoTecla.visible = false;
			capaVectorial.activate();
			
			//acceder a children reunion.children[0].point
			//acceder a children reunion.children[1].point
			//circuloReunion.position = path.getPointAt(0,0); //posici�n inicial. NO creo
		}
	
		/**
		*  Cuando pulse el bot�n del rat�n se obtendr� el item que est� debajo y ...
		*  1.- si no ha pulsado ning�n item que no haga nada
		*  2.- si se ha pulsado en cualquier lado del Path con la tecla CTRL pulsada lo mueve en bloque
		*  3.- si se ha pulsado en un nodo con la tecla SHIFT pulsada borra dicho nodo
		*  4.- si se ha pulsado en un segmento/nodo del path preparado para mover el segmento
		*  5.- si se ha pulsado en la l�nea del path inserta un nodo preparado para mover
		*/
		tool.onMouseDown = function(event){
			//switch (event.event.button) {
				// leftclick
			//	case 0:
			//		alert('Left mouse button pressed');
			//		break;
				// rightclick
			//	case 2:
			//		alert('Right mouse button pressed');
			//		break;
			//}
			console.debug("Ha entrado en onMouseDown");

			//Controlamos para que cuando pulsemos CTRL y SHIFT aparezca un tooltiptext al lado del cursor
			tool.onKeyDown = function(e){
				if (e.key == 'control' && !controlBorrar){
					 //Colocamos el tooltiptext al lado del cursor
					if (project.activeLayer != capaGenerica){capaGenerica.activate();}
					grupoTecla.position.x = event.point.x + 18;
					grupoTecla.position.y = event.point.y + 10;
					if (!grupoTecla.visible){
						teclaPulsada.content = "Ctrl"; // que escriba Ctrl
						grupoTecla.visible = true;
					}
					if (project.activeLayer != capaVectorial){capaVectorial.activate();}
				}else if (e.key == 'shift' && !controlBorrar && !controlReunion){
					 //Colocamos el tooltiptext al lado del cursor
					if (project.activeLayer != capaGenerica){capaGenerica.activate();}
					grupoTecla.position.x = event.point.x + 18;
					grupoTecla.position.y = event.point.y + 10;
					if (!grupoTecla.visible){
						teclaPulsada.content = "Shift"; // que escriba Shift
						grupoTecla.visible = true;
					}
					if (project.activeLayer != capaVectorial){capaVectorial.activate();}
				}
			}
			
			tool.onKeyUp = function(e){
				if (e.key == 'control' || e.key == 'shift'){
					if (project.activeLayer != capaGenerica){capaGenerica.activate();}
					if (grupoTecla.visible){
						grupoTecla.visible = false;
						teclaPulsada.content = "null";
					}
					if (project.activeLayer != capaVectorial){capaVectorial.activate();}
				}
			}
			
			segment = path = null;
			
			/*if (controlReunion){
				console.info("controlReunion = true");
				circuloReunion = new paper.Path.Circle({
					center: event.point,
					radius: reunionRadio,
					fillColor: reunionColor
					});
				
				/*
				//TODO letra R rReunion y name: erre
				grupoReunion = new Group();
				reunion.addChild(circuloReunion);
				reunion.addChild(rReunion);
				
				//acceder a children reunion.children[0].point
				//acceder a children reunion.children[1].point
				//circuloReunion.position = path.getPointAt(0,0); //posici�n inicial. NO creo
				*/
				
			/*} else{*/
			//Obtenemos d�nde se ha pulsado el rat�n
			var hitResult = project.hitTest(event.point, hitOptions);
			if (hitResult != null){
				var hitClaseItem = hitResult.item.className; //Otra forma m�s fiable de saber qu� item hemos clickado
				var hitNombreItem = hitResult.item.name; //Otra forma para evitar que clicke el tooltiptext
				
				console.info(hitResult.item.name);
			}else{ //Si no ha pinchado nada, useas�, controlMover = true
				return;
			}
			
			//EN MODO DEBUG CON EL CHROME NO ENTRAN LOS MODIFICADORES CONTROL NI SHIFT
			//Si pulsamos CTRL o SHIFT + Click rat�n ...
			if (event.modifiers.control && controlPincel && hitNombreItem != "cursor"){ // && hitNombreItem != "cursor"					/*** CURSOR ***/
				//Si se ha pulsado CTRL + CLICK que lo prepare para moverse. Controla si clicka sobre alguno de los elementos del tooltiptext que no lo modifique  
				//if ( hitResult && hitClaseItem != "Raster" && hitNombreItem != "tooltiptext" && hitNombreItem != "textotooltip" && hitNombreItem != "contornotooltip" && (controlReunion || controlPincel)) {
				if (hitResult && hitNombreItem == "vector"){
					moverPath = true;
					path = hitResult.item;
					//project.activeLayer.addChild(hitResult.item); //no s� si hay que incluirlo luego
				}
			}else if (event.modifiers.control && controlReunion && hitNombreItem != "cursor"){ // && hitNombreItem != "cursor"					/*** CURSOR ***/
				//Si se ha pulsado CTRL + CLICK que lo prepare para moverse. Controla si clicka sobre alguno de los elementos del tooltiptext que no lo modifique  
				//if ( hitResult && hitClaseItem != "Raster" && hitNombreItem != "tooltiptext" && hitNombreItem != "textotooltip" && hitNombreItem != "contornotooltip" && (controlReunion || controlPincel)) {
				if (hitResult && hitNombreItem == "reunion"){
					moverPath = true;
					path = hitResult.item;
					//project.activeLayer.addChild(hitResult.item); //no s� si hay que incluirlo luego
				}
			}else if (event.modifiers.shift && controlPincel) {
				//pulsando SHIFT + CLICK en el segmento/nodo borra el nodo     			// && hitNombreItem != "cursor"					/*** CURSOR ***/
				if ( hitResult && hitClaseItem != "Raster" && !hitResult.item.hasFill() && hitNombreItem != "cursor" && !controlReunion && !controlBorrar) { //Si hemos hecho click sobre algo y que no sea la im�gen y si ha sido en una reunion que no la modifique
					if (hitResult.type == 'segment') {
						hitResult.segment.remove();
					}
				}
			}else if (controlReunion && hayImagen){ //Si se ha pulsado el bot�n de Reuni�n que cree una nueva reuni�n al clickar sobre el canvas
				console.info("controlReunion = true");
					if (project.activeLayer != capaVectorial){
						capaVectorial.activate();
					}
					circuloReunion = new paper.Path.Circle({
						center: event.point,
						radius: reunionRadio,
						fillColor: reunionColor,
						name: "reunion"
						});
			}else if (controlPincel && hayImagen){ //si no se ha pulsado ning�n item o se ha clickado sobre el raster/im�gen que cree un nuevo path y en onMouseDrag se dibuja
				 //|| hitNombreItem == "cursor"					/*** CURSOR ***/
				if (!hitResult || hitClaseItem === "Raster" || hitResult.type == "fill" || hitNombreItem == "cursor"){ //si hitResult=null o se ha clickado sobre la im�gen o sobre un objeto con relleno/Reuni�n
					if (project.activeLayer != capaVectorial){
						capaVectorial.activate();
					}
					path = new paper.Path({ //Crea un nuevo Path
						strokeColor: vectorColor,
						strokeWidth: vectorGrosor,
						strokeCap: vectorRedondezPunta,
						name: "vector"
						});
					//path.add(event.point);
					//center: [0, 0],
					//path.add(event.point);
					console.info("controlPincel = true");
					//path.strokeWidth = 8;
					//path.strokeJoin = 'round'; //La redondez de la punta
					dibujar = true;
				} else if ( hitResult && hitClaseItem != "Raster") {
					//si pulsa en cualquier lugar del path y que no sea sobre el raster/im�gen...
					console.info("guardamos el path clickado");
					path = hitResult.item; //guardamos el path sobre el que se ha pulsado
					
					if (hitResult.type == 'segment') {
						//Y si se ha pulsado sobre un segmento/nodo del propio path
						console.info("onMouseDown guardamos el path clickado y segment.point");
						segment = hitResult.segment; //guardamos el segmento/nodo del propio path
		
					} else if (hitResult.type == 'stroke') {
						//Y si se ha pulsado sobre la l�nea del propio path
						console.info("onMouseDown guardamos el path clickado");
						var location = hitResult.location;
						segment = path.insert(location.index + 1, event.point); //inserta un nodo y lo guardamos
						path.smooth(); //Suaviza el nuevo v�rtice
					}
				}
			} else if (controlBorrar){
				console.info("controlBorrar = true");
				if (hitResult && hitClaseItem != "Raster") {
					hitResult.item.remove(); //Elimina el path completo con sus hijos pero en realidad no lo destruye por completo, luego se puede recuperar. Devuelve un booleano
				}
			}
		console.info("a punto de salir del onMouseDown");
		}

		/**
		*  Mientras est� encima de un item se selecciona
		*/
	 	//S�lo cuando pasamos por encima de un vector se selecciona (la im�gen no)
		tool.onMouseMove = function(event){
			console.info("Ha entrado en onMouseMove");
			//paper.tool.mouseStartPos = new Point(event.point); //Para el zoom
			//Obtengo la posici�n del cursor para hacer Zoom
			//posicionRaton = getPosicionRaton(canvas, event);
			posicionRaton = canvas.getBoundingClientRect(); //Recojo la posici�n del rat�n en la ��pantalla??. Para el Zoom

		    //posx = posicionRaton.x;
		    //posy = posicionRaton.y;

			//movemos el el c�rculo del tama�o del pincel con el cursor.					/*** CURSOR ***/
			if (!controlMover && !controlBorrar){
				moverCursor(event.point);
			}
			
			project.activeLayer.selected = false;
			
			//Que s�lo seleccione los vectores y las reuniones. Ni la im�gen ni ninguno de los elementos del tooltiptext 
			if (controlReunion && event.item && event.item.name == "reunion"){
				event.item.selected = true;
			}
			if (controlPincel && event.item && event.item.name == "vector"){
				event.item.selected = true;
			}
			if (controlBorrar && event.item && (event.item.name == "vector" || event.item.name == "reunion")){
				event.item.selected = true;
			}
			/*if (event.item && event.item.className != "Raster" && event.item.name != "tooltiptext" && event.item.name != "reunion"){
				event.item.selected = true;
			}*/
		}
		
		/**Cuando arrastremos el rat�n con el bot�n pulsado ...
		*  1.- moveremos el Path completo si se hab�a pulsado CTRL
		*  2.- arrastraremos el segmento/nodo si se ha�a pulsado sobre �l
		*  3.- arrastraremos el el nuevo segmento/nodo que acabamos de crear si se ha�a pulsado sobre la l�nea/Path
		*/
		tool.onMouseDrag = function(event){
			console.info("Ha entrado en onMouseDrag");
			
			if (controlMover){
				//TODO Faltar�a poner el viewToProject *****************************
				for (i=0; i<project.layers.length;i++){
					project.layers[i].position.x += event.delta.x;
					project.layers[i].position.y += event.delta.y;
				}
			}
			else if (dibujar){
				//TODO controlar que no salga del l�mite de la im�gen y/o del canvas. Controlar cuando tiene zoom
				console.info("dibujar");
				path.add(event.point);
				console.info("Pos. nuevo punto: " + event.point);
			}else{
				console.info("dibujar ELSE");
				if (moverPath) { //pulsando CONTROL + CLICK mueve path entero
					path.position.x += event.delta.x;
		  			path.position.y += event.delta.y;
					//path.position += event.delta; //No funciona as� cuando pongo tool. ...
				}else if (segment) {
					segment.point.x += event.delta.x;
					segment.point.y += event.delta.y;
					//segment.point += event.delta; //No funciona as� cuando pongo tool. ...
					path.smooth(); //Suaviza el nuevo v�rtice
		  		}else if (path) {
		  			path.position.x += event.delta.x;
		  			path.position.y += event.delta.y;
					//path.position += event.delta; //No funciona as� cuando pongo tool. ...
				}
			}
			//movemos el c�rculo del tama�o del pincel con el cursor.					/*** CURSOR ***/
			if (!controlMover && !controlBorrar){
				moverCursor(event.point);
			}
		}

		/**
		* Cuando soltemos el rat�n se inicializan las variables que controlan el movimiento o dibujo
		*/
		tool.onMouseUp = function(event){
			console.info("Ha entrado en onMouseUp");
			if (dibujar){
				dibujar = false;
				path.simplify(5); //El ratio de simplificado por defecto es 2.5
			}else
				if (moverPath){
					moverPath = false;
				}
		}
		
		/**
		 * 
		 */
		function moverCursor(punto){
			//TODO controlar que cuando se salga de los l�mites desaparezca el c�rculo del cursor
			if (project.activeLayer != capaCursor){capaCursor.activate();}
			if (cursorTamanoPincel.visible == false){cursorTamanoPincel.visible = true;}
			cursorTamanoPincel.position.x = punto.x;//event.point.x;
			cursorTamanoPincel.position.y = punto.y;//event.point.y;
			if (project.activeLayer != capaVectorial){capaVectorial.activate();}
			console.info("cursor: " & cursorTamanoPincel.position.x & ", " & cursorTamanoPincel.position.y);
		}
		
		
	} // End window.onload()
		
		/**
		 * 
		 * Funciones de los controles de la interfaz que interactúan con el canvas
		 * 
		 */

				/**
				 * TODO QUITAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR (no se usa)
				 */
				function redimensionarImagen(){
					var MAX_WIDTH = $('#canvas_croquis').width();//$('#entorno').width(); //Anchura del div
					var MAX_HEIGHT = $('#canvas_croquis').height();//$('#entorno').height(); //Altura del div
					var tempW = imagenRaster.width;
					var tempH = imagenRaster.height;
					ratioZoomFactor = 1;
					porcentajeZoom = 0;

					//}
					//TODO
					if (tempW >= MAX_WIDTH || tempH >= MAX_HEIGHT){ //Si cualquiera de las dimensiones de la imágen es mayor que la del canvas que quite nivel de zoom
						//if (tempW >= tempH){ //Comprobamos la dimensión que va a predominar. Si el ancho de la imágen es mayor que el alto de la imágen
						if (tempH >= MAX_HEIGHT){ //Pero si el alto de la imágen es mayor que el alto del canvas que se calcule el ratio respecto del alto del canvas
							porcentajeZoom = Math.abs(MAX_HEIGHT * 100 / tempH);
							ratioZoomFactor = (tempH / MAX_HEIGHT);
						}else{ //Sino que se calcule el ratio respecto del ancho del canvas
							porcentajeZoom = Math.abs(MAX_WIDTH * 100 / tempW);
							ratioZoomFactor = (tempW / MAX_WIDTH);
						}

					}else{ //Sino que deje el tamaño de la imágen al 100% y en el centro
						ratioZoomFactor = 1;
					}
					 
					var point = paper.view.viewToProject(paper.view.center); //point //Convertimos a coordenadas dentro del proyecto
					var zoomCenter = point.subtract(paper.view.center); 
					var moveFactor = tool.zoomFactor - 1.0;
					paper.view.zoom /= ratioZoomFactor;
		            paper.view.center = paper.view.center.subtract(zoomCenter.multiply(moveFactor));

					
					var puntoCentroImagen = new paper.Point(MAX_WIDTH / 2, MAX_HEIGHT / 2);
					imagenRaster.position = puntoCentroImagen;
					//paper.view.draw;
					
					//document.getElementById("zoom_texto").value =  porcentajeZoom;
				}
				
				control_pincel.onclick = function( event ){
					var botonAuxPincel = document.getElementById("control_pincel");
					var botonAuxReunion = document.getElementById("control_reunion");
					var botonAuxBorrar = document.getElementById("control_borrar");
					var botonAuxMover = document.getElementById("control_mover");
					
					if ( botonAuxPincel.classList.contains("boton_no_pulsado") ){ //Si NO est� pulsado boton_pincel lo clickamos
						botonAuxPincel.classList.remove("boton_hover");
						botonAuxPincel.classList.toggle("boton_no_pulsado");
						botonAuxPincel.classList.add("boton_pulsado");
						canvas.classList.remove("cursor_mover");
						canvas.classList.remove("cursor_borrar");
						canvas.classList.add("cursor_none");
						
						document.getElementById("control_grosor").disabled = false;
						document.getElementById("control_grosor").value = vectorGrosor;
						document.getElementById("etiqueta").innerHTML = "Tamaño pincel: " + vectorGrosor;
						document.getElementById("control_color").disabled = false;
						document.getElementById("control_color").value = vectorColor;
						controlPincel = true;
						controlReunion = false;
						controlBorrar = false;
						controlMover = false;
						setGrosor(); //Restauramos el grosor del cursor para el grosor del vector
						if ( botonAuxReunion.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_reunion lo desclickamos
							botonAuxReunion.classList.remove("boton_pulsado");
							botonAuxReunion.classList.add("boton_hover");
							botonAuxReunion.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxBorrar.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxBorrar.classList.remove("boton_pulsado");
							botonAuxBorrar.classList.add("boton_hover");
							botonAuxBorrar.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxMover.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxMover.classList.remove("boton_pulsado");
							botonAuxMover.classList.add("boton_hover");
							botonAuxMover.classList.toggle("boton_no_pulsado");
						}
					}
					botonAuxPincel = null;
					botonAuxReunion = null;
					botonAuxBorrar = null;
					botonAuxMover = null;
				}
				
				control_reunion.onclick = function( event ){
					var botonAuxPincel = document.getElementById("control_pincel");
					var botonAuxReunion = document.getElementById("control_reunion");
					var botonAuxBorrar = document.getElementById("control_borrar");
					var botonAuxMover = document.getElementById("control_mover");
					
					if ( botonAuxReunion.classList.contains("boton_no_pulsado") ){ //Si NO est� pulsado boton_pincel lo clickamos
						botonAuxReunion.classList.remove("boton_hover");
						botonAuxReunion.classList.toggle("boton_no_pulsado");
						botonAuxReunion.classList.add("boton_pulsado");
						canvas.classList.remove("cursor_mover");
						canvas.classList.remove("cursor_borrar");
						canvas.classList.add("cursor_none");
						
						document.getElementById("control_grosor").disabled = false;
						document.getElementById("control_grosor").value = vectorGrosor;
						document.getElementById("etiqueta").innerHTML = "Tamaño pincel: " + vectorGrosor;
						document.getElementById("control_color").disabled = false;
						document.getElementById("control_color").value = reunionColor;
						controlPincel = false;
						controlReunion = true;
						controlBorrar = false;
						controlMover = false;
						setGrosor(); //Restauramos el grosor del cursor para el grosor de la reunión
						if ( botonAuxBorrar.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxBorrar.classList.remove("boton_pulsado");
							botonAuxBorrar.classList.add("boton_hover");
							botonAuxBorrar.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxPincel.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_reunion lo desclickamos
							botonAuxPincel.classList.remove("boton_pulsado");
							botonAuxPincel.classList.add("boton_hover");
							botonAuxPincel.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxMover.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxMover.classList.remove("boton_pulsado");
							botonAuxMover.classList.add("boton_hover");
							botonAuxMover.classList.toggle("boton_no_pulsado");
						}
					}
					botonAuxPincel = null;
					botonAuxReunion = null;
					botonAuxBorrar = null;
					botonAuxMover = null;
				}
				
				control_borrar.onclick = function( event ){
					var botonAuxPincel = document.getElementById("control_pincel");
					var botonAuxReunion = document.getElementById("control_reunion");
					var botonAuxBorrar = document.getElementById("control_borrar");
					var botonAuxMover = document.getElementById("control_mover");
					
					if ( botonAuxBorrar.classList.contains("boton_no_pulsado") ){ //Si NO est� pulsado boton_pincel lo clickamos
						botonAuxBorrar.classList.remove("boton_hover");
						botonAuxBorrar.classList.toggle("boton_no_pulsado");
						botonAuxBorrar.classList.add("boton_pulsado");
						canvas.classList.remove("cursor_none");
						canvas.classList.remove("cursor_mover");
						canvas.classList.add("cursor_borrar");

						document.getElementById("control_grosor").disabled = true;
						document.getElementById("control_color").disabled = true;
						controlPincel = false;
						controlReunion = false;
						controlBorrar = true;
						controlMover = false;
						cursorTamanoPincel.visible = false;
						if ( botonAuxReunion.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxReunion.classList.remove("boton_pulsado");
							botonAuxReunion.classList.add("boton_hover");
							botonAuxReunion.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxPincel.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_reunion lo desclickamos
							botonAuxPincel.classList.remove("boton_pulsado");
							botonAuxPincel.classList.add("boton_hover");
							botonAuxPincel.classList.toggle("boton_no_pulsado"); 
						}else if ( botonAuxMover.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxMover.classList.remove("boton_pulsado");
							botonAuxMover.classList.add("boton_hover");
							botonAuxMover.classList.toggle("boton_no_pulsado");
						}
					}
					botonAuxPincel = null;
					botonAuxReunion = null;
					botonAuxBorrar = null;
					botonAuxMover = null;
				}
				
				control_mover.onclick = function ( event ){
					var botonAuxPincel = document.getElementById("control_pincel");
					var botonAuxReunion = document.getElementById("control_reunion");
					var botonAuxBorrar = document.getElementById("control_borrar");
					var botonAuxMover = document.getElementById("control_mover");
					
					if ( botonAuxMover.classList.contains("boton_no_pulsado") ){ //Si NO est� pulsado boton_pincel lo clickamos
						botonAuxMover.classList.remove("boton_hover");
						botonAuxMover.classList.toggle("boton_no_pulsado");
						botonAuxMover.classList.add("boton_pulsado");
						canvas.classList.remove("cursor_none");
						canvas.classList.remove("cursor_borrar");
						canvas.classList.add("cursor_mover");
						document.getElementById("control_grosor").disabled = true;
						document.getElementById("control_color").disabled = true;
						//TODO deshabilitar cursor
						cursorTamanoPincel.visible = false;
						controlPincel = false;
						controlReunion = false;
						controlBorrar = false;
						controlMover = true;
						if ( botonAuxReunion.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxReunion.classList.remove("boton_pulsado");
							botonAuxReunion.classList.add("boton_hover");
							botonAuxReunion.classList.toggle("boton_no_pulsado");
						}else if ( botonAuxPincel.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_reunion lo desclickamos
							botonAuxPincel.classList.remove("boton_pulsado");
							botonAuxPincel.classList.add("boton_hover");
							botonAuxPincel.classList.toggle("boton_no_pulsado"); 
						}else if ( botonAuxBorrar.classList.contains("boton_pulsado") ){ //Si est� pulsado boton_borrar lo desclickamos
							botonAuxBorrar.classList.remove("boton_pulsado");
							botonAuxBorrar.classList.add("boton_hover");
							botonAuxBorrar.classList.toggle("boton_no_pulsado");
						}
					}
					botonAuxPincel = null;
					botonAuxReunion = null;
					botonAuxBorrar = null;
					botonAuxMover = null;
				}
				
				function loadImagen(e){
					//Quito la propiedad
					document.getElementById("btn_submit").disabled = false;
					//Quito la clase (estilo)
					document.getElementById("btn_submit").classList.remove("disabled");
					
					var fichero = new FileReader();
					//Activamos la capa de la im�gen y la cargamos
					capaImagen.activate();

					//Trigger del fichero cargado desde el cuadro de dialogo
					fichero.onload = function(event){
						//canvas.classList.remove("cursor_none");
						//canvas.classList.add("cursor_wait");
						if (imagenRaster != null){
							console.info("va a borrar la imagen")
							imagenRaster.remove();
							imagenRaster = null;
							hayImagen = false;

							//Borramos el contenido de las capas imagen y vectorial
							capaImagen.removeChildren();
							capaVectorial.removeChildren();
							contexto.clearRect(0, 0, canvas.width, canvas.height);
						}
							//Cargamos la imagen elegida
							imagenRaster = new paper.Raster({
						  		source: event.target.result, //rutaImagen,
								selected: false});
							
							//Redimensiona el entorno
							var MAX_WIDTH = $('#canvas_croquis').width();//$('#entorno').width(); //Anchura del div
							var MAX_HEIGHT = $('#canvas_croquis').height();//$('#entorno').height(); //Altura del div
							var tempW = imagenRaster.width;
							var tempH = imagenRaster.height;
							paper.view.zoom = ratioZoomFactor = 1;
							//porcentajeZoom = 0;
				
							if (tempW >= MAX_WIDTH || tempH >= MAX_HEIGHT){ //Si cualquiera de las dimensiones de la imágen es mayor que la del canvas que quite nivel de zoom
								//if (tempW >= tempH){ //Comprobamos la dimensión que va a predominar. Si el ancho de la imágen es mayor que el alto de la imágen
								if (tempH >= MAX_HEIGHT){ //Pero si el alto de la imágen es mayor que el alto del canvas que se calcule el ratio respecto del alto del canvas
									//porcentajeZoom = Math.abs(MAX_HEIGHT * 100 / tempH);
									ratioZoomFactor = (tempH / MAX_HEIGHT);
								}else{ //Sino que se calcule el ratio respecto del ancho del canvas
									//porcentajeZoom = Math.abs(MAX_WIDTH * 100 / tempW);
									ratioZoomFactor = (tempW / MAX_WIDTH);
								}
							}else{ //Sino que deje el tamaño de la imágen al 100% y en el centro
								ratioZoomFactor = 1;
							}
							 
							var point = paper.view.viewToProject(paper.view.center); //point //Convertimos a coordenadas dentro del proyecto
							var zoomCenter = point.subtract(paper.view.center); 
							var moveFactor = tool.zoomFactor - 1.0;
							originalZoom = paper.view.zoom /= ratioZoomFactor;
					        paper.view.center = paper.view.center.subtract(zoomCenter.multiply(moveFactor));
							
							var puntoCentroImagen = new paper.Point(MAX_WIDTH / 2, MAX_HEIGHT / 2);
							originalCentro = imagenRaster.position = paper.view.viewToProject(puntoCentroImagen);
							originalMoveFactor = tool.zoomFactor; //Para PROBARRRRRRRRRRRRRRRRRRRRRRRRRR
							originalCentro = zoomCenter; //Para PROBARRRRRRRRRRRRRRRRRRRRRRRRRR
							//paper.view.draw;
							
							//document.getElementById("zoom_texto").value =  porcentajeZoom;
							capaVectorial.activate(); //Activa la capa de los vectores y lista para dibujar //***************************************
							controlPincel = true;
							hayImagen = true;
							//canvas.classList.remove("cursor_wait");
							//canvas.classList.add("cursor_none");
				    }
					fichero.readAsDataURL(e.target.files[0]); //imágen en formato base64
				}
					
				control_guardar.onclick = function( event ){
					//for ( i=0 ; i<project.layers.length ; i++ ){}
					//var tempImg = project.activelayer.rasterize(300); //rasterize tiene el par�metro "resoluci�n" o paper.proj...
					var mimeType = "image/jpeg";
					var calidad = 1.0; //La calidad m�s alta
					//var dataString = tempImg.toDataURL(mimeType);
					var dataString = document.getElementById("canvas_croquis").toDataURL(mimeType, calidad);
					//tempImg.remove();
					var bajar = document.getElementById("descargar")
					bajar.classList.remove("invisible");
					bajar.href = dataString;
					//downloadme.href = canvasImg.src;
					//window.open(dataString, "toDataURL() image", "width=800, height=200");//Abre en una nueva ventana la im�gen
				}
				
				function descargarImagen() {
				    // feel free to choose your event ;) 
			
				    // just for example
				    // var OFFSET = $(this).offset();
				    // var x = event.pageX - OFFSET.left;
				    // var y = event.pageY - OFFSET.top;
			
				    // standard data to url
				    var imgdata = document.getElementById("canvas_croquis").toDataURL("image/png");
				    // modify the dataUrl so the browser starts downloading it instead of just showing it
				    var newdata = imgdata.replace(/^data:image\/png/,'data:application/octet-stream');
				    // give the link the values it needs
				       $('a.linkwithnewattr').attr('download','your_pic_name.png').attr('href',newdata);
				}
				
				function img_and_link() {
					$('body').append(
						    $('<a>')
						      .attr('href-lang', 'image/png')
						      .attr('href', 'data:image/png;utf8,' +  unescape($('png').outerHTML))
						      .text('Download')
						  );
					  /*$('body').append(
					    $('<a>')
					      .attr('href-lang', 'image/svg+xml')
					      .attr('href', 'data:image/svg+xml;utf8,' +  unescape($('svg')[0].outerHTML))
					      .text('Download')
					  );*/
					}
				
				function getColor(){
					if (controlPincel){
						vectorColor = document.getElementById("control_color").value;
					}else if (controlReunion){
						reunionColor = document.getElementById("control_color").value;
					}
				}
				
				function setGrosor(){
					document.getElementById("etiqueta").innerHTML = "Tamaño pincel: " + document.getElementById("control_grosor").value;

					var anteriorRadioSinStroke;
					var nuevoRadioSinStroke;
					
					anteriorRadioSinStroke = cursorTamanoPincel.bounds.width / 2;
					if (controlPincel) {
						nuevoRadioSinStroke = (document.getElementById("control_grosor").value - cursorTamanoPincel.strokeWidth) / 2; //Calculamos el nuevo radio sín la línea del círculo)
						cursorTamanoPincel.scale(nuevoRadioSinStroke / anteriorRadioSinStroke); //Modificamos el tamaño del círculo
						vectorGrosor = cursorTamanoPincel.bounds.width + cursorTamanoPincel.strokeWidth;//Diámetro actual = Ancho círculo + ancho línea círculo. Así se consigue el diámetro del círculo
					}else if (controlReunion){
						nuevoRadioSinStroke = (document.getElementById("control_grosor").value - cursorTamanoPincel.strokeWidth) / 2;
						cursorTamanoPincel.scale(nuevoRadioSinStroke / anteriorRadioSinStroke); //Modificamos el tamaño del círculo
						reunionRadio = cursorTamanoPincel.bounds.width + cursorTamanoPincel.strokeWidth;//Diámetro actual = Ancho círculo + ancho línea círculo. Así se consigue el diámetro del círculo
					}
				}
				
				function moverGrosor(direccion){
					if (controlPincel || controlReunion){
						if (direccion == "arriba"){
							document.getElementById("control_grosor").stepUp(1);
						}else if (direccion == "abajo"){
							document.getElementById("control_grosor").stepDown(1);
						}
						setGrosor();
					}
				}
				
				//Al mover el slider del Zoom
				function setZoom(){
		 			diferenciaZoom = document.getElementById("control_zoom").value - diferenciaZoom; // - document.getElementById("zoom_texto").value;
			 			if (diferenciaZoom > 0){ //Si es positivo hago zoom
			 				for (i=0 ; i < diferenciaZoom ; i++){
								setMasZoom(); //setMasZoom(diferenciaZoom);
							}
						}else{ //Si es negativo quito zoom
							diferenciaZoom = Math.abs(diferenciaZoom);
							//z*=-1; //Lo convierto a positivo
							for (i=0 ; i < diferenciaZoom ; i++){
								setMenosZoom(); //setMenosZoom( Math.abs(diferenciaZoom) );//Lo convierto a positivo
							}
						}
					//document.getElementById("zoom_texto").value = document.getElementById("control_zoom").value;
					document.getElementById("zoom_factor_texto").value = diferenciaZoom;
					
					diferenciaZoom = document.getElementById("control_zoom").value;//Esta siiiiiii
				}
			
				//Al pulsar - o + del Zoom
				function moverZoom(direccion){ 
					document.getElementById("zoom_factor_texto").value = ratioZoomFactor;
					
					var controlZoom = document.getElementById("control_zoom");
					if (direccion == "arriba"){
						if (controlZoom.value < upperZoomLimit){
							controlZoom.stepUp();
							setMasZoom();
						}
					}else if (direccion == "abajo"){
						if (controlZoom.value > lowerZoomLimit){
							controlZoom.stepDown();
							setMenosZoom();
						}
					}
					//document.getElementById("zoom_texto").value = document.getElementById("control_zoom").value;
					diferenciaZoom = document.getElementById("control_zoom").value;
				}
			
				/**
				 * Si el canvas contiene algo dibujado o una imágen que haga Scroll
				 */
				function comprobarContenidoCanvas(){
					var resul = false;
					//Recorre todas las capas
					for (i=0; i<project.layers.length;i++){
						//Si contiene hijos: líneas, puntos, imágenes
						if (project.layers[i].name != "capa del cursor" && project.layers[i].name != "capa generica"){
							if (project.layers[i].hasChildren()){
								resul = true;
								break;
							}
						}
					}
					return resul;
				}
				
				
				//TODO            QUITAR EL PARÁMETRO SI NO LO UTILIZA
				function setMasZoom(){
			       	//var children = project.activeLayer.children;
			       	//Scroll up

					if (comprobarContenidoCanvas && document.getElementById("control_zoom").value <= upperZoomLimit) { //paper.view.zoom < upperZoomLimit 

			            //var point = paper.DomEvent.getOffset(e.originalEvent, $('#canvas_croquis')[0]);
			               
			   			//var point = $('#canvas_croquis').offset(); //var
			   		    //var x = event.clientX - posicionRaton.left; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n X dentro del canvas
			   			//var y =  event.clientY - posicionRaton.top; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n Y dentro del canvas
			   			point = paper.view.viewToProject(imagenRaster.view.center); //point //Convertimos a coordenadas dentro del proyecto
			            var zoomCenter = point.subtract(paper.view.center);
			            var moveFactor = tool.zoomFactor - 1.0;
			            paper.view.zoom *= tool.zoomFactor;
			            paper.view.center = paper.view.center.add(zoomCenter.multiply(moveFactor / tool.zoomFactor));
			            tool.mode = '';
			            
				        //document.getElementById("control_zoom").value ++; //Cambiamos el slider del zoom
			        }
				}
				
				function setMenosZoom(){
					//scroll down
					if (document.getElementById("control_zoom").value >= lowerZoomLimit){ //paper.view.zoom > lowerZoomLimit
			            //var point = paper.DomEvent.getOffset(e.originalEvent, $('#canvas_croquis')[0]);
			
						//var point = $('#canvas_croquis').offset();
						
						//var x = event.clientX - posicionRaton.left; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n X dentro del canvas
						//var y =  event.clientY - posicionRaton.top; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n Y dentro del canvas
						var point = paper.view.viewToProject(paper.view.center); //point //Convertimos a coordenadas dentro del proyecto
			            var zoomCenter = point.subtract(paper.view.center);   
			            var moveFactor = tool.zoomFactor - 1.0;
			            paper.view.zoom /= tool.zoomFactor;
			            paper.view.center = paper.view.center.subtract(zoomCenter.multiply(moveFactor))
			            
			            //document.getElementById("control_zoom").value --; //Cambiamos el slider del zoom
			        }
				}
			
				function resetZoom(){
					
					//TODO centrar todas las capas o el paper. bORRAR LAS VARIABLES GLOBALES QUE UTILIZO AQUÍ (loadImagen(e)) ***********************************
					paper.view.zoom = originalZoom;
					//paper.view.center = paper.view.viewToProject(originalCentro);
					//paper.view.center = paper.view.projectToView(originalCentro);
					paper.view.center = originalCentro;
					tool.zoomFactor = originalMoveFactor;
					document.getElementById("control_zoom").value = 5;
				}
				
				$("#canvas_croquis").mousewheel(function(event, delta) {
			    	var delta = 0;
			        //var children = project.activeLayer.children;
					//var zTexto = document.getElementById("zoom_texto");
			        
					var zControl = document.getElementById("control_zoom");
			            
			        event.preventDefault();
			        event = event || window.event;
			        if (event.type == 'mousewheel') {       //this is for chrome/IE
		                delta = event.originalEvent.wheelDelta;
		            }
		            else if (event.type == 'DOMMouseScroll') {  //this is for FireFox
		                delta = event.originalEvent.detail*-1;  //FireFox reverses the scroll so we force to to re-reverse...
		            }
			
			        //var v = comprobarContenidoCanvas();
			        if (comprobarContenidoCanvas()){ // Comprobamos que exista algo dentro del canvas
				        if((delta > 0) && (document.getElementById("control_zoom").value < upperZoomLimit)) { //scroll up. paper.view.zoom
				            //var point = paper.DomEvent.getOffset(e.originalEvent, $('#canvas_croquis')[0]);
							//point = $('#canvas_croquis').offset(); //var
						    
							//Mas
							//Zoom(1);
							var x = event.clientX - posicionRaton.left; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n X dentro del canvas
							var y =  event.clientY - posicionRaton.top; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n Y dentro del canvas
							var point = paper.view.viewToProject(x,y); //Convertimos a coordenadas dentro del proyecto
				            var zoomCenter = point.subtract(paper.view.center);
				            var moveFactor = tool.zoomFactor - 1.0;
				            paper.view.zoom *= tool.zoomFactor;
				            paper.view.center = paper.view.center.add(zoomCenter.multiply(moveFactor / tool.zoomFactor));
				            tool.mode = '';
				            
				            /*porcentajeZoom = ((ratioZoomFactor + tool.zoomFactor) * porcentajeZoom) / ratioZoomFactor;
				            document.getElementById("zoom_texto").value = porcentajeZoom;*/
				            //porcentajeZoom = Math.abs(1 - ratioZoomFactor);
				            //document.getElementById("zoom_texto").value = (paper.view.zoom * porcentajeZoom) / ratioZoomFactor;
				            //document.getElementById("zoom_texto").value = porcentajeZoom;
				            
				            
				            //zTexto.value = parseInt(zTexto.value) + 1;
				            zControl.value = parseInt(zControl.value) + 1;
				        }
				        else if((delta < 0) && (document.getElementById("control_zoom").value > lowerZoomLimit)){ // (paper.view.zoom > lowerZoomLimit) && (paper.view.zoom != 1.0000000000000002)){ //scroll down //Como paper.view.zoom se queda en 1.0000000000002 hace un zoom de m�s por lo que lo evito poni�ndolo en las condici�n
							//TODO cuando llegue al nivel m�ximo de zoom se quede en el medio del canvas 
							
				        	//var point = paper.DomEvent.getOffset(e.originalEvent, $('#canvas_croquis')[0]);
							//var point = $('#canvas_croquis').offset();
							
							//Menos
							//Zoom(2);
							var x = event.clientX - posicionRaton.left; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n X dentro del canvas
							var y =  event.clientY - posicionRaton.top; //De la posici�n del rat�n dentro de la pantalla calculamos la posici�n Y dentro del canvas
							var point = paper.view.viewToProject(x,y); //Convertimos a coordenadas dentro del proyecto
				            var zoomCenter = point.subtract(paper.view.center);   
				            var moveFactor = tool.zoomFactor - 1.0;
				            paper.view.zoom /= tool.zoomFactor;
				            paper.view.center = paper.view.center.subtract(zoomCenter.multiply(moveFactor));
				            
				            //redimensionarImagen();
				            //document.getElementById("zoom_texto").value = (paper.view.zoom * porcentajeZoom) / ratioZoomFactor;
				            //document.getElementById("zoom_texto").value = porcentajeZoom;
				            
				            
				            
				            //zTexto.value = parseInt(zTexto.value) - 1; //Cambiamos el texto del zoom
				            zControl.value = parseInt(zControl.value) - 1; //Cambiamos el slider del zoom
				        }
			        }
			    });
								
				
				
				
			