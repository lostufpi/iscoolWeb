package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Questao;

@RequestScoped
public class QuestaoDao {

	private final Session session;
	
	protected QuestaoDao(){
		this(null);
	}
	
	@Inject
	public QuestaoDao(Session session){
		this.session = session;
	}
	
	public void inserirQuestao(Questao questao){
		this.session.save(questao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Questao> listarQuestoesPorProva(long id){
		return this.session.createCriteria(Questao.class)
				.add(Restrictions.eq("prova.id", id))
				.addOrder(Order.asc("numero"))
				.list();
	}
	
	public void atualizarQuestao(Questao questao){
		this.session.update(questao);
	}
	
	public void excluirQuestao(Questao questao){
		this.session.delete(questao);
	}
	
	public Questao pesquisarQuestao(long idDoGabarito, long idDaProva, int numero){
		return (Questao) this.session.createCriteria(Questao.class)
				.add(Restrictions.eq("gabarito.id", idDoGabarito))
				.add(Restrictions.eq("prova.id", idDaProva))
				.add(Restrictions.eq("numero", numero))
				.uniqueResult();
	}
	
	public Questao pesquisarQuestaoPorId(long id){
		return (Questao) this.session.createCriteria(Questao.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
}