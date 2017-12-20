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
	
	<style>
		#scroll {
  			width: 100%;
  			height:450px;
  			overflow:auto;
		}
	</style>
	
    <title>
    	IsCool
    </title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<!-- DataTables CSS -->
    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

</head>

<body>

    <div>
        <nav class="navbar navbar-default navbar-static-top" style="margin-bottom: 0">
        
            <div class="navbar-header">
                <a class="navbar-brand" href="${linkTo[ProfessorController].telaPrincipal()}">
                	<strong>
                    	<span class="glyphicon glyphicon-thumbs-up"></span> 
                        IsCool
                    </strong>
                </a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
            
                <li class="dropdown">
                
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <b>
                        	${professorLogado.professor.nome}
                        </b> 
                        <i class="fa fa-user fa-fw"></i>  
                        <i class="fa fa-caret-down"></i>
                    </a>
                    
                    <ul class="dropdown-menu">
                    
                        <li>
                        	<a href="${linkTo[ProfessorController].perfil()}">
                        		<i class="fa fa-user fa-fw"></i> 
                        		<fmt:message key="profile" />
                        	</a>
                        </li>
                        
                        <li class="divider">
                        </li>
                        
                        <li>
                        	<a href="${linkTo[ProfessorController].logout()}">
                        		<i class="fa fa-sign-out fa-fw"></i> 
                        		<fmt:message key="logout" />
                        	</a>
                        </li>
                        
                    </ul>
                </li>
            </ul>


            <div class="navbar-default sidebar">
            
                <div class="sidebar-nav">
                
                    <ul class="nav" id="side-menu">
                    
                    	<li>
                            <form action="<c:url value="/disciplina/listarDisciplinasPorProfessor"/>" method="get">		 
								<input type="hidden" name="idDoProfessor" value="${professorLogado.professor.id }" />
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-th-list fa-fw"></i>
									Minhas Disciplinas
								</button>
							</form>
                        </li>
                                                  
                    </ul>
                    
                </div>
            </div>
        </nav>
        </div>

        <div id="page-wrapper">
        
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"> 
                    	${prova.nome} 
                    </h1>
                </div>
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                	<div class="panel panel-info">
                		<div class="panel-heading">
                            Informações
                        </div>
                        <div class="panel-body">
                        	Faça uma pré-vizualização das questões da prova selecionada, ou ainda, imprima a prova comlpeta. 
                        </div>
                	</div>
                	
                	<input type="button" onclick="cont();" value="Imprimir Prova" class="btn btn-success"><br><br>
                	
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            Prova
                        </div>
                       
                        <div class="panel-body" id="scroll">
                        	${prova.cabecalho }<br>
                        	<center>
                        		${prova.nome }
                        	</center>
                        	<hr>
                        	<br>
                        	<br>	
							
							<c:forEach items="${questaoList }" var="questao" varStatus="status">		
								${questao.descricao }<br>
									
								a) ${questao.primeiraAlternativa }<br>
									
								b) ${questao.segundaAlternativa }<br>
									
								c) ${questao.terceiraAlternativa }<br>
									
								d) ${questao.quartaAlternativa }<br>
									
								e) ${questao.quintaAlternativa }<br><br><br>
								
							</c:forEach>
							
							<form action="<c:url value="/questao/finalizar"/>" method="post">
  								<input type="hidden" name="idDaDisciplina" value="${prova.disciplina.id }" />
  									<div class="form-group">
  									
									<div class="col-sm-offset-1 col-sm-2">
										<button type="submit" class="btn btn-success">Voltar</button>
									</div>
								</div>
  								</form>                               
                        </div>
                    </div>
                </div>
        </div>
    </div>
    
    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
	<script>
		function cont(){
		   var conteudo = document.getElementById('scroll').innerHTML;
		   tela_impressao = window.open('about:blank');
		   tela_impressao.document.write(conteudo);
		   tela_impressao.window.print();
		   tela_impressao.window.close();
		}
	</script>
</body>

</html>