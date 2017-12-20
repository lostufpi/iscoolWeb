<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="gravatar"
	uri="http://www.paalgyula.hu/schemas/tld/gravatar"%>

<!DOCTYPE html>

<html lang="pt-br">
<head>
<title>IsCool</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<style>
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
	background-color: #2196f3;
	color: white;
}

/* Set height of the grid so .sidenav can be 100% (adjust as needed) */
.row.content {
	height: 570px
}

/* Set gray background color and 100% height */
.sidenav {
	padding-top: 20px;
	background-color: #f1f1f1;
	height: 100%;
}

/* Set black background color, white text and some padding */
footer {
	background-color: #555;
	color: white;
	padding: 15px;
}
</style>
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand"
					href="${linkTo[AlunoController].telaPrincipal}"
					style="color: white; font-size: 20px"><i
					class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"
						style="color: white; font-size: 16px"> <b>${alunoLogado.nome}</b>
							<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
					</a>
						<ul class="dropdown-menu dropdown-user">
							<li><a href="${linkTo[AlunoController].perfil()}"> <i
									class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
							</a></li>
							<li class="divider"></li>
							<li><a href="${linkTo[AlunoController].logout()}"> <i
									class="fa fa-sign-out fa-fw"></i> <fmt:message key="logout" />
							</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-2 sidenav">
				<button class="btn btn-default btn-block btn-success" type="submit"
					form="formMinhasDisciplinas">
					<i class="fa fa-list-alt" aria-hidden="true"></i> Minhas
					Disciplinas
				</button>
				<button class="btn btn-default btn-block btn-primary" type="submit"
					form="formMinhasConquistas">
					<i class="fa fa-trophy" aria-hidden="true"></i> Minhas Conquistas
				</button>
				<button class="btn btn-default btn-block btn-default" type="submit"
					form="formAjustarPrivacidade">
					<i class="fa fa-lock" aria-hidden="true"></i> Ajustar Privacidade
				</button>
			</div>
			<div class="col-sm-8 text-left">

				<c:if test="${errors != null }">
					<br>
					<div class="alert alert-danger alert-dismissible" role="alert">
						<i class="fa fa-exclamation" aria-hidden="true"></i> <span
							class="sr-only">Error:</span>
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<c:forEach var="error" items="${errors}">
							<b>${error.message}</b>
							<br />
						</c:forEach>
					</div>
				</c:if>

				<c:if test="${feedback != null }">
					<br>
					<div class="alert alert-info alert-dismissible" role="alert">
						<i class="fa fa-check" aria-hidden="true"></i>
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<b>${feedback}</b><br />
					</div>
				</c:if>

				<c:if test="${alert != null }">
					<br>
					<div class="alert alert-warning alert-dismissible" role="alert">
						<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<b>${alert}</b><br />
					</div>
				</c:if>

				<h3 class="page-header text-primary">Ajustar Privacidade</h3>
				<b>Tipo de privacidade atual: </b>
				<hr>
				<form role="form" action="<c:url value="/aluno/ajustarPrivacidade"/>" method="post" id="formAjutarPrivacidade">
					<div class="form-group">
						<p>Existem três níveis de privacidade, essa privacidade está relacionada aos elementos de ranking e conquistas.</p>
						<ul>
							<li><b>Visível: </b> Ao selecionar essa opção, seu nome estará totalmente visível para todos os outros aluno que também utilizam essa opção, bem como suas conquistas. Você também verá o nome dos alunos que utilizam essa opção.</li>
							<li><b>Invisível: </b>Ao selecionar essa opção, seu nome de forma alguma estará vísivel para outros alunos, no entanto você não poderá ver o ranking.</li>
							<li><b>Oculto: </b> Ao selecionar essa opção, seu nome aparecerá em forma de asteriscos, desta forma você poderá ver sua posição entre os alunos participantes do ranking, mas não saberá quem oculpa determinada posição.</li>
						</ul>
						<label for="privacidadeRadio">Ajustar Privacidade</label>
						<br><label><input type="radio" name="optradio" required> Visível</label>
						<br><label><input type="radio" name="optradio" required> Invisível</label>
						<br><label><input type="radio" name="optradio" required> Oculto</label>
					</div>
					<div class="pull-right">
						<button type="submit" class="btn btn-success" form="cadastrar" >Informações</button>
						<button type="submit" class="btn btn-default" form="cancelar">Cancelar</button>
					</div>
				</form>
			</div>
			<div class="col-sm-2 sidenav"></div>
		</div>
	</div>

	<footer class="container-fluid text-center">
		<span class="copyright">Copyright &copy; LabEaSII - IsCool 2016</span>
	</footer>

	<form action="<c:url value="/disciplina/listarDisciplinaPorAluno"/>"
		method="get" id="formMinhasDisciplinas">
		<input type="hidden" name="idDoAluno" value="${alunoLogado.id}">
	</form>

	<form action="<c:url value="/aluno/ajustarPrivacidade"/>" method="get"
		id="formAjustarPrivacidade">
		<input type="hidden" name="idDoAluno" value="${alunoLogado.id}">
	</form>

	<form action="<c:url value="/insignia/insignias"/>" method="get"
		id="formMinhasConquistas">
		<input type="hidden" name="id" value="${alunoLogado.id}">
	</form>
</body>
</html>