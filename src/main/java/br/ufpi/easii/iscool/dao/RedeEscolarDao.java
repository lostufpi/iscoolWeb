package br.ufpi.easii.iscool.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.RedeEscolar;

@RequestScoped
public class RedeEscolarDao {

	private final Session session;
	
	protected RedeEscolarDao(){
		this(null);
	}
	
	@Inject
	public RedeEscolarDao(Session session){
		this.session = session;
	}
	
	//Inserir no banco de dados uma rede que ainda não consta no banco
	public void inserirRedeEscolar(RedeEscolar rede){
		this.session.save(rede);
	}
	
	//Pesquisar rede pelo seu id
	public RedeEscolar pesquisarRedeEscolarPorId(long id){
		return (RedeEscolar) this.session.createCriteria(RedeEscolar.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	//Atualizar rede escolar já existente no banco
	public void atualizarRedeEscolar(RedeEscolar rede){
		this.session.update(rede);
	}
	
	public boolean pesquisarRedePorCnpjOuRazaoSocial(String cnpj, String razaoSocial){
		if(this.session.createCriteria(RedeEscolar.class).add(Restrictions.eq("dadosCadastrais.cnpj", cnpj)).uniqueResult() != null ||
				this.session.createCriteria(RedeEscolar.class).add(Restrictions.eq("dadosCadastrais.razaoSocial", razaoSocial)).uniqueResult() != null){
			return true;
		}
		return false;
	}
}