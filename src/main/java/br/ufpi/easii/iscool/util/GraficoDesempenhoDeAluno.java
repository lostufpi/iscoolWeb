package br.ufpi.easii.iscool.util;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.easii.iscool.entidade.Nota;

public class GraficoDesempenhoDeAluno {

	private String nome;
	private String aluno;
	private String titulo;
	private String tipo;
	private boolean cor;
	private List<String> categorias = new ArrayList<String>();
	private List<Double> dados = new ArrayList<Double>();
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	public List<String> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	
	public List<Double> getDados() {
		return dados;
	}
	
	public void setDados(List<Double> dados) {
		this.dados = dados;
	}
	
	public void gerarDadosDeDesempenho(List<Nota> notas){
		for(Nota n : notas){
			categorias.add(n.getProva().getNome());
			dados.add(n.getPorcentagemDeAcerto());
		}
		this.setCategorias(categorias);
		this.setDados(dados);
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}
}