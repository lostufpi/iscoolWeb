package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufpi.easii.iscool.enuns.Letra;

@Entity
public class Resposta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Letra resposta;
	
	@ManyToOne
	@JoinColumn(name = "id_prova")
	private Prova prova;
	
	@ManyToOne
	@JoinColumn(name = "id_questao")
	private Questao questao;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;
	
	private boolean acertou;
	
	@NotNull
	private int numero;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Letra getResposta() {
		return resposta;
	}

	public void setResposta(Letra resposta) {
		this.resposta = resposta;
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public boolean isAcertou() {
		return acertou;
	}

	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
	}
}