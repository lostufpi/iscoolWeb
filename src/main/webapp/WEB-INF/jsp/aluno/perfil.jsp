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
				href="${linkTo[AlunoController].telaPrincipal()}"> <strong>
					<span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message
						key="iscool" />
			</strong>
			</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <b>${alunoLogado.nome}</b></b>
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="${linkTo[AlunoController].perfil()}">
							<i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
					</a></li>
					<li class="divider"></li>
					<li><a href="${linkTo[AlunoController].logout()}"> <i
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
                            <form action="<c:url value="/disciplina/listarDisciplinaPorAluno"/>" method="get">
									 
								<input type="hidden" class="form-control" id="rede" name="idDoAluno"
									value="${alunoLogado.id }" />
									
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-th-list fa-fw"></i>
									Minhas Disciplinas
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
								src="<gravatar:image email="${alunoLogado.email }" size="170"/>"
								alt="Gravatar" title="Alterar Imagem" alt="">
							</a>
						</div>
						<div class="media-body">
							<h3 class="media-heading">
								<b>${alunoLogado.nome }</b>
							</h3>
								<b>Email: </b>${alunoLogado.email }<br>
								<b>Rede Escolar: </b>${alunoLogado.aluno.turma.escola.redeEscolar.nome }<br>
								<b>Escola: </b>${alunoLogado.aluno.turma.escola.nome }<br>
								<b>Série: </b>${alunoLogado.aluno.turma.serie }
								<b>/Turma: </b>${alunoLogado.aluno.turma.nome }
								<b>/Turno: </b>${alunoLogado.aluno.turma.turno }<br>
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
						<a href="#orange" data-toggle="tab">Dados do usuário</a>
					</li>
					<li>
						<a href="#yellow" data-toggle="tab">Endereço do usuário</a>
					</li>
				</ul>
				
				<div id="my-tab-content" class="tab-content">
					<div class="tab-pane active" id="orange">
					<div class="panel panel-primary">
						<div class="panel-body">
						<h5 class="page-header"><b>Alterar senha</b></h5>
						<form action="<c:url value="/aluno/atualizarSenha"/>" method="post" class="form-horizontal">
						
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
							<form class="form-horizontal" action="<c:url value="/aluno/atualizarNome"/>" method="post">
								<div class="form-group">
									<label for="nome" class="col-sm-2 control-label">Nome</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="nome" name="nome" value="${alunoLogado.nome }"/>
									</div>
								</div>

								<div class="pull-right">
									<button type="submit" class="btn btn-success">Salvar</button>
								</div>
							</form>
							
						<h5 class="page-header"><b>Alterar Dados Cadastrais</b></h5>
							<form class="form-horizontal" action="<c:url value="/aluno/atualizarAluno"/>" method="post">
								<div class="form-group">
									<label for="identidade" class="col-sm-2 control-label">Identidade</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="identidade" name="aluno.dadosPessoais.identidade" value="${alunoLogado.aluno.dadosPessoais.identidade }"/>
									</div>
								</div>
								
								<div class="form-group">
									<label for="cpf" class="col-sm-2 control-label">CPF</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="cpf" name="aluno.dadosPessoais.cpf" value="${alunoLogado.aluno.dadosPessoais.cpf }"/>
									</div>
								</div>
								
								<div class="form-group">
									<label for="telefone" class="col-sm-2 control-label">Telefone</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="telefone" name="aluno.dadosPessoais.telefone" value="${alunoLogado.aluno.dadosPessoais.telefone }"/>
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
								<form action="<c:url value="/aluno/atualizarEndereco"/>" method="post" class="form-horizontal">
								
									<div class="form-group">
									
										<label for="logradouro" class="col-sm-1 control-label">Logradouro</label>
										<div class="col-sm-2">
											<select class="form-control" name="aluno.endereco.logradouro">
	                        					<option value="RUA"></option>
	  											<option value="RUA">Rua</option>
	  											<option value="AVENIDA">Avenida</option>
											</select>
										</div>
										
										<label for="endereco" class="col-sm-1 control-label">Endereço</label>
										<div class="col-sm-5">
											<input type="text" class="form-control" id="endereco" name="aluno.endereco.endereco" value="${alunoLogado.aluno.endereco.endereco }"/>
										</div>
										
										<label for="numero" class="col-sm-1 control-label">Número</label>
										<div class="col-sm-2">
											<input type="number" class="form-control" id="numero" name="aluno.endereco.numero" value="${alunoLogado.aluno.endereco.numero }"/>
										</div>
									</div>
									
									<div class="form-group">
									
										<label for="bairro" class="col-sm-1 control-label">Bairro</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="bairro" name="aluno.endereco.bairro" value="${alunoLogado.aluno.endereco.bairro }"/>
										</div>
										
										<label for="cidade" class="col-sm-1 control-label">Cidade</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="cidade" name="aluno.endereco.cidade" value="${alunoLogado.aluno.endereco.cidade }"/>
										</div>
										
										<label for="estado" class="col-sm-1 control-label">Estado</label>
										<div class="col-sm-3">
											<input type="text" class="form-control" id="estado" name="aluno.endereco.estado" value="${alunoLogado.aluno.endereco.estado }"/>
										</div>
									</div>
									
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