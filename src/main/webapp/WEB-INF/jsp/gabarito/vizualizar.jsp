<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="pt-br">




<head>
	<title>IsCool - Login</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="<c:url value="/css/style.css" />" rel="stylesheet">
	<link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
	
	<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	
	<div id="wrapper">

		 <!-- Navigation -->
		 <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
		 	<div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${linkTo[UsuariosController].login() }">
                	<strong>
                		<span class="glyphicon glyphicon-thumbs-up"></span>
                		<fmt:message key="iscool" />
                	</strong>
                </a>

                <ul class="nav navbar-nav">
					<li>
						<a href="${linkTo[RedeEscolarController].inserirRedeEscolar()}">
							Nova Rede Escolar
						</a>
					</li>
				</ul>
				
				<ul class="nav navbar-nav">
					<li>
						<a href="${linkTo[GabaritoController].vizualizar()}">
							Download do cartão resposta
						</a>
					</li>
				</ul>
				
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="${linkTo[UsuarioController].login()}">
							Login
						</a>
					</li>
				</ul>
            </div>
            <!-- /.navbar-header -->
		 </nav>
	</div>
	
	<div class="row">
		<div class="row">
		<div class="col-md-12">
			<iframe src="<c:url value="/documentos/gabarito/gabarito_20_questoes.pdf"/>" width="100%" height="780" style="border: none;">
			</iframe>
		</div>
	</div>
</div>
	
	<script src="<c:url value="/js/jquery.min.js" />"></script>
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>	
</body>
</html>