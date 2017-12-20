package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.CursoDao;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Curso;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Professor;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;
import br.ufpi.easii.iscool.util.EmailUtil;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
public class ProfessorController {

	private UsuarioDao usuarios;
	private Result result;
	private Validator validator;
	private EmailUtil emailUtil;
	private DisciplinaDao disciplinas;
	private ProfessorLogado professorLogado;
	private GestorEscolarLogado gestorEscolarLogado;
	private CursoDao cursos;

	protected ProfessorController(){
		this(null, null, null, null, null, null, null, null);
	}

	@Inject
	public ProfessorController(UsuarioDao usuarios, Result result, Validator validator, EmailUtil emailUtil, DisciplinaDao disciplinas, ProfessorLogado professorLogado, GestorEscolarLogado gestorEscolarLogado, CursoDao cursos){
		this.usuarios = usuarios;
		this.result = result;
		this.validator = validator;
		this.emailUtil = emailUtil;
		this.disciplinas = disciplinas;
		this.professorLogado = professorLogado;
		this.gestorEscolarLogado = gestorEscolarLogado;
		this.cursos = cursos;
	}

	//Methods Get
	@Get("/criarContaProfessor")
	public void criarConta(){
	}
	
	@Get
	public void inserirProfessor(){
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
	}
	
	@Get
	public void perfil(){
		
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		List<Curso> listaCursos = cursos.pesquisarCursoPorProfessor(professorLogado.getId());
		
		result.include("cursos", listaCursos);
		result.include("professor", usuarios.pesquisarUsuarioPorId(professorLogado.getId()));
	}
	
	@Get
	public List<Disciplina> telaPrincipal(){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		List<Disciplina> disciplinasIndividuais =  new ArrayList<Disciplina>();
		for(Disciplina disciplina : disciplinas.listarDisciplinasPorProfessor(professorLogado.getId())){
			if(disciplina.getTurma() == null)
				disciplinasIndividuais.add(disciplina);
		}
		return disciplinasIndividuais;
	}
	
	//Methods Post
	
	@Post
	public void atualizarUsuario(Professor professor){
		
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		professorLogado.logout();
		professorLogado.login(professor);
		
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
	
	@Post
	public void criarConta(Professor professor, String confirmarSenha){
		if(usuarios.pesquisarUsuarioPorEmail(professor) == false){
			professor.setSenha(GeradorDeSenha.generateHash(professor.getSenha()));
			professor.setAluno(false);
			professor.setProfessor(true);
			usuarios.inserirUsuario(professor);
			String mensagem = "Caro professor, <br>" + professor.getNome() + " você foi cadastrado com sucesso! <br>"
					+ "Bem-Vindo ao IsCool. <br>Seu login é: " + professor.getEmail();
			try {
				emailUtil.send("[IsCool] - Cadastro Realizado", mensagem, professor.getEmail());
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			result.include("mensagem", "Conta criada com sucesso!");
			result.redirectTo(RedeEscolarController.class).iscool();
		}	
	}

	@Post
	public void inserirProfessor(Professor professor, long idDaTurma){
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		if(usuarios.pesquisarUsuarioPorEmail(professor)){
			validator.add(new SimpleMessage("mensagem", "Usuário já existente"));
			validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		}else{
			if(professor.getEmail() != null && professor.getNome() != null){
				String senha = GeradorDeSenha.geradorDeSenha();
				String hashSenha = GeradorDeSenha.generateHash(senha);
				professor.setSenha(hashSenha);
				professor.setAluno(false);
				professor.setProfessor(true);
				String mensagem = "Caro professor, <br>" + professor.getNome() + " você foi cadastrado com sucesso! <br>"
						+ "Bem-Vindo ao IsCool. <br>Seu login é: " + professor.getEmail() +
						"Sua senha é: " + senha;
				try {
					emailUtil.send("[IsCool] - Cadastro Realizado", mensagem, professor.getEmail());
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				usuarios.inserirUsuario(professor);
				result.include("mensagem", "Professor cadastrado com sucesso!");
				result.redirectTo(DisciplinaController.class).listarDisciplinasPorTurma(idDaTurma);
			}
		}
	}
	
	@Post
	public void atualizarSenha(String senhaAtual, String novaSenha, String confirmaSenha){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		Usuario usuario = usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		validator.check(usuario.getSenha().equals(senhaAtual), new SimpleMessage("erro", "Senha atual incorreta!"));
		validator.onErrorUsePageOf(ProfessorController.class).perfil();
		validator.check(novaSenha.length() >= 6 && novaSenha.length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 caracteres!"));
		validator.onErrorUsePageOf(ProfessorController.class).perfil();
		validator.check(novaSenha.equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorUsePageOf(ProfessorController.class).perfil();
		usuario.setSenha(novaSenha);
		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Senha alterada com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
	
	@Post
	public void atualizarNome(String nome){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		Usuario usuario = usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		validator.check(usuario != null, new SimpleMessage("erro", "Usuário não possui permissão para alterar essa campo!"));
		validator.onErrorUsePageOf(ProfessorController.class).perfil();
		usuario.setNome(nome);
		Professor aluno = (Professor) usuario;
		professorLogado.logout();
		professorLogado.login(aluno);
		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Nome do aluno atualizado com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
	
	@Post 
	public void atualizarProfessor(Professor professor){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		Professor p = (Professor) usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		if(professor.getNivelDeFormacao() != null)
			p.setNivelDeFormacao(professor.getNivelDeFormacao());
		else if(professor.getCurriculo() != null)
			p.setCurriculo(professor.getCurriculo());
		else
			p.setDadosPessoais(professor.getDadosPessoais());
		professorLogado.logout();
		professorLogado.login(p);
		usuarios.atualizarUsuario(p);
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
	
	@Post 
	public void atualizarEndereco(Professor professor){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		Professor p = (Professor) usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		p.setEndereco(professor.getEndereco());
		professorLogado.logout();
		professorLogado.login(p);
		usuarios.atualizarUsuario(p);
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
	
	@Path("/professor/logout")
	public void logout(){
		professorLogado.logout();
		result.redirectTo(RedeEscolarController.class).iscool();
	}
	
	public void adicionarCurso(Curso curso){
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		Professor professor = (Professor) usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		validator.check(curso.getInstituicao() != null && curso.getNome() != null, new SimpleMessage("erro", "O curso deve ter nome e instituição!"));
		validator.onErrorRedirectTo(ProfessorController.class).perfil();
		curso.setProfessor(professor);
		cursos.inserirCurso(curso);
		result.include("mensagem", "Curso adicionado com sucesso!");
		result.redirectTo(ProfessorController.class).perfil();
	}
}