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
	    });
    	
    	//Llamada controlador AJAX Sectores
    	//  var result = "Llamadaaaaaaaaaa";
    	
    	$( " #zonas" ).change(function(){
    		console.info("Llamada controlador AJAX Sectores");
    		var id_zona = $(this).find("option:selected").val(); //Coje el valor seleccionado del propio elemento (this = select option)
    		console.debug("Zona seleccionada = " + id_zona);
    			
    			//Url donde se encuentra el servicio Ajax
    			var url =  "<%=Constantes.CONTROLLER_ZONAS_JSON%>";
    			
    			$.ajax( url , {
    				"type": "GET", // por defecto siempre es get por lo que sobraría
    				"success": function(result) {
    					console.info(result);	
    				},
    				"error": function(result) {
    					console.error("Error ajax", result);
    				},
    				"data": { id_zona: id_zona }, //La key: el dato
    				"async": true,
    			});
    			
    		});

    </script>
    
    

</body>

</html>
