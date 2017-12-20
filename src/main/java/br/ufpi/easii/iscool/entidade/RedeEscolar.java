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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RedeEscolar implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@NotNull
	private String nome;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GestorDeRede gestorDeRede;
	
	@OneToMany(mappedBy = "redeEscolar", targetEntity = Escola.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Escola> escolas = new ArrayList<>();
	
	@Embedded
	private DadosCadastrais dadosCadastrais;
	
	@Embedded
	private Endereco endereco;
	
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

	public final GestorDeRede getGestorDeRede() {
		return gestorDeRede;
	}

	public final void setGestorDeRede(GestorDeRede gestorDeRede) {
		this.gestorDeRede = gestorDeRede;
	}

	public List<Escola> getEscolas() {
		return escolas;
	}

	public void setEscolas(List<Escola> escolas) {
		this.escolas = escolas;
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
