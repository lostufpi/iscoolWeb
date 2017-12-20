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
  <script src="../bower_components/jquery/dist/jquery.min.js"></script>
  <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
      background-color: #2196f3;
      color: white;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 600px}
    
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
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white; font-size:16px"> <b>${gestorDeRedeLogado.nome}</b>
          <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
        <ul class="dropdown-menu dropdown-user">
          <li><a href="${linkTo[GestorDeRedeController].perfil()}">
              <i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
          </a></li>
          <li class="divider"></li>
          <li><a href="${linkTo[GestorDeRedeController].logout()}"> 
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
      <button class="btn btn-default btn-block btn-default" type="submit"  form="formNovaEscola"><i class="fa fa-file-text-o" aria-hidden="true"></i> Nova Escola</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formMinhasEscolas"><i class="fa fa-list-alt" aria-hidden="true"></i> Minhas Escolas</button>
    </div>
    <div class="col-sm-8 text-left">
    <h3 class="page-header text-primary"><i class="fa fa-pencil" aria-hidden="true"></i> Atualizar Escola</h3> 
      <form class="form-horizontal" action="<c:url value="/escola/atualizarEscola"/>" method="post" id="formAtualizarEscola">
        <input type="hidden" name="idDaEscola" value="${escola.id }" />
        <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
        <h5>Dados cadastrais</h5>
        <div class="form-group">
          <label class="col-sm-1 control-label" for="nome">Nome </label>
          <div class="col-sm-5">
            <input class="form-control" type="text" name="escola.nome" value="${escola.nome}" id="nome">
          </div>
          <label class="col-sm-2 control-label" for="nivelDeEnsino">Nível de ensino </label>
          <div class="col-sm-4">
            <input class="form-control" type="text" name="escola.identificadorNaRede" value="${escola.identificadorNaRede}" id="nivelDeEnsino">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label" for="razaoSocial">Razão social </label>
          <div class="col-sm-5">
            <input class="form-control" type="text" name="escola.dadosCadastrais.razaoSocial" value="${escola.dadosCadastrais.razaoSocial}" id="razaoSocial">
          </div>
          <label class="col-sm-1 control-label" for="cnpj">CNPJ </label>
          <div class="col-sm-5">
            <input class="form-control" type="text" name="escola.dadosCadastrais.cnpj" value="${escola.dadosCadastrais.cnpj}">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-1 control-label" for="telefone">Telefone </label>
          <div class="col-sm-3">
            <input class="form-control" type="text" name="escola.dadosCadastrais.telefone" value="${escola.dadosCadastrais.telefone }">
          </div>
        </div>
        <hr>
        <h5>Dados do gestor</h5>
        <div class="form-group">
          <label class="col-sm-1 control-label" for="nomeGestor">Nome </label>
          <div class="col-sm-5">
            <input class="form-control" type="text" name="escola.gestorEscolar.nome" value="${escola.gestorEscolar.nome}" id="nomeGestor">
          </div>
          <label class="col-sm-2 control-label" for="email">Email </label>
          <div class="col-sm-4">
            <input class="form-control" type="email" name="escola.gestorEscolar.email" value="${escola.gestorEscolar.email}" id="email">
          </div>
        </div>
        <hr>
        <h5>Dados de localidade</h5>
        <div class="form-group">
        	<label class="col-sm-2 control-label" for="logradouro">Logradouro </label>
        	<div class="col-md-2">
        		<select class="form-control" name="escola.endereco.logradouro" id="logradouro">
        			<option value="" disabled></option>
                  	<option value="RUA">Rua</option>
                  	<option value="AVENIDA">A/venida</option>
        		</select>
        	</div>
        	<label class="col-sm-1 control-label" for="endereco">Endereço </label>
        	<div class="col-md-4">
        		<input class="form-control" type="text" name="escola.endereco.endereco" id="endereco" value="${escola.endereco.endereco }">
        	</div>
        	<label class="col-sm-1 control-label" for="numero">Número </label>
        	<div class="col-md-2">
        		<input class="form-control" type="number" name="escola.endereco.numero" id="numero" value="${escola.endereco.numero }">
        	</div>
        </div>
        <div class="form-group">
        	<label class="col-sm-2 control-label" for="bairro">Bairro </label>
        	<div class="col-md-3">
        		<input class="form-control" type="text" name="escola.endereco.bairro" id="bairro" value="${escola.endereco.bairro }">
        	</div>
        	<label class="col-sm-1 control-label" for="cidade">Cidade </label>
        	<div class="col-md-3">
        		<input class="form-control" type="text" name="escola.endereco.cidade" id="cidade" value="${escola.endereco.cidade }">
        	</div>
        	<label class="col-sm-1 control-label" for="estado">Estado </label>
        	<div class="col-md-2">
        		<input class="form-control" type="text" name="escola.endereco.estado" id="bairro" value="${escola.endereco.estado }">
        	</div>
        </div>
        <div class="pull-right">
          <button type="submit" class="btn btn-success" form="formAtualizarEscola">Atualizar <i class="fa fa-pencil"></i></button>
        </div>
      </form>
    </div>
    <div class="col-sm-2 sidenav">
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<form action="<c:url value="/escola/inserirEscola"/>" method="get" id="formNovaEscola">
</form>
<form action="<c:url value="/redeEscolar/listarEscolas"/>" method="get" id="formMinhasEscolas">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>
<form action="<c:url value="/redeEscolar/telaPrincipal"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>
</body>
</html>