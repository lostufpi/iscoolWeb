package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.ExplicacaoDao;
import br.ufpi.easii.iscool.dao.GabaritoDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.SimuladoDao;
import br.ufpi.easii.iscool.dao.TurmaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Explicacao;
import br.ufpi.easii.iscool.entidade.Gabarito;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Resposta;
import br.ufpi.easii.iscool.entidade.RespostaPython;
import br.ufpi.easii.iscool.entidade.Simulado;
import br.ufpi.easii.iscool.entidade.Turma;
import br.ufpi.easii.iscool.enuns.Letra;

@Controller
public class SimuladoController {

	private SimuladoDao simulados;
	private ProvaDao provas;
	private TurmaDao turmas;
	private GabaritoDao gabaritos;
	private Result result;
	private DisciplinaDao disciplinas;
	private UsuarioDao usuarios;
	private QuestaoDao questoes;
	private ExplicacaoDao explicacoes;
	private RespostaDao respostaDao;
	private InsigniaController insignias;
	private NotaDao notaDao;
	
	protected SimuladoController(){
		this(null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public SimuladoController(SimuladoDao simulados, Result result, TurmaDao turmas, DisciplinaDao disciplinas, GabaritoDao gabaritos, ProvaDao provas, UsuarioDao usuarios, QuestaoDao questoes, ExplicacaoDao explicacoes, RespostaDao respostaDao, InsigniaController insignias, NotaDao notaDao){
		this.simulados = simulados;
		this.result = result;
		this.turmas = turmas;
		this.disciplinas = disciplinas;
		this.gabaritos = gabaritos;
		this.provas = provas;
		this.simulados = simulados;
		this.questoes = questoes;
		this.explicacoes = explicacoes;
		this.respostaDao = respostaDao;
		this.insignias = insignias;
		this.notaDao = notaDao;
		this.usuarios = usuarios;
	}
	
	@Get
	public Turma inserirSimulado(long idTurma){
		List<Disciplina> disciplinasTurma = disciplinas.listarDisciplinasPorTurma(idTurma);
		for(Disciplina d : disciplinasTurma) {
			System.out.println(d.getNome() + "/" + d.getId());
		}
		result.include("disciplinas", disciplinasTurma);
		return turmas.pesquisarTurmaPorId(idTurma);
	}
	
	@Post
	public void inserirSimulado(Simulado simulado, long idTurma, List<Long> disciplinasIds, List<Integer> numeroDeQuestoes){
		
		Turma turma = turmas.pesquisarTurmaPorId(idTurma);
		Disciplina disciplina;
		Gabarito gabarito;
		Prova prova;
		int questoes = 0;
		
		simulado.setTurma(turma);
		simulado.setQuantidadeQuestoes(questoes);
		simulados.inserirSimulado(simulado);
		
		for(int i = 0; i < disciplinasIds.size(); i++) {
			if(numeroDeQuestoes.get(i) != 0) {
				disciplina = disciplinas.pesquisarDisciplinaPorId(disciplinasIds.get(i));
				gabarito = new Gabarito();
				prova = new Prova();
				prova.setCabecalho(simulado.getNome());
				prova.setDataDeRealizacao(new Date());
				prova.setDisciplina(disciplina);
				prova.setGabarito(gabarito);
				prova.setNome(simulado.getNome());
				prova.setQuestoes(numeroDeQuestoes.get(i));
				prova.setSimulado(simulado);
				
				gabarito.setProva(prova);
				
				gabaritos.inserirGabarito(gabarito);
				provas.inserirProva(prova);
				
				questoes = questoes + numeroDeQuestoes.get(i);
			}
		}
		
		simulado.setQuantidadeQuestoes(questoes);
		simulados.inserirSimulado(simulado);
		
		result.include("mensagem", "Simulado cadastrado com sucesso");
		result.include("mensagem", "Simulado cadastrado com sucesso");
	}
	
	@Consumes({"application/json", "application/xml"})
	@Post
	public void corrigir(JsonArray respostas, long alunoId, long simuladoId){
		Simulado simulado = simulados.pesquisarSimuladoPorId(simuladoId);
		
		Gson gson = new Gson();
		
		TypeToken<List<RespostaPython>> token = new TypeToken<List<RespostaPython>>(){};
		ArrayList<RespostaPython> respostasPython = gson.fromJson(respostas, token.getType());
		
		int inicio = 0;
		int fim = 0;
		
		for(Prova p : simulado.getProvas()){
			List<Resposta> respostasDoAluno = new ArrayList<Resposta>();
			List<Questao> respostasCorretas = questoes.listarQuestoesPorProva(p.getId());
			Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(alunoId);
			Nota nota = new Nota();
			
			Resposta respostaAtual;
			Nota notaEncontrada;
			
			fim = fim + p.getQuestoes();
			
			for(RespostaPython r : respostasPython.subList(inicio, fim)){
				respostaAtual = new Resposta();
				respostaAtual.setResposta(converteResposta(r.getResposta()));
				respostaAtual.setAluno(aluno);
				respostaAtual.setProva(p);
				
				respostasDoAluno.add(respostaAtual);
			}
			int numeroDeAcertos = 0;
			int numeroDeErros = 0;
			int questoesSemResposta = 0;
			int acertosComPenalidade = 0;
			int penalidade = 0;
			double porcentagemDeAcerto = 0;
			
			for(int i = 0; i < respostasCorretas.size(); i++){
				respostasDoAluno.get(i).setNumero(respostasCorretas.get(i).getNumero());
				respostasDoAluno.get(i).setQuestao(respostasCorretas.get(i));
				if(respostasDoAluno.get(i).getResposta().equals(respostasCorretas.get(i).getResposta())){
					respostasDoAluno.get(i).setAcertou(true);
					numeroDeAcertos = numeroDeAcertos + 1;
					
					
					
					Questao questao = respostasCorretas.get(i);
					Explicacao explicacao = new Explicacao();
					
					questao.acertou();
					questao.incrementaResposta();
					
					explicacao.setAcertou(true);
					explicacao.setDescricao(questao.getDescricao());
					explicacao.setExplicacao(questao.getExplicacao());
					respostasDoAluno.get(i).setQuestao(questao);
					questoes.atualizarQuestao(questao);
					explicacoes.inserirExplicacao(explicacao);
				}
				else if(!respostasDoAluno.get(i).getResposta().equals(respostasCorretas.get(i).getResposta()) && !respostasDoAluno.get(i).getResposta().equals(Letra.NR)){
					respostasDoAluno.get(i).setAcertou(false);
					numeroDeErros = numeroDeErros + 1;
					Questao questao = respostasCorretas.get(i);
					questao.incrementaResposta();
					respostasDoAluno.get(i).setQuestao(questao);
					questoes.atualizarQuestao(questao);
				}
				else{
					respostasDoAluno.get(i).setAcertou(false);
					questoesSemResposta = questoesSemResposta + 1;
					Questao questao = respostasCorretas.get(i);
					questao.incrementaResposta();
					respostasDoAluno.get(i).setQuestao(questao);
					questoes.atualizarQuestao(questao);
				}
			}
			
			corrigirQuestoes(respostasDoAluno, respostasCorretas, aluno, p);
			
			if(p.getValorDaPenalidade() != 0){
				penalidade = numeroDeErros/p.getValorDaPenalidade();
				if(penalidade <= numeroDeAcertos)
					acertosComPenalidade = numeroDeAcertos - penalidade;
				else
					acertosComPenalidade = 0;
				porcentagemDeAcerto = (acertosComPenalidade * 100)/respostasCorretas.size();
			}else{
				acertosComPenalidade = numeroDeAcertos;
				porcentagemDeAcerto = (acertosComPenalidade * 100)/respostasCorretas.size();
			}
			
			nota.setAcertosComPenalidade(acertosComPenalidade);
			nota.setAluno(aluno);
			nota.setNota(numeroDeAcertos);
			nota.setNumeroDeErros(numeroDeErros);
			nota.setPenalidade(penalidade);
			nota.setPorcentagemDeAcerto(porcentagemDeAcerto);
			nota.setProva(p);
			nota.setProvaCorrigida(true);
			nota.setQuestoesSemResposta(questoesSemResposta);
			nota.setCaminhoDaImagem("foto-" + aluno.getId() + "-" + p.getId() + ".png");
			insignias.inserirInsigniasPorProva(nota);
			
			
			if(notaDao.pesquisarNota(aluno.getId(), p.getId()) == null){
				notaDao.inserirNota(nota);
			}else{
				notaEncontrada = notaDao.pesquisarNota(aluno.getId(), p.getId());
				notaEncontrada.setAcertosComPenalidade(acertosComPenalidade);
				notaEncontrada.setAluno(aluno);
				notaEncontrada.setNota(numeroDeAcertos);
				notaEncontrada.setNumeroDeErros(numeroDeErros);
				notaEncontrada.setPenalidade(penalidade);
				notaEncontrada.setPorcentagemDeAcerto(porcentagemDeAcerto);
				notaEncontrada.setProva(p);
				notaEncontrada.setProvaCorrigida(true);
				notaEncontrada.setQuestoesSemResposta(questoesSemResposta);
				notaDao.atualizarNota(nota);
			}
			
			inicio = inicio + p.getQuestoes(); 
		}
	}
	
	public void corrigirQuestoes(List<Resposta> respostas, List<Questao> respostasCorretas, Aluno aluno, Prova prova){
		int size = respostasCorretas.size();
		for(int i = 0;i < size; i++){
			if(respostaDao.pesquisarResposta(aluno.getId(), prova.getId(), respostasCorretas.get(i).getNumero()) == null){
				Resposta resposta = new Resposta();
				resposta.setNumero(respostasCorretas.get(i).getNumero());
				resposta.setResposta(respostas.get(i).getResposta());
				resposta.setAcertou(respostas.get(i).isAcertou());
				resposta.setQuestao(respostas.get(i).getQuestao());
				resposta.setProva(prova);
				resposta.setAluno(aluno);
				respostaDao.inserirResposta(resposta);
			}else{
				Resposta resposta = respostaDao.pesquisarResposta(aluno.getId(), prova.getId(), respostasCorretas.get(i).getNumero());
				resposta.setResposta(respostas.get(i).getResposta());
				resposta.setAcertou(respostas.get(i).isAcertou());
				resposta.setQuestao(respostas.get(i).getQuestao());
				respostaDao.atualizarResposta(resposta);
			}
		}
	}
	
	private Letra converteResposta(String resposta){
		if(resposta.equals("A")){
			return Letra.A;
		}else if(resposta.equals("B")){
			return Letra.B;
		}else if(resposta.equals("C")){
			return Letra.C;
		}else if(resposta.equals("D")){
			return Letra.D;
		}else if(resposta.equals("E")){
			return Letra.E;
		}
		return Letra.NR;
	}
}