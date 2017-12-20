package br.ufpi.easii.iscool.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;

import br.ufpi.easii.iscool.entidade.Gabarito;

@RequestScoped
public class GabaritoDao {

	private final Session session;

	protected GabaritoDao(){
		this(null);
	}

	@Inject
	public GabaritoDao(Session session){
		this.session = session;
	}

	public void inserirGabarito(Gabarito gabarito){
		this.session.save(gabarito);
	}
}
