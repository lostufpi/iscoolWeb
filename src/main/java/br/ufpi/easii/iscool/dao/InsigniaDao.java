package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Insignia;
import br.ufpi.easii.iscool.entidade.InsigniaDefault;
import br.ufpi.easii.iscool.entidade.InsigniaDisciplina;

@RequestScoped
public class InsigniaDao {
	private final Session session;
	
	protected InsigniaDao(){
		this(null);
	}
	
	@Inject
	public InsigniaDao(Session session){
		this.session = session;
	}
	
	public void inserirInsignia(Insignia insignia){
		this.session.saveOrUpdate(insignia);
	}
	
	public void inserirInsigniaDisciplina(InsigniaDisciplina insignia){
		this.session.saveOrUpdate(insignia);
	}
	
	public void deletarInsigniaDisciplina(InsigniaDisciplina insigniaDisciplina){
		this.session.delete(insigniaDisciplina);
	}
	
	@SuppressWarnings("unchecked")
	public List<Insignia> listarInsigniasPorAluno(long idAluno){
		return this.session.createCriteria(Insignia.class)
				.add(Restrictions.eq("aluno.id", idAluno))
				.list();
	}
	
	public Insignia pesquisarInsignia(long idAluno, String codigo){
		return (Insignia) this.session.createCriteria(Insignia.class)
				.add(Restrictions.eq("aluno.id", idAluno))
				.add(Restrictions.eq("codigo", codigo))
				.uniqueResult();
	}
	
	public InsigniaDisciplina pesquisarInsigniaDisciplina(long idInsignia, long idDisciplina){
		return (InsigniaDisciplina) this.session.createCriteria(InsigniaDisciplina.class)
				.add(Restrictions.eq("insigniaDefault.id", idInsignia))
				.add(Restrictions.eq("disciplina.id", idDisciplina))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<InsigniaDefault> listarTodas(){
		return this.session.createCriteria(InsigniaDefault.class)
				.list();
	}
	
	public InsigniaDefault pesquisarInsigniaDefault(long id){
		return (InsigniaDefault) this.session.createCriteria(InsigniaDefault.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<InsigniaDisciplina> listarInsigniasDisciplina(long idDisciplina){
		return this.session.createCriteria(InsigniaDisciplina.class)
				.add(Restrictions.eq("disciplina.id", idDisciplina))
				.list();
	}

	public InsigniaDisciplina pesquisarInsigniaDisciplina(long id) {
		return (InsigniaDisciplina) this.session.createCriteria(InsigniaDisciplina.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}
}