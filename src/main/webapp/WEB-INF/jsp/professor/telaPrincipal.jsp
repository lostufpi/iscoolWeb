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
      height: 60px;
      color: white;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 552px}
    
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
      <a class="navbar-brand" href="${linkTo[ProfessorController].telaPrincipal}" style="color:white; font-size:30px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
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
      <h3 class="page-header text-primary">Bem vindo, ${professorLogado.nome }</h3>
      <div class="media">
        <div class="media-left">
          <a href="https://public-api.wordpress.com/oauth2/authorize?client_id=1854&response_type=code&blog_id=0&state=825cf9490f2631c384f54cc5369057e2b9ba0c95e7c2efd6c7c5f5a9df47eb7c&redirect_uri=https%3A%2F%2Fen.gravatar.com%2Fconnect%2F%3Faction%3Drequest_access_token">
            <img class="media-object img-rounded" src="<gravatar:image email="${professorLogado.email }" size="120"/>" alt="Gravatar" title="Alterar Imagem" alt="">
          </a>
        </div>
        <div class="media-body">
          <b>Email: </b>${professorLogado.email }<br>
          <c:if test="${professorLogado.professor.dadosPessoais.cpf != null}">
              <b>CPF: </b>${professorLogado.professor.dadosPessoais.cpf }<br>
          </c:if>
          <c:if test="${professorLogado.professor.dadosPessoais.telefone != null}">
              <b>Telefone: </b>${professorLogado.professor.dadosPessoais.telefone }<br>
          </c:if>
          <c:if test="${professorLogado.professor.endereco.endereco != null}">
              <b>${professorLogado.professor.endereco.logradouro}:</b> ${professorLogado.professor.endereco.endereco}
          </c:if>
          <c:if test="${professorLogado.professor.endereco.numero != null}">
              <b>Número:</b> ${professorLogado.professor.endereco.numero}<br>
          </c:if>
          <c:if test="${professorLogado.professor.endereco.numero != null}">
              <b>Número:</b> ${professorLogado.professor.endereco.numero}<br>
          </c:if>
          <c:if test="${professorLogado.professor.endereco.bairro != null}">
              <b>Bairro:</b> ${professorLogado.professor.endereco.bairro}
          </c:if>
          <c:if test="${professorLogado.professor.endereco.cidade != null}">
              <b>Cidade:</b> ${professorLogado.professor.endereco.cidade}
          </c:if>
          <c:if test="${professorLogado.professor.endereco.estado != null}">
              <b>Estado:</b> ${professorLogado.professor.endereco.estado}
          </c:if>
        </div>
      </div>
      <hr>
    </div>
    <div class="col-sm-2 sidenav">
    </div>
  </div>
</div>

<form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get" id="formMinhasDisciplinas">  
  <input type="hidden" class="form-control" id="rede" name="idDoProfessor" value="${professorLogado.id }" />
</form>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>
</body>
</html>