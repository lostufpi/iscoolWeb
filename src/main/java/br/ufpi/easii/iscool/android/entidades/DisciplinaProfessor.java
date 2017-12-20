package br.ufpi.easii.iscool.android.entidades;

public class DisciplinaProfessor {
	private long id;
	private String nome;
	private String turno;
	private String serie;
	private String escola;
	
	public DisciplinaProfessor(long id, String nome, String turno, String serie, String escola) {
		this.id = id;
		this.nome = nome;
		this.turno = turno;
		this.serie = serie;
		this.escola = escola;
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
	
	public String getTurno() {
		return turno;
	}
	
	public void setTurno(String turno) {
		this.turno = turno;
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
}