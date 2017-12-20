package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Curso;

@RequestScoped
public class CursoDao {

	private final Session session;
	
	public CursoDao(){
		this(null);
	}

	@Inject
	public CursoDao(Session session){
		this.session = session;
	}
	
	public void inserirCurso(Curso curso){
		this.session.save(curso);
	}
	
	public Curso pesquisarCursoPorId(long id){
		return (Curso) this.session.createCriteria(Curso.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Curso> pesquisarCursoPorProfessor(long id){
		return this.session.createCriteria(Curso.class)
				.add(Restrictions.eq("professor.id", id)).list();
	}
	
	public void excluirCurso(Curso curso){
		this.session.delete(curso);
	}
}
