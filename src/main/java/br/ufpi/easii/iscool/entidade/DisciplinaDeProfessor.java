package br.ufpi.easii.iscool.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_disciplinaDeProfessor")
public class DisciplinaDeProfessor extends Disciplina{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	private List<Aluno> alunos = new ArrayList<>();

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
}