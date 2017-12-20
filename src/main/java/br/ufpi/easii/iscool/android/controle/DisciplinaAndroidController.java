package br.ufpi.easii.iscool.android.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
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

@Controller
public class DisciplinaAndroidController {
	private DisciplinaDao disciplinasDao;
	private UsuarioDao usuarios;
	private final Result result;
	
	protected DisciplinaAndroidController(){
		this(null, null, null);
	}
	
	@Inject
	public DisciplinaAndroidController(DisciplinaDao disciplinasDao, UsuarioDao usuarios, Result result){
		this.disciplinasDao = disciplinasDao;
		this.usuarios = usuarios;
		this.result = result;
	}
	
	@Get("/disciplina/listar/{idUsuario}")
	public void listarDisciplinas(long idUsuario){
		System.out.println("Id: " + idUsuario);
		Usuario usuario = usuarios.pesquisarUsuarioPorId(idUsuario);
		if(usuario instanceof Aluno){
			List<Disciplina> disciplinas = disciplinasDao.listarDisciplinasPorTurma(((Aluno) usuario).getTurma().getId());
			List<DisciplinaAluno> disciplinasAluno = new ArrayList<DisciplinaAluno>();
			for(Disciplina d : disciplinas){
				DisciplinaAluno disciplinaAluno = new DisciplinaAluno(d.getId(), d.getNome(), d.getTurma().getTurno().toString(), d.getTurma().getSerie(), "");
				disciplinasAluno.add(disciplinaAluno);
			}
			AlunoAndroid alunoAndroid = new AlunoAndroid(usuario.getId(), usuario.getNome(), ((Aluno) usuario).getTurma().getSerie(), ((Aluno) usuario).getTurma().getNome(), ((Aluno) usuario).getTurma().getTurno().toString(), ((Aluno) usuario).getTurma().getEscola().getNome(), usuario.isAluno(), disciplinasAluno);
			result.use(Results.json()).withoutRoot().from(alunoAndroid).recursive().serialize();
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
	}
}
