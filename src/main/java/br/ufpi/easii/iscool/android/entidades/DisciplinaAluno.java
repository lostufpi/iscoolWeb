package br.ufpi.easii.iscool.android.entidades;

public class DisciplinaAluno {
	private long id;
	private String nome;
	private String serie;
	private String escola;
	private String nomeProfessor;
	
	public DisciplinaAluno(long id, String nome, String serie, String escola, String nomeProfessor) {
		this.id = id;
		this.nome = nome;
		this.serie = serie;
		this.escola = escola;
		this.nomeProfessor = nomeProfessor;
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
	
	public String getEscola() {
		return escola;
	}
	
	public void setEscola(String escola) {
		this.escola = escola;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}
}