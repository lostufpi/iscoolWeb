package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Escola;

@RequestScoped
public class EscolaDao {

	private final Session session;
	
	protected EscolaDao(){
		this(null);
	}
	
	@Inject
	public EscolaDao(Session session){
		this.session = session;
	}
	
	//Insere nova escola no banco de dados
	public void inserirEscolar(Escola escola){
		System.out.println("Id: " + escola.getGestorEscolar().getNome() + " " + escola.getGestorEscolar().getId());
		this.session.save(escola);
	}
	
	//Pesquisa uma escola por seu numero de id
	public Escola pesquisarEscolaPorId(long id){
		return (Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	//Pesquisa se uma escola pertencente a uma rede já existe no banco de dados e retorna true se já existir
	public boolean pesquisarEscolaExistente(String identificadorNaRede, String nome, long idDaRede){
		Escola escola = (Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("identificadorNaRede", identificadorNaRede))
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("redeEscolar.id", idDaRede))
				.uniqueResult();
		return escola != null;
	}
	
	//Pesquisa se uma escola que não pertence a nenhuma rede já existe no banco de dados e retorna true se já existir
	public boolean pesquisarEscolaExistente(String identificadorNaRede, String nome){
		Escola escola = (Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("identificadorNaRede", identificadorNaRede))
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("redeEscolar.id", null))
				.uniqueResult();
		return escola != null;
	}
	
	//Pesquisa um escola existente em uma rede passando todos os seus dados
	public Escola pesquisarEscolaExistente(String identificadorNaRede, String nome, String razaoSocial, String idDaRede, String cnpj){
		Escola escola = (Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("identificadorNaRede", identificadorNaRede))
				.add(Restrictions.eq("nome", nome))
				.add(Restrictions.eq("redeEscolar.id", idDaRede))
				.add(Restrictions.eq("dadosCadastrais.razaoSocial", razaoSocial))
				.add(Restrictions.eq("dadosCadastrais.cnpj", cnpj))
				.uniqueResult();
		return escola;
	}
	
	//Pesquisa uma escola através de seu cnpj e razão social e se já existir retorna true
	public boolean pesquisarEscolaPorCnpjOuRazaoSocial(String cnpj, String razaoSocial){
		if((Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("dadosCadastrais.cnpj", cnpj)).uniqueResult() 
				!= null 
				|| (Escola) this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("dadosCadastrais.razaoSocial", razaoSocial)).uniqueResult() 
				!= null){
			return true;
		}
		return false;
	}
	
	//Lista todas as escolas pertencentes a uma determinada rede
	@SuppressWarnings("unchecked")
	public List<Escola> listarEscolasPorIdDaRede(long idDaRede){
		return this.session.createCriteria(Escola.class)
				.add(Restrictions.eq("redeEscolar.id", idDaRede))
				.list();
	}
	
	//Atualiza a escola passada por parametro
	public void atualizarEscola(Escola escola){
		if(pesquisarEscolaPorCnpjOuRazaoSocial(escola.getDadosCadastrais().getCnpj(), escola.getDadosCadastrais().getRazaoSocial()) == false){
			this.session.update(escola);
		}
	}
	
	//Exclui a escola passada por parametro
	public void excluirEscola(Escola escola){
		this.session.delete(escola);
	}
}