package br.ufpi.easii.iscool.controle;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.RedeEscolarDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.GestorDeRede;
import br.ufpi.easii.iscool.entidade.RedeEscolar;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.GestorDeRedeLogado;

/**
 * @author Alexandre
 *
 */

@Controller
public class GestorDeRedeController {

	private final UsuarioDao usuarios;
	private final Result result;
	private final GestorDeRedeLogado gestorDeRedeLogado;
	private final Validator validator;
	private RedeEscolarDao redes;

	protected GestorDeRedeController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public GestorDeRedeController(UsuarioDao usuarios, Result result, GestorDeRedeLogado gestorDeRedeLogado, Validator validator, RedeEscolarDao redes){
		this.usuarios = usuarios;
		this.result = result;
		this.gestorDeRedeLogado = gestorDeRedeLogado;
		this.validator = validator;
		this.redes = redes;
	}

	/**
	 * Acessa o perfil do gestor de rede logado no momento
	 */
	@Get
	public void perfil(){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		result.include("rede", redes.pesquisarRedeEscolarPorId(gestorDeRedeLogado.getRede().getId()));
	}

	@Post
	public void atualizarSenha(String senhaAtual, String novaSenha, String confirmaSenha){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		Usuario usuario = usuarios.pesquisarUsuarioPorId(gestorDeRedeLogado.getId());

		validator.check(usuario.getSenha().equals(senhaAtual), new SimpleMessage("erro", "Senha atual incorreta!"));
		validator.onErrorUsePageOf(GestorDeRedeController.class).perfil();
		
		validator.check(novaSenha.length() >= 6 && novaSenha.length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 caracteres!"));
		validator.onErrorUsePageOf(GestorDeRedeController.class).perfil();
		
		validator.check(novaSenha.equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorUsePageOf(GestorDeRedeController.class).perfil();
		
		usuario.setSenha(novaSenha);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Senha alterada com sucesso!");
		result.redirectTo(GestorDeRedeController.class).perfil();
	}

	@Post
	public void atualizarNome(String nome){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Usuario usuario = usuarios.pesquisarUsuarioPorId(gestorDeRedeLogado.getId());
		
		validator.check(usuario != null, new SimpleMessage("erro", "Usuário não possui permissão para alterar essa campo!"));
		validator.onErrorUsePageOf(GestorDeRedeController.class).perfil();

		usuario.setNome(nome);

		GestorDeRede gestor = (GestorDeRede) usuario;

		gestorDeRedeLogado.logout();
		gestorDeRedeLogado.login(gestor);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Nome do gestor atualizado com sucesso!");
		result.redirectTo(GestorDeRedeController.class).perfil();
	}
	
	@Post 
	public void atualizarRede(RedeEscolar rede, long idDaRede){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		RedeEscolar redeEscolar = redes.pesquisarRedeEscolarPorId(idDaRede);
		
		validator.check(gestorDeRedeLogado.getId() == redeEscolar.getGestorDeRede().getId(), new SimpleMessage("erro", "Você não possui permissão para alterar esse recurso!"));
		validator.onErrorRedirectTo(GestorDeRedeController.class).perfil();
		
		validator.check(rede.getDadosCadastrais().getCnpj() != null && rede.getDadosCadastrais().getRazaoSocial() != null &&
				rede.getNome() != null, new SimpleMessage("erro", "CNPJ, nome e razão social não podem ser nulos!"));
		validator.onErrorRedirectTo(GestorDeRedeController.class).perfil();
		
		redeEscolar.setDadosCadastrais(rede.getDadosCadastrais());
		
		redes.atualizarRedeEscolar(redeEscolar);
	
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(GestorDeRedeController.class).perfil();
	}
	
	@Post 
	public void atualizarEndereco(RedeEscolar rede, long idDaRede){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		RedeEscolar redeEscolar = redes.pesquisarRedeEscolarPorId(idDaRede);
		
		System.out.println("Rede: " + redeEscolar.getId());
		
		redeEscolar.setEndereco(rede.getEndereco());
		
		redes.atualizarRedeEscolar(redeEscolar);
	
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(GestorDeRedeController.class).perfil();
	}
	
	@Path("/gestorDeRede/logout")
	public void logout(){
		gestorDeRedeLogado.logout();
		result.redirectTo(RedeEscolarController.class).iscool();
	}
}