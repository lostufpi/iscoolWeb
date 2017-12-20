package br.ufpi.easii.iscool.controle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.TurmaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.DisciplinaDeProfessor;
import br.ufpi.easii.iscool.entidade.Turma;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.AlunoLogado;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;
import br.ufpi.easii.iscool.util.EmailUtil;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
public class AlunoController {

	private UsuarioDao usuarios;
	private Result result;
	private Validator validator;
	private TurmaDao turmas;
	private EmailUtil emailUtil;
	private GestorEscolarLogado gestorEscolarLogado;
	private AlunoLogado alunoLogado;
	private ProfessorLogado professorLogado;
	private DisciplinaDao disciplinas;

	protected AlunoController(){
		this(null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public AlunoController(UsuarioDao usuarios, Result result, Validator validator, TurmaDao turmas, EmailUtil emailUtil, GestorEscolarLogado gestorEscolarLogado, AlunoLogado alunoLogado, ProfessorLogado professorLogado, DisciplinaDao disciplinas){
		this.usuarios = usuarios;
		this.result = result;
		this.validator = validator;
		this.turmas = turmas;
		this.emailUtil = emailUtil;
		this.gestorEscolarLogado = gestorEscolarLogado;
		this.alunoLogado = alunoLogado;
		this.professorLogado = professorLogado;
		this.disciplinas = disciplinas;
	}
	
	public Aluno ajustarPrivacidade(long idAluno){
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idAluno);
		return aluno;
	}
	
	@Get
	public void perfil(){
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(alunoLogado.getId());
		
		result.include("aluno", aluno);
	}

	@Get
	public void telaPrincipal(){
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
	}

	@Get
	public Turma inserirAluno(long idDaTurma){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);

		validator.check(turma != null, new SimpleMessage("erro", "Turma inexistente"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();

		validator.check(turma.getEscola().getGestorEscolar().getId() == gestorEscolarLogado.getId(), new SimpleMessage("erro", "Você não tem permissão para alterar essa turma"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();

		return turma;
	}
	
	@Get
	public DisciplinaDeProfessor inserirAlunoDisciplina(long idDaDisciplina){
		
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		DisciplinaDeProfessor disciplina = (DisciplinaDeProfessor) disciplinas.pesquisarDisciplinaPorId(idDaDisciplina);
		
		validator.check(disciplina != null, new SimpleMessage("erro", "Disciplina inexistente"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		
		validator.check(disciplina.getProfessor().getId() == professorLogado.getId(), new SimpleMessage("erro", "Você não tem permissão para alterar essa disciplina"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		
		validator.check(disciplina instanceof DisciplinaDeProfessor, new SimpleMessage("erro", "Você não tem permissão para alterar essa disciplina"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		
		return disciplina;
	}

	@Post
	public void inserirAluno(Aluno aluno, long idDaTurma){

		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);

		validator.check(usuarios.pesquisarUsuarioPorEmail(aluno) ==  false, new SimpleMessage("", ""));
		validator.onErrorUsePageOf(this).inserirAluno(idDaTurma);

		if(aluno.getEmail() != null && aluno.getNome() != null){
			String senha = "123456"; //GeradorDeSenha.geradorDeSenha();
			String hashSenha = GeradorDeSenha.generateHash(senha);

			aluno.setSenha(hashSenha);
			aluno.setTurma(turma);
			aluno.setAluno(true);
			aluno.setProfessor(false);

			String mensagem = "Caro " + aluno.getNome() + " , Bem-Vindo(a) ao IsCool. Você foi cadastrado(a) com sucesso! <br>"
					+ " <br><b>Seu login:<b> " + aluno.getEmail() +
					"<br><b>Sua senha:<b> " + senha
					+"<br>Para alterar sua senha acesse o perfil da sua conta.";

			try {
				emailUtil.send("[IsCool]", mensagem, aluno.getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			usuarios.inserirUsuario(aluno);
		
			result.include("mensagem", "Aluno cadastrado com sucesso!");
			result.redirectTo(TurmaController.class).acessarTurma(idDaTurma);
		}
	}
	
	@Post
	public void inserirAlunoDisciplina(Aluno aluno, long idDaDisciplina){
		
		validator.check(professorLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		DisciplinaDeProfessor disciplina = (DisciplinaDeProfessor) disciplinas.pesquisarDisciplinaPorId(idDaDisciplina);
		
		if(aluno.getEmail() != null && aluno.getNome() != null){

			System.out.println("Turma do aluno: " + disciplina.getNome());
			
			List<Aluno> alunos = new ArrayList<Aluno>();
			
			alunos.add(aluno);
			
			disciplina.setAlunos(alunos);

			String mensagem = "Caro " + aluno.getNome() + ". Você foi cadastrado na disciplina com sucesso! <br>"
					+ " <br>Seu login é: " + aluno.getEmail() +
					"<br>Sua senha é: "
					+"<br>Para alterar sua senha acesse o perfil da sua conta.";

			try {
				emailUtil.send("[IsCool]", mensagem, aluno.getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			usuarios.inserirUsuario(aluno);

			result.include("mensagem", "Aluno cadastrado com sucesso!");
			result.redirectTo(ProfessorController.class).telaPrincipal();
		}

	}

	@Post
	public void importarArquivoCSV(UploadedFile uploadedFile, long idDaTurma){

		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		if(uploadedFile != null){

			Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);
			String nomeDoArquivo = uploadedFile.getFileName();
			String extencao = null;

			for(int i = 0;i < nomeDoArquivo.length(); i++){
				if(nomeDoArquivo.charAt(i) == '.'){
					extencao = nomeDoArquivo.substring(i, nomeDoArquivo.length());
					System.out.println("Extenção do arquivo: " + extencao);
				}
			}

			if(extencao.equals(".csv")){
				InputStream inputStream = uploadedFile.getFile();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				ArrayList<Aluno> alunosCadastrados = new ArrayList<Aluno>();
				ArrayList<Aluno> alunosJaCadastrados = new ArrayList<Aluno>();

				String linha = null;

				try{
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					while((linha = bufferedReader.readLine()) != null){
						String informacao = null;

						int inicio = 0;
						int controle = 0;

						Aluno aluno = new Aluno();

						String senha = GeradorDeSenha.geradorDeSenha();

						for(int i = 0;i < linha.length(); i++){
							if(linha.charAt(i) == ';'){
								informacao = linha.substring(inicio, i);
								inicio = i + 1;
								if(controle == 0){
									System.out.println("Identificador na rede: " + informacao);
									aluno.setNome(informacao);
								}						
								controle = controle + 1;
							}
							if(linha.length()-1 == i){
								informacao = linha.substring(inicio, i + 1);
								System.out.println("Email do gestor: " + informacao);
								aluno.setEmail(informacao);
							}
						}

						aluno.setSenha(senha);
						aluno.setTurma(turma);
						aluno.setAluno(true);
						aluno.setProfessor(false);

						if(usuarios.pesquisarUsuarioPorEmail(aluno) == false){
							System.out.println("Entrou no if");
							usuarios.inserirUsuario(aluno);
							alunosCadastrados.add(aluno);
						}else{
							System.out.println("Entrou no else");
							alunosJaCadastrados.add(aluno);
						}
					}

					result.include("alunosCadastrados", alunosCadastrados);
					result.include("alunosJaCadastrados", alunosJaCadastrados);
					result.redirectTo(TurmaController.class).acessarTurma(idDaTurma);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}else{
				System.out.println("É necessário entrar com um arquivo do tipo .csv para fazer a importação!");
				validator.add(new SimpleMessage("mensagem", "É necessário entrar com um arquivo do tipo .csv para fazer a importação!"));
				validator.onErrorUsePageOf(AlunoController.class).inserirAluno(idDaTurma);
			}
		}else{
			System.out.println("É necessário entrar com o arquivo para fazer a importação!");
			validator.add(new SimpleMessage("mensagem", "É necessário entrar com o arquivo para fazer a importação!"));
			validator.onErrorUsePageOf(AlunoController.class).inserirAluno(idDaTurma);
		}
	}

	@Get
	public List<Usuario> listarAlunosPorTurma(long idDaTurma){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);

		validator.check(turma != null, new SimpleMessage("erro", "Turma inexistente!"));
		validator.onErrorRedirectTo(EscolaController.class).telaPrincipal();

		result.include("escola", turmas.pesquisarTurmaPorId(idDaTurma).getEscola());
		result.include("turma", turma);
		result.include("idDaTurma", idDaTurma);

		return usuarios.listarAlunosPorTurma(idDaTurma);
	}

	@Get
	public List<Usuario> listarAlunosPorDisciplina(long idDaTurma){
		result.include("escola", turmas.pesquisarTurmaPorId(idDaTurma).getEscola().getId());
		return usuarios.listarAlunosPorTurma(idDaTurma);
	}

	@Get
	public List<Disciplina> listarDisciplinaPorAluno(long idDoAluno){
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idDoAluno);
		return aluno.getTurma().getDisciplinas();
	}
	
	@Post
	public void atualizarSenha(String senhaAtual, String novaSenha, String confirmaSenha){
		
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		Usuario usuario = usuarios.pesquisarUsuarioPorId(alunoLogado.getId());

		validator.check(usuario.getSenha().equals(senhaAtual), new SimpleMessage("erro", "Senha atual incorreta!"));
		validator.onErrorUsePageOf(AlunoController.class).perfil();
		
		validator.check(novaSenha.length() >= 6 && novaSenha.length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 caracteres!"));
		validator.onErrorUsePageOf(AlunoController.class).perfil();
		
		validator.check(novaSenha.equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorUsePageOf(AlunoController.class).perfil();
		
		usuario.setSenha(novaSenha);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Senha alterada com sucesso!");
		result.redirectTo(AlunoController.class).perfil();
	}
	
	@Post
	public void atualizarNome(String nome){
		
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Usuario usuario = usuarios.pesquisarUsuarioPorId(alunoLogado.getId());
		
		validator.check(usuario != null, new SimpleMessage("erro", "Usuário não possui permissão para alterar essa campo!"));
		validator.onErrorUsePageOf(AlunoController.class).perfil();

		usuario.setNome(nome);

		Aluno aluno = (Aluno) usuario;

		alunoLogado.logout();
		alunoLogado.login(aluno);

		usuarios.atualizarUsuario(usuario);
		result.include("mensagem", "Nome do aluno atualizado com sucesso!");
		result.redirectTo(AlunoController.class).perfil();
	}
	
	@Post 
	public void atualizarAluno(Aluno aluno, long idDaRede){
		
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Aluno a = (Aluno) usuarios.pesquisarUsuarioPorId(alunoLogado.getId());
		
		a.setDadosPessoais(aluno.getDadosPessoais());
		
		alunoLogado.logout();
		alunoLogado.login(a);
		
		usuarios.atualizarUsuario(a);
	
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(AlunoController.class).perfil();
	}
	
	@Post 
	public void atualizarEndereco(Aluno aluno, long idDaRede){
		
		validator.check(alunoLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Aluno a = (Aluno) usuarios.pesquisarUsuarioPorId(alunoLogado.getId());
		
		a.setEndereco(aluno.getEndereco());
		
		alunoLogado.logout();
		alunoLogado.login(a);
		
		usuarios.atualizarUsuario(a);
	
		result.include("mensagem", "Dados atualizados com sucesso!");
		result.redirectTo(AlunoController.class).perfil();
	}
	

	@Delete("aluno/{id}")
	public void excluirAluno(long id){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(id);

		usuarios.excluirUsuario(aluno);

		result.redirectTo(AlunoController.class).listarAlunosPorTurma(aluno.getTurma().getId());
	}
	
	@Path("/aluno/logout")
	public void logout(){
		alunoLogado.logout();
		result.redirectTo(RedeEscolarController.class).iscool();
	}
}