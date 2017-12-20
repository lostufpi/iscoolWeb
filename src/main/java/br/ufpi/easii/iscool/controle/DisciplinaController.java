package br.ufpi.easii.iscool.controle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.TurmaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Professor;
import br.ufpi.easii.iscool.entidade.Turma;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.sessao.AlunoLogado;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;

@Controller
@Named
public class DisciplinaController {

	private DisciplinaDao disciplinas;
	private ProvaDao provas;
	private UsuarioDao usuarios;
	private TurmaDao turmas;
	private Validator validator;
	private Result result;
	private ProfessorLogado professorLogado;
	private GestorEscolarLogado gestorEscolarLogado;
	private AlunoLogado alunoLogado;

	protected DisciplinaController(){
		this(null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public DisciplinaController(ProvaDao provas, DisciplinaDao disciplinas, TurmaDao turmas, Result result, Validator validator, UsuarioDao usuarios, ProfessorLogado professorLogado, GestorEscolarLogado gestorEscolarLogado, AlunoLogado alunoLogado){
		this.disciplinas = disciplinas;
		this.turmas = turmas;
		this.result = result;
		this.validator = validator;
		this.usuarios = usuarios;
		this.provas = provas;
		this.professorLogado = professorLogado;
		this.gestorEscolarLogado = gestorEscolarLogado;
		this.alunoLogado = alunoLogado;
	}
	
	@Get
	public Disciplina acessarDisciplina(long id){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		String alert = null;
		validator.check(disciplina != null && professorLogado.getProfessor().getId() == disciplina.getProfessor().getId(), new SimpleMessage("erro", "Você não faz parte desta disciplina!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		if(provas.listarProvasPorDisciplina(id).size() == 0)
			alert = "Ainda não há provas cadastrada para essa disciplina!";
		
		result.include("alert",alert);
		result.include("listaDeProvas", provas.listarProvasPorDisciplina(id));
		return disciplinas.pesquisarDisciplinaPorId(id);
	}
	
	@Get
	public Disciplina acessarDisciplinaPorAluno(long id){
		System.out.println("ID da disciplina: " + id);

		result.include("listaDeProvas", provas.listarProvasPorDisciplina(id));
		return disciplinas.pesquisarDisciplinaPorId(id);
	}

	
	@Get
	public Disciplina atribuirProfessor(long id){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		result.include("disciplina", disciplina);
		return disciplinas.pesquisarDisciplinaPorId(id);
	}
	
	@Get
	public Disciplina atualizarDisciplina(long idDaDisciplina){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		return disciplinas.pesquisarDisciplinaPorId(idDaDisciplina);
	}

	@Get
	public Turma inserirDisciplina(long idDaTurma){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma); 
		
		validator.check(turma != null, new SimpleMessage("erro", "Turma inexistente!"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		validator.check(turma.getEscola().getGestorEscolar().getId() == gestorEscolarLogado.getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esta turma!"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		return turma;
	}
	
	@Get
	public Professor inserirDisciplinaDeProfessor(long idDoProfessor){
		
		validator.check(professorLogado.getId() == idDoProfessor, new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		
		return (Professor) usuarios.pesquisarUsuarioPorId(idDoProfessor);
	}
	
	@Get
	public List<Disciplina> listarDisciplinaPorAluno(long idDoAluno){
		
		Usuario usuario = usuarios.pesquisarUsuarioPorId(idDoAluno);
		String alert = null;
		
		validator.check(usuario != null, new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(AlunoController.class).telaPrincipal();
		
		validator.check(alunoLogado.getId() == usuario.getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(AlunoController.class).telaPrincipal();
		
		Aluno aluno = (Aluno) usuario;
		List<Disciplina> disciplinasDoAluno = disciplinas.listarDisciplinasPorTurma(aluno.getTurma().getId());
		
		if(disciplinasDoAluno.size() == 0)
			alert = "Você ainda não possui disciplinas cadastradas!";
		
		result.include("alert", alert);
		return disciplinas.listarDisciplinasPorTurma(aluno.getTurma().getId());
	}
	
	@Get()
	public List<Disciplina> listarDisciplinasPorProfessor(long idDoProfessor){
		
		validator.check(professorLogado.getId() == idDoProfessor, new SimpleMessage("erro", "Você não possui permissão para acessar essa página!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		
		List<Disciplina> disciplinasEscolares = new ArrayList<Disciplina>();
		List<Disciplina> disciplinasIndividuais = new ArrayList<Disciplina>();
		String alert = null;
		
		for(Disciplina disciplina : disciplinas.listarDisciplinasPorProfessor(professorLogado.getId())){
			if(disciplina.getTurma() == null) 
				disciplinasIndividuais.add(disciplina);
			else 
				disciplinasEscolares.add(disciplina);
		}
		
		if(disciplinasEscolares.size() == 0)
			alert = "Você não possui nenhuma disciplina";
	
		result.include("alert", alert);
		result.include("disciplinasIndividuais", disciplinasIndividuais);
		
		return disciplinasEscolares;
	}
	
	@Get
	public List<Disciplina> listarDisciplinasPorTurma(long idDaTurma){
		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		result.include("escola", turmas.pesquisarTurmaPorId(idDaTurma).getEscola());
		result.include("idDaTurma", idDaTurma);
		result.include("turma", turma);
		return disciplinas.listarDisciplinasPorTurma(idDaTurma);
	}
	
	//Methods Post
	
	@Post
	public void atribuirProfessor(long id, String email){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		System.out.println("ID: " + id + "\nEmail: " + email);

		Professor professor = usuarios.pesquisarProfessorPorEmail(email);

		if(professor != null){
			Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);

			disciplina.setProfessor(professor);

			disciplinas.atualizarDisciplina(disciplina);

			result.redirectTo(DisciplinaController.class).listarDisciplinasPorTurma(disciplina.getTurma().getId());
		}else{
			result.redirectTo(ProfessorController.class).inserirProfessor();
		}
	}
	
	@Post
	public void atualizarDisciplina(Disciplina disciplina, long idDaDisciplina){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Disciplina d = disciplinas.pesquisarDisciplinaPorId(idDaDisciplina);

		if(disciplinas.pesquisarDisciplinaExistente(disciplina.getNome(), disciplina.getDescricao(), d.getTurma().getId()) == false){
			d.setNome(disciplina.getNome());
			d.setDescricao(disciplina.getDescricao());
			disciplinas.atualizarDisciplina(d);
			result.include("mensagem", "Disciplina atualizada com sucesso!");
			result.redirectTo(TurmaController.class).acessarTurma(d.getTurma().getId());
		}else{
			validator.add(new SimpleMessage("mensagem", "Disciplina com mesmos dados já cadastrada na turma!"));
			validator.onErrorUsePageOf(DisciplinaController.class).atualizarDisciplina(idDaDisciplina);
		}
	}
	
	@Post
	public void importarArquivoCSV(UploadedFile uploadedFile, long idDaTurma){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		if(uploadedFile != null){

			Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);
			String nomeDoArquivo = uploadedFile.getFileName();
			String extensao = null;

			for(int i = 0;i < nomeDoArquivo.length(); i++){
				if(nomeDoArquivo.charAt(i) == '.'){
					extensao = nomeDoArquivo.substring(i, nomeDoArquivo.length());
					System.out.println("Extenção do arquivo: " + extensao);
				}
			}

			if(extensao.equals(".csv")){

				InputStream inputStream = uploadedFile.getFile();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				ArrayList<Disciplina> disciplinasCadastradas = new ArrayList<Disciplina>();
				ArrayList<Disciplina> disciplinasJaCadastradas = new ArrayList<Disciplina>();

				String linha = null;

				try{
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

					while((linha = bufferedReader.readLine()) != null){
						String informacao = null;

						int inicio = 0;
						int controle = 0;

						Disciplina disciplina = new Disciplina();

						for(int i = 0;i < linha.length(); i++){
							if(linha.charAt(i) == ';'){
								informacao = linha.substring(inicio, i);
								inicio = i + 1;
								if(controle == 0){
									System.out.println("Identificador na rede: " + informacao);
									disciplina.setNome(informacao);
								}						
								controle = controle + 1;
							}
							if(linha.length()-1 == i){
								informacao = linha.substring(inicio, i + 1);
								System.out.println("Email do gestor: " + informacao);
								disciplina.setDescricao(informacao);
							}
						}

						disciplina.setTurma(turma);

						System.out.println("Descricao: " + disciplina.getDescricao());
						System.out.println("Nome: " + disciplina.getNome());
						System.out.println("Turma: " + disciplina.getTurma().getNome());

						if(disciplinas.pesquisarDisciplinaExistente(disciplina.getNome(), disciplina.getDescricao(), idDaTurma) == false){
							disciplinas.inserirDisciplina(disciplina);
							disciplinasCadastradas.add(disciplina);
						}else{
							disciplinasJaCadastradas.add(disciplina);
						}
					}
					result.include("disciplinasCadastradas", disciplinasCadastradas);
					result.include("disciplinasJaCadastradas", disciplinasJaCadastradas);
					result.redirectTo(TurmaController.class).acessarTurma(idDaTurma);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}

			}else{
				System.out.println("É necessário entrar com um arquivo do tipo .csv para fazer a importação!");
				validator.add(new SimpleMessage("mensagem", "É necessário entrar com um arquivo do tipo .csv para fazer a importação!"));
				validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplina(idDaTurma);
			}
		}else{
			System.out.println("É necessário entrar com o arquivo para fazer a importação!");
			validator.add(new SimpleMessage("mensagem", "É necessário entrar com o arquivo para fazer a importação!"));
			validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplina(idDaTurma);
		}
	}
	
	@Post
	public void importarDisciplinas(UploadedFile uploadedFile){
		
		validator.check(uploadedFile != null, new SimpleMessage("erro", "É necessário entrar com um arquivo .csv para realizar a importação!"));
		validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplinaDeProfessor(professorLogado.getId());
		
		String nomeDoArquivo = uploadedFile.getFileName();
		String extensao = null;
		
		for(int i = 0;i < nomeDoArquivo.length(); i++){
			if(nomeDoArquivo.charAt(i) == '.'){
				extensao = nomeDoArquivo.substring(i, nomeDoArquivo.length());
			}
		}
		
		validator.check(extensao.equals(".csv"), new SimpleMessage("erro", "É necessário entrar com um arquivo .csv para realizar a importação!"));
		validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplinaDeProfessor(professorLogado.getId());
		
		InputStream inputStream = uploadedFile.getFile();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		ArrayList<Disciplina> disciplinasCadastradas = new ArrayList<Disciplina>();
		ArrayList<Disciplina> disciplinasJaCadastradas = new ArrayList<Disciplina>();

		String linha = null;
		
		try{
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			while((linha = bufferedReader.readLine()) != null){
				String informacao = null;
				int inicio = 0;
				int controle = 0;

				Disciplina disciplina = new Disciplina();
				
				for(int i = 0;i < linha.length(); i++){
					if(linha.charAt(i) == ';'){
						informacao = linha.substring(inicio, i);
						inicio = i + 1;
						if(controle == 0){
							System.out.println("Identificador na rede: " + informacao);
							disciplina.setNome(informacao);
						}						
						controle = controle + 1;
					}
					if(linha.length()-1 == i){
						informacao = linha.substring(inicio, i + 1);
						System.out.println("Email do gestor: " + informacao);
						disciplina.setDescricao(informacao);
					}
				}
				
				disciplina.setProfessor((Professor) usuarios.pesquisarUsuarioPorId(professorLogado.getId()));
				
				if(disciplinas.pesquisarDisciplinaDeProfessorExistente(disciplina.getNome(), disciplina.getDescricao(), professorLogado.getId()) == false){
					disciplinas.inserirDisciplina(disciplina);
					disciplinasCadastradas.add(disciplina);
				}else{
					disciplinasJaCadastradas.add(disciplina);
				}
			}
			
			result.include("disciplinasCadastradas", disciplinasCadastradas);
			result.include("disciplinasJaCadastradas", disciplinasJaCadastradas);
			result.redirectTo(ProfessorController.class).telaPrincipal();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Post
	public void inserirDisciplina(Disciplina disciplina, long idDaTurma){
		System.out.println("Entrou na inserção de disciplina");
		Turma turma = turmas.pesquisarTurmaPorId(idDaTurma);

		disciplina.setTurma(turma);

		if(disciplinas.pesquisarDisciplinaExistente(disciplina.getNome(), disciplina.getDescricao(), disciplina.getTurma().getId()) == false){

			disciplinas.inserirDisciplina(disciplina);

			result.include("mensagem", "Disciplina cadastrada com sucesso!");
			result.redirectTo(TurmaController.class).acessarTurma(idDaTurma);

		}else{
			validator.add(new SimpleMessage("mensagem", "Disciplina com mesmos dados já cadastrada na turma!"));
			validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplina(idDaTurma);
		}
	}

	@Post
	public void inserirDisciplinaDeProfessor(Disciplina disciplina){

		Professor professor = (Professor) usuarios.pesquisarUsuarioPorId(professorLogado.getId());
		
		if(disciplinas.pesquisarDisciplinaExistente(disciplina.getNome(), disciplina.getDescricao(), professorLogado.getId()) == false){
			
			disciplina.setProfessor(professor);
			
			disciplinas.inserirDisciplina(disciplina);
			
			result.include("mensagem", "Disciplina cadastrada com sucesso!");
			result.redirectTo(ProfessorController.class).telaPrincipal();
			
		}else{
			validator.add(new SimpleMessage("mensagem", "Disciplina com mesmos dados já cadastrada ao professor!"));
			validator.onErrorUsePageOf(DisciplinaController.class).inserirDisciplinaDeProfessor(professorLogado.getId());
		}
	}
	
	//Methods delete
	
	@Delete("disciplina/{id}")
	public void excluirDisciplina(long id){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);

		disciplinas.excluirDisciplina(disciplina);

		result.redirectTo(DisciplinaController.class).listarDisciplinasPorTurma(disciplina.getTurma().getId());
	}
}