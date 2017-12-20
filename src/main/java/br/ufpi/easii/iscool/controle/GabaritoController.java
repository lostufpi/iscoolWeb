package br.ufpi.easii.iscool.controle;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.enuns.Letra;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;
import br.ufpi.easii.iscool.util.NotificacaoUtil;

@Controller
public class GabaritoController {
	
	private QuestaoDao questoes;
	private Result result;
	private ProvaDao provas;
	private Validator validator;
	private ProfessorLogado professorLogado;
	
	protected GabaritoController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public GabaritoController(QuestaoDao questoes, Result result, ProvaDao provas, Validator validator, ProfessorLogado professorLogado){
		this.questoes = questoes;
		this.result = result;
		this.provas = provas;
		this.validator = validator;
		this.professorLogado = professorLogado;
	}
	
	@Get
	public List<Questao> editarGabarito(long id){
		Prova prova = provas.pesquisarProvaPorId(id);
		validator.check(prova != null && prova.getDisciplina().getProfessor().getId() == professorLogado.getProfessor().getId(), new SimpleMessage("erro", "Você não possui permissão para alterar esse gabarito!"));
		validator.onErrorUsePageOf(ProfessorController.class).telaPrincipal();
		result.include("prova", provas.pesquisarProvaPorId(id));
		return questoes.listarQuestoesPorProva(id);
	}
	
	@Post
	public void editarGabarito(long idDaProva, List<Questao> questaoList, List<Letra> respostas, List<Integer> numeros){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		int size = respostas.size();
		for(int i = 0; i < size; i++){
			Questao questao = questoes.pesquisarQuestao(prova.getGabarito().getId(), idDaProva, numeros.get(i)); 
			questao.setResposta(respostas.get(i));
			questoes.atualizarQuestao(questao);
		}
		for(Aluno a : prova.getDisciplina().getTurma().getAlunos()){
			NotificacaoUtil.enviarNotificacao("Gabarito",  a.getToken(), "O professor alterou o gabarito da prova " + prova.getNome() + " da disciplina " + prova.getDisciplina().getNome(), "");
		}
		result.redirectTo(DisciplinaController.class).acessarDisciplina(prova.getDisciplina().getId());
	}
	
	@Get
	public void vizualizar(){
	}
}
