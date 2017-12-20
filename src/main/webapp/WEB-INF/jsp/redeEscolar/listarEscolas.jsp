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
      <button class="btn btn-default btn-block btn-default" type="submit" form="formMinhasEscolas"><i class="fa fa-list" aria-hidden="true"></i> Minhas Escolas</button>
    </div>
    <div class="col-sm-8 text-left">
      <h3 class="page-header text-primary"><i class="fa fa-list-alt"></i> Minhas Escolas</h3>
      <c:if test="${alert != null }">
       	<div class="alert alert-warning alert-dismissible" role="alert">
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
				<b>${alert}</b><br />
            </div>
        
        <div class="col-lg-6 col-md-6">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-file-text-o fa-4x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge"><i class="fa fa-university fa-2x"></i></div>
                <div>
                	<h3>Nova Escola</h3>
                </div>
              </div>
            </div>
          </div>
          <div class="panel-footer">
            <button type="submit" class="btn btn-link btn-small text-right" form="formNovaEscola">Nova Escola</button>
          </div>
        </div>
      </div>
		</c:if>
      <c:if test="${alert == null }">
      <table class=" table table-striped table-bordered table-hover" id="tableMinhasEscolas">
        <thead>
          <tr>
            <th>Escola</th>
            <th>Nível</th>
            <th>Gestor</th>
            <th>Email do gestor</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${escolaList }" var="escola" varStatus="status">
            <tr>
              <td>${escola.nome}</td>
              <td class="text-center">${escola.identificadorNaRede}</td>
              <td class="text-center">${escola.gestorEscolar.nome }</td>
              <td class="text-center">${escola.gestorEscolar.email }</td>
              <td class="text-center">
                <form action="<c:url value="/escola/atualizarEscola"/>" method="get">
                            <input type="hidden" name="id" value="${escola.id }" />
                            <button type="submit" class="btn btn-warning btn-circle btn-small">
                              <i class="fa fa-pencil"></i>
                            </button>
                            </form>
                <form action="<c:url value="/escola/${escola.id}"/>" method="post">
                  <button type="submit" class="link btn btn-danger btn-small"
                    name="_method" value="DELETE">
                    <span class="glyphicon glyphicon-trash"></span> 
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

<form action="<c:url value="/escola/inserirEscola"/>" method="get" id="formNovaEscola">
</form>
<form action="<c:url value="/redeEscolar/listarEscolas"/>" method="get" id="formMinhasEscolas">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>
<form action="<c:url value="/redeEscolar/telaPrincipal"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

<script>
  $(document).ready(function() {
    $('#tableMinhasEscolas').DataTable({
      responsive: true
    });
  });
</script>
</body>
</html>

