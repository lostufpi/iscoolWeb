package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Escola implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@Embedded
	private DadosCadastrais dadosCadastrais;
	
	@Embedded
	private Endereco endereco;
	
	@NotNull
	private String identificadorNaRede;
	
	@ManyToOne
	@JoinColumn(name = "id_rede")
	private RedeEscolar redeEscolar;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GestorEscolar gestorEscolar;

	@OneToMany(mappedBy = "escola", targetEntity = Turma.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Turma> turmas = new ArrayList<>();
	
	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final String getIdentificadorNaRede() {
		return identificadorNaRede;
	}

	public final void setIdentificadorNaRede(String identificadorNaRede) {
		this.identificadorNaRede = identificadorNaRede;
	}

	public final RedeEscolar getRedeEscolar() {
		return redeEscolar;
	}

	public final void setRedeEscolar(RedeEscolar redeEscolar) {
		this.redeEscolar = redeEscolar;
	}

	public final GestorEscolar getGestorEscolar() {
		return gestorEscolar;
	}

	public final void setGestorEscolar(GestorEscolar gestorEscolar) {
		this.gestorEscolar = gestorEscolar;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public DadosCadastrais getDadosCadastrais() {
		return dadosCadastrais;
	}

	public void setDadosCadastrais(DadosCadastrais dadosCadastrais) {
		this.dadosCadastrais = dadosCadastrais;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}