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

    <title>IsCool</title>

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
                        <b>${alunoLogado.aluno.nome}</b> <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li>
                        	<a href="${linkTo[ProfessorController].perfil()}">
                        		<i class="fa fa-user fa-fw"></i> 
                        		<fmt:message key="profile" />
                        	</a>
                        </li>
                        <li>
                        	<a href="#">
                        		<i class="fa fa-gear fa-fw"></i> 
                        		<fmt:message key="settings" />
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
									value="${alunoLogado.aluno.id }" />
									
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
                    <h4 class="page-header text-center"> Imprimir Resultados</h4>
                    <input type="button" onclick="cont();" value="Imprimir Resultados" class="btn btn-success"><br><br>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row" id="imprimir">
                <div class="col-lg-12">
                        	Rede Escolar: <b>${prova.disciplina.turma.escola.redeEscolar.nome }</b><br>
                        	Escola: <b>${prova.disciplina.turma.escola.nome }</b><br>
                        	Série/Curso: <b>${prova.disciplina.turma.serie }</b><br>
                        	Turma: <b>${prova.disciplina.turma.nome }</b><br>
                        	Turno: <b>${prova.disciplina.turma.turno }</b><br>
                        	Disciplina: <b>${prova.disciplina.nome }</b><br>
                        	Descrição da Disciplina: <b>${prova.disciplina.nome }</b><br>
                        	Prova: <b>${prova.nome }</b><br>
                        	
                        	<h4>
                        		<center> 
                        			Resultados da Prova ${prova.nome } 
                        		</center>
                        	</h4>
                        	
                        	Notas
                        </div>
                        	<table class="table table-striped table-bordered table-hover table-responsive">
								<thead>
									<tr>
										<th class="text-center" style="width: 30%">Aluno</th>
										<th class="text-center" style="width: 12%">Nº de questões</th>
										<th class="text-center" style="width: 10%">Nº de acertos</th>
										<th class="text-center" style="width: 30%">Porcentagem de acertos</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${notaList }" var="nota" varStatus="status">
										<tr>
											<td>
												<b>
													${nota.aluno.nome}
												</b>
											</td>
											<td class="text-center">
												<b>
													${numeroDeQuestoes }
												</b>
											</td>
											<td class="text-center">
												<b>
													${nota.nota }
												</b>
											</td>
											<td>
												<b>
													${nota.porcentagemDeAcerto } %
												</b> 
												em relação ao número de questões da prova
											</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<form action="<c:url value="/disciplina/acessarDisciplina"/>" method="get" id="voltar">
  								<input type="hidden" name="id" value="${prova.disciplina.id }" />
  									<div class="form-group">
  									
									<div class="col-sm-offset-1 col-sm-2">
										
									</div>
								</div>
  								</form>
                        <!-- /.panel-body -->
                    </div>
                    <div>
                    <center>
                    	<button type="submit" class="btn btn-success" form="voltar">Voltar</button>
                    </center>
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
        $('#tabelaNotas').DataTable({
                responsive: true
        });
    });
    </script>
	
	<script>
		function cont(){
		   var conteudo = document.getElementById('imprimir').innerHTML;
		   tela_impressao = window.open('about:blank');
		   tela_impressao.document.write(conteudo);
		   tela_impressao.window.print();
		   tela_impressao.window.close();
		}
	</script>
</body>

</html>