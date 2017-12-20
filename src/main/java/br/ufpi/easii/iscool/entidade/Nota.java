package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Nota implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int nota;
	
	private int XP;
	
	private double porcentagemDeAcerto;
	
	private int numeroDeErros;
	
	private int penalidade;
	
	private int questoesSemResposta;
	
	private int acertosComPenalidade;
	
	@NotNull
	private String caminhoDaImagem;
	
	@ManyToOne
	@JoinColumn(name = "id_prova")
	private Prova prova;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;

	private boolean provaCorrigida;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public double getPorcentagemDeAcerto() {
		return porcentagemDeAcerto;
	}

	public void setPorcentagemDeAcerto(double porcentagemDeAcerto) {
		this.porcentagemDeAcerto = porcentagemDeAcerto;
	}

	public boolean isProvaCorrigida() {
		return provaCorrigida;
	}

	public void setProvaCorrigida(boolean provaCorrigida) {
		this.provaCorrigida = provaCorrigida;
	}

	public int getNumeroDeErros() {
		return numeroDeErros;
	}

	public void setNumeroDeErros(int numeroDeErros) {
		this.numeroDeErros = numeroDeErros;
	}

	public int getPenalidade() {
		return penalidade;
	}

	public void setPenalidade(int penalidade) {
		this.penalidade = penalidade;
	}

	public int getQuestoesSemResposta() {
		return questoesSemResposta;
	}

	public void setQuestoesSemResposta(int questoesSemResposta) {
		this.questoesSemResposta = questoesSemResposta;
	}

	public int getAcertosComPenalidade() {
		return acertosComPenalidade;
	}

	public void setAcertosComPenalidade(int acertosComPenalidade) {
		this.acertosComPenalidade = acertosComPenalidade;
	}

	public String getCaminhoDaImagem() {
		return caminhoDaImagem;
	}

	public void setCaminhoDaImagem(String caminhoDaImagem) {
		this.caminhoDaImagem = caminhoDaImagem;
	}

	public int getXP() {
		return XP;
	}

	public void setXP(int xP) {
		XP = xP;
	}
}