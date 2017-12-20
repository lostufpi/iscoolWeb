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
    
    <style>
		#scroll {
  			width: 100%;
  			height:450px;
  			overflow:auto;
		}
	</style>

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
                        <b>${professorLogado.nome}</b> <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
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
									value="${professorLogado.id }" />
									
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
                    <h4 class="page-header text-primary"> Atualizar Questão </h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <c:if test="${mensagem != null }">
                			<div class="alert alert-warning">
                		
                				${mensagem }
                		
                			</div>
                		</c:if>
            
            <div class="row">
                <div class="col-lg-12">
                	<div class="panel panel-info">
                		<div class="panel-heading">
                            Informações
                        </div>
                        <div class="panel-body">
                        	Modifique as informações que deseja na questão selecionada. <br>                        	
                        </div>
                	</div>
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            Atualizar Questão ${questao.id }
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body" id="scroll">
                        	<div id="sample">
  								<script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script> <script type="text/javascript">
									//<![CDATA[
        							bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  								//]]>
  								</script>
  								<form action="<c:url value="/questao/atualizarQuestao"/>" method="post" id="atualizarQuestao">
    							<h6>
    								Descrição da Questão
  								</h6>
  								<textarea name="questao.descricao" style="width: 100%; height:150px;">
  									${questao.descricao }
  								</textarea><br />
  								
  								<h6>
    								Alternativa 1
  								</h6>
  								<textarea name="questao.primeiraAlternativa" style="width: 100%; height:100px;">
  									${questao.primeiraAlternativa }
  								</textarea><br />
                                 
                                <h6>
    								Alternativa 2
  								</h6>
  								<textarea name="questao.segundaAlternativa" style="width: 100%; height:100px;">
  									${questao.segundaAlternativa }
  								</textarea><br />
  								
  								<h6>
    								Alternativa 3
  								</h6>
  								<textarea name="questao.terceiraAlternativa" style="width: 100%; height:100px;">
  									${questao.terceiraAlternativa }
  								</textarea><br />
  								
  								<h6>
    								Alternativa 4
  								</h6>
  								<textarea name="questao.quartaAlternativa" style="width: 100%; height:100px;">
  									${questao.quartaAlternativa }
  								</textarea><br />
  								
  								<h6>
    								Alternativa 5
  								</h6>
  								<textarea name="questao.quintaAlternativa" style="width: 100%; height:100px;">
  									${questao.quintaAlternativa }
  								</textarea><br />
                                
                                <h4>
                                <b> Gabarito Atual: ${questao.resposta }</b> <br>
                                </h4>
                                
                                <input type="hidden" name="idDaQuestao" value="${questao.id }" />
                                <h5>
                                	
    								Marque a resposta correta
  								</h5>       
                                <div class="col-sm-8">
										<input type="radio" id="resposta" name="questao.resposta"
											value="A" required="true"/>A 
										<input type="radio" id="resposta" name="questao.resposta"
											value="B" required="true"/>B
										<input type="radio" id="resposta" name="questao.resposta"
											value="C" required="true"/>C
										<input type="radio" id="resposta" name="questao.resposta"
											value="D" required="true"/>D
										<input type="radio" id="resposta" name="questao.resposta"
											value="E" required="true" />E   
								</div>
  								</form>
  								
  								<form action="<c:url value="/questao/finalizar"/>" method="post" id="finalizar">
  									<input type="hidden" name="idDaDisciplina" value="${prova.disciplina.id }" />
  								</form>
  								<div >
  									<button type="submit" class="btn btn-success" form="atualizarQuestao">Salvar</button>
  									<button type="submit" class="btn btn-danger" form="finalizar">Finalizar</button>
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