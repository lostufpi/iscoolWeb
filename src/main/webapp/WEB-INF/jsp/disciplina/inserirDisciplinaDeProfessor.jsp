<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-br">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><fmt:message key="iscool" /></title>

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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
                        	<fmt:message key="iscool" />
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
									value="${professorLogado.id }" />
									
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-th-list fa-fw"></i>
									Minhas Disciplinas
								</button>
							</form>
                        </li>
                        
                        <li>
                            <form action="<c:url value="/disciplina/inserirDisciplinaDeProfessor"/>" method="get">
									 
								<input type="hidden" class="form-control" id="rede" name="idDoProfessor"
									value="${professorLogado.id }" />
									
								<button type="submit" class="btn btn-primary btn-link">
									<i class="fa fa-plus fa-fw"></i>
									Adicionar Disciplina
								</button>
							</form>
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
                    <h3 class="page-header text-primary">
                    	Inserir Disciplina
                    </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            
            
            <div class="row">
           		<div class="col-lg-12">     
                
                	<div class="panel panel-primary">
                	
                		<div class="panel panel-heading">
                			Inserir Disciplina Manualmente
                		</div>
                		
                		<div class="panel panel-body">
                		
                			<form action="<c:url value="/disciplina/inserirDisciplinaDeProfessor"/>" method="post" class="form-horizontal" id="inserir">
                			
                				<div class="form-group">
                					<label for="nome" class="col-sm-2 control-label">Nome *</label>
                					
                					<div class="col-md-10">
                						<input type="text" class="col-sm-12 form-control" id="nome" name="disciplina.nome" required="true" />
                					</div>
                				</div>
                				
                				<div class="form-group" class="col-lg-12">
                					<label for="descricao" class="col-sm-2 control-label">Descricão *</label>
                					
                					<div class="col-md-10">
                						<input type="text" class="col-sm-9 form-control" id="descricao" name="disciplina.descricao" required="true" />
                					</div>
                				</div>
                			</form>
                			
                			<div class="pull-right">
               					<button type="submit" class="btn btn-success" form="inserir">Cadastrar</button>
               					<button type="reset" class="btn btn-default" form="inserir">Limpar</button>
                			</div>
                		</div>
                	</div>
                	
                	<div class="panel panel-primary">
                		<div class="panel panel-heading">Importar Disciplinas</div>
                		
                		<div class="panel panel-body">
                		
                			<form action="<c:url value="/disciplina/importarDisciplinas"/>" method="post" enctype="multipart/form-data" id="importar">
                				<label>Importe todas as suas disciplinas de uma só vez</label>
                				
                				<input type="file" name="uploadedFile" required="true"/>		                				
                			</form>
                			
                			<div class="pull-right">
               					<button type="submit" class="btn btn-success" form="importar">Importar Arquivo <i class="fa fa-file-o"></i></button>
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