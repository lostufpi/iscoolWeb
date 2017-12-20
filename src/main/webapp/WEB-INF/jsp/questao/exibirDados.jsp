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
                    <h3 class="page-header text-primary"> Estatísticas da questão </h3>
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
                        	<b>Numero de alunos que responderam a questão: </b> ${numeroDeRespostas } <br>
                        	<b>Numero de alunos que acertaram a questão: </b> ${numeroDeAcertos }<br>
                        	<b>Numero de alunos que erraram a questão: </b> ${numeroDeErros } <br>
                        	<b>Porcentagem de aluno que acertaram a questão: </b> ${porcentagemDeAcertos }%<br>
                        	<b>Resposta Correta: </b> ${questao.resposta } <br>                        	
                        </div>
                	</div>
                	
                	<div class="panel-group">
                	<c:choose>
                		<c:when test="${questao.resposta == 'A' }">
                			<div class="panel panel-primary">
                      		<div class="panel-heading">
                        		<h4 class="panel-title">
					        		<a data-toggle="collapse" href="#collapse1">Alunos que marcaram a alternativa A - Alternativa correta</a>
					      		</h4>
                        	</div>
	                        <div id="collapse1" class="panel-collapse collapse">
		                        <div class="panel-body">  
	                        		<c:forEach items="${respostasA}" var="resposta" varStatus="count">
	                        			<ul>
	                        				<li>
	                        					${resposta.aluno.nome } <br>
	                        				</li>
	                        			</ul>
	                        		</c:forEach>
	                        	</div>
	                       	</div>
                    		</div>
                		</c:when>
                		<c:otherwise>
                			<div class="panel panel-danger">
                      		<div class="panel-heading">
                        		<h4 class="panel-title">
					        		<a data-toggle="collapse" href="#collapse1">Alunos que marcaram a alternativa A</a>
					      		</h4>
                        	</div>
	                        <div id="collapse1" class="panel-collapse collapse">
		                        <div class="panel-body">  
	                        		<c:forEach items="${respostasA}" var="resposta" varStatus="count">
	                        			<ul>
	                        				<li>
	                        					${resposta.aluno.nome } <br>
	                        				</li>
	                        			</ul>
	                        		</c:forEach>
	                        	</div>
	                       	</div>
                    		</div>
                		</c:otherwise>
                	</c:choose>	                	
						
					<c:choose>
						<c:when test="${questao.resposta == 'B' }">
							<div class="panel panel-primary">
                      			<div class="panel-heading">
                        		<h4 class="panel-title">
					        		<a data-toggle="collapse" href="#collapse2">Alunos que marcaram a alternativa B - Alternativa correta</a>
					      		</h4>
                        	</div>
                        	<div id="collapse2" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasB}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:when>
						<c:otherwise>
							<div class="panel panel-danger">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse2">Alunos que marcaram a alternativa B</a>
					      	</h4>
                        </div>
                        <div id="collapse2" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasB}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${questao.resposta == 'C' }">
							<div class="panel panel-primary">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse3">Alunos que marcaram a alternativa C - Alternativa correta</a>
					      	</h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasC}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:when>
						<c:otherwise>
							<div class="panel panel-danger">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse3">Alunos que marcaram a alternativa C</a>
					      	</h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasC}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:otherwise>
					</c:choose>
                    
                    
					<c:choose>
						<c:when test="${questao.resposta == 'D' }">
							<div class="panel panel-primary">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse4">Alunos que marcaram a alternativa D - Alternativa correta</a>
					      	</h4>
                        </div>
                        <div id="collapse4" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasD}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:when>
						
						<c:otherwise>
							<div class="panel panel-danger">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse4">Alunos que marcaram a alternativa D</a>
					      	</h4>
                        </div>
                        <div id="collapse4" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasD}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
						</c:otherwise>
					</c:choose>
					
                    <c:choose>
                    	<c:when test="${questao.resposta == 'E' }">
                    		<div class="panel panel-primary">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse5">Alunos que marcaram a alternativa E - Alternativa correta</a>
					      	</h4>
                        </div>
                        <div id="collapse5" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasE}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
                    	</c:when>
                    	
                    	<c:otherwise>
                    		<div class="panel panel-danger">
                      	<div class="panel-heading">
                        	<h4 class="panel-title">
					        	<a data-toggle="collapse" href="#collapse5">Alunos que marcaram a alternativa E</a>
					      	</h4>
                        </div>
                        <div id="collapse5" class="panel-collapse collapse">
	                        <div class="panel-body">  
                        		<c:forEach items="${respostasE}" var="resposta" varStatus="count">
                        			<ul>
                        				<li>
                        					${resposta.aluno.nome } <br>
                        				</li>
                        			</ul>
                        		</c:forEach>
                        	</div>
                       	</div>
                    </div>
                    	</c:otherwise>
                    </c:choose>
                    
					  
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