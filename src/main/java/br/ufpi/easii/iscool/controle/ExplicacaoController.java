package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;

import javax.inject.Inject;

import org.jsoup.Jsoup;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.entidade.Explicacao;
import br.ufpi.easii.iscool.entidade.Resposta;

@Controller
public class ExplicacaoController {

	private Result result;
	private RespostaDao respostaDao;
	
	public ExplicacaoController() {
		this(null, null);
	}
	
	@Inject
	protected ExplicacaoController(Result result, RespostaDao respostaDao) {
		this.result = result;
		this.respostaDao = respostaDao;
	}
	
	
	@Get
	public void listar(long idAluno, long idProva){
		ArrayList<Resposta> respostas = (ArrayList<Resposta>) respostaDao.listarRespostasPorProvaEAluno(idAluno, idProva);
		
		Explicacao explicacao = new Explicacao();
		ArrayList<Explicacao> explicacoes = new ArrayList<>();
		
		for(Resposta r : respostas){
			explicacao = new Explicacao();
			explicacao.setAcertou(r.isAcertou());
			explicacao.setDescricao(r.getQuestao().getDescricao());
			explicacao.setExplicacao(r.getQuestao().getExplicacao());
			explicacao.setPrimeiraAlternativa(r.getQuestao().getPrimeiraAlternativa());
			explicacao.setSegundaAlternativa(r.getQuestao().getSegundaAlternativa());
			explicacao.setTerceiraAlternativa(r.getQuestao().getTerceiraAlternativa());
			explicacao.setQuartaAlternativa(r.getQuestao().getQuartaAlternativa());
			explicacao.setQuintaAlternativa(r.getQuestao().getQuintaAlternativa());
			explicacao.setRespostaCorreta(r.getQuestao().getResposta());
			explicacao.setRespostaAluno(r.getResposta());
			explicacoes.add(explicacao);
		}
		
		result.include("explicacoes", explicacoes);
	}
	
	@Get("explicacao/listar/{idAluno}/{idProva}")
	public void listaExplicacao(long idAluno, long idProva) {
		ArrayList<Resposta> respostas = (ArrayList<Resposta>) respostaDao.listarRespostasPorProvaEAluno(idAluno, idProva);
		
		Explicacao explicacao = new Explicacao();
		ArrayList<Explicacao> explicacoes = new ArrayList<>();
		
		for(Resposta r : respostas){
			explicacao = new Explicacao();
			explicacao.setAcertou(r.isAcertou());
			String primeiraAlternativa  = Jsoup.parse(r.getQuestao().getPrimeiraAlternativa()).text();
			String segundaAlternativa = Jsoup.parse(r.getQuestao().getSegundaAlternativa()).text();
			String terceiraAlternativa = Jsoup.parse(r.getQuestao().getTerceiraAlternativa()).text();
			String quartaAlternativa = Jsoup.parse(r.getQuestao().getQuartaAlternativa()).text();
			String quintaAlternativa = Jsoup.parse(r.getQuestao().getQuintaAlternativa()).text();
			String descricao = Jsoup.parse(r.getQuestao().getDescricao()).text();
			String explicacaoText = Jsoup.parse(r.getQuestao().getExplicacao()).text();
			
			explicacao.setDescricao(descricao);
			explicacao.setExplicacao(explicacaoText);
			explicacao.setPrimeiraAlternativa(primeiraAlternativa);
			explicacao.setSegundaAlternativa(segundaAlternativa);
			explicacao.setTerceiraAlternativa(terceiraAlternativa);
			explicacao.setQuartaAlternativa(quartaAlternativa);
			explicacao.setQuintaAlternativa(quintaAlternativa);
			explicacao.setRespostaCorreta(r.getQuestao().getResposta());
			explicacao.setRespostaAluno(r.getResposta());
			explicacoes.add(explicacao);
		}
		
		result.use(Results.json()).withoutRoot().from(explicacoes).serialize();
	}
}