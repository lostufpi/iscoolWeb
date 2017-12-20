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
    .row.content {height: 770px}
    
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
      <h3 class="page-header text-primary"><i class="fa fa-pie-chart" aria-hidden="true"></i> Resultados - ${prova.nome}</h3>
      <table class="table table-striped table-bordered table-hover table-responsive" id="tableNotas">
        <thead>
          <tr>
            <th class="text-center">Aluno</th>
            <th class="text-center">Acertos</th>
            <th class="text-center">Erros</th>
            <th class="text-center">Sem resposta</th>
            <th class="text-center">Percentual de acerto</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${notaList }" var="nota" varStatus="status">
            <tr>
              <td class="text-left">${nota.aluno.nome}</td>
              <td class="text-right">${nota.nota} de ${numeroDeQuestoes}</td>
              <td class="text-right">${nota.numeroDeErros} de ${numeroDeQuestoes}</td>
              <td class="text-right">${nota.questoesSemResposta} de ${numeroDeQuestoes}</td>
              <td class="text-right">
                    <c:if test="${nota.porcentagemDeAcerto >= 70.0 }">
                      <b class="text-primary">
                        ${nota.porcentagemDeAcerto } %
                      </b>
                    </c:if>
                    <c:if test="${nota.porcentagemDeAcerto < 70.0 }">
                      <b class="text-danger">
                        ${nota.porcentagemDeAcerto } %
                      </b>
                    </c:if>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class=container-fluid>
        <div class="row">
          <div class="col-md-12">
            <div class="col-md-6">
              <div class="panel panel-success">
                <div class="panel-body" id = "container" style = "width: 100%; height: 400px;"></div>
                <input type="hidden" id="idDaProva" value="${prova.id }">
              </div>
            </div>
            <div class="col-md-6">
              <div class="panel panel-success">
                <div class="panel-body" id = "container2" style = "width: 100%; height: 400px;"></div>
              </div>
            </div>
          </div>
        </div>
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

<form action="<c:url value="/disciplina/acessarDisciplina"/>" method="get" id="formVoltar">
  <input type="hidden" name="id" value="${prova.disciplina.id }" />
</form>

<form action="<c:url value="/prova/inserirProva"/>" method="get" id="formNovaProva">
  <input type="hidden" name="id" value="${disciplina.id}" />
</form>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
<script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
<script src = "http://code.highcharts.com/highcharts.js" ></script>

<script>
  $(document).ready(function() {
    $('#tableNotas').DataTable({
      responsive: true
    });
  });
</script>

<script type="text/javascript">
    $(function(){
      
      var idDaProva = $('#idDaProva').val();
      
      $.getJSON('http://localhost:8080/iscool/nota/' + idDaProva+ '/graficos', function(data){
        $('#container').highcharts({
              chart: {
                plotBackgroundColor: null,
                  plotBorderWidth: null,
                  plotShadow: false,
                  type: data.grafico.tipo
              },
              title: {
                  text: data.grafico.titulo
              },
              
              plotOptions: {
                  series: {
                      dataLabels: {
                          enabled: false,
                          format: '{point.name}: {point.y:.1f}%'
                      },
                      showInLegend: true
                  }
              },
              
              tooltip: {
                  headerFormat: '<span style="font-size:14px">{series.name}</span><br>',
                  pointFormat: '<span style="color:{point.color}; font-size:13px">{point.name}</span>: <b>{point.y:.2f}%</b> do total<br/>'
              },
              
              series: [{
                  name: 'Porcentagem de acertos dos alunos em relação ao número de questões',
                  colorByPoint: true,
                  data: data.grafico.acertos
              }],
          }
        );
        
        $('#container2').highcharts({
              chart: {
                plotBackgroundColor: null,
                  plotBorderWidth: null,
                  plotShadow: false,
                  type: data.grafico.tipo
              },
              title: {
                  text: 'Estatísticas do número de acertos, erros e questões sem resposta'
              },
              
              plotOptions: {
                  series: {
                      dataLabels: {
                          enabled: false,
                          format: '{point.name}: {point.y:.1f}%'
                      },
                      showInLegend: true
                  }
              },
              
              tooltip: {
                  headerFormat: '<span style="font-size:12px">{series.name}</span><br>',
                  pointFormat: '<span style="color:{point.color}; font-size:10px">{point.name}</span>: <b>{point.y:.2f}%</b> do total<br/>'
              },
              
              series: [{
                  name: 'Porcentagem de acertos dos alunos em relação ao número de questões',
                  colorByPoint: true,
                  data: data.grafico.questoes
              }],
          }
        );
      })
    })
    </script>
    <script>
    var chart1; // globally available
    $(function() {
          chart1 = new Highcharts.StockChart({
             chart: {
                renderTo: 'container'
             },
             rangeSelector: {
                selected: 1
             },
             series: [{
                name: 'USD to EUR',
                data: usdtoeur // predefined JavaScript array
             }]
          });
       });
    </script>
</body>
</html>