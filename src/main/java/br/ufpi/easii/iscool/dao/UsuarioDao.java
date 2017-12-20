package br.ufpi.easii.iscool.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.GestorDeRede;
import br.ufpi.easii.iscool.entidade.GestorEscolar;
import br.ufpi.easii.iscool.entidade.Professor;
import br.ufpi.easii.iscool.entidade.Usuario;

@RequestScoped
public class UsuarioDao {

	private final Session session;

	protected UsuarioDao(){
		this(null);
	}

	@Inject
	public UsuarioDao(Session session){
		this.session = session;
	}
	
	public void alterarPrivacidade(Aluno aluno){
		this.session.update(aluno);
	}

	//Inserir usuário ainda não existente no banco de dados e retorna true se conseguir persistir o usuário
	public void inserirUsuario(Usuario usuario){
		try{
			this.session.save(usuario);
		}catch(Exception e){
			System.out.println("Erro ao tentar salvar usuário");
			System.out.println(e.getMessage());
		}
	}

	//Pesquisa usuário por email e retorna boolean caso já exista no banco
	public boolean pesquisarUsuarioPorEmail(Usuario usuario){
		Usuario u = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
		return u != null;
	}
	
	public Usuario pesquisarUsuarioPorEmail(String email){
		Usuario u = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		return u;
	}

	//Pesquisa um usuário por email e senha e retorna o mesmo
	public Usuario pesquisarUsuarioPorEmailESenha(String email, String senha){
		Usuario u = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("senha", senha))
				.uniqueResult();
		return u;
	}

	//pesquisa um usuário por Id único e retorna o mesmo
	public Usuario pesquisarUsuarioPorId(long id){
		Usuario u = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		return u;
	}
	
	public void alterarUsuario(Usuario usuario){
		this.session.update(usuario);
	}

	@SuppressWarnings("unchecked")
	//Lista todos os usuários
	public List<Usuario> listarUsuario(){
		return this.session.createCriteria(Usuario.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	//Lista todos os usuários do tipo gestor de rede
	public List<Usuario> listaGestoresDeRede(){
		return this.session.createCriteria(GestorDeRede.class).list();
	}

	@SuppressWarnings("unchecked")
	//Lista todos os usuários do tipo gestor de escola
	public List<Usuario> listaGestoresDeEscola(){
		return this.session.createCriteria(GestorEscolar.class).list();
	}

	@SuppressWarnings("unchecked")
	//Lista todos os usuários do tipo professor
	public List<Usuario> listaProfessores(){
		return this.session.createCriteria(Professor.class).list();
	}

	@SuppressWarnings("unchecked")
	//Lista todos os usuários do tipo aluno
	public List<Usuario> listaAlunos(){
		return this.session.createCriteria(Aluno.class).list();
	}

	//Atualiza a instância de um usuário já existente no banco de dados
	public void atualizarUsuario(Usuario usuario){
		this.session.update(usuario);
	}

	//Deleta um usuário já existente no banco de dados
	public void excluirUsuario(Usuario usuario){
		this.session.delete(usuario);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listarAlunosPorTurma(long id){
		return this.session.createCriteria(Aluno.class)
				.add(Restrictions.eq("turma.id", id))
				.list();
	}
		
	public Professor pesquisarProfessorPorEmail(String email){
		return (Professor) this.session.createCriteria(Professor.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> listarAlunoPorTurma(long id){
		return (List<Aluno>) this.session.createCriteria(Aluno.class)
				.add(Restrictions.eq("turma.id", id))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Aluno> listarFilhos(String cpf){
		return this.session.createCriteria(Aluno.class)
				.add(Restrictions.eq("cpfResponsavel", cpf))
				.list();
	}
}
