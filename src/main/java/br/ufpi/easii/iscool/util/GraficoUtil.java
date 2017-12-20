package br.ufpi.easii.iscool.util;

import java.util.List;

/**
 * @author Alexandre
 *
 */

public class GraficoUtil {
	
	private String titulo;
	private String nome;
	private String tipo;
	private boolean cor;
	private List<Dado> dados;
	
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
	
	public List<Dado> getDados() {
		return dados;
	}
	
	public void setDados(List<Dado> dados) {
		this.dados = dados;
	}

	public boolean isCor() {
		return cor;
	}

	public void setCor(boolean cor) {
		this.cor = cor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
