package br.ufpi.easii.iscool.entidade;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_disciplina")
public class DisciplinaDeTurma extends Disciplina{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
