<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.bean.Via"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

       

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Vias                    	
                    	<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_NUEVO%>" type="button" class="btn btn-outline btn-success">
                    		<i class="fa fa-plus"></i> Nueva
                    	</a>
                    </h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            
            <!--  tabla -->
            
            <table id="tabla" class="display" cellspacing="0" width="100%">
		        <thead>
		            <tr>
		                <th>Id</th>
		                <th>Nombre</th>
		                <th>Grado</th>
		                <th>Longitud</th>		                
		            </tr>
		        </thead>
		 		       		 
		        <tbody>
	        	<%
	        	//Recoger el atributo "vias" que nos llegara del Servlet con una coleccion de Vias
	        	ArrayList<Via> vias = (ArrayList<Via>)request.getAttribute("vias");	        	
	        	Via v = null;
	        	for ( int i=0; i < vias.size() ; i++ ){
	        		v = vias.get(i);	
	        	%>
		            <tr>
		                <td><%=v.getId()%></td>
		                <td>
		                	<a href="<%=Constantes.CONTROLLER_VIAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=v.getId()%>">
		                		<%=v.getNombre()%>
		                	</a>
		                </td>
		                <td><%=v.getGrado()%></td>
		                <td><%=v.getLongitud()%></td>		                
		            </tr>
		            
		       <% } //end for %>
		            
		       </tbody>
		       
		    </table>      
            
            
            </div>
            
        </div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>            