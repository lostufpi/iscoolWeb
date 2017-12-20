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
      <a class="navbar-brand" href="${linkTo[EscolarController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
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
      <button class="btn btn-default btn-block btn-default" type="submit" form="formNovaNoticia"><i class="fa fa-list" aria-hidden="true"></i> Nova Notícia</button>
    </div>
    <div class="col-sm-8 text-left">
        
	    <c:if test="${errors != null }">
	    	<br>
        	<div class="alert alert-danger alert-dismissible" role="alert">
        		<i class="fa fa-exclamation" aria-hidden="true"></i>
				<span class="sr-only">Error:</span>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
               	<c:forEach var="error" items="${errors}">
   					<b>${error.message}</b><br />
				</c:forEach>
			</div>
        </c:if>
        
        <c:if test="${feedback != null }">
        	<div class="alert alert-info alert-dismissible" role="alert">
             	<i class="fa fa-check" aria-hidden="true"></i>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
 				<b>${feedback}</b>
             </div>
		</c:if>
		
		<c:if test="${alert != null }">
			<br>
        	<div class="alert alert-warning alert-dismissible" role="alert">
             	<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
 				<b>${alert}</b><br />
             </div>
		</c:if>
		
      <h3 class="page-header text-primary">Bem-Vindo, ${gestorEscolarLogado.nome}</h3>
      <div class="media">
        <div class="media-left">
          <a href="https://public-api.wordpress.com/oauth2/authorize?client_id=1854&response_type=code&blog_id=0&state=825cf9490f2631c384f54cc5369057e2b9ba0c95e7c2efd6c7c5f5a9df47eb7c&redirect_uri=https%3A%2F%2Fen.gravatar.com%2Fconnect%2F%3Faction%3Drequest_access_token">
            <img class="media-object img-rounded" src="<gravatar:image email="${gestorEscolarLogado.email }" size="120"/>" alt="Gravatar" title="Alterar Imagem" alt="">
          </a>
        </div>
        <div class="media-body">
          <b>Email: </b>${gestorEscolarLogado.email }<br>
          <b>Rede: </b>${gestorEscolarLogado.escola.nome }<br>
          <b>Razão Social: </b>${gestorEscolarLogado.escola.dadosCadastrais.razaoSocial }<br>
          <b>Cnpj: </b>${gestorEscolarLogado.escola.dadosCadastrais.cnpj }<br>
          <c:if test="${gestorEscolarLogado.escola.dadosCadastrais.telefone != null}">
            <b>Telefone: </b>${gestorEscolarLogado.escola.dadosCadastrais.telefone }<br>
          </c:if>
          <c:if test="${gestorEscolarLogado.escola.endereco.logradouro != null}">
            <b>${gestorEscolarLogado.escola.endereco.logradouro}:</b> ${gestorEscolarLogado.escola.endereco.endereco}
          </c:if>
          <c:if test="${gestorEscolarLogado.escola.endereco.numero != null}">
            <b>Número:</b> ${gestorEscolarLogado.escola.endereco.numero}<br>
          </c:if>
          <c:if test="${gestorEscolarLogado.escola.endereco.bairro != null}">
            <b>Bairro:</b> ${gestorEscolarLogado.escola.endereco.bairro}
          </c:if>
          <c:if test="${gestorEscolarLogado.escola.endereco.cidade != null}">
            <b>Cidade:</b> ${gestorEscolarLogado.escola.endereco.cidade}
          </c:if>
          <c:if test="${gestorEscolarLogado.escola.endereco.estado != null}">
            <b>Estado:</b> ${gestorEscolarLogado.escola.endereco.estado}
          </c:if>
        </div>
      </div>
      <hr>
      <div class="col-lg-3 col-md-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-file-text-o fa-2x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-users fa-2x"></i></div>
                <div>
                	<h5><b>Nova Turma</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formNovaTurma">Nova Turma</button>
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
                	<h5><b>Minhas Turmas</b></h5>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formMinhasTurmas">Minhas Turmas</button>
          </div>
        </div>
      </div>
      
    </div>
    <div class="col-sm-2 sidenav">
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<form action="<c:url value="/turma/inserirTurma"/>" method="get" id="formNovaTurma">
  
</form>
<form action="<c:url value="/turma/listarTurmasPorEscola"/>" method="get" id="formMinhasTurmas">
	<input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }">  
</form>
<form action="<c:url value="/feed/inserir"/>" method="get" id="formNovaNoticia">
	<input type="hidden" name="idDaEscola" value="${gestorEscolarLogado.escola.id }">  
</form>
</body>
</html>