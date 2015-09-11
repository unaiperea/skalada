<%@page import="java.util.ArrayList"%>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page import="com.ipartek.formacion.skalada.bean.Grado"%>

<jsp:include page="../includes/head.jsp"></jsp:include>
<jsp:include page="../includes/nav.jsp"></jsp:include>

       

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Grados
                    	<!--  Llamar al Servlet, nunca a una .JSP -->
		            	<a href="<%=Constantes.CONTROLLER_GRADOS%>?accion=<%=Constantes.ACCION_NUEVO%>&id=-1" type="button" class="btn btn-outline btn-success">
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
			                <th>Descripci�n:</th>
                
			            </tr>
			        </thead>
			 		       		 
			        <tbody>
			            
			            <!-- C�digo Java -->
			            <%
			            	//Recoger� el atributo "grado" que nos llegar� del Servlet con una colecci�n de Grados
			            	ArrayList<Grado> grados = (ArrayList<Grado>)request.getAttribute("grados");
			            	Grado g = null;
			            	for (int i=0; i<grados.size();i++){
			            		g = grados.get(i);
			            %>
			            <tr>
			                <td><%=g.getId()%></td>
			                <td>
			                	<a href="<%=Constantes.CONTROLLER_GRADOS%>?accion=<%=Constantes.ACCION_DETALLE%>&id=<%=g.getId()%>">
			                		<%=g.getNombre()%>
			                	</a>
			                </td>
			                <td><%=g.getDescripcion()%></td>
			            </tr>
			            <% } //end for %>
			            
			         </tbody>
	     		</table>
            </div>
            
        </div>
        
<jsp:include page="../includes/foot.jsp"></jsp:include>
