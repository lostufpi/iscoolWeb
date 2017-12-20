<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IsCool - Participantes</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Social Buttons CSS -->
    <link href="../bower_components/bootstrap-social/bootstrap-social.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <!-- DataTables CSS -->
    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
    
</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${linkTo[ProfessorController].telaPrincipal()}">
                	<strong>
                    	<span class="glyphicon glyphicon-thumbs-up"></span> 
                        	IsCool
                    </strong>
                </a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <b>${professorLogado.professor.nome}</b> <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li>
                        	<a href="${linkTo[ProfessorController].perfil()}">
                        		<i class="fa fa-user fa-fw"></i> 
                        		<fmt:message key="profile" />
                        	</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                        	<a href="${linkTo[ProfessorController].logout()}">
                        		<i class="fa fa-sign-out fa-fw"></i> 
                        		<fmt:message key="logout" />
                        	</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                    	<li>
                            <form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get">
									 
								<input type="hidden" class="form-control" id="rede" name="idDoProfessor"
									value="${professorLogado.professor.id }" />
									
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-th-list fa-fw"></i>
									Minhas Disciplinas
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
            <!-- /.navbar-static-side -->
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header text-primary">${prova.nome }</h3>
                    <div class="pull-right">                    	
                    	<button type="submit" class="btn btn-primary" form="calcular">
							Calcular notas <i class="fa fa-calculator"></i>
						</button>
						<button type="submit" class="btn btn-default" form="voltar">Voltar</button>
					</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                	<h4 class="page-header text-info">Informações de correção</h4>
                        	<ul>
                        		<li>
                        			Selecione o aluno ao qual deseja corrigir a prova clicando na opção <b>Correção</b>.
                        		</li>
                        		<li>
                        			Se caso já houver uma correção para a prova do aluno em questão, poderá ver as respostas marcadas anteriormente pelo aluno, clicando na opção <b>Listar Respostas</b>.
                        		</li>
                        		<li>
                        			Clique na opção <b>Desempenho na disciplina</b> para ver o desempenho do aluno.
                        		</li>
                        		<li>
                        			Após alterar as respostas do aluno clique em <b>Calcular notas</b>.
                        		</li>
                        	</ul>
                
                	<h4 class="page-header text-success">Lista de alunos</h4>
                    <div class="panel panel-success">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div id="sample">
  							<table class="table table-striped table-bordered table-hover table-responsive" id="tabelaAlunos">
							<thead>
								<tr>
									<th class="text-center">Nome</th>
									<th class="text-center">Listar respostas</th>
									<th class="text-center">Desempenho na disciplina</th>
									<th class="text-center">Correção</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${usuarioList }" var="aluno" varStatus="status">
									<tr>
										<td>${aluno.nome}</td>
										<td class="text-center">
											<form action="<c:url value="/resposta/listarRespostas"/>" method="Get">
												<input type="hidden" name="idDoAluno" value="${aluno.id }"/>
												<input type="hidden" name="idDaProva" value="${prova.id }"/>
												<button type="submit" class="btn btn-primary btn-circle">
													<i class="fa fa-list"></i>
												</button>
											</form>
										</td>
										<td class="text-center">
											<form action="<c:url value="/nota/listarNotasPorAluno"/>" method="get" class="form-inline">
												<input type="hidden" name="idDoAluno" value="${aluno.id}">
												<input type="hidden" name="idDaProva" value="${prova.id}">
												<button type="submit" class="btn btn-success btn-circle" title="Vizualizar Resultados">
													<i class="fa fa-bar-chart"></i>
												</button>
											</form>
										</td>
										<td class="text-center">
											<form action="<c:url value="/questao/corrigirQuestoes"/>" method="Get">
												<input type="hidden" name="idDoAluno" value="${aluno.id }"/>
												<input type="hidden" name="idDaProva" value="${prova.id }"/>
												<button type="submit" class="btn btn-primary btn-circle">
													<i class="fa fa-check"></i>
												</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<form action="<c:url value="/questao/finalizar"/>" method="post" id="voltar">
  								<input type="hidden" name="idDaDisciplina" value="${prova.disciplina.id }" />
  									<div class="form-group">
								</div>
  								</form>
  						<form action="<c:url value="/nota/calcularNotas"/>" method="post" id="calcular">
							<input type="hidden" name="idDaProva" value="${prova.id}">
						</form>		        	
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
    
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    
    
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#tabelaAlunos').DataTable({
                responsive: true
        });
    });
    </script>
	
	
</body>

</html>