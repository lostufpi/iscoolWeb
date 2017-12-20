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
    .row.content {height: 510px}
    
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
<body style="background-color: #f1f1f1;">

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
  <div class="wrapper">
    <div class="col-sm-2 sidenav">
      <button class="btn btn-default btn-block btn-success" type="submit" form="formMinhasDisciplinas"><i class="fa fa-list-alt" aria-hidden="true"></i> Minhas Disciplinas</button>
    </div>
    <div class="col-sm-8 text-left" style="background-color: #ffffff">
      <h3 class="page-header text-primary"><i class="fa fa-file-o"></i> Resultado - ${prova.nome}</h3>
      <b>Número de questões na prova: </b> ${numeroDeQuestoes }<br>
      <b>Número de acertos: </b> ${nota.nota}<br>
      <b>Número de erros: </b> ${nota.numeroDeErros}<br>
      <b>Porcentagem de acerto: </b> ${nota.porcentagemDeAcerto }% <br>
      <b>Número de questões sem resposta: </b> ${nota.questoesSemResposta } <br>
      <c:if test="${alert != null }">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>    
          <b>${alert}</b><br />
      </div>
      </c:if>
      <c:if test="${alert == null }">
      <center>
        <table class="table table-striped table-bordered table-hover table-condensed" id="tableMinhasRespostas" style="width: 40%;">
        <thead>
          <tr>
            <th class="text-center">Questão</th>
            <th class="text-center">Minha resposta</th>
            <th class="text-center">Resposta correta</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${respostaList}" var="resposta" varStatus="count">
              <tr>
                <td class="text-center"><b>${count.index + 1}</b></td>
                <td class="text-center"><b>
                  ${resposta.resposta }</b>
                  <c:choose>
                    <c:when test="${respostasCorretas.get(count.index).resposta == resposta.resposta }">
                      <i class="fa fa-check fa-1x text-primary"></i>
                    </c:when>    
                    <c:otherwise>
                      <i class="fa fa-times fa-1x text-danger"></i> 
                    </c:otherwise>
                  </c:choose>
                </td>
                <td class="text-center"><b>${respostasCorretas.get(count.index).resposta}</b></td>
              </tr>
          </c:forEach>
        </tbody>
      </table>
      	<i class="fa fa-check fa-1x text-primary"></i> <b>Acerto</b>
      	<i class="fa fa-times fa-1x text-danger"></i> <b>Erro</b>
      	<i class="fa fa-exclamation-triangle text-warning" aria-hidden="true"></i><b>Sem resposta</b>
      </center>
      </c:if>
    </div>
    <div class="col-sm-2 sidenav" style="background-color: #f1f1f1;">
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

</body>
</html>