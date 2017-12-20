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
				data-toggle="dropdown" href="#"> <b>${professorLogado.nome}</b>
					<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="${linkTo[ProfessorController].perfil()}">
							<i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
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
								name="idDoProfessor" value="${professorLogado.id }" />

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
					<h4 class="page-header text-primary">Corrigir Prova</h4>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
				
					<div class="panel panel-info">
                		<div class="panel-heading">
                            Informações
                        </div>
                        <div class="panel-body">
                        	- Selecione as respostas de acordo com as respostas marcadas pelo aluno na prova, após isso clique em salvar, para salvar as respostas, no caso do aluno não marcar nenhuma alternativa marque NR.                       	
                        </div>
                	</div>
					
					<div class="panel panel-success">
						<div class="panel-heading">Correção</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
										<form action="<c:url value="/questao/corrigirQuestoes"/>" method="post" id="salvar">
											<div class="col-md-12" >
											<center>		
												<table class="table table-striped table-bordered table-hover" id="tabelaRespostas" style="width: 40%" >
													<caption>Respostas do aluno ${aluno.nome }</caption>
													<thead>
														<tr>
															<th style="width: 10%" class="text-center">Questão</th>
															<th style="width: 90%" class="text-center">Resposta</th>
														</tr>
													</thead>
													<tbody>
											<c:forEach items="${questaoList }" var="questao" varStatus="count">
												<input type="hidden" name="idDaProva" value="${prova.id }" />
												<input type="hidden" name="idDoAluno" value="${aluno.id }" />
												<input type="hidden" name="numeros[${count.index }]" value="${questao.numero }"/>
												
												
													
														<tr>
															<td class="text-center">
																<b>${questao.numero }</b>
															</td>
															
															<td class="text-center">
																<label class="radio-inline">
												<input type="radio" name="respostas[${count.index }]" value="A" class="required" required="true"/>A
												</label>
												<label class="radio-inline"> 
												<input type="radio" name="respostas[${count.index }]" value="B" class="required"/>B
												</label> 
												<label class="radio-inline">
												<input type="radio" name="respostas[${count.index }]" value="C" class="required"/>C
												</label>
												<label class="radio-inline"> 
												<input type="radio" name="respostas[${count.index }]" value="D" class="required"/>D
												</label>
												<label class="radio-inline"> 
												<input type="radio" name="respostas[${count.index }]" value="E" class="required"/>E 
												</label>
												<label class="radio-inline"> 
												<input type="radio" name="respostas[${count.index }]" value="NR" class="required"/>NR 
												</label>
																
															</td>
														</tr>
																							
												 
										</c:forEach>
											</tbody>
												</table>	
												</center>
</div>
											<div class="form-group">
												<div class="col-sm-offset-0 col-sm-1">
												</div>
											</div>
										</form>
										<form action="<c:url value="/questao/finalizar"/>" method="post" id="cancelar">
  											<input type="hidden" name="idDaDisciplina" value="${prova.disciplina.id }" />
  										</form>
							
							<div>
								<center>
                        		<button type="submit" class="btn btn-success" form="salvar">Salvar respostas</button>
                        		<button type="submit" class="btn btn-warning" form="cancelar">Cancelar</button>
                        		</center>
                        	</div>
										
							</div>
							
							<!-- /.panel-body -->
						</div>
					</div>
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
		
</body>

</html>