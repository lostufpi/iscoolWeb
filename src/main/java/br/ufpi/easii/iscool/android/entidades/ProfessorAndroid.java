package br.ufpi.easii.iscool.android.entidades;

import java.util.List;

public class ProfessorAndroid {
	private long id;
	private String nome;
	private boolean aluno;
	private List<DisciplinaProfessor> disciplinas;
	
	public ProfessorAndroid(long id, String nome, boolean aluno, List<DisciplinaProfessor> disciplinas) {
		this.id = id;
		this.nome = nome;
		this.disciplinas = disciplinas;
		this.aluno = aluno;
	}

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
	
	public List<DisciplinaProfessor> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<DisciplinaProfessor> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public boolean isAluno() {
		return aluno;
	}

	public void setProfessor(boolean aluno) {
		this.aluno = aluno;
	}
}