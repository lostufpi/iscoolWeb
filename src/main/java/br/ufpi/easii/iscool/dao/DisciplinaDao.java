package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Disciplina;

@RequestScoped
public class DisciplinaDao {

	private final Session session;

	public DisciplinaDao(){
		this(null);
	}

	@Inject
	public DisciplinaDao(Session session){
		this.session = session;
	}

	public void inserirDisciplina(Disciplina disciplina){
		this.session.save(disciplina);
	}
	
	public Disciplina pesquisarDisciplinaPorId(long id){
		return (Disciplina) this.session.createCriteria(Disciplina.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	public boolean pesquisarDisciplinaExistente(String nome, String descricao, long idDaTurma){
		return this.session.createCriteria(Disciplina.class)
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("descricao", descricao))
				.add(Restrictions.eq("turma.id", idDaTurma))
				.uniqueResult() != null;
	}
	
	public boolean pesquisarDisciplinaDeProfessorExistente(String nome, String descricao, long idDoProfessor){
		return this.session.createCriteria(Disciplina.class)
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("descricao", descricao))
				.add(Restrictions.eq("professor.id", idDoProfessor))
				.add(Restrictions.eq("turma", null))
				.uniqueResult() != null;
	}
	
	public void atualizarDisciplina(Disciplina disciplina){
		this.session.update(disciplina);
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> listarDisciplinasPorTurma(long id){
		return this.session.createCriteria(Disciplina.class)
				.add(Restrictions.eq("turma.id", id))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> listarDisciplinasPorProfessor(long id){
		return this.session.createCriteria(Disciplina.class)
				.add(Restrictions.eq("professor.id", id))
				.list();
	}
	
	public void excluirDisciplina(Disciplina disciplina){
		this.session.delete(disciplina);
	}
}

