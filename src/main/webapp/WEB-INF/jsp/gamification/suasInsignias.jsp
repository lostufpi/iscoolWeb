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
    .row.content {height: 1200px}
    
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
      <h3 class="page-header text-primary"><i class="fa fa-gamepad" aria-hidden="true"></i> Suas insígnias - ${disciplina.nome}</h3>
      <c:if test="${mensagem != null }">
      	<div class="alert alert-info alert-dismissible" role="alert">
	    	<span class="glyphicon glyphicon-check" aria-hidden="true"></span>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>		
	    			<b>${mensagem}</b><br />
		</div>
	</c:if>
      <h5><b>Olá, professor. Aqui estão suas insígnias que está utilizando no momento, fique a vontade para editá-las ou parar de utiliza-las.</b></h5>
      <hr>
      <div class="container-fluid">
      	<div class="row">
      		<c:forEach items="${insigniasD}" var="insignia" varStatus="id">
      			<input type="hidden" value="${insignia.id}" name="idInsignia">
      			<div class="col-md-12">
      				<div class="col-md-2">
      					<c:if test="${insignia.imagem == 's1'}">
							<img src="<c:url value="/imagens/s1.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
						<c:if test="${insignia.imagem == 's2'}">
							<img src="<c:url value="/imagens/s2.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
						<c:if test="${insignia.imagem == 's3'}">
							<img src="<c:url value="/imagens/s3.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
						<c:if test="${insignia.imagem == 's4'}">
							<img src="<c:url value="/imagens/s4.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
						<c:if test="${insignia.imagem == 's5'}">
							<img src="<c:url value="/imagens/s5.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
						<c:if test="${insignia.imagem == 's6'}">
							<img src="<c:url value="/imagens/s6.jpg"/>" width="50%" height="50%" class="img-circle">
						</c:if>
      				</div>
      				<div class="col-md-8">
      					<div class="media">
      						<div class="media-body">
      							<h4 class="media-heading"><b>${insignia.nome } - ${insignia.pontuacao} XP</b></h4>
      							${insignia.mensagem }
      						</div>
      					</div>
      				</div>
      				<div class="col-md-2">
      					<button class="btn btn-warning btn-sm btn-block">Editar</button>
      					<form action="<c:url value="/insignia/${insignia.id}"/>" method="post">
      						<button type="submit" name="_method" value="DELETE" class="btn btn-danger btn-sm btn-block">Deletar</button>
      					</form>
      					<br><br>	
      				</div>
      			</div>
      		</c:forEach>
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

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myInsigniaModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
      		<div class="modal-header">
      			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      			<h4 class="modal-title" id="gridSystemModalLabel">Configure sua insígnia</h4>
      			<div class="modal-body">
      				Id da insignia ${insignia.id }
      			</div>
      			<div class="modal-footer">
		        	<button type="button" class="btn btn-default" data-dismiss="modal">Sair</button>
		        	<button type="button" class="btn btn-primary">Salvar</button>
		      	</div>
      		</div>
      	</div>
	</div>	  		
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				 <h4 class="modal-title" id="myModalLabel">Suas insígnias</h4>
			</div>
			<div class="modal-body">
				<c:forEach items="${insigniasD}" var="insignia" varStatus="id">
					<div class="media">
					  <div class="media-left">
					      <c:if test="${insignia.imagem == 's1'}">
							<img src="<c:url value="/imagens/s1.jpg"/>" width="10%" height="10%" class="img-circle">
						  </c:if>
					  </div>
					  <div class="media-body">
					    <h4 class="media-heading">${insignia.nome }</h4>
					    ${insignia.mensagem }
					  </div>
					</div>
				</c:forEach>
			</div>
			<div class="modal-footer">
		    	<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
			</div>
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