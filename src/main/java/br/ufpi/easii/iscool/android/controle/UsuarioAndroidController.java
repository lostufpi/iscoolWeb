package br.ufpi.easii.iscool.android.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.android.entidades.AlunoAndroid;
import br.ufpi.easii.iscool.android.entidades.DisciplinaAluno;
import br.ufpi.easii.iscool.android.entidades.DisciplinaProfessor;
import br.ufpi.easii.iscool.android.entidades.ProfessorAndroid;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Professor;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.enuns.Privacidade;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
public class UsuarioAndroidController {

	private final UsuarioDao usuarios;
	private final DisciplinaDao disciplinasDao;
	private final Result result;
	
	protected UsuarioAndroidController(){
		this(null, null, null, null);
	}

	@Inject
	public UsuarioAndroidController(UsuarioDao usuarios, Result result, Validator validator, DisciplinaDao disciplinaDao){
		this.usuarios = usuarios;
		this.result = result;
		this.disciplinasDao = disciplinaDao;
	}

	@Post
	public void login(String login, String senha){
		Usuario usuario = usuarios.pesquisarUsuarioPorEmailESenha(login, GeradorDeSenha.generateHash(senha));
		if(usuario == null){
			result.use(Results.json()).withoutRoot().from(0).serialize();
		}
		if(usuario instanceof Professor){
			List<Disciplina> disciplinas = disciplinasDao.listarDisciplinasPorProfessor(usuario.getId()); 
			List<DisciplinaProfessor> disciplinasProfessor = new ArrayList<DisciplinaProfessor>();
			for(Disciplina d : disciplinas){
				DisciplinaProfessor disciplinaProfessor = new DisciplinaProfessor(d.getId(), d.getNome(), d.getTurma().getTurno().toString(), d.getTurma().getSerie(), d.getTurma().getEscola().getNome());
				disciplinasProfessor.add(disciplinaProfessor);
			}
			ProfessorAndroid professorAndroid = new ProfessorAndroid(usuario.getId(), usuario.getNome(), usuario.isAluno(), disciplinasProfessor);
			result.use(Results.json()).withoutRoot().from(professorAndroid).recursive().serialize();	
		} 
		if(usuario instanceof Aluno){
			List<Disciplina> disciplinas = disciplinasDao.listarDisciplinasPorTurma(((Aluno) usuario).getTurma().getId());
			List<DisciplinaAluno> disciplinasAluno = new ArrayList<DisciplinaAluno>();
			for(Disciplina d : disciplinas){
				DisciplinaAluno disciplinaAluno = new DisciplinaAluno(d.getId(), d.getNome(), d.getTurma().getTurno().toString(), d.getTurma().getSerie(), "Professor João");
				disciplinasAluno.add(disciplinaAluno);
			}
			AlunoAndroid alunoAndroid = new AlunoAndroid(usuario.getId(), usuario.getNome(), ((Aluno) usuario).getTurma().getSerie(), ((Aluno) usuario).getTurma().getNome(), ((Aluno) usuario).getTurma().getTurno().toString(), ((Aluno) usuario).getTurma().getEscola().getNome(), usuario.isAluno(), disciplinasAluno);
			result.use(Results.json()).withoutRoot().from(alunoAndroid).recursive().serialize();
		}
	}
	
	@Post
	public void lista(long idDisciplina){
		List<Aluno> alunos = usuarios.listarAlunoPorTurma(disciplinasDao.pesquisarDisciplinaPorId(idDisciplina).getTurma().getId());
		List<AlunoAndroid> alunosAndroid = new ArrayList<AlunoAndroid>();
		AlunoAndroid alunoAndroid;
		for(Aluno a: alunos){
			alunoAndroid = new AlunoAndroid(a.getId(), a.getNome(), a.getTurma().getSerie(),
					a.getTurma().getNome(), a.getTurma().getTurno().toString(), a.getTurma().getEscola().getNome(),
					a.isAluno(), null);
			alunosAndroid.add(alunoAndroid);
		}
		if(alunosAndroid.size() > 0){
			result.use(Results.json()).withoutRoot().from(alunosAndroid).recursive().serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(0).recursive().serialize();
		}
	}
	
	@Post
	public void atualizarToken(String idUser, String token){
		long id = Long.parseLong(idUser);
		Usuario usuario = usuarios.pesquisarUsuarioPorId(id);
		usuario.setToken(token);
		usuarios.alterarUsuario(usuario);
		result.use(Results.json()).from("OK").serialize();
	}
	
	@Post
	public void alterarPrivacidade(long idAluno, String privacidade){
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idAluno);
		if(privacidade.equals("I")){
			aluno.setPrivacidade(Privacidade.INVISIVEL);
		} else if(privacidade.equals("V")){
			aluno.setPrivacidade(Privacidade.VISIVEL);
		} else {
			aluno.setPrivacidade(Privacidade.OCULTO);
		}
		usuarios.alterarPrivacidade(aluno);
		result.use(Results.json()).withoutRoot().from("OK").serialize();
	}
	
	public void removerToken(String idUser){
		Usuario usuario = usuarios.pesquisarUsuarioPorId(Long.parseLong(idUser));	
		usuario.setToken(null);
		usuarios.alterarUsuario(usuario);
	}
}