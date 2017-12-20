package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufpi.easii.iscool.enuns.Turno;

@Entity
public class Turma implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "id_escola")
	private Escola escola;
	
	@NotNull
	private String serie;
	
	@NotNull
	private String nome;
	
	@OneToMany(mappedBy = "turma", targetEntity = Aluno.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Aluno> alunos;
	
	@OneToMany(mappedBy = "turma", targetEntity = Disciplina.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private Turno turno;

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}

	public final Escola getEscola() {
		return escola;
	}

	public final void setEscola(Escola escola) {
		this.escola = escola;
	}

	public final String getSerie() {
		return serie;
	}

	public final void setSerie(String serie) {
		this.serie = serie;
	}

	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final Turno getTurno() {
		return turno;
	}

	public final void setTurno(Turno turno) {
		this.turno = turno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}