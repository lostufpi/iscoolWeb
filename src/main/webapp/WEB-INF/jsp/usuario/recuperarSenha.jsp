<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>

<html lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IsCool</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    <!-- Theme CSS -->
    <link href="css/agency.min.css" rel="stylesheet">
</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top" style="background-color:#2196f3;">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top" style="color:white; font-size:30px">
                    <span>
                        <i class="fa fa-thumbs-up"></i>
                    </span>
                    IsCool
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li>
                        <a href="${linkTo[RedeEscolarController].iscool() }">Página Inicial</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#login">Login</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Services Section -->

    <!-- Login Section -->
    <section id="login">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Recuperar Senha</h2>
                    <h3 class="section-subheading text-muted">Sua nova senha será enviada para seu email cadastrado na aplicação.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                        <div class="row">
                            <div class="col-md-2"></div>
                            <div class="col-md-8 text-center">
                            	<c:if test="${usuario == null }">
                            		<form role="form" id="formRecuperar" action="<c:url value="/usuario/recuperarSenha"/>" method="post">
		                                <div class="form-group">
		                                    <input type="email" class="form-control" placeholder="Usuário ou Email *" id="usuario" name="email" required data-validation-required-message="Por favor, entre com seu email ou nome de usuário.">
		                                    <p class="help-block text-danger"></p>
		                                </div>
		                                <button type="submit" class="btn btn-xl">Confirmar</button>
                                	</form>
                                </c:if>
                                <c:if test="${usuario != null }">
                                	<h3 class="section-subheading text-muted">Você confirma ser este usuário?</h3>
                                	<h3 class="section-subheading text-muted">Nome: ${usuario.nome }</h3>
                            		<form role="form" id="formLogin" action="<c:url value="/usuario/confirmar"/>" method="post">
		                                <input type="hidden" name="email" value="${usuario.email }">	
		                                <button type="submit" class="btn btn-xl">Confirmar</button>
                                	</form>
                                </c:if>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                </div>
            </div>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <span class="copyright">Copyright &copy; LabEaSII - IsCool 2016</span>
                </div>
                <!--<div class="col-md-4">
                    <ul class="list-inline social-buttons">
                        <li><a href="#"><i class="fa fa-twitter"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-facebook"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-linkedin"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline quicklinks">
                        <li><a href="#">Privacy Policy</a>
                        </li>
                        <li><a href="#">Terms of Use</a>
                        </li>
                    </ul>
                </div>
            </div>
            -->
        </div>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>

</body>

</html>
