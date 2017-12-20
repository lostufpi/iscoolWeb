<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

<title>
	IsCool - Corrigir Prova
</title>

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
				href="${linkTo[ProfessorController].telaPrincipal()}"> <strong>
					<span class="glyphicon glyphicon-thumbs-up"></span> IsCool
			</strong>
			</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <b>${professorLogado.nome}</b>
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="${linkTo[GestorDeRedeController].perfil()}">
							<i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
					</a></li>
					<li class="divider"></li>
					<li><a href="${linkTo[ProfessorController].logout()}"> <i
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
						<form
							action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>"
							method="get">

							<input type="hidden" class="form-control" id="rede"
								name="idDoProfessor" value="${professorLogado.id }" />

							<button type="submit" class="btn btn-primary btn-link">
								<i class="fa fa-th-list fa-fw"></i> Minhas Disciplinas
							</button>
						</form>
					</li>
				</ul>
				<!-- /.nav-second-level -->
				</li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header text-primary">Corrigir Prova</h4>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
				
					<div class="panel panel-info">
                		<div class="panel-heading">
                            Informações da correção
                        </div>
                        <div class="panel-body">
                        	<b>Escola: </b> ${prova.disciplina.turma.escola.nome } <br>
                        	<b>Série: </b> ${prova.disciplina.turma.serie } <br>
                        	<b>Turma: </b> ${prova.disciplina.turma.nome } <br>
                        	<b>Turno: </b> ${prova.disciplina.turma.turno } <br>
                        	<b>Disciplina: </b> ${prova.disciplina.nome } <br>
                        	<b>Descrição: </b> ${prova.disciplina.descricao } <br>
                        	<b>Prova: </b> ${prova.nome } <br>
                        	<b>Aluno: </b> ${aluno.nome } / <b>Email: </b> ${aluno.email } <br>
                        	
                        </div>
                	</div>
					
					<div class="panel panel-success">
						<div class="panel-heading">Correção</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
								
								<div class="panel panel-info col-md-6"> 
									<div class="panel-heading">Gabarito Da Prova</div>
									<div class="painel-body">
										<c:forEach items="${lista}" var="questao">
											${questao.numero } - ${questao.resposta } <br>
										</c:forEach>	
									</div>
								</div>
								
						
								<div class="panel panel-info col-lg-6">
									<div class="panel-heading">Respostas Da Prova</div>
									<div class="painel-body">
										<form action="<c:url value="/prova/corrigirQuestao"/>"
											method="post">
											<input type="hidden" name="idDoAluno" value="${aluno.id }" />
											<input type="hidden" name="idDaProva" value="${prova.id }" />
											Numero da questão:<br>
											<input type="number" name="resposta.numero" /> 
											
											<br>Resposta do aluno: <br>
											<input type="radio" name="resposta.resposta" value="A" />A 
											<input type="radio" name="resposta.resposta" value="B" />B 
											<input type="radio" name="resposta.resposta" value="C" />C 
											<input type="radio" name="resposta.resposta" value="D" />D 
											<input type="radio" name="resposta.resposta" value="E" />E <br>

											<div class="form-group">
												<div class="col-sm-offset-0 col-sm-1">
													<button type="submit" class="btn btn-success">Salvar</button>
												</div>
											</div>
										</form>
										<form action="<c:url value="/prova/calcularNota"/>"
											method="post">
											<div class="col-sm-offset-1 col-sm-2">
												<input type="hidden" name="idDoAluno" value="${aluno.id }" />
												<input type="hidden" name="idDaProva" value="${prova.id }" />

												<button type="submit" class="btn btn-success">Finalizar</button>
											</div>
										</form>
									</div>
								</div>
							</div>
							<!-- /.panel-body -->
						</div>
					</div>
				</div>
				<!-- /.row -->
			</div>
			<!-- /#page-wrapper -->

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