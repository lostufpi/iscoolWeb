package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * @author Alexandre
 */

@Entity
public class Desafio implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6818699886029519336L;

	private String nome;
	
	@Enumerated(EnumType.STRING)
	private TipoDesafio tipoDeDesafio;
	
	@OneToMany(mappedBy = "desafio", targetEntity = Aluno.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Aluno> participantes = new ArrayList<Aluno>();
	
	@OneToMany(mappedBy = "desafio", targetEntity = Aluno.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Aluno> vendedores = new ArrayList<Aluno>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoDesafio getTipoDeDesafio() {
		return tipoDeDesafio;
	}

	public void setTipoDeDesafio(TipoDesafio tipoDeDesafio) {
		this.tipoDeDesafio = tipoDeDesafio;
	}

	public List<Aluno> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Aluno> participantes) {
		this.participantes = participantes;
	}

	public List<Aluno> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<Aluno> vendedores) {
		this.vendedores = vendedores;
	}
}