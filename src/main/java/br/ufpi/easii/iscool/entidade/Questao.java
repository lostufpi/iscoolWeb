package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufpi.easii.iscool.enuns.Letra;

@Entity
public class Questao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
	
	@Lob
	private String explicacao;
	
	@NotNull
	private int numero;
	
	private int numeroDeAcertos;
	
	private int numeroDeRespostas;
	
	@Enumerated(EnumType.STRING)
	private Letra resposta;
	
	@ManyToOne
	@JoinColumn(name = "id_prova")
	private Prova prova;
	
	@ManyToOne
	@JoinColumn(name = "id_gabarito")
	private Gabarito gabarito;
	
	@OneToMany(mappedBy = "questao", targetEntity = Resposta.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Resposta> respostas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Letra getResposta() {
		return resposta;
	}

	public void setResposta(Letra resposta) {
		this.resposta = resposta;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Gabarito getGabarito() {
		return gabarito;
	}

	public void setGabarito(Gabarito gabarito) {
		this.gabarito = gabarito;
	}

	public int getNumeroDeAcertos() {
		return numeroDeAcertos;
	}

	public void setNumeroDeAcertos(int numeroDeAcertos) {
		this.numeroDeAcertos = numeroDeAcertos;
	}
	
	public void acertou(){
		this.numeroDeAcertos = this.numeroDeAcertos + 1;
	}
	
	public void incrementaResposta(){
		this.numeroDeRespostas = this.numeroDeRespostas + 1;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostasCorretas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public String getExplicacao() {
		return explicacao;
	}

	public void setExplicacao(String explicacao) {
		this.explicacao = explicacao;
	}

	public int getNumeroDeRespostas() {
		return numeroDeRespostas;
	}

	public void setNumeroDeRespostas(int numeroDeRespostas) {
		this.numeroDeRespostas = numeroDeRespostas;
	}
}