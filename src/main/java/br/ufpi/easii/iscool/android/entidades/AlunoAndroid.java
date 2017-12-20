package br.ufpi.easii.iscool.android.entidades;

import java.util.List;

public class AlunoAndroid {
	private long id;
	private String nome;
	private String serie;
	private String descricaoDaTurma;
	private String turno;
	private String escola;
	private boolean aluno;
	private List<DisciplinaAluno> disciplinas;
	
	public AlunoAndroid(long id, String nome, String serie, String descricaoDaTurma, String turno, String escola, boolean aluno,
			List<DisciplinaAluno> disciplinas) {
		this.id = id;
		this.nome = nome;
		this.serie = serie;
		this.descricaoDaTurma = descricaoDaTurma;
		this.turno = turno;
		this.escola = escola;
		this.aluno = aluno;
		this.disciplinas = disciplinas;
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

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDescricaoDaTurma() {
		return descricaoDaTurma;
	}

	public void setDescricaoDaTurma(String descricaoDaTurma) {
		this.descricaoDaTurma = descricaoDaTurma;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

	public List<DisciplinaAluno> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<DisciplinaAluno> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public boolean isAluno() {
		return aluno;
	}

	public void setAluno(boolean aluno) {
		this.aluno = aluno;
	}
}