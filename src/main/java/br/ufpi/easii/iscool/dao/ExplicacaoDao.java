package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Explicacao;

@RequestScoped
public class ExplicacaoDao {
	
	private final Session session;
	
	public ExplicacaoDao() {
		this(null);
	}
	
	@Inject
	public ExplicacaoDao(Session session) {
		this.session = session;
	}
	
	public void inserirExplicacao(Explicacao explicacao) {
		this.session.saveOrUpdate(explicacao);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Explicacao> listarPorProvaAluno(long idAluno) {
		return this.session.createCriteria(Explicacao.class)
				.add(Restrictions.eq("aluno.id", idAluno))
				.list();
	}
}