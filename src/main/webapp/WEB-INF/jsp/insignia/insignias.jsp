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
<link
	href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">

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
	height: 700px
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

				<h3 class="page-header text-primary">Minhas Conquistas</h3>
				<center>
					<img src="<c:url value="/imagens/medalha.jpg"/>" width="12%"
						height="12%" class="img-circle">
					<h5>
						<b>${alunoLogado.nome }</b>
					</h5>
					<h5>
						<b>Nível: Estudante principiante</b>
					</h5>
				</center>
				<hr>

				<div class="container-fluid">
				<div class="row">
					<c:forEach items="${insigniasList}" var="insignia" varStatus="id">
	        			<div class="col-md-4">
							<center>
								<c:if test="${insignia.codigo == 's1'}">
									<img src="<c:url value="/imagens/s1.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's2'}">
									<img src="<c:url value="/imagens/s2.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's3'}">
									<img src="<c:url value="/imagens/s3.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's4'}">
									<img src="<c:url value="/imagens/s4.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's5'}">
									<img src="<c:url value="/imagens/s5.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's6'}">
									<img src="<c:url value="/imagens/s6.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's7'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's8'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's9'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's10'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's11'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
								<c:if test="${insignia.codigo == 's12'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="30%" height="30%" class="img-circle">
								</c:if>
							</center>
							<center><b>${insignia.nome }</b></center>
							<center>${insignia.descricao }</center>
						</div>
	        		</c:forEach>
				</div>
					
        		</div>

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
</body>
</html>