package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Nota;

@RequestScoped
public class NotaDao {

	private final Session session;
	
	protected NotaDao(){
		this(null);
	}
	
	@Inject
	public NotaDao(Session session){
		this.session = session;
	}
	
	public void inserirNota(Nota nota){
		this.session.saveOrUpdate(nota);
	}
	
	public void atualizarNota(Nota nota){
		this.session.update(nota);
	}
	
	@SuppressWarnings("unchecked")
	public List<Nota> listarNotasPorProva(long idDaProva){
		return this.session.createCriteria(Nota.class)
				.add(Restrictions.eq("prova.id", idDaProva))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Nota> listarNotasPorAluno(long idDoAluno){
		return this.session.createCriteria(Nota.class)
				.add(Restrictions.eq("aluno.id", idDoAluno))
				.list();
	}
	
	public Nota listarNotas(long idProva, long idAluno){
		return (Nota) this.session.createCriteria(Nota.class)
				.add(Restrictions.eq("aluno.id", idAluno))
				.add(Restrictions.eq("prova.id", idProva))
				.uniqueResult();
	}
	
	public Nota pesquisarNota(long idDoAluno, long idDaProva){
		return (Nota) this.session.createCriteria(Nota.class)
				.add(Restrictions.eq("aluno.id", idDoAluno))
				.add(Restrictions.eq("prova.id", idDaProva))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Nota> listarNotasPorOrdem(long idDaProva){
		return this.session.createCriteria(Nota.class)
				.add(Restrictions.eq("prova.id", idDaProva))
				.addOrder(Order.desc("porcentagemDeAcerto"))
				.list();
	}
}