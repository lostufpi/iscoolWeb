package br.ufpi.easii.iscool.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Simulado;

@RequestScoped
public class SimuladoDao {
	private final Session session;
	
	protected SimuladoDao(){
		this(null);
	}
	
	@Inject
	public SimuladoDao(Session session){
		this.session = session;
	}
	
	public void inserirSimulado(Simulado simulado){
		this.session.saveOrUpdate(simulado);
	}
	
	public Simulado pesquisarSimuladoPorId(long id){
		return (Simulado) this.session.createCriteria(Simulado.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
}
