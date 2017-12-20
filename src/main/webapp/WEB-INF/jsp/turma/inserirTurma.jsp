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
    .row.content {height: 550px}
    
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
      <a class="navbar-brand" href="${linkTo[RedeEscolarController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
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
      <button class="btn btn-default btn-block btn-default" type="submit"  form="formNovaTurma"><i class="fa fa-file-text-o" aria-hidden="true"></i> Nova Turma</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formMinhasTurmas"><i class="fa fa-list" aria-hidden="true"></i> Minhas Turmas</button>
    </div>
    <div class="col-sm-8 text-left">
      <h3 class="page-header text-primary"><i class="fa fa-file-text-o"></i> Nova Turma</h3>
      <form class="form-horizontal" role="form" id="formNovaTurmaPost" action="<c:url value="/turma/inserirTurma"/>" method="post">
        <h5>Dados da Turma</h5>
        <div class="form-group">
          <label for="serie" class="col-sm-2 control-label"> Série *</label>
          <div class="col-sm-10">
              <input type="text" class="form-control" id="serie" name="turma.serie" required/>
          </div>
        </div>
        <div class="form-group">
          <label for="nome" class="col-sm-2 control-label"> Nome * </label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="nome" name="turma.nome" required/>
          </div>
        </div>
        <div class="form-group">    
          <label for="turno" class="col-sm-2 control-label"> Turno *</label>
          <div class="col-sm-10">
          	<input type="radio" id="turno" name="turma.turno" value="MANHA" required/>Manhã 
          	<input type="radio" id="turno" name="turma.turno" value="TARDE" required/>Tarde 
          	<input type="radio" id="turno" name="turma.turno" value="NOITE" required/>Noite 
          	<input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }" />
          </div>
        </div>
        </form>
      <div class="btn-group pull-right"> 
        <button type="submit" class="btn btn-success" form="formNovaTurmaPost">Salvar <i class="fa fa-floppy-o"></i></button>
      </div>

      <h3 class="page-header text-primary"><i class="fa fa-file-text-o"></i> Novas Turmas</h3>
      <form action="<c:url value="/turma/importarArquivoCSV"/>" method="post" enctype="multipart/form-data" id="formImportarArquivo">
        <label>Importe várias das suas turmas de uma só vez através de um arquivo CSV</label>
        <input type="file" name="uploadedFile" required>
        <input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }" />
      </form>
      <div class="pull-right">
        <button type="submit" class="btn btn-success" form="formImportarArquivo">Importar Arquivo <i class="fa fa-file-o"></i></button>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">Modelo do Arquivo <i class="fa fa-search"></i></button>
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

<form action="<c:url value="/turma/listarTurmasPorEscola"/>" method="get" id="formMinhasTurmas">
  <input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }" />
</form>

<form action="<c:url value="/escola/telaPrincipal"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }" />
</form>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <img alt="Modelo de arquivo" src="<c:url value="/imagens/importacaoEscolas.JPG"/>">
  </div>
</div>

</body>
</html>

