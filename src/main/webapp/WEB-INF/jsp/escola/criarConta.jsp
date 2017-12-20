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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>   
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
                        <a class="page-scroll" href="#criarRede">Criar Escola</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Services Section -->

    <!-- Login Section -->
    <section id="criarRede">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Criar Escola</h2>
                    <h3 class="section-subheading text-muted">Informe os dados necessários para criação da sua escola.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form role="form" id="formCriarEscola" action="<c:url value="/escola/criarConta"/>" method="post">
                        <div class="row">
                        	<c:if test="${errors != null }">
									<div class="alert alert-danger alert-dismissible" role="alert" >
										<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										<span class="sr-only">Error:</span>
										<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
										<c:forEach var="error" items="${errors}">
					    					${error.message}<br />
										</c:forEach>
									</div>
								</c:if>
								<c:if test="${mensagem != null }">
									<div class="alert alert-primary alert-dismissible" role="alert">
										<span class="glyphicon glyphicon-check" aria-hidden="true"></span>
											<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
											${mensagem }
									</div>
								</c:if>
                            <div class="col-md-6">
                            	<h6 class="section-subheading text-muted">Dados cadastrais</h6>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Nome da escola *" id="nomeDaEscola" name="escola.nome" required data-validation-required-message="Por favor, informe o nome da escola.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                	<input type="text" class="form-control" placeholder="Nível de ensino *" id="nivelDeEnsino" name="escola.identificadorNaRede" required data-validation-required-message="Por favor, informe o nível de ensino da escola."/>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Razão Social *" id="razaoSocial" name="escola.dadosCadastrais.razaoSocial" required data-validation-required-message="Por favor, informe a razão social da escola.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="CNPJ *" id="cnpj" name="escola.dadosCadastrais.cnpj" required data-validation-required-message="Por favor, informe o CNPJ da escola.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Nome do gestor *" id="nomeGestor" name="gestor.nome" required data-validation-required-message="Por favor, informe o nome do gestor da escola.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                            	<h6 class="section-subheading text-muted">Dados da conta</h6>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="E-mail do gestor *" id="emailGestor" name="gestor.email" required data-validation-required-message="Por favor, informe o e-mail do gestor da escola.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" placeholder="Senha *" id="senha" name="gestor.senha" required data-validation-required-message="Por favor, informe a senha para acesso da conta.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" placeholder="Confirme a senha *" id="confirmeSenha" name="confirmaSenha" required data-validation-required-message="Por favor, confirme a senha de acesso da conta.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <button type="submit" class="btn btn-xl">Criar Escola</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <span class="copyright">Copyright &copy; LabEaSII - IsCool 2016</span>
                </div>
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
