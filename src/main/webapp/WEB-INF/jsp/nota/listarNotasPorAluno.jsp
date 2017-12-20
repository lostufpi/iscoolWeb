<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>IsCool - Participantes</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Social Buttons CSS -->
<link href="../bower_components/bootstrap-social/bootstrap-social.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"
				href="${linkTo[ProfessorController].telaPrincipal()}"> <strong>
					<span class="glyphicon glyphicon-thumbs-up"></span> IsCool
			</strong>
			</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <b>${professorLogado.professor.nome}</b>
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="${linkTo[ProfessorController].perfil()}"> <i
							class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
					</a></li>
					<li><a href="#"> <i class="fa fa-gear fa-fw"></i> <fmt:message
								key="settings" />
					</a></li>
					<li class="divider"></li>
					<li><a href="${linkTo[ProfessorController].logout()}"> <i
							class="fa fa-sign-out fa-fw"></i> <fmt:message key="logout" />
					</a></li>
				</ul> <!-- /.dropdown-user --></li>
			<!-- /.dropdown -->
		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li>
						<form
							action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>"
							method="get">

							<input type="hidden" class="form-control" id="rede"
								name="idDoProfessor" value="${professorLogado.professor.id }" />

							<button type="submit" class="btn btn-primary btn-link">
								<i class="fa fa-th-list fa-fw"></i> Minhas Disciplinas
							</button>
						</form>
					</li>
				</ul>
				<!-- /.nav-second-level -->
				</li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header text-primary">Desempenho do aluno na
						disciplina</h4>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">

					<div class="panel panel-info">
						<div class="panel-heading">Informações</div>
						<div class="panel-body">
							<b>Disciplina: </b> ${prova.disciplina.nome } <br> <b>Descrição:
							</b> ${prova.disciplina.descricao } <br> <b>Aluno: </b>
							${aluno.nome } / <b>Email: </b> ${aluno.email } <br>

						</div>
					</div>

					<div class="panel panel-success">
						<div class="panel-heading">Notas Do Aluno</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="col-md-12 col-offset-2">


								<center>
									<table class="table table-striped table-bordered table-hover"
										id="tabelaAlunos" style="width: 100%">
										<caption>Notas do aluno</caption>
										<thead>
											<tr>
												<th style="width: 30%" class="text-center">Prova</th>
												<th style="width: 20%" class="text-center">Acertos</th>
												<th style="width: 20%" class="text-center">Porcentagem
													de acerto</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${notaList }" var="nota" varStatus="count">
												<tr>
													<td class="text-left"><b>${nota.prova.nome }</b></td>

													<td class="text-right"><b>${nota.nota }</b></td>

													<td class="text-right">
														<c:if test="${nota.porcentagemDeAcerto >= 70.0 }">
															<b class="text-primary">${nota.porcentagemDeAcerto }%</b>
														</c:if>
														<c:if test="${nota.porcentagemDeAcerto < 70.0 }">
															<b class="text-danger">${nota.porcentagemDeAcerto }%</b>
														</c:if>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</center>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-0 col-sm-1"></div>
							</div>

							<div>
								<button type="submit" class="btn btn-success" form="salvar">Voltar</button>

							</div>

						</div>

						<!-- /.panel-body -->
					</div>
				</div>

			</div>

			<div class="panel panel-success">
				<div class="panel-body" id="container"
					style="width: 100%; height: 400px;"></div>
				<input type="hidden" id="idDoAluno" value="${aluno.id }"> <input
					type="hidden" id="idDaProva" value="${prova.id }">
			</div>

			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>


	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script src="http://code.highcharts.com/highcharts.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
    $(document).ready(function() {
        $('#tabelaAlunos').DataTable({
                responsive: true
        });
    });
    </script>

	<script type="text/javascript">
    	$(document).ready(function(){
    		
    		var grafico = function(source){
    			$('#container').highcharts({
    				title: {
    					text: source.titulo
    				},
    				
    				series: [{
    					name: source.nome,
    					colorByPoint: source.cor,
    					data: source.dados
    				}]
    			});
    		}
    	});
    </script>

	<script type="text/javascript"> 
    $(function () {
    	
    	var idDaProva = $('#idDaProva').val();
    	var idDoAluno = $('#idDoAluno').val();
    	
    	$.getJSON('http://localhost:8080/iscool/nota/' + idDaProva+ '/' + idDoAluno + '/graficos', function(data){
    		$('#container').highcharts({
                title: {
                    text: data.grafico.nome,
                    x: -20 //center
                },
                subtitle: {
                    text: data.grafico.titulo,
                    x: -20
                },
                xAxis: {
                    categories: data.grafico.categorias
                },
                yAxis: {
                    title: {
                        text: 'Percentual de acerto na prova'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: ' % de acerto'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: data.grafico.aluno,
                    data: data.grafico.dados
                }]
            });
    	})
    });
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