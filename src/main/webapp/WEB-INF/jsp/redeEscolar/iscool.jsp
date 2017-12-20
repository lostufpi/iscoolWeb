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
	
	<style>
		footer {
      		background-color: #555;
      		color: white;
      		padding: 15px;
    	}
	</style>
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
                        <a class="page-scroll" href="#services">Serviços</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#portfolio">Galeria</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#about">Planos</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#team">Time</a>
                    </li>
                    <li>
                        <a href="${linkTo[UsuarioController].login()}">Login</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <header>
        <div class="container">
            <div class="intro-text">
                <div class="intro-lead-in">Precisa de um aplicativo de gestão, correção e discussão de provas objetivas?</div>
                <div class="intro-heading">Então Bem Vindo ao Iscool!</div>
            </div>
        </div>
    </header>

    <!-- Services Section -->
    <section id="services">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Serviços</h2>
                    <h3 class="section-subheading text-muted">Consulte os serviços que disponibilizamos.</h3>
                </div>
            </div>
            <div class="row text-center">
                <div class="col-md-6">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-mobile fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Aplicativo Mobile</h4>
                    <p class="text-muted">Corrija suas provas em segundos fazendo uso apenas de seu smartphone e tenha os resultados na palma da sua mão. Tenha ainda acesso ao chat de suas disciplinas para potencializar o conhecimento de seus alunos.</p>
                </div>
                <div class="col-md-6">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-laptop fa-stack-1x fa-inverse"></i>
                    </span>
                    <h4 class="service-heading">Aplicação Web</h4>
                    <p class="text-muted">Gerencie todos os envolvidos no processo de uma avaliação, registrando sua rede, escolas, professores e alunos, além do gerencimento de provas, resultados e uma série de estatísticas interessantes para acompanhar uma escola como um todo.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Portfolio Grid Section -->
    <section id="portfolio" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Galeria</h2>
                    <h3 class="section-subheading text-muted">Conheça um pouco do nosso aplicativo.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3 col-sm-4 portfolio-item">
                    <a href="#portfolioModal1" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/chatt.jpeg" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Chats</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-4 portfolio-item">
                    <a href="#portfolioModal2" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/conquistas.jpeg" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Conquistas</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-4 portfolio-item">
                    <a href="#portfolioModal3" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/grafico.jpeg" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Resultados</h4>
                    </div>
                </div>
                <div class="col-md-3 col-sm-4 portfolio-item">
                    <a href="#portfolioModal3" class="portfolio-link" data-toggle="modal">
                        <div class="portfolio-hover">
                            <div class="portfolio-hover-content">
                                <i class="fa fa-plus fa-3x"></i>
                            </div>
                        </div>
                        <img src="img/portfolio/rankings.jpeg" class="img-responsive" alt="">
                    </a>
                    <div class="portfolio-caption">
                        <h4>Rankings</h4>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Planos</h2>
                    <h3 class="section-subheading text-muted">Conheça nossos planos e escolha o melhor para o seu negócio.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <ul class="timeline">
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/portfolio/gestorderede.jpg" alt="" height="200" width="200">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>Gestor de Rede</h4>
                                    <h4 class="subheading">Se possui uma rede de ensino, esse é o plano certo pra você!</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Crie sua conta e comece já a gerenciar as escolas de sua rede e os gestores das mesmas. Tenha todas as estatísticas de suas escolas de forma rápida e ágil.
                                    <a href="${linkTo[RedeEscolarController].inserirRedeEscolar()}">Criar Conta</a>
                                    </p>
                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/portfolio/gestorescolar.jpg" alt="" height="200" width="200">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>Gestor Escolar</h4>
                                    <h4 class="subheading">Possui ou gerencia alguma escola? Que tal conferir esse plano?!</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">O que você acha de gerenciar sua escola com apenas alguns cliques? Isso mesmo, com esse plano você pode criar suas turmas, disciplinas, cadastrar seus alunos, professores e muito mais. Obtenha também as estatísticas das entidades formadoras de sua escola de forma simples e rápida!
                                    <a href="${linkTo[EscolaController].criarConta()}">Criar Conta</a> 
                                    </p>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="timeline-image">
                                <img class="img-circle img-responsive" src="img/portfolio/professor.jpg" alt="" height="500" width="500">
                            </div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4>Professor</h4>
                                    <h4 class="subheading">Cansado de conferir gabaritos? Aproveite esta oportunidade!</h4>
                                </div>
                                <div class="timeline-body">
                                    <p class="text-muted">Crie sua conta de professor e tenha disponível um leque de opções para ajudá-lo! Gerencie suas provas através da aplicação web e as corrija através do aplicativo mobile. Tenha disponível ainda um chat de discussão de suas provas entre você e seus alunos.
                                    <a href="${linkTo[ProfessorController].criarConta()}">Criar Conta</a>
                                    </p>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </section>

    <!-- Team Section -->
    <section id="team" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Time de Desenvolvedores</h2>
                    <h3 class="section-subheading text-muted">Dê uma conferida em quem trabalha nessa ferramenta.</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/portfolio/professorpedro.jpg" class="img-responsive img-circle" alt="">
                        <h4>Pedro de Alcântara</h4>
                        <p class="text-muted">Idealizador</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/portfolio/pp.jpg" class="img-responsive img-circle" alt="">
                        <h4>Alexandre Lages</h4>
                        <p class="text-muted">Desenvolvedor Web</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="team-member">
                        <img src="img/portfolio/pp2.jpg" class="img-responsive img-circle" alt="">
                        <h4>Gustavo Lustosa</h4>
                        <p class="text-muted">Desenvolvedor Mobile</p>
                        <ul class="list-inline social-buttons">
                            <li><a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                    <p class="large text-muted"></p>
                </div>
            </div>
        </div>
    </section>

<!--     Clients Aside -->
<!--     <aside class="clients"> -->
<!--         <div class="container"> -->
<!--             <div class="row"> -->
<!--                 <div class="col-md-3 col-sm-6"> -->
<!--                     <a href="#"> -->
<!--                         <img src="img/logos/envato.jpg" class="img-responsive img-centered" alt=""> -->
<!--                     </a> -->
<!--                 </div> -->
<!--                 <div class="col-md-3 col-sm-6"> -->
<!--                     <a href="#"> -->
<!--                         <img src="img/logos/designmodo.jpg" class="img-responsive img-centered" alt=""> -->
<!--                     </a> -->
<!--                 </div> -->
<!--                 <div class="col-md-3 col-sm-6"> -->
<!--                     <a href="#"> -->
<!--                         <img src="img/logos/themeforest.jpg" class="img-responsive img-centered" alt=""> -->
<!--                     </a> -->
<!--                 </div> -->
<!--                 <div class="col-md-3 col-sm-6"> -->
<!--                     <a href="#"> -->
<!--                         <img src="img/logos/creative-market.jpg" class="img-responsive img-centered" alt=""> -->
<!--                     </a> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
<!--     </aside> -->

    <!-- Contact Section -->
    <!--<section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Contate-nos</h2>
                    <h3 class="section-subheading text-muted">Mande-nos suas sugestões</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Seu Nome *" id="name" required data-validation-required-message="Please enter your name.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Seu Email *" id="email" required data-validation-required-message="Please enter your email address.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Seu Telefone *" id="phone" required data-validation-required-message="Please enter your phone number.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Digite Sua Mensagem *" id="message" required data-validation-required-message="Please enter a message."></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" class="btn btn-xl">Enviar Mensagem</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>-->

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <center><span class="copyright">Copyright &copy; Laboratório LOST - IsCool 2017</span></center>
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
