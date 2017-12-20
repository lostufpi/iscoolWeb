package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Resposta;

@RequestScoped
public class RespostaDao {

	private final Session session;

	protected RespostaDao(){
		this(null);
	}

	@Inject
	public RespostaDao(Session session){
		this.session = session;
	}

	public void inserirResposta(Resposta resposta){
		this.session.saveOrUpdate(resposta);
	}
	
	public void atualizarResposta(Resposta resposta){
		this.session.update(resposta);
	}

	@SuppressWarnings("unchecked")
	public List<Resposta> listarRespostasPorProvaEAluno(long idDoAluno, long idDaProva){
		return session.createCriteria(Resposta.class)
				.add(Restrictions.eq("aluno.id", idDoAluno))
				.add(Restrictions.eq("prova.id", idDaProva))
				.addOrder(Order.asc("numero"))
				.list();
	}
	
	public Resposta pesquisarResposta(long idDoAluno, long idDaProva, int numero){
		return (Resposta) session.createCriteria(Resposta.class)
				.add(Restrictions.eq("aluno.id", idDoAluno))
				.add(Restrictions.eq("prova.id", idDaProva))
				.add(Restrictions.eq("numero", numero))
				.uniqueResult();
	}
}
