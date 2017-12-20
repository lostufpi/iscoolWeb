package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Turma;
import br.ufpi.easii.iscool.enuns.Turno;

@RequestScoped
public class TurmaDao {

private final Session session;
	
	protected TurmaDao(){
		this(null);
	}
	
	@Inject
	public TurmaDao(Session session){
		this.session = session;
	}
	
	public void inserirTurma(Turma turma){
		this.session.save(turma);
	}
	
	public Turma pesquisarTurmaPorId(long id){
		return (Turma) this.session.createCriteria(Turma.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	public boolean pesquisarTurmaExistente(String serie, String nomeDaTurma, Turno turno, long idDaEscola){
		Turma turma  = (Turma) this.session.createCriteria(Turma.class)
				.add(Restrictions.eq("serie", serie))
				.add(Restrictions.eq("nome", nomeDaTurma))
				.add(Restrictions.eq("turno", turno))
				.add(Restrictions.eq("escola.id", idDaEscola))
				.uniqueResult();
		return turma != null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Turma> listarTurmasPorEscola(long idDaEscola){
		return this.session.createCriteria(Turma.class)
				.add(Restrictions.eq("escola.id", idDaEscola))
				.list();
	}
	
	public void excluirTurma(Turma turma){
		this.session.delete(turma);
	}
	
	public void atualizarTurma(Turma turma){
		this.session.update(turma);
	}
}