<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>

<!DOCTYPE html>

<html lang="pt-br">
<head>
  <title>IsCool</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
      background-color: #2196f3;
      color: white;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 1000px}
    
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
      <a class="navbar-brand" href="${linkTo[ProfessorController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white; font-size:16px"> <b>${professorLogado.nome}</b>
          <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
        <ul class="dropdown-menu dropdown-user">
          <li><a href="${linkTo[ProfessorController].perfil()}">
              <i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
          </a></li>
          <li class="divider"></li>
          <li><a href="${linkTo[ProfessorController].logout()}"> 
            <i class="fa fa-sign-out fa-fw"></i> <fmt:message key="logout" />
          </a></li>
        </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
  
<div class="container-fluid text-center">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <button class="btn btn-default btn-block btn-success" type="submit" form="formMinhasDisciplinas"><i class="fa fa-list-alt" aria-hidden="true"></i> Minhas Disciplinas</button>
    </div>
    <div class="col-sm-8 text-left">
      <h3 class="page-header text-primary"><i class="fa fa-gamepad" aria-hidden="true"></i> Configurar insígnia</h3>
      <h5><b>Olá, professor. Configure essa insígnia da maneira que achar melhor, coloque uma mensagem para os seus alunos que a conquistarem e um nome para ela.</b></h5>
      <div class="container-fluid">
      	<div class="row">
      		<center>
      			<c:if test="${insignia.imagem == 's1'}">
								<img src="<c:url value="/imagens/s1.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's2'}">
									<img src="<c:url value="/imagens/s2.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's3'}">
									<img src="<c:url value="/imagens/s3.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's4'}">
									<img src="<c:url value="/imagens/s4.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's5'}">
									<img src="<c:url value="/imagens/s5.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's6'}">
									<img src="<c:url value="/imagens/s6.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's7'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's8'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's9'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's10'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's11'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								<c:if test="${insignia.imagem == 's12'}">
									<img src="<c:url value="/imagens/medalha.jpg"/>" width="15%" height="15%" class="img-circle">
								</c:if>
								
      		</center>	
      		<center><b>${insignia.descricao }</b></center>
      	</div>
      	<div class="row">
      		<div class="col-md-12">
      			<form action="<c:url value="/gamification/salvarInsignia"/>" method="post">
      				<div class="form-group">
					<label for="nomeDaInsignia">
						Nome da insígnia
					</label>
					<input type="text" class="form-control" id="nomeDaInsignia" name="nomeDaInsignia" required/>
				</div>
				
				<div class="form-group">
					 
					<label for="mensagemDaInsignia">
						Mensagem ao aluno
					</label>
					<input type="text" class="form-control" id="mensagemDaInsignia" name="mensagemDaInsignia" required/>
					<input type="hidden" name="idInsignia" value="${insignia.id }"/>
					<input type="hidden" name="idDisciplina" value="${disciplina.id }"/>
				</div>
				
				<div class="form-group">
					 
					<label for="pontuacaoDaInsignia">
						Defina um valor em pontos para essa insígnia. Esse valor será usado na criação dos rankings da prova e disciplina
					</label>
					<input type="number" class="form-control" id="pontuacaoDaInsignia" name="pontuacaoDaInsignia" required/>
				</div>
				<div class="pull-right">
					<button class="btn btn-primary" type="submit">Salvar</button>
				</div>
      			</form>
      		</div>
      	</div>
      </div>
    </div>
    <div class="col-sm-2 sidenav">
      <button type="submit" form="formNovaProva" class="btn btn-primary btn-block"><i class="fa fa-file" aria-hidden="true"></i> Nova prova</button>
      <button type="submit" form="formGamificacao" class="btn btn-primary btn-block"><i class="fa fa-gamepad" aria-hidden="true"></i> Gamificação</button>
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get" id="formMinhasDisciplinas">  
  <input type="hidden" class="form-control" id="rede" name="idDoProfessor" value="${professorLogado.id }" />
</form>

<form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDoProfessor" value="${professorLogado.professor.id}" />
</form>

<form action="<c:url value="/prova/inserirProva"/>" method="get" id="formNovaProva">
  <input type="hidden" name="id" value="${disciplina.id}" />
</form>

<form action="<c:url value="/gamification/gamificacao"/>" method="get" id="formGamificacao">
  <input type="hidden" name="id" value="${disciplina.id}" />
</form>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

</body>
</html>