package br.ufpi.easii.iscool.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import br.ufpi.easii.iscool.enuns.Privacidade;

@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Aluno extends Usuario{

	private static final long serialVersionUID = 1L;
	
	private String cpfResponsavel;
	
	@Enumerated(EnumType.STRING)
	private Privacidade privacidade;
	
	@OneToMany(mappedBy = "aluno", targetEntity = Nota.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Nota> notas = new ArrayList<Nota>();
	
	@OneToMany(mappedBy = "aluno", targetEntity = Resposta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Resposta> respostas = new ArrayList<>();
	
	@OneToMany(mappedBy = "aluno", targetEntity = Insignia.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Insignia> insigneas;
	
	@ManyToOne
	@JoinColumn(name = "id_turma")
	private Turma turma;
	
	@ManyToOne
	@JoinColumn(name = "id_responsavel")
	private Responsavel responsavel;
	
	@Embedded
	private DadosPessoais dadosPessoais;
	
	@Embedded
	private Endereco endereco;

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
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

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Privacidade getPrivacidade() {
		return privacidade;
	}

	public void setPrivacidade(Privacidade privacidade) {
		this.privacidade = privacidade;
	}
}