package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufpi.easii.iscool.enuns.Letra;

@Entity
public class Explicacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String explicacao;
	
	@NotNull
	private boolean acertou;
	
	@Lob
	private String descricao;
	
	@Lob
	private String primeiraAlternativa;
	
	@Lob
	private String segundaAlternativa;
	
	@Lob
	private String terceiraAlternativa;
	
	@Lob
	private String quartaAlternativa;
	
	@Lob
	private String quintaAlternativa;
	
	@Enumerated(EnumType.STRING)
	private Letra respostaCorreta;
	
	@Enumerated(EnumType.STRING)
	private Letra respostaAluno;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "id_prova")
	private Prova prova;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExplicacao() {
		return explicacao;
	}

	public void setExplicacao(String explicacao) {
		this.explicacao = explicacao;
	}

	public boolean isAcertou() {
		return acertou;
	}

	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPrimeiraAlternativa() {
		return primeiraAlternativa;
	}

	public void setPrimeiraAlternativa(String primeiraAlternativa) {
		this.primeiraAlternativa = primeiraAlternativa;
	}

	public String getSegundaAlternativa() {
		return segundaAlternativa;
	}

	public void setSegundaAlternativa(String segundaAlternativa) {
		this.segundaAlternativa = segundaAlternativa;
	}

	public String getTerceiraAlternativa() {
		return terceiraAlternativa;
	}

	public void setTerceiraAlternativa(String terceiraAlternativa) {
		this.terceiraAlternativa = terceiraAlternativa;
	}

	public String getQuartaAlternativa() {
		return quartaAlternativa;
	}

	public void setQuartaAlternativa(String quartaAlternativa) {
		this.quartaAlternativa = quartaAlternativa;
	}

	public String getQuintaAlternativa() {
		return quintaAlternativa;
	}

	public void setQuintaAlternativa(String quintaAlternativa) {
		this.quintaAlternativa = quintaAlternativa;
	}

	public Letra getRespostaCorreta() {
		return respostaCorreta;
	}

	public void setRespostaCorreta(Letra respostaCorreta) {
		this.respostaCorreta = respostaCorreta;
	}

	public Letra getRespostaAluno() {
		return respostaAluno;
	}

	public void setRespostaAluno(Letra respostaAluno) {
		this.respostaAluno = respostaAluno;
	}
}