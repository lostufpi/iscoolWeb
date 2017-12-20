package br.ufpi.easii.iscool.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Feed;
import br.ufpi.easii.iscool.entidade.FeedEscolar;

@RequestScoped
public class FeedDao {

	private final Session session;
	
	public FeedDao() {
		this(null);
	}
	
	@Inject
	public FeedDao(Session session) {
		this.session = session;
	}
	
	public void inserirFeed(Feed feed) {
		this.session.save(feed);
	}
	
	@SuppressWarnings("unchecked")
	public List<Feed> listarTodos() {
		return this.session.createCriteria(Feed.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Feed> listarMax(int numero, long idEscola) {
		return (ArrayList<Feed>) this.session.createCriteria(FeedEscolar.class)
				.add(Restrictions.eq("escola.id", idEscola))
				.setMaxResults(numero)
				.list();
	}
}