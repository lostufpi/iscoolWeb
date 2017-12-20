<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="gravatar"
	uri="http://www.paalgyula.hu/schemas/tld/gravatar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><fmt:message key="iscool" /></title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Social Buttons CSS -->
<link href="../bower_components/bootstrap-social/bootstrap-social.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"
				href="${linkTo[EscolaController].telaPrincipal()}"> <strong>
					<span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message
						key="iscool" />
			</strong>
			</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <b>${gestorEscolarLogado.nome}</b></b>
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="${linkTo[GestorEscolarController].perfil()}">
							<i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
					</a></li>
					<li class="divider"></li>
					<li><a href="${linkTo[GestorEscolarController].logout()}"> <i
							class="fa fa-sign-out fa-fw"></i> <fmt:message key="logout" />
					</a></li>
				</ul> <!-- /.dropdown-user --></li>
			<!-- /.dropdown -->
		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">

					<li>
                            <a href="#">
                            	<i class="fa fa-file-text-o fa-fw"></i> 
                            		<fmt:message key="add" />
                            	<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                 <li> 
                                     <a href="${linkTo[TurmaController].inserirTurma()}">
                                     	<i class="fa fa-group fa-fw"></i> 
                                     	<fmt:message key="class" />
                                     </a>
                                 </li>
                                 
                                 <li> 
                                     <a href="${linkTo[ProfessorController].inserirProfessor()}">
                                     	<i class="fa fa-graduation-cap"></i> 
                                     	Professor
                                     </a>
                                 </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <form action="<c:url value="/turma/listarTurmasPorEscola"/>" method="get">
									 
								<input type="hidden" class="form-control" id="rede" name="idDaEscola"
									value="${gestorEscolarLogado.escola.id }" />
									
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-th-list fa-fw"></i>
									<fmt:message key="classes" />
								</button>
							</form>
                        </li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<form action="<c:url value="/redeEscolar/telaPrincipal"/>"
			method="get" id="voltar"></form>

		<!-- Page Content -->
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<hr>
				<div class="col-lg-12">
					<div class="media">
						<div class="media-left">
							<a
								href="https://public-api.wordpress.com/oauth2/authorize?client_id=1854&response_type=code&blog_id=0&state=825cf9490f2631c384f54cc5369057e2b9ba0c95e7c2efd6c7c5f5a9df47eb7c&redirect_uri=https%3A%2F%2Fen.gravatar.com%2Fconnect%2F%3Faction%3Drequest_access_token">
								<img class="media-object img-rounded"
								src="<gravatar:image email="${gestorEscolarLogado.email }" size="170"/>"
								alt="Gravatar" title="Alterar Imagem" alt="">
							</a>
						</div>
						<div class="media-body">
							<h3 class="media-heading">
								<b>${gestorEscolarLogado.nome }</b>
							</h3>
							<h5>
								<b>Email: </b>${gestorEscolarLogado.email }</h5>
							<h5>
								<b>Escola: </b>${gestorEscolarLogado.escola.nome }</h5>
							<h5>
								<b>Rede: </b>${gestorEscolarLogado.escola.redeEscolar.nome }</h5>
						</div>
						<hr>
					</div>
				</div>
				<!-- /.row -->
				<div class="col-lg-12">
					<div class="pull-right">
						<button type="submit" class="btn btn-default">Voltar</button>
					</div>
					<br> <br>
				</div>
			</div>
			<!-- /#page-wrapper -->
			<hr>
			
			<c:if test="${errors != null }">
	        	<div class="alert alert-danger alert-dismissible" role="alert">
	               	<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
					<span class="sr-only">Error:</span>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
	               	<c:forEach var="error" items="${errors}">
	   					<b>${error.message}</b><br />
					</c:forEach>
                </div>
			</c:if>
			
			<c:if test="${mensagem != null }">
	        	<div class="alert alert-info alert-dismissible" role="alert">
	               	<span class="glyphicon glyphicon-check" aria-hidden="true"></span>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
	   				<b>${mensagem}</b><br />
                </div>
			</c:if>
			
			<div id="content" class="col-lg-12">
				<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
					<li class="active">
						<a href="#red" data-toggle="tab">Dados da escola</a>
					</li>
					<li>
						<a href="#orange" data-toggle="tab">Dados do usuário</a>
					</li>
					<li>
						<a href="#yellow" data-toggle="tab">Endereço da escola</a>
					</li>
				</ul>
				
				<div id="my-tab-content" class="tab-content">
					<div class="tab-pane active" id="red">
						<div class="panel panel-primary">
							<div class="panel-body">
								<h5 class="page-header"><b>Alterar dados</b></h5>
								<form action="<c:url value="/gestorEscolar/atualizarEscola"/>" method="post" class="form-horizontal">
									
									<div class="form-group">
									
										<label for="nomeDaRede" class="col-sm-2 control-label">Nome da escola </label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="nomeDaRede"	name="escola.nome" value="${escola.nome }"/>
										</div>
									</div>
									<div class="form-group">
										<label for="razaoSocial" class="col-sm-2 control-label">Razão social </label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="razaoSocial" name="escola.dadosCadastrais.razaoSocial" value="${escola.dadosCadastrais.razaoSocial }"/>
										</div>
									</div>
									<div class="form-group">
										<label for="cnpj" class="col-sm-2 control-label">CNPJ </label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="cnpj" name="escola.dadosCadastrais.cnpj" value="${escola.dadosCadastrais.cnpj }"/>
										</div>
										
										<label for="telefone" class="col-sm-2 control-label">Telefone </label>
										<div class="col-sm-4">
											<input type="phone" class="form-control" id="telefone" name="escola.dadosCadastrais.telefone" value="${escola.dadosCadastrais.telefone }"/>
										</div>
									</div>
									
									<input type="hidden" name="idDaEscola" value="${escola.id }" />
									
									<div class="pull-right">
										<button type="submit" class="btn btn-success">Salvar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					
					<div class="tab-pane" id="orange">
					<div class="panel panel-primary">
						<div class="panel-body">
						<h5 class="page-header"><b>Alterar senha</b></h5>
						<form action="<c:url value="/gestorEscolar/atualizarSenha"/>" method="post" class="form-horizontal">
						
							<div class="form-group">
							
								<label for="senhaAtual" class="col-sm-2 control-label">Senha Atual </label>
								<div class="col-sm-2">
									<input type="password" class="form-control" id="senhaAtual"	name="senhaAtual" />
								</div>
								
								<label for="novaSenha" class="col-sm-2 control-label">Nova Senha </label>
								<div class="col-sm-2">
									<input type="password" class="form-control" id="novaSenha" name="novaSenha" />
								</div>
								
								<label for="confirmaSenha" class="col-sm-2 control-label">Confirma Senha </label>
								<div class="col-sm-2">
									<input type="password" class="form-control" id="confirmaSenha" name="confirmaSenha" />
								</div>
							</div>
							
							<div class="pull-right">
								<button type="submit" class="btn btn-success">Salvar</button>
							</div>
						</form>
						
						<h5 class="page-header"><b>Alterar Nome</b></h5>
							<form class="form-horizontal" action="<c:url value="/gestorEscolar/atualizarNome"/>" method="post">
								<div class="form-group">
									<label for="nome" class="col-sm-2 control-label">Nome</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="nome" name="nome" value="${gestorEscolarLogado.nome }"/>
									</div>
								</div>

								<div class="pull-right">
									<button type="submit" class="btn btn-success">Salvar</button>
								</div>
							</form>
						</div>
						</div>
					</div>
					
					<div class="tab-pane" id="yellow">
						<div class="panel panel-primary">
							<div class="panel-body">
								<h5 class="page-header"><b>Alterar endereço</b></h5>
								<form action="<c:url value="/gestorEscolar/atualizarEndereco"/>" method="post" class="form-horizontal">
								
									<div class="form-group">
									
										<label for="logradouro" class="col-sm-1 control-label">Logradouro</label>
										<div class="col-sm-2">
											<select class="form-control" name="escola.endereco.logradouro">
	                        					<option value="RUA"></option>
	  											<option value="RUA">Rua</option>
	  											<option value="AVENIDA">Avenida</option>
											</select>
										</div>
										
										<label for="endereco" class="col-sm-1 control-label">Endereço</label>
										<div class="col-sm-5">
											<input type="text" class="form-control" id="endereco" name="escola.endereco.endereco" value="${escola.endereco.endereco }"/>
										</div>
										
										<label for="numero" class="col-sm-1 control-label">Número</label>
										<div class="col-sm-2">
											<input type="number" class="form-control" id="numero" name="escola.endereco.numero" value="${escola.endereco.numero }"/>
										</div>
									</div>
									
									<div class="form-group">
									
										<label for="bairro" class="col-sm-1 control-label">Bairro</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="bairro" name="escola.endereco.bairro" value="${escola.endereco.bairro }"/>
										</div>
										
										<label for="cidade" class="col-sm-1 control-label">Cidade</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="cidade" name="escola.endereco.cidade" value="${escola.endereco.cidade }"/>
										</div>
										
										<label for="estado" class="col-sm-1 control-label">Estado</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="estado" name="escola.endereco.estado" value="${escola.endereco.estado }"/>
										</div>
									</div>
									
									<input type="hidden" name="idDaEscola" value="${escola.id }" />
									
									<div class="pull-right">
										<button type="submit" class="btn btn-success">Salvar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="text-center">
				(c) IsCool - EASII 
			</div>
		</div>

		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$('#tabs').tab();
			});
		</script>

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>
</body>
</html>