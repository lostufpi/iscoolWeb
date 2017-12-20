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
    .row.content {height: 860px}
    
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
    </div>
    <div class="col-sm-8 text-left">
      <h3 class="page-header text-primary"><i class="fa fa-book"></i> Disciplinas</h3>
        <b>Turma: </b> Turma: ${turma.serie} / ${turma.nome} / ${turma.turno} <br>
        <hr>
        <table class="table table-striped table-bordered table-hover table-responsive" id="tableDisciplinas">
        	<thead>
        		<tr>
                    <th class="text-center">Nome</th>
                    <th class="text-center">Descrição</th>
                    <th class="text-center">Professor</th>
                    <th class="text-center">Alterar Professor</th>
                    <th class="text-center">Ações</th>
                    <th class="text-center">Excluir</th>
                </tr>
        	</thead>
        	<tbody>
        		<c:forEach items="${disciplinaList }" var="disciplina" varStatus="status">
        			<tr>
	                    <td><b>${disciplina.nome}</b></td>
	                    <td><b>${disciplina.descricao }</b></td>
	                    <td class="text-center">
	                    	<c:choose>
	                    		<c:when test="${disciplina.professor.nome == null}">
	                    			<form action="<c:url value="/disciplina/atribuirProfessor"/>" method="get">
	                    				<input type="hidden" name="id" value="${disciplina.id}">
	                    				<button type="submit" class="btn btn-default"> Atribuir Professor 
	                    				</button>
	                        		</form>
	                            </c:when>
	                            <c:otherwise>
	                                <img src="<gravatar:image email="${disciplina.professor.email }" size="25"/>" alt="Gravatar" title="${disciplina.professor.nome }"/>
	                                ${disciplina.professor.nome }
	                            </c:otherwise>
	                        </c:choose> 
	                    </td>
	                    <td class="text-center">
	                        <form action="<c:url value="/disciplina/atribuirProfessor"/>" method="get">
	                        <input type="hidden" name="id" value="${disciplina.id}">
	                            <button type="submit" class="btn btn-default">
	                                <i class="fa fa-graduation-cap" aria-hidden="true"></i>
	                            </button>
	                        </form>
	                    </td>
	                    
	                    <td class="text-center">
	                        <form action="<c:url value="/disciplina/atualizarDisciplina"/>" method="get">
	                        <input type="hidden" name="idDaDisciplina" value="${disciplina.id }" />
	                        <button type="submit" class="btn btn-default btn-circle">
	                            <i class="fa fa-pencil"></i>
	                        </button>
	                        </form>
	                    </td>                                       
	                    <td class="text-center">
	                        <form action="<c:url value="/disciplina/${disciplina.id}"/>" method="post">
	                            <button type="submit" class="link btn btn-default btn-circle"
	                                name="_method" value="DELETE">
	                                <i class="fa fa-trash" aria-hidden="true"></i>
	                            </button>
	                        </form>
	                    </td>
	                </tr>
        		</c:forEach>
        	</tbody>
        </table>
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

<form action="<c:url value="/turma/acessarTurma"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaTurma" value="${turma.id }" />
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

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

<script>
  $(document).ready(function() {
    $('#tableDisciplinas').DataTable({
      responsive: true
    });
  });
</script>

</body>
</html>