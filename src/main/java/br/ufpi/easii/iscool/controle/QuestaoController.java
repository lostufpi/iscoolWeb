package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Resposta;
import br.ufpi.easii.iscool.enuns.Letra;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;

@Controller
public class QuestaoController {

	private QuestaoDao questoes;
	private ProvaDao provas;
	private Result result;
	private UsuarioDao alunos;
	private RespostaDao respostasDao;
	private Validator validator;
	private ProfessorLogado professorLogado;

	protected QuestaoController(){
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public QuestaoController(QuestaoDao questoes, ProvaDao provas, Result result, UsuarioDao alunos, RespostaDao respostas, Validator validator, ProfessorLogado professorLogado){
		this.questoes = questoes;
		this.result = result;
		this.provas = provas;
		this.alunos = alunos;
		this.respostasDao = respostas;
		this.validator = validator;
		this.professorLogado = professorLogado;
	}

	@Get
	public Prova inserirQuestao(long id){
		Prova prova = provas.pesquisarProvaPorId(id);
		validator.check(prova != null && prova.getDisciplina().getProfessor().getId() == professorLogado.getProfessor().getId(), new SimpleMessage("", "Você não tem permissão para alterar essa prova!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		List<Questao> listQuestao = questoes.listarQuestoesPorProva(id);

		if(prova.getQuestoes() >= listQuestao.size() + 1){
			result.include("cont", questoes.listarQuestoesPorProva(id).size() + 1);
			return provas.pesquisarProvaPorId(id);
		}else{
			result.include("mensagem", "A prova já atingiu seu número máximo de questões!");
			result.redirectTo(DisciplinaController.class).acessarDisciplina(prova.getDisciplina().getId());
		}
		return null;
	}

	@Post
	public void inserirQuestao(Questao questao, long idDaProva, long id){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		List<Questao> listaQuestoes = questoes.listarQuestoesPorProva(idDaProva);
		
		if(!questao.getDescricao().equals("")){
			if((prova.getQuestoes() >= listaQuestoes.size()+1)){
				questao.setProva(prova);
				questao.setNumero(listaQuestoes.size() + 1);
				questao.setGabarito(prova.getGabarito());
				questoes.inserirQuestao(questao);
				result.redirectTo(QuestaoController.class).inserirQuestao(idDaProva);
			}else{
				validator.add(new SimpleMessage("mensagem", "A prova já atingiu seu número de questões!"));
				validator.onErrorUsePageOf(QuestaoController.class).inserirQuestao(idDaProva);
			}
		}else{
			validator.add(new SimpleMessage("mensagem", "Preencha o campo de descrição da questão!"));
			validator.onErrorUsePageOf(DisciplinaController.class).acessarDisciplina(id);
		}
	}
	
	@Get
	public Questao atualizarQuestao(long idDaQuestao){
		return questoes.pesquisarQuestaoPorId(idDaQuestao);
	}
	
	@Post
	public void atualizarQuestao(long idDaQuestao, Questao questao){
		Questao q = questoes.pesquisarQuestaoPorId(idDaQuestao);
		
		q.setDescricao(questao.getDescricao());
		q.setPrimeiraAlternativa(questao.getPrimeiraAlternativa());
		q.setSegundaAlternativa(questao.getSegundaAlternativa());
		q.setTerceiraAlternativa(questao.getTerceiraAlternativa());
		q.setQuartaAlternativa(questao.getQuartaAlternativa());
		q.setQuintaAlternativa(questao.getQuintaAlternativa());
		q.setResposta(questao.getResposta());
		
		questoes.atualizarQuestao(q);		
		result.include("mensagem", "Questão atualizada com sucesso!");
		result.redirectTo(QuestaoController.class).listarQuestoes(q.getProva().getId());
	}

	@Post
	public void finalizar(long idDaDisciplina){
		result.redirectTo(DisciplinaController.class).acessarDisciplina(idDaDisciplina);
	}

	@Get
	public List<Questao> listarQuestoesPorProva(long idDaProva){
		result.include("prova", provas.pesquisarProvaPorId(idDaProva));
		return questoes.listarQuestoesPorProva(idDaProva);
	}
	
	public List<Questao> listarQuestoes(long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		
		validator.check(prova != null && prova.getDisciplina().getProfessor().getId() == professorLogado.getProfessor().getId(), new SimpleMessage("erro", "Você não tem permissão para acessar essa prova!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		result.include("prova", provas.pesquisarProvaPorId(idDaProva));
		return questoes.listarQuestoesPorProva(idDaProva);
	}

	@Get
	public List<Questao> corrigirQuestoes(long idDoAluno, long idDaProva){

		Prova prova = provas.pesquisarProvaPorId(idDaProva);

		List<Questao> questao = questoes.listarQuestoesPorProva(idDaProva);

		Aluno aluno = (Aluno) alunos.pesquisarUsuarioPorId(idDoAluno);

		result.include("aluno", aluno);
		result.include("prova", prova);
		return questao;
	}

	@Post
	public void corrigirQuestoes(List<Letra> respostas, List<Integer> numeros, long idDoAluno, long idDaProva){
		int size = respostas.size();
		for(int i = 0;i < size; i++){
			if(respostasDao.pesquisarResposta(idDoAluno, idDaProva, numeros.get(i)) == null){
				Resposta resposta = new Resposta();
				resposta.setNumero(numeros.get(i));
				resposta.setResposta(respostas.get(i));
				resposta.setProva(provas.pesquisarProvaPorId(idDaProva));
				resposta.setAluno((Aluno) alunos.pesquisarUsuarioPorId(idDoAluno));
				respostasDao.inserirResposta(resposta);
			}else{
				Resposta resposta = respostasDao.pesquisarResposta(idDoAluno, idDaProva, numeros.get(i));
				resposta.setResposta(respostas.get(i));
				respostasDao.inserirResposta(resposta);
			}
		}
		result.redirectTo(ProvaController.class).listarAlunosPorProva(idDaProva);
	}
	
	@Delete("questao/{id}")
	public void excluirQuestao(long id){
		Questao questao = questoes.pesquisarQuestaoPorId(id);
		questoes.excluirQuestao(questao);
		result.include("mensagem", "Questão excluída com sucesso!");
		result.redirectTo(QuestaoController.class).listarQuestoes(questao.getProva().getId());
	}
	
	@Get
	public Questao exibirDados(long idDaQuestao){
		Questao questao = questoes.pesquisarQuestaoPorId(idDaQuestao);
		
		ArrayList<Resposta> respostasA = new ArrayList<Resposta>();
		ArrayList<Resposta> respostasB = new ArrayList<Resposta>();
		ArrayList<Resposta> respostasC = new ArrayList<Resposta>();
		ArrayList<Resposta> respostasD = new ArrayList<Resposta>();
		ArrayList<Resposta> respostasE = new ArrayList<Resposta>();
		ArrayList<Resposta> respostasCorretas = new ArrayList<Resposta>();
		
		for(Resposta r : questao.getRespostas()){
			if(r.getResposta().equals(Letra.A)){
				respostasA.add(r);
			}else if(r.getResposta().equals(Letra.B)){
				respostasB.add(r);
			}else if(r.getResposta().equals(Letra.C)){
				respostasC.add(r);
			}else if(r.getResposta().equals(Letra.D)){
				respostasD.add(r);
			}else if(r.getResposta().equals(Letra.E)){
				respostasE.add(r);
			}
			
			if(r.getResposta().equals(questao.getResposta())){
				respostasCorretas.add(r);
			}
		}
		
		result.include("numeroDeRespostas", questao.getRespostas().size());
		
		result.include("respostasA", respostasA);
		result.include("respostasB", respostasB);
		result.include("respostasC", respostasC);
		result.include("respostasD", respostasD);
		result.include("respostasE", respostasE);
		result.include("numeroDeAcertos", respostasCorretas.size());
		
		result.include("numeroDeErros", questao.getRespostas().size() - respostasCorretas.size());
		
		if(questao.getRespostas().size() > 0){
			result.include("porcentagemDeAcertos", ((respostasCorretas.size()*100)/questao.getRespostas().size()));
		}
		
		return questoes.pesquisarQuestaoPorId(idDaQuestao);
	}
}