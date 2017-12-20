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
  <script src="../bower_components/jquery/dist/jquery.min.js"></script>
  <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
      background-color: #2196f3;
      color: white;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 640px}
    
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
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }
  </style>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${linkTo[RedeEscolarController].telaPrincipal}" style="color:white; font-size:20px"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <b>IsCool</b></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:white; font-size:16px"> <b>${gestorDeRedeLogado.nome}</b>
          <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
        <ul class="dropdown-menu dropdown-user">
          <li><a href="${linkTo[GestorDeRedeController].perfil()}">
              <i class="fa fa-user fa-fw"></i> <fmt:message key="profile" />
          </a></li>
          <li class="divider"></li>
          <li><a href="${linkTo[GestorDeRedeController].logout()}"> 
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
      <button class="btn btn-default btn-block btn-default" type="submit"  form="formNovaEscola"><i class="fa fa-file-text-o" aria-hidden="true"></i> Nova Escola</button>
      <button class="btn btn-default btn-block btn-default" type="submit" form="formMinhasEscolas"><i class="fa fa-list-alt" aria-hidden="true"></i> Minhas Escolas</button>
    </div>
    <div class="col-sm-8 text-left">
    <h3 class="page-header text-primary"><i class="fa fa-user"></i> Meu Perfil</h3> 
      <div class="col-md-12">
        <div class="media">
          <div class="media-left">
            <a href="https://public-api.wordpress.com/oauth2/authorize?client_id=1854&response_type=code&blog_id=0&state=825cf9490f2631c384f54cc5369057e2b9ba0c95e7c2efd6c7c5f5a9df47eb7c&redirect_uri=https%3A%2F%2Fen.gravatar.com%2Fconnect%2F%3Faction%3Drequest_access_token">
                <img class="media-object img-rounded" src="<gravatar:image email="${gestorDeRedeLogado.email }" size="120"/>" alt="Gravatar" title="Alterar Imagem" alt="">
              </a>
          </div>
          <div class="media-body">
            <b>Email: </b>${gestorDeRedeLogado.email }<br>
            <b>Rede: </b>${gestorDeRedeLogado.rede.nome }<br>
            <b>Razão Social: </b>${gestorDeRedeLogado.rede.dadosCadastrais.razaoSocial }<br>
            <b>Cnpj: </b>${gestorDeRedeLogado.rede.dadosCadastrais.cnpj }<br>
            <c:if test="${gestorDeRedeLogado.rede.dadosCadastrais.telefone != null}">
              <b>Telefone: </b>${gestorDeRedeLogado.rede.dadosCadastrais.telefone }<br>
            </c:if>
            <c:if test="${gestorDeRedeLogado.rede.endereco.logradouro != null}">
              <b>${gestorDeRedeLogado.rede.endereco.logradouro}:</b> ${gestorDeRedeLogado.rede.endereco.endereco}
            </c:if>
            <c:if test="${gestorDeRedeLogado.rede.endereco.numero != null}">
              <b>Número:</b> ${gestorDeRedeLogado.rede.endereco.numero}<br>
            </c:if>
            <c:if test="${gestorDeRedeLogado.rede.endereco.bairro != null}">
              <b>Bairro:</b> ${gestorDeRedeLogado.rede.endereco.bairro}
            </c:if>
            <c:if test="${gestorDeRedeLogado.rede.endereco.cidade != null}">
              <b>Cidade:</b> ${gestorDeRedeLogado.rede.endereco.cidade}
            </c:if>
            <c:if test="${gestorDeRedeLogado.rede.endereco.estado != null}">
              <b>Estado:</b> ${gestorDeRedeLogado.rede.endereco.estado}
            </c:if>
          </div>
        </div>  
        <hr>  
      </div>
      
      <div class="col-md-12">
        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
          <li class="active">
            <a href="#red" data-toggle="tab">Dados da rede</a>
          </li>
          <li>
            <a href="#orange" data-toggle="tab">Dados do usuário</a>
          </li>
          <li>
            <a href="#yellow" data-toggle="tab">Endereço da rede</a>
          </li>
        </ul>

        <div id="my-tab-content" class="tab-content">
          <div class="tab-pane active" id="red">
                <h5><b>Alterar dados</b></h5>
                <form action="<c:url value="/gestorDeRede/atualizarRede"/>" method="post" class="form-horizontal">
                  
                  <div class="form-group">
                  
                    <label for="nomeDaRede" class="col-sm-2 control-label">Nome da rede </label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="nomeDaRede" name="rede.nome" value="${rede.nome }"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="razaoSocial" class="col-sm-2 control-label">Razão social </label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="razaoSocial" name="rede.dadosCadastrais.razaoSocial" value="${rede.dadosCadastrais.razaoSocial }"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="cnpj" class="col-sm-2 control-label">CNPJ </label>
                    <div class="col-sm-4">
                      <input type="text" class="form-control" id="cnpj" name="rede.dadosCadastrais.cnpj" value="${rede.dadosCadastrais.cnpj }"/>
                    </div>
                    
                    <label for="telefone" class="col-sm-2 control-label">Telefone </label>
                    <div class="col-sm-4">
                      <input type="text" class="form-control" id="telefone" name="rede.dadosCadastrais.telefone" value="${rede.dadosCadastrais.telefone }"/>
                    </div>
                  </div>
                  
                  <input type="hidden" name="idDaRede" value="${rede.id }" />
                  
                  <div class="pull-right">
                    <button type="submit" class="btn btn-success">Salvar</button>
                  </div>
                </form>
              </div>
          
          <div class="tab-pane" id="orange">
            <h5><b>Alterar senha</b></h5>
            <form action="<c:url value="/gestorDeRede/atualizarSenha"/>" method="post" class="form-horizontal">
            
              <div class="form-group">
              
                <label for="senhaAtual" class="col-sm-2 control-label">Senha Atual </label>
                <div class="col-sm-2">
                  <input type="password" class="form-control" id="senhaAtual" name="senhaAtual" />
                </div>
                
                <label for="novaSenha" class="col-sm-2 control-label">Nova Senha </label>
                <div class="col-sm-2">
                  <input type="password" class="form-control" id="novaSenha" name="novaSenha" />
                </div>
                
                <label for="confirmaSenha" class="col-sm-2 control-label">Confirma Senha </label>
                <div class="col-sm-2">
                  <input type="password" class="form-control" id="confirmaSenha" name="confirmaSenha" />
                </div>
              </div>
              
              <div class="pull-right">
                <button type="submit" class="btn btn-success">Salvar</button>
              </div>
            </form>
            
            <h5><b>Alterar Nome</b></h5>
              <form class="form-horizontal" action="<c:url value="/gestorDeRede/atualizarNome"/>" method="post">
                <div class="form-group">
                  <label for="nome" class="col-sm-2 control-label">Nome</label>
                  <div class="col-sm-10">
                    <input type="text" class="form-control" id="nome" name="nome" value="${gestorDeRedeLogado.nome }"/>
                  </div>
                </div>

                <div class="pull-right">
                  <button type="submit" class="btn btn-success">Salvar</button>
                </div>
              </form>
            </div>
          
          <div class="tab-pane" id="yellow">
                <h5><b>Alterar endereço</b></h5>
                <form action="<c:url value="/gestorDeRede/atualizarEndereco"/>" method="post" class="form-horizontal">
                
                  <div class="form-group">
                  
                    <label for="logradouro" class="col-sm-2 control-label">Logradouro</label>
                    <div class="col-sm-2">
                      <select class="form-control" name="rede.endereco.logradouro">
                          <option value="RUA" disabled></option>
                          <option value="RUA">Rua</option>
                          <option value="AVENIDA">Avenida</option>
                      </select>
                    </div>
                    
                    <label for="endereco" class="col-sm-1 control-label">Endereço</label>
                    <div class="col-sm-4">
                      <input type="text" class="form-control" id="endereco" name="rede.endereco.endereco" value="${rede.endereco.endereco }"/>
                    </div>
                    
                    <label for="numero" class="col-sm-1 control-label">Número</label>
                    <div class="col-sm-2">
                      <input type="number" class="form-control" id="numero" name="rede.endereco.numero" value="${rede.endereco.numero }"/>
                    </div>
                  </div>
                  
                  <div class="form-group">
                  
                    <label for="bairro" class="col-sm-1 control-label">Bairro</label>
                    <div class="col-sm-3">
                      <input type="text" class="form-control" id="bairro" name="rede.endereco.bairro" value="${rede.endereco.bairro }"/>
                    </div>
                    
                    <label for="cidade" class="col-sm-1 control-label">Cidade</label>
                    <div class="col-sm-3">
                      <input type="text" class="form-control" id="cidade" name="rede.endereco.cidade" value="${rede.endereco.cidade }"/>
                    </div>
                    
                    <label for="estado" class="col-sm-1 control-label">Estado</label>
                    <div class="col-sm-3">
                      <input type="text" class="form-control" id="estado" name="rede.endereco.estado" value="${rede.endereco.estado }"/>
                    </div>
                  </div>
                  
                  <input type="hidden" name="idDaRede" value="${rede.id }" />
                  
                  <div class="pull-right">
                    <button type="submit" class="btn btn-success">Salvar</button>
                  </div>
                </form>
              </div>
            </div>
      </div>
    </div>

    <div class="col-sm-2 sidenav">
      <button type="submit" form="formVoltar" class="btn btn-default btn-block">Voltar</button>
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>LabEaSII - IsCool 2016 Todos os direitos reservados</p>
</footer>

<form action="<c:url value="/escola/inserirEscola"/>" method="get" id="formNovaEscola">
</form>
<form action="<c:url value="/redeEscolar/listarEscolas"/>" method="get" id="formMinhasEscolas">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>
<form action="<c:url value="/redeEscolar/telaPrincipal"/>" method="get" id="formVoltar">
  <input type="hidden" name="idDaRede" value="${gestorDeRedeLogado.rede.id }" />
</form>

<script type="text/javascript">
  jQuery(document).ready(function($) {
    $('#tabs').tab();
  });
</script>
</body>
</html>