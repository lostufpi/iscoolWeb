package br.ufpi.easii.iscool.controle;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.GestorDeRede;
import br.ufpi.easii.iscool.entidade.GestorEscolar;
import br.ufpi.easii.iscool.entidade.Professor;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.AlunoLogado;
import br.ufpi.easii.iscool.sessao.GestorDeRedeLogado;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;
import br.ufpi.easii.iscool.util.EmailUtil;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
public class UsuarioController {

	private final UsuarioDao usuarios;
	private final Result result;
	private final Validator validator;
	private final GestorDeRedeLogado gestorDeRedeLogado;
	private final GestorEscolarLogado gestorEscolarLogado;
	private final ProfessorLogado professorLogado;
	private final AlunoLogado alunoLogado;
	private final EmailUtil emailUtil;

	protected UsuarioController(){
		this(null, null, null, null, null, null, null, null);
	}

	@Inject
	public UsuarioController(UsuarioDao usuarios, Result result, Validator validator, GestorDeRedeLogado gestorDeRedeLogado, GestorEscolarLogado gestorEscolarLogado, ProfessorLogado professorLogado, AlunoLogado alunoLogado, EmailUtil emailUtil){
		this.usuarios = usuarios;
		this.result = result;
		this.validator = validator;
		this.gestorDeRedeLogado = gestorDeRedeLogado;
		this.gestorEscolarLogado = gestorEscolarLogado;
		this.professorLogado = professorLogado;
		this.alunoLogado = alunoLogado;
		this.emailUtil = emailUtil;
	}

	@Get("login")
	public void login(){
	}
	
	@Post
	public void recuperarSenha(String email){
		Usuario usuario = usuarios.pesquisarUsuarioPorEmail(email);
		System.out.println("Email: " + usuario.getNome());
		result.include("usuario", usuario);
		result.redirectTo(UsuarioController.class).recuperarSenha();
	}
	
	@Post
	public void confirmar(String email){
		Usuario usuario = usuarios.pesquisarUsuarioPorEmail(email);
		String senha = GeradorDeSenha.geradorDeSenha();
		String senhaCripitografada = GeradorDeSenha.generateHash(senha);
		usuario.setSenha(senhaCripitografada);
		
		usuarios.atualizarUsuario(usuario);
		
		String mensagem = "Caro(a) " + usuario.getNome() + " , sua senha foi alterada com sucesso! <br>"
				+ " <br><b>Seu login:<b> " + usuario.getEmail() +
				"<br><b>Sua nova senha:<b> " + senha
				+"<br>Para alterar para uma senha de sua preferência acesse o perfil da sua conta.";

		try {
			emailUtil.send("[IsCool]", mensagem, usuario.getEmail());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(RedeEscolarController.class).iscool();
	}

	@Post
	public void login(Usuario usuario){
		final Usuario carregado = usuarios.pesquisarUsuarioPorEmailESenha(usuario.getEmail(), GeradorDeSenha.generateHash(usuario.getSenha()));
		validator.check(carregado != null, new SimpleMessage("login", "Login e/ou senha inválidos"));		
		validator.onErrorRedirectTo(this).login();
		if(carregado instanceof GestorDeRede){
			GestorDeRede gestor = (GestorDeRede) carregado;
			gestorDeRedeLogado.login(gestor);
			if(gestorDeRedeLogado.isLogado())
				result.redirectTo(RedeEscolarController.class).telaPrincipal();
			else
				System.out.println("O usuário não está logado!");
		}else if(carregado instanceof GestorEscolar){
			GestorEscolar gestor = (GestorEscolar) carregado;
			gestorEscolarLogado.login(gestor);
			if(gestorEscolarLogado.isLogado())
				result.redirectTo(EscolaController.class).telaPrincipal();
		}else if(carregado instanceof Professor){
			Professor professor = (Professor) carregado;
			professorLogado.login(professor);			
			if(professorLogado.isLogado())
				result.redirectTo(ProfessorController.class).telaPrincipal();
		}else if(carregado instanceof Aluno){
			Aluno aluno = (Aluno) carregado;
			alunoLogado.login(aluno);
			if(alunoLogado.isLogado())
				result.redirectTo(AlunoController.class).telaPrincipal();
		}
	}
	
	@Get("recuperarSenha")
	public void recuperarSenha(){
		
	}

	@Post
	public void atualizarUsuario(long id, String senhaAtual, String novaSenha, String confirmaSenha){
		Usuario usuario = usuarios.pesquisarUsuarioPorId(id);
		if(usuario.getSenha().equals(senhaAtual)){
			if(novaSenha.equals(confirmaSenha)){
				usuario.setSenha(novaSenha);
				usuarios.atualizarUsuario(usuario);
			}
			else
				System.out.println("As senhas não coincidem!");
		}else
			System.out.println("Senha atual incorreta!");
	}
}