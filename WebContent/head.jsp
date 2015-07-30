<!DOCTYPE html>
<%@page import="com.ipartek.formacion.skalada.Constantes"%>
<html lang="en">

<head>

	<base href="<%=Constantes.ROOT_APP%>">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Modern Business - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<style>
body {
    padding: 0;
    margin: 0;
}

header {
  position: relative;
  background-color: #3f51b5;
  height: 150px;  
  box-shadow: 0 2px 5px rgba(0,0,0,0.26);
}

.add-button {
  position: absolute;
  right: 100px;
  bottom: -28px;
  width: 56px;
  height: 56px;
  overflow: visible;
  -webkit-transition: transform .4s cubic-bezier(.58,-0.37,.45,1.46),
    color 0s ease .4s,font-size .2s;
  -moz-transition: transform .4s cubic-bezier(.58,-0.37,.45,1.46),
    color 0s ease .4s,font-size .2s;
  transition: transform .4s cubic-bezier(.58,-0.37,.45,1.46),
    color 0s ease .4s,font-size .2s;
  text-align: center;
  line-height: 56px;
  font-size: 28px;
  color: rgba(255,255,255,1);
}

.add-button:before {
  position: relative;
  z-index: 100;
  content:"+";
  
}



.add-button:hover {
 
  color: rgba(255,255,255,0);
  transform: rotate(45deg);
}

.sub-button {
  position: absolute;
  display: inline-block;
  background-color:#ff4081;
  color: rgba(255,255,255,0);
  width: 28px;
  height: 28px;
  line-height:48px;
  font-family: "FontAwesome";
  font-size: 12px;
  -webkit-transition: top .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    left .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    bottom .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    right .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    width .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    height .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    transform .1s ease 0s,
    border-radius .2s  ease .2s;
   -moz-transition: top .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    left .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    bottom .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    right .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    width .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    height .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    transform .1s ease 0s,
    border-radius .2s  ease .2s;
   transition: top .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    left .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    bottom .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    right .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    width .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    height .2s cubic-bezier(.58,-0.37,.45,1.46) .2s,
    transform .1s ease 0s,
    border-radius .2s  ease .2s;
}

.tl {
  top: 0;
  left: 0;
  border-radius: 28px 0 0 0;
}

.tr {  
  top: 0;
  right: 0;
  border-radius: 0 28px 0 0;
}

.bl {
  bottom: 0;
  left: 0; 
  border-radius: 0 0 0 28px;
}

.br { 
  bottom: 0;
  right: 0;
  border-radius: 0 0 28px 0;
}


.tl:before {
  content:"";
}

.tr:before {
  content:"";
}

.bl:before {
  content:"";
}

.br:before {
  content:"";
}


.add-button:hover .sub-button {
  width: 48px;
  height: 48px;
  transform: rotate(-45deg);
  
  color: rgba(255,255,255,1);
  -webkit-transition: top .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    left .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    bottom .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    right .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    width .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    height .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    color .3s ease .8s,
    transform .3s ease .8s,
    border-radius .4s  ease .6s;
   -moz-transition: top .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    left .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    bottom .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    right .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    width .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    height .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    color .3s ease .8s,
    transform .3s ease .8s,
    border-radius .4s  ease .6s;
   transition: top .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    left .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    bottom .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    right .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    width .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    height .4s cubic-bezier(.58,-0.37,.45,1.46) .4s,
    color .3s ease .8s,
    transform .3s ease .8s,
    border-radius .4s  ease .6s;
}

.add-button:hover .tl {
  top: -25px;
  left: -25px;
  border-radius: 28px;
}

.add-button:hover .tr {  
  top: -25px;
  right: -25px;
  border-radius: 28px;
}

.add-button:hover .bl {
  bottom: -25px;
  left: -25px; 
  border-radius: 28px;
}

.add-button:hover .br { 
  bottom: -25px;
  right: -25px;
  border-radius: 28px;
}
</style>
