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

    <title>IsCool - Inserir Aluno</title>

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
                <a class="navbar-brand" href="${linkTo[EscolaController].telaPrincipal()}">
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
                        <strong>${gestorEscolarLogado.nome }</strong><i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> Perfil</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Configurações</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="${linkTo[GestorEscolarController].logout()}"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
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
                            <a href="#">
                            	<i class="fa fa-file-text-o fa-fw"></i> 
                            	Cadastrar
                            	<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <form action="<c:url value="/aluno/inserirAluno"/>" method="get">
                                    	<input type="hidden" class="form-control" id="rede" name="idDaTurma"
									value="${turma.id }" />
									<button type="submit" class="btn btn-primary btn-link">
										<i class="fa fa-plus-square fa-fw"></i>
    									Aluno	
									</button>
                                    </form>
                                </li>
                                <li>
                                   <form action="<c:url value="/disciplina/inserirDisciplina"/>" method="get">
                                    	<input type="hidden" class="form-control" id="rede" name="idDaTurma"
									value="${turma.id }" />
									<button type="submit" class="btn btn-primary btn-link">
										<i class="fa fa-plus-square fa-fw"></i>
    									Disciplina	
									</button>
                                    </form>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        
                        <li>
                            <a href="#">
                            	<i class="fa fa-list"></i> 
                            	Listar
                            	<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                	<form action="<c:url value="/aluno/listarAlunosPorTurma"/>" method="get">
                        				<input type="hidden" name="idDaTurma" value="${turma.id }" />
                        				<button type="submit" class="btn btn-primary btn-link">
                        					<i class="fa fa-th-list fa-fw"></i> 
                        					Participantes
                        			</form>
                                </li>
                                <li>
                                   <form action="<c:url value="/disciplina/listarDisciplinasPorTurma"/>" method="get">
                                    	<input type="hidden" class="form-control" id="rede" name="idDaTurma"
									value="${turma.id }" />
									<button type="submit" class="btn btn-primary btn-link">
										<i class="fa fa-list"></i>
    									Disciplinas	
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
                    <h4 class="page-header text-primary">Atualizar Disciplina</h4>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <c:if test="${errors != null }">
                <div class="alert alert-warning">		
                	<c:forEach var="error" items="${errors}">
    					<b>${error.message}</b><br />
					</c:forEach>
                </div>
			</c:if>
			
			<c:if test="${mensagem != null }">
                <div class="alert alert-info">		
                	<b>${mensagem}</b><br/>
                </div>
			</c:if>
                
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            Atualização
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                             <form class="form-horizontal" role="form"
                                action="<c:url value="/disciplina/atualizarDisciplina"/>" method="post"
                                class="form-inline">
                                <div class="form-group">
                                    <label for="nome" class="col-sm-2 control-label"> Nome
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nome"
                                            name="disciplina.nome" value="${disciplina.nome }"/>
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label for="cnpj" class="col-sm-2 control-label"> Descrição
                                    </label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="cnpj"
                                            name="disciplina.descricao" value="${disciplina.descricao }"/>
                                    </div>
                                </div>
								
								<input type="hidden" name="idDaDisciplina" value="${disciplina.id}" />
								
                                <div class="form-group">
                                    <div class="col-sm-offset-1 col-sm-2">
                                        <button type="submit" class="btn btn-success">Cadastrar</button>
                                    </div>
                                </div>
                            </form>
                        <!-- /.panel-body -->
                        </div>
                    </div>   
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