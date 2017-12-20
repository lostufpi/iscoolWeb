package br.ufpi.easii.iscool.controle;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.EscolaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.GestorEscolar;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;

@Controller
public class GestorEscolarController {

	private final UsuarioDao usuarios;
	private final Result result;
	private final GestorEscolarLogado gestorEscolarLogado;
	private final Validator validator;
	private EscolaDao escolas;
	
	protected GestorEscolarController(){
		this(null, null, null, null, null);
	}

	@Inject
	public GestorEscolarController(UsuarioDao usuarios, Result result, GestorEscolarLogado gestorEscolarLogado, Validator validator, EscolaDao escolas){
		this.usuarios = usuarios;
		this.result = result;
		this.gestorEscolarLogado = gestorEscolarLogado;
		this.validator = validator;
		this.escolas = escolas;
	}
	
	@Get
	public void perfil(){
		result.include("escola", escolas.pesquisarEscolaPorId(gestorEscolarLogado.getEscola().getId()));
	}
	
	@Post
	public void atualizarSenha(String senhaAtual, String novaSenha, String confirmaSenha){

		Usuario usuario = usuarios.pesquisarUsuarioPorId(gestorEscolarLogado.getId());

		validator.check(usuario.getSenha().equals(senhaAtual), new SimpleMessage("erro", "Senha atual incorreta!"));
		validator.onErrorUsePageOf(GestorEscolarController.class).perfil();
		
		validator.check(novaSenha.length() >= 6 && novaSenha.length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 caracteres!"));
		validator.onErrorUsePageOf(GestorEscolarController.class).perfil();
		
		validator.check(novaSenha.equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorUsePageOf(GestorEscolarController.class).perfil();
		
		usuario.setSenha(novaSenha);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Senha alterada com sucesso!");
		result.redirectTo(GestorEscolarController.class).perfil();
	}
	
	@Post
	public void atualizarNome(String nome){
		Usuario usuario = usuarios.pesquisarUsuarioPorId(gestorEscolarLogado.getId());
		
		validator.check(usuario != null, new SimpleMessage("erro", "Usuário não possui permissão para alterar essa campo!"));
		validator.onErrorUsePageOf(GestorEscolarController.class).perfil();

		usuario.setNome(nome);

		GestorEscolar gestor = (GestorEscolar) usuario;

		gestorEscolarLogado.logout();
		gestorEscolarLogado.login(gestor);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Nome do gestor atualizado com sucesso!");
		result.redirectTo(GestorEscolarController.class).perfil();
	}
	
	public void atualizarEscola(Escola escola, long idDaEscola){
		
		Escola escolaAtualizar = escolas.pesquisarEscolaPorId(idDaEscola);
		
		validator.check(gestorEscolarLogado.getId() == escolaAtualizar.getGestorEscolar().getId(), new SimpleMessage("erro", "Você não possui permissão para alterar esse recurso!"));
		validator.onErrorRedirectTo(GestorEscolarController.class).perfil();
		
		validator.check(escola.getDadosCadastrais().getCnpj() != null && escola.getDadosCadastrais().getRazaoSocial() != null && escola.getNome() != null, new SimpleMessage("erro", "CNPJ, nome e razão social não podem ser nulos!"));
		validator.onErrorRedirectTo(GestorEscolarController.class).perfil();
		
		escolaAtualizar.setDadosCadastrais(escola.getDadosCadastrais());
		
		escolas.atualizarEscola(escolaAtualizar);
		
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(GestorEscolarController.class).perfil();
	}
	
	@Post 
	public void atualizarEndereco(Escola escola, long idDaEscola){
		
		Escola escolaAtualizar = escolas.pesquisarEscolaPorId(idDaEscola);
		
		escolaAtualizar.setEndereco(escola.getEndereco());
		
		escolas.atualizarEscola(escolaAtualizar);
	
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(GestorEscolarController.class).perfil();
	}
	
	@Path("/gestorEscolar/logout")
	public void logout(){
		gestorEscolarLogado.logout();
		result.redirectTo(RedeEscolarController.class).iscool();
	}
}