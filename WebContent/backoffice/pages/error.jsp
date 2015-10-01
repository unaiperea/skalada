<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<%@page contentType="text/html"%> 
<%@page pageEncoding="UTF-8"%> 

<%@page errorPage="true"%> 

<!DOCTYPE html>
<html lang="es">

<head>

	<base href="<%=Constantes.ROOT_APP%>">

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Error 500</title>

    <!-- Bootstrap Core CSS -->
    <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<style type="text/css">
	#contenedor{
		background-image: url('img/mendi.jpg');
	    background-position: center; /*right bottom, left top;*/
	    background-repeat: no-repeat, repeat;
	    background-size: cover;
	}
	
	#img-alien{
		padding-top: 20%;
		padding-left: 10%;
	}
	
	h1{
		font-size: 5em;
		color: white;
	}
	
	h2{
		font-size: 3.5em;
		color: white;
	}
	
	h3{
		font-size: 2em;
		color: red;
	}
	
</style>


</head>

<body>
	
	<div id="contenedor" class="container-fluid">
		<div class="row">
			<div class="col-xs-6">
				<img id="img-alien" src="img/alien500.png"></img>
			</div>
			
			<div class="col-xs-2">
				<h2>error </h2>
			</div>
			<div class="col-xs-4">
				<h1>500</h1>
			</div>

		</div>
		
		<div class="row center-block">
			<div class="col-xs-12">
				<h3>Perdona las molestias, algo ha cascado en mi interior. Ponte en contacto con el administrador</h3>
			</div>
		</div>
		
		<div class="row center-block">
			<div class="col-xs-12">
				<a href="<%=Constantes.VIEW_PUBLIC_INDEX%>">
					<img src="img/home.png" alt="ir a la Home"></img>
				</a>
			</div>
		</div>
		
	</div>
	
</body>

</html>




