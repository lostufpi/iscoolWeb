package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.ufpi.easii.iscool.dao.ExplicacaoDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Explicacao;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Resposta;
import br.ufpi.easii.iscool.entidade.RespostaPython;
import br.ufpi.easii.iscool.enuns.Letra;

@Controller
public class PythonController {
	private QuestaoDao questaoDao;
	private ExplicacaoDao explicacaoDao;
	private UsuarioDao usuarioDao;
	private ProvaDao provaDao;
	private NotaDao notaDao;
	private RespostaDao respostaDao;
	private InsigniaController insignias;
	
	public PythonController(){
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	protected PythonController(QuestaoDao questaoDao, UsuarioDao usuarioDao, ProvaDao provaDao, NotaDao notaDao, RespostaDao respostaDao, InsigniaController insignias, ExplicacaoDao explicacaoDao){
		this.questaoDao = questaoDao;
		this.usuarioDao = usuarioDao;
		this.provaDao = provaDao;
		this.notaDao = notaDao;
		this.respostaDao = respostaDao;
		this.insignias = insignias;
		this.explicacaoDao = explicacaoDao;
	}
	
	@Consumes({"application/json", "application/xml"})
	@Post
	public void corrigir(JsonArray respostas, long alunoId, long provaId){
		Gson gson = new Gson();
		
		TypeToken<List<RespostaPython>> token = new TypeToken<List<RespostaPython>>(){};
		ArrayList<RespostaPython> respostasPython = gson.fromJson(respostas, token.getType());
		
		List<Resposta> respostasDoAluno = new ArrayList<Resposta>();
		List<Questao> respostasCorretas = questaoDao.listarQuestoesPorProva(provaId);
		Aluno aluno = (Aluno) usuarioDao.pesquisarUsuarioPorId(alunoId);
		Prova prova = provaDao.pesquisarProvaPorId(provaId);
		Nota nota = new Nota();
		
		Resposta respostaAtual;
		Nota notaEncontrada;
		
		for(RespostaPython r : respostasPython){
			respostaAtual = new Resposta();
			respostaAtual.setResposta(converteResposta(r.getResposta()));
			respostaAtual.setAluno(aluno);
			respostaAtual.setProva(prova);
			
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
				questaoDao.atualizarQuestao(questao);
				explicacaoDao.inserirExplicacao(explicacao);
			}
			else if(!respostasDoAluno.get(i).getResposta().equals(respostasCorretas.get(i).getResposta()) && !respostasDoAluno.get(i).getResposta().equals(Letra.NR)){
				respostasDoAluno.get(i).setAcertou(false);
				numeroDeErros = numeroDeErros + 1;
				Questao questao = respostasCorretas.get(i);
				questao.incrementaResposta();
				respostasDoAluno.get(i).setQuestao(questao);
				questaoDao.atualizarQuestao(questao);
			}
			else{
				respostasDoAluno.get(i).setAcertou(false);
				questoesSemResposta = questoesSemResposta + 1;
				Questao questao = respostasCorretas.get(i);
				questao.incrementaResposta();
				respostasDoAluno.get(i).setQuestao(questao);
				questaoDao.atualizarQuestao(questao);
			}
		}
		
		corrigirQuestoes(respostasDoAluno, respostasCorretas, aluno, prova);
		
		if(prova.getValorDaPenalidade() != 0){
			penalidade = numeroDeErros/prova.getValorDaPenalidade();
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
		nota.setProva(prova);
		nota.setProvaCorrigida(true);
		nota.setQuestoesSemResposta(questoesSemResposta);
		nota.setCaminhoDaImagem("foto-" + aluno.getId() + "-" + prova.getId() + ".png");
		insignias.inserirInsigniasPorProva(nota);
		
		
		if(notaDao.pesquisarNota(aluno.getId(), prova.getId()) == null){
			notaDao.inserirNota(nota);
		}else{
			notaEncontrada = notaDao.pesquisarNota(aluno.getId(), prova.getId());
			notaEncontrada.setAcertosComPenalidade(acertosComPenalidade);
			notaEncontrada.setAluno(aluno);
			notaEncontrada.setNota(numeroDeAcertos);
			notaEncontrada.setNumeroDeErros(numeroDeErros);
			notaEncontrada.setPenalidade(penalidade);
			notaEncontrada.setPorcentagemDeAcerto(porcentagemDeAcerto);
			notaEncontrada.setProva(prova);
			notaEncontrada.setProvaCorrigida(true);
			notaEncontrada.setQuestoesSemResposta(questoesSemResposta);
			notaDao.atualizarNota(nota);
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