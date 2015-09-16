 <%@page import="com.ipartek.formacion.skalada.Constantes"%>
</div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
<!--     <script src="bower_components/raphael/raphael-min.js"></script> -->
<!--     <script src="bower_components/morrisjs/morris.min.js"></script> -->
<!--     <script src="js/morris-data.js"></script> -->
    
    <!-- DataTables JavaScript -->
    <script src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    
    
    <!-- Enganche para DataTable -->
    <script>
    
    	//Habilitar DataTable
	    $(document).ready(function() {
	        $('#tabla').DataTable({
	                responsive: true
	        });
	    
    	
    	//Llamada controlador AJAX Sectores
    	$( "#zonas" ).change(function(){
    		console.info("Llamada controlador AJAX Sectores");
    		var id_zona = $(this).find("option:selected").val(); //Coje el valor seleccionado del propio elemento (this = select option)
    		console.debug("Zona seleccionada = " + id_zona);
    			
   			//Url donde se encuentra el servicio Ajax
   			var url =  "<%=Constantes.CONTROLLER_ZONAS_JSON%>";
   			$.ajax( url , {
   				"type": "GET", // por defecto siempre es get por lo que sobraría
   				"success": function(result){
   					rellenarSelectSector(result);
   				},
   				"error": function(result) {
   					console.error("Error ajax", result);
   				},
   				"data": { id_zona: id_zona }, //La key: el dato
   				"async": true,
   			});//End: Ajax
   			
   		});//End: change zona

   		/**
   		* Limpiar y rellenar el select options con los datos obtenidos del servicio Ajax para los Sectores
   		*/
    	function rellenarSelectSector(result){
    		console.debug("Vaciar select sectores");
    		$("#sectores").html(""); //Limpiamos el select
    		
    		console.debug("Inyectar options");
    		$(result).each(function (index, value){ //Se puede abreviar con (i, v)
    			console.debug("index: " + value.id + " Value:" + value.nombre);
    			$("#sectores").append('<option value="'+value.id+'">'+value.nombre+'</option>')
    		});
    	}
	});//End: Ready
	
	
	
    </script>
    
    

</body>

</html>
