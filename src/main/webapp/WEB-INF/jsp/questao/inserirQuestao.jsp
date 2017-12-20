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
    .row.content {height: 1500px}
    
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
  <script type="text/javascript" src="jquery.js">
	$(function(){
	    $(".input-search").keyup(function(){
	        var tabela = $(this).attr('alt');
	        if( $(this).val() != ""){
	            $("."+tabela+" tbody>tr").hide();
	            $("."+tabela+" td:contains-ci('" + $(this).val() + "')").parent("tr").show();
	        } else{
	            $("."+tabela+" tbody>tr").show();
	        }
	    }); 
	});
	$.extend($.expr[":"], {
	    "contains-ci": function(elem, i, match, array) {
	        return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
	    }
	});
	</script>
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
      <h3 class="page-header text-primary"><i class="fa fa-pencil" aria-hidden="true"></i> <b>Questão ${cont } de ${prova.questoes }</b></h3>
      <script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script> <script type="text/javascript">
									//<![CDATA[
        							bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  								//]]>
  								</script>
		<form action="<c:url value="/questao/inserirQuestao"/>" method="post" id="inserirQuestao">
    	<h5><b>Descrição da Questão</b></h5>
		<textarea name="questao.descricao" style="width: 100%; height:100px;" required>
  		</textarea><br />
  								
		<h5><b>Alternativa 1</b></h5>
		<textarea name="questao.primeiraAlternativa" style="width: 100%; height:80px;">
  		</textarea><br />
                                 
        <h5><b>Alternativa 2</b></h5>
        <textarea name="questao.segundaAlternativa" style="width: 100%; height:80px;">
  		</textarea><br />
  								
		<h6><b>Alternativa 3</b></h6>
		<textarea name="questao.terceiraAlternativa" style="width: 100%; height:80px;">
  		</textarea><br />
  								
		<h6><b>Alternativa 4</b></h6>
		<textarea name="questao.quartaAlternativa" style="width: 100%; height:80px;">
  		</textarea><br />
  								
		<h6><b>Alternativa 5</b></h6>
		<textarea name="questao.quintaAlternativa" style="width: 100%; height:80px;">
  		</textarea><br />
  								
  		<h6><b>Explicação da questão</b></h6>
  		<textarea name="questao.explicacao" style="width: 100%; height:80px;">
  		</textarea><br />
  								
		<input type="hidden" class="form-control" id="nome" name="idDaProva" value="${prova.id }"/>
		<input type="hidden" class="form-control" id="nome" name="id" value="${prova.disciplina.id }"/>
                                
		<h6><b>Resposta correta</b></h6>       
		<div class="col-sm-8">
			<input type="radio" id="resposta" name="questao.resposta" value="A" />A 	
			<input type="radio" id="resposta" name="questao.resposta" value="B" />B	
			<input type="radio" id="resposta" name="questao.resposta" value="C" />C
			<input type="radio" id="resposta" name="questao.resposta" value="D" />D
			<input type="radio" id="resposta" name="questao.resposta" value="E" />E   
		</div>
  		</form>
  		<form action="<c:url value="/questao/finalizar"/>" method="post" id="finalizar">
  			<input type="hidden" name="idDaDisciplina" value="${prova.disciplina.id }" />
  		</form>
  		<div class="pull-right">
  			<button type="submit" class="btn btn-success" form="inserirQuestao">Salvar</button>
  			<button type="submit" class="btn btn-danger" form="finalizar">Finalizar</button>
  		</div>
    </div>
    <div class="col-sm-2 sidenav">
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get" id="formMinhasDisciplinas">  
  <input type="hidden" class="form-control" id="rede" name="idDoProfessor" value="${professorLogado.id }" />
</form>

<form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDoProfessor" value="${professorLogado.professor.id}" />
</form>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
</body>
</html>