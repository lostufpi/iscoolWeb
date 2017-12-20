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
import br.ufpi.easii.iscool.dao.EscolaDao;
import br.ufpi.easii.iscool.dao.TurmaDao;
import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.Turma;
import br.ufpi.easii.iscool.enuns.Turno;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;

@Controller
@Named
public class TurmaController {

	private TurmaDao turmas;
	private EscolaDao escolas;
	private Result result;
	private Validator validator;
	private GestorEscolarLogado gestorEscolarLogado;

	protected TurmaController(){
		this(null, null, null, null, null);
	}

	@Inject
	public TurmaController(EscolaDao escolas, TurmaDao turmas, Result result, Validator validator, GestorEscolarLogado gestoEscolarLogado){
		this.escolas = escolas;
		this.turmas = turmas;
		this.result = result;
		this.validator = validator;
		this.gestorEscolarLogado = gestoEscolarLogado;
	}

	@Get
	public void inserirTurma(){
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
	}

	@Post
	public void inserirTurma(Turma turma, long idDaEscola){

		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Escola escola = escolas.pesquisarEscolaPorId(idDaEscola);
		
		turma.setEscola(escola);
		
		if(turma.getNome() != null && turma.getSerie() != null && turma.getTurno() != null && escola != null){
			if(turmas.pesquisarTurmaExistente(turma.getSerie(), turma.getNome(), turma.getTurno(), turma.getEscola().getId()) == false){

				turmas.inserirTurma(turma);

				result.include("feedback", "Turma cadastrada com sucesso!");
				result.redirectTo(EscolaController.class).telaPrincipal();
			}else{
				System.out.println("Turma com mesmos dados já existente na escola!");
				validator.add(new SimpleMessage("erro", "Turma com mesmos dados já cadastrada na escola!"));
				validator.onErrorUsePageOf(TurmaController.class).inserirTurma();
			}
		}else{
			System.out.println("Entre com todos os dados do formulário!");
		}
	}

	@Post
	public void importarArquivoCSV(UploadedFile uploadedFile, long idDaEscola){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		if(uploadedFile != null){

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

				ArrayList<Turma> turmasJaCadastradas = new ArrayList<Turma>();
				ArrayList<Turma> turmasCadastradas = new ArrayList<Turma>();
				
				String linha = null;

				try{
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					while((linha = bufferedReader.readLine()) != null){
						String informacao = null;

						int inicio = 0;
						int controle = 0;

						Turma turma = new Turma();

						for(int i = 0;i < linha.length(); i++){
							if(linha.charAt(i) == ';'){
								informacao = linha.substring(inicio, i);
								inicio = i + 1;
								if(controle == 0){
									System.out.println("Identificador na rede: " + informacao);
									turma.setSerie(informacao);
								}						
								if(controle == 1){
									System.out.println("Nome da escola: " + informacao);
									turma.setNome(informacao);
								}
								controle = controle + 1;
							}
							if(linha.length()-1 == i){
								informacao = linha.substring(inicio, i + 1);
								System.out.println("Email do gestor: " + informacao);
								if(informacao.equals("MANHA")) turma.setTurno(Turno.MANHA);
								if(informacao.equals("TARDE")) turma.setTurno(Turno.TARDE);
								if(informacao.equals("NOITE")) turma.setTurno(Turno.NOITE);
							}
						}

						Escola escola = escolas.pesquisarEscolaPorId(idDaEscola);

						turma.setEscola(escola);
						
						if(turmas.pesquisarTurmaExistente(turma.getSerie(), turma.getNome(), turma.getTurno(), turma.getEscola().getId()) == false){
							turmas.inserirTurma(turma);
							turmasCadastradas.add(turma);
						}else{
							turmasJaCadastradas.add(turma);
						}
					}
					result.include("turmasCadastradas", turmasCadastradas);
					result.include("turmasJaCadastradas", turmasJaCadastradas);
					result.redirectTo(EscolaController.class).telaPrincipal();
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}else{
				System.out.println("É necessário entrar com um arquivo do tipo .csv para fazer a importação!");
				validator.add(new SimpleMessage("mensagem", "É necessário entrar com um arquivo do tipo .csv para fazer a importação!"));
				validator.onErrorUsePageOf(TurmaController.class).inserirTurma();
			}
		}else{
			System.out.println("É necessário entrar com o arquivo para fazer a importação!");
			validator.add(new SimpleMessage("mensagem", "É necessário entrar com o arquivo para fazer a importação!"));
			validator.onErrorUsePageOf(TurmaController.class).inserirTurma();
		}
	}
	
	@Get
	public List<Turma> listarTurmasPorEscola(long idDaEscola){
		String alerta = null;
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		validator.check(gestorEscolarLogado.getEscola().getId() == idDaEscola, new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		validator.check(turmas.listarTurmasPorEscola(idDaEscola).size() > 0, new SimpleMessage("erro", "Você ainda não possui turmas! Cadastre sua primeira turma."));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		if(turmas.listarTurmasPorEscola(idDaEscola).size() == 0)
			alerta = "Você ainda não possui escolas cadastradas";
		result.include("alert", alerta);
		return turmas.listarTurmasPorEscola(idDaEscola);
	}
	
	@Get
	public Turma acessarTurma(long idDaTurma){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		validator.check(turmas.pesquisarTurmaPorId(idDaTurma) != null, new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		validator.check(gestorEscolarLogado.getId() == turmas.pesquisarTurmaPorId(idDaTurma).getEscola().getGestorEscolar().getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(EscolaController.class).telaPrincipal();
		
		return turmas.pesquisarTurmaPorId(idDaTurma);
	}
	
	@Delete("turma/{id}")
	public void excluirTurma(long id){
		
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para alterar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Turma turma = turmas.pesquisarTurmaPorId(id);
		
		long idDaEscola = turma.getEscola().getId();
		
		turmas.excluirTurma(turma);
		
		result.include("alert", "Turma excluída com sucesso!");
		result.redirectTo(TurmaController.class).listarTurmasPorEscola(idDaEscola);
	}
}