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
    
    
  </style>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${linkTo[AlunoController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white; font-size:16px"> <b>${alunoLogado.nome}</b>
          <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
        <ul class="dropdown-menu dropdown-user">
          <li><a href="${linkTo[AlunoController].perfil()}">
              <i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
          </a></li>
          <li class="divider"></li>
          <li><a href="${linkTo[AlunoController].logout()}"> 
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
      <h3 class="page-header text-primary">${disciplina.nome}</h3>
      <c:if test="${alert != null }">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>    
          <b>${alert}</b><br />
      </div>
      </c:if>
      <c:if test="${alert == null }">
        <table class="table table-striped table-bordered table-hover" id="tableMinhasProvas">
        <thead>
          <tr>
            <th class="text-center">Nome da prova</th>
            <th class="text-center">Resultado</th>
            <th class="text-center">Ranking</th>
            <th class="text-center">Imprimir resultado</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${listaDeProvas}" var="prova" varStatus="status">
              <tr>
                <td class="text-left">${prova.nome}</td>
                <td class="text-center">
                  <form action="<c:url value="/resposta/listarRespostasPorAluno"/>" method="get" class="form-inline">
                    <input type="hidden" name="idDaProva" value="${prova.id}">
                    <input type="hidden" name="idDoAluno" value="${alunoLogado.id}">
                    <button type="submit" class="btn btn-success" title="Vizualizar Resultados">
                    <i class="fa fa-bar-chart"></i>
                    Resultado
                    </button>
                  </form>
                </td>
                <td class="text-center">
                	<form action="<c:url value="/gamification/rankingPorProva"/>" method="get" class="form-inline">
                    <input type="hidden" name="id" value="${prova.id}">
                    <button type="submit" class="btn btn-success" title="Vizualizar Ranking da Prova">
                    <i class="fa fa-trophy" aria-hidden="true"></i>
                    Ranking
                    </button>
                  </form>
                </td>
                <td class="text-center">
                  <form action="<c:url value="/explicacao/listar"/>" method="get">
                    <input type="hidden" name="idProva" value="${prova.id}">
                    <input type="hidden" name="idAluno" value="${alunoLogado.id}">
                    <button type="submit" class="btn btn-primary" title="Visualizar explicações">
                      <i class="fa fa-print"></i>
                      Explicações
                    </button>
                  </form>
                </td>
              </tr>
          </c:forEach>
        </tbody>
      </table>
      </c:if>
    </div>
    <div class="col-sm-2 sidenav">
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<form action="<c:url value="/nota/listarNotasPorAlunoEDisciplina"/>" method="get" id="formMeuDesempenho">
  <input type="hidden" name="idDaDisciplina" value="${disciplina.id}">
  <input type="hidden" name="idDoAluno" value="${alunoLogado.id}">
</form>

<form action="<c:url value="/disciplina/listarDisciplinaPorAluno"/>" method="get" id="formMinhasDisciplinas">
  <input type="hidden" name="idDoAluno" value="${alunoLogado.id}">  
</form>

<form action="<c:url value="/aluno/telaPrincipal"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaRede" value="${alunoLogado.id }" />
</form>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

<script>
  $(document).ready(function() {
    $('#tableMinhasProvas').DataTable({
      responsive: true
    });
  });
</script>
</body>
</html>