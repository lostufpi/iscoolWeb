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
    .row.content {height: 560px}
    
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
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }
  </style>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${linkTo[EscolaController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white; font-size:16px"> <b>${gestorEscolarLogado.nome}</b>
          <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
        <ul class="dropdown-menu dropdown-user">
          <li><a href="${linkTo[GestorEscolarController].perfil()}">
              <i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
          </a></li>
          <li class="divider"></li>
          <li><a href="${linkTo[GestorEscolarController].logout()}"> 
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
      <button class="btn btn-default btn-block btn-default" type="submit"  form="formAddAluno"><i class="fa fa-user" aria-hidden="true"></i> Adicionar Aluno</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formAddDisciplina"><i class="fa fa-book" aria-hidden="true"></i> Adicionar Disciplina</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formListParticipantes"><i class="fa fa-users" aria-hidden="true"></i> Participantes</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formListDisciplinas"><i class="fa fa-list" aria-hidden="true"></i> Disciplinas</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formVoltar"><i class="fa fa-refresh" aria-hidden="true"></i> Trocar Turma</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formHome"><i class="fa fa-home" aria-hidden="true"></i> Página Inicial</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formSimulado"><i class="fa fa-home" aria-hidden="true"></i> Adicinar Simulado</button>
    </div>
    <div class="col-sm-8 text-left">
      <h3 class="page-header text-primary"><i class="fa fa-users"></i> Turma: ${turma.serie} / ${turma.nome} / ${turma.turno} </h3>
        <b>Escola: </b> ${turma.escola.nome } <br>
        <b>Série: </b> ${turma.serie } <br>
        <b>Turma: </b> ${turma.nome }
        <hr>
        
        
        <div class="col-lg-3 col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-file-text-o fa-2x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-user fa-2x"></i></div>
                <div>
                	<h5><b>Adicionar Aluno</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formAddAluno">Adicionar Aluno</button>
          </div>
        </div>
      </div>
      
      <div class="col-lg-3 col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-file-text-o fa-2x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-book fa-2x"></i></div>
                <div>
                	<h5><b>Add Disciplina</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formAddDisciplina">Adicionar Disciplina</button>
          </div>
        </div>
      </div>
      
      <div class="col-lg-3 col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-list fa-2x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-users fa-2x"></i></div>
                <div>
                	<h5><b>Participantes</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formListParticipantes">Listar Participantes</button>
          </div>
        </div>
      </div>
      
      <div class="col-lg-3 col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-list fa-2x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-book fa-2x"></i></div>
                <div>
                	<h5><b>List Disciplinas</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formListDisciplinas">Listar Disciplinas</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-2 sidenav">
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>



<form action="<c:url value="/turma/inserirTurma"/>" method="get" id="formNovaTurma">
</form>
<form action="<c:url value="/turma/listarTurmasPorEscola"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }" />
</form>

<form action="<c:url value="/escola/telaPrincipal"/>" method="get" id="formHome">
  
</form>

<form action="<c:url value="/aluno/inserirAluno"/>" method="get" id="formAddAluno">
	<input type="hidden" class="form-control" id="rede" name="idDaTurma" value="${turma.id }" />
</form>

<form action="<c:url value="/disciplina/inserirDisciplina"/>" method="get" id="formAddDisciplina">
	<input type="hidden" class="form-control" id="rede" name="idDaTurma" value="${turma.id }" />
</form>

<form action="<c:url value="/aluno/listarAlunosPorTurma"/>" method="get" id="formListParticipantes">
	<input type="hidden" name="idDaTurma" value="${turma.id }" />
</form>

<form action="<c:url value="/disciplina/listarDisciplinasPorTurma"/>" method="get" id="formListDisciplinas">
	<input type="hidden" class="form-control" id="rede" name="idDaTurma" value="${turma.id }" />
</form>

<form action="<c:url value="/simulado/inserirSimulado"/>" method="get" id="formSimulado">
	<input type="hidden" class="form-control" id="rede" name="idTurma" value="${turma.id }" />
</form>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

</body>
</html>