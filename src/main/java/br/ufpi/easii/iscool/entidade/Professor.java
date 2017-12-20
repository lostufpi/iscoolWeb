package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import br.ufpi.easii.iscool.enuns.NivelDeFormacao;

@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")

public class Professor extends Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.ORDINAL)
	public NivelDeFormacao nivelDeFormacao;
	
	private String curriculo;
	
	@OneToMany(mappedBy = "professor", targetEntity = Curso.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Curso> cursos = new ArrayList<Curso>();

	public Professor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NivelDeFormacao getNivelDeFormacao() {
		return nivelDeFormacao;
	}

	public void setNivelDeFormacao(NivelDeFormacao nivelDeFormacao) {
		this.nivelDeFormacao = nivelDeFormacao;
	}
	
	@Embedded
	private DadosPessoais dadosPessoais;
	
	@Embedded
	private Endereco endereco;

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public void setDadosPessoais(DadosPessoais dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}
	
	public String nomeProfessor(){
		return this.getNome();
	}
}