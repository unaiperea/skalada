<%@page import="com.ipartek.formacion.skalada.bean.Sector"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

       

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tipos de Escalada
                    	<!--  Llamar al Servlet, nunca a una .JSP -->
		            	<a href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_NUEVO%>&id=-1" type="button" class="btn btn-outline btn-success">
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
			                <th>Zona</th>
                
			            </tr>
			        </thead>
			 		       		 
			        <tbody>
			            
			            <!-- C�digo Java -->
			            <%
			            	//Recoger� el atributo "sector" que nos llegar� del Servlet con una colecci�n de Sectores
			            	ArrayList<Sector> sectores = (ArrayList<Sector>)request.getAttribute("sectores");
			            	Sector s = null;
			            	for (int i=0; i<sectores.size();i++){
			            		s = sectores.get(i);
			            %>
			            <tr>
			                <td><%=s.getId()%></td>
			                <td>
			                	<a href="<%=Constantes.CONTROLLER_SECTORES%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=s.getId()%>">
			                		<%=s.getNombre()%>
			                	</a>
			                </td>
			                <td><%=s.getZona().getNombre()%></td> <!-- Cojo el atributo de tipo Zona y cojo su nombre -->
			            </tr>
			            <% } //end for %>
			            
			         </tbody>
	     		</table>
            </div>
            
        </div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>
