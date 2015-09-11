<%@page import="com.ipartek.formacion.skalada.bean.Zona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

       

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tipos de Escalada
                    	<!--  Llamar al Servlet, nunca a una .JSP -->
		            	<a href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_NUEVO%>&id=-1" type="button" class="btn btn-outline btn-success">
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
			            </tr>
			        </thead>
			 		       		 
			        <tbody>
			            
			            <!-- C�digo Java -->
			            <%
			            	//Recoger� el atributo "tipoEscalada" que nos llegar� del Servlet con una colecci�n de Tipos de escalada
			            	ArrayList<Zona> zona = (ArrayList<Zona>)request.getAttribute("zona");
			           		Zona z = null;
			            	for (int i=0; i<zona.size();i++){
			            		z = zona.get(i);
			            %>
			            <tr>
			                <td><%=z.getId()%></td>
			                <td>
			                	<a href="<%=Constantes.CONTROLLER_ZONAS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=z.getId()%>">
			                		<%=z.getNombre()%>
			                	</a>
			                </td>
			            </tr>
			            <% } //end for %>
			            
			         </tbody>
	     		</table>
            </div>
            
        </div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>
