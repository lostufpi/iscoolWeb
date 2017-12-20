package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Prova;

@RequestScoped
public class ProvaDao {

	private final Session session;
	
	protected ProvaDao(){
		this(null);
	}
	
	@Inject
	public ProvaDao(Session session){
		this.session = session;
	}
	
	public void inserirProva(Prova prova){
		this.session.save(prova);
	}
	
	@SuppressWarnings("unchecked")
	public List<Prova> listarProvasPorDisciplina(long id){
		return this.session.createCriteria(Prova.class)
				.add(Restrictions.eq("disciplina.id", id))
				.list();
	}
	
	public Prova pesquisarProvaPorId(long id){
		return (Prova) this.session.createCriteria(Prova.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	public void atualizarProva(Prova prova){
		this.session.update(prova);
		
	}
	
	public void excluirProva(Prova prova){
		this.session.delete(prova);
	}
}
