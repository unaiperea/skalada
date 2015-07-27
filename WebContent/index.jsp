<%@page import="com.ipartek.formacion.skalada.Constantes"%>

<!DOCTYPE html>
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


 



    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Start Bootstrap</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="about.html">About</a>
                    </li>
                    <li>
                        <a href="services.html">Services</a>
                    </li>
                    <li>
                        <a href="contact.html">Contact</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Portfolio <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="portfolio-1-col.html">1 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-2-col.html">2 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-3-col.html">3 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-4-col.html">4 Column Portfolio</a>
                            </li>
                            <li>
                                <a href="portfolio-item.html">Single Portfolio Item</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Blog <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="blog-home-1.html">Blog Home 1</a>
                            </li>
                            <li>
                                <a href="blog-home-2.html">Blog Home 2</a>
                            </li>
                            <li>
                                <a href="blog-post.html">Blog Post</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Other Pages <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="full-width.html">Full Width Page</a>
                            </li>
                            <li>
                                <a href="sidebar.html">Sidebar Page</a>
                            </li>
                            <li>
                                <a href="faq.html">FAQ</a>
                            </li>
                            <li>
                                <a href="404.html">404</a>
                            </li>
                            <li>
                                <a href="pricing.html">Pricing Table</a>
                            </li>
                        </ul>
                    </li>
                    
                    <!-- enlace backoffice -->
                    <li>
                    	<a href="backoffice/pages/login.jsp">Login</a>
                    </li>
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Header Carousel -->
    <header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>
                <div class="carousel-caption">
                    <h2>Caption 1</h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Two');"></div>
                <div class="carousel-caption">
                    <h2>Caption 2</h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Three');"></div>
                <div class="carousel-caption">
                    <h2>Caption 3</h2>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
    </header>

    <!-- Page Content -->
    <div class="container">
    
    	<div class="row">
    		<div class="add-button">
				    <div class="sub-button tl"></div>
				    <div class="sub-button tr"></div>
				    <div class="sub-button bl"></div>
				    <div class="sub-button br"></div>
				  </div>
    	</div>
    

        <!-- Marketing Icons Section -->
        <div class="row">
            <div class="col-lg-12">
            	 
                <h1 class="page-header">
                    Welcome to Modern Business
                </h1>
                
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i> Bootstrap v3.2.0</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Free &amp; Open Source</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i> Easy to Use</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->

        <!-- Portfolio Section -->
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header">Portfolio Heading</h2>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
            <div class="col-md-4 col-sm-6">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-portfolio img-hover" src="http://placehold.it/700x450" alt="">
                </a>
            </div>
        </div>
        <!-- /.row -->

        <!-- Features Section -->
        <div class="row">
            <div class="col-lg-12">
                <h2 class="page-header">Modern Business Features</h2>
            </div>
            <div class="col-md-6">
                <p>The Modern Business template by Start Bootstrap includes:</p>
                <ul>
                    <li><strong>Bootstrap v3.2.0</strong>
                    </li>
                    <li>jQuery v1.11.0</li>
                    <li>Font Awesome v4.1.0</li>
                    <li>Working PHP contact form with validation</li>
                    <li>Unstyled page elements for easy customization</li>
                    <li>17 HTML pages</li>
                </ul>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, omnis doloremque non cum id reprehenderit, quisquam totam aspernatur tempora minima unde aliquid ea culpa sunt. Reiciendis quia dolorum ducimus unde.</p>
            </div>
            <div class="col-md-6">
                <img class="img-responsive" src="http://placehold.it/700x450" alt="">
            </div>
        </div>
        <!-- /.row -->

        <hr>

        <!-- Call to Action Section -->
        <div class="well">
            <div class="row">
                <div class="col-md-8">
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Molestias, expedita, saepe, vero rerum deleniti beatae veniam harum neque nemo praesentium cum alias asperiores commodi.</p>
                </div>
                <div class="col-md-4">
                    <a class="btn btn-lg btn-default btn-block" href="#">Call to Action</a>
                </div>
            </div>
        </div>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>

</body>

</html>
