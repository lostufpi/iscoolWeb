package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Prova implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String cabecalho;
	
	@OneToMany(mappedBy = "prova", targetEntity = Nota.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Nota> notas;
	
	@ManyToOne
	@JoinColumn(name = "id_disciplina")
	private Disciplina disciplina;
	
	@ManyToOne
	@JoinColumn(name = "id_simulado")
	private Simulado simulado;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private Gabarito gabarito;
	
	@OneToMany(mappedBy = "prova", targetEntity = Resposta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Resposta> respostas;
	
	@NotNull
	private int questoes;
	
	private int valorDaPenalidade;
	
	@Temporal(value=TemporalType.DATE)
	private Date dataDeRealizacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public int getQuestoes() {
		return questoes;
	}

	public void setQuestoes(int questoes) {
		this.questoes = questoes;
	}

	public Gabarito getGabarito() {
		return gabarito;
	}

	public void setGabarito(Gabarito gabarito) {
		this.gabarito = gabarito;
	}

	public List<Nota> getNotas() {
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public int getValorDaPenalidade() {
		return valorDaPenalidade;
	}

	public void setValorDaPenalidade(int valorDapenalidade) {
		this.valorDaPenalidade = valorDapenalidade;
	}

	public Date getDataDeRealizacao() {
		return dataDeRealizacao;
	}

	public void setDataDeRealizacao(Date dataDeRealizacao) {
		this.dataDeRealizacao = dataDeRealizacao;
	}

	public Simulado getSimulado() {
		return simulado;
	}

	public void setSimulado(Simulado simulado) {
		this.simulado = simulado;
	}
}