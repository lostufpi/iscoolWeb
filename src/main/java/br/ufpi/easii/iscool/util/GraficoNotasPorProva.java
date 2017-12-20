package br.ufpi.easii.iscool.util;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.easii.iscool.entidade.Nota;

public class GraficoNotasPorProva{
	
	private String titulo;
	private String nome;
	private String tipo;
	private boolean cor;
	private List<Dado> acertos = new ArrayList<Dado>();
	private List<Dado> questoes = new ArrayList<Dado>();
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isCor() {
		return cor;
	}
	
	public void setCor(boolean cor) {
		this.cor = cor;
	}
	
	public List<Dado> getAcertos() {
		return acertos;
	}

	public void setAcertos(List<Dado> acertos) {
		this.acertos = acertos;
	}

	public List<Dado> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Dado> questoes) {
		this.questoes = questoes;
	}

	public void gerarDadosDeAcertos(List<Nota> notas){
		int maior = 0, menor = 0;
		Dado dado;
		
		for(Nota n : notas){
			if(n.getPorcentagemDeAcerto() >= 70.0) maior = maior + 1;
			else menor = menor + 1;
		}
		if(notas.size() > 0){
			if(maior > 0){
				dado = new Dado();
				dado.setName("Acima/Igual 70% ");
				dado.setY((double) ((maior * 100)/notas.size()));
				System.out.println("Porcentagem de acima de 70% de acerto: " + (double) ((maior * 100)/notas.size()));
				this.acertos.add(dado);
			}
			if(menor > 0){
				dado = new Dado();
				dado.setName("Abaixo de 70%");
				dado.setY((double) ((menor * 100)/notas.size()));
				System.out.println("Porcentagem abaixo de 70% de acerto: " + (double) ((menor * 100)/notas.size()));
				this.acertos.add(dado);
			}
		}
		this.setAcertos(acertos);
	}
	
	public void gerarDadosDeQuestoes(List<Nota> notas){
		int total = 0, semResposta = 0, acertos = 0, erros = 0;
		Dado dado;
		
		for(Nota n : notas){
			semResposta = semResposta + n.getQuestoesSemResposta();
			acertos = acertos + n.getNota();
			erros = erros + n.getNumeroDeErros();
		}
		total = semResposta + acertos + erros;
		
		if(total > 0){
			if(((semResposta * 100)/total) > 0.0){
				dado = new Dado();
				dado.setName("Questões sem resposta");
				dado.setY((double) ((semResposta * 100)/total));
				System.out.println("Porcentagem de questões sem resposta: " + (double) ((semResposta * 100)/total));
				this.questoes.add(dado);
			}
			if(((acertos * 100)/total) > 0.0){
				dado = new Dado();
				dado.setName("Questões acertadas");
				dado.setY((double) ((acertos * 100)/total));
				System.out.println("Porcentagem de questões corretas: " + (double) ((acertos * 100)/total));
				this.questoes.add(dado);
			}
			if(((erros * 100)/total) > 0.0){
				dado = new Dado();
				dado.setName("Questões erradas");
				dado.setY((double) ((erros * 100)/total));
				System.out.println("Porcentagem de questões erradas:" + (double) ((erros * 100)/total));
				this.questoes.add(dado);
			}
		}
		this.setQuestoes(questoes);
	}
}