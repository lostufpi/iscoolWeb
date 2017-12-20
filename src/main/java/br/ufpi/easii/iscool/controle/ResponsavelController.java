package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.android.entidades.AlunoAndroid;
import br.ufpi.easii.iscool.android.entidades.DisciplinaAluno;
import br.ufpi.easii.iscool.android.entidades.NotaAndroid;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;

@Controller
public class ResponsavelController {
	private UsuarioDao usuarios;
	private DisciplinaDao disciplinasDao;
	private NotaDao notasDao;
	private ProvaDao provasDao;
	private Result result;
	
	protected ResponsavelController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public ResponsavelController(UsuarioDao usuarios, Result result, DisciplinaDao disciplinasDao, NotaDao notasDao, ProvaDao provasDao){
		this.usuarios = usuarios;
		this.result = result;
		this.disciplinasDao = disciplinasDao;
		this.notasDao = notasDao;
		this.provasDao = provasDao;
	}
	
	@Post
	public void login(String cpf){
		ArrayList<Aluno> filhos = (ArrayList<Aluno>) usuarios.listarFilhos(cpf);
		if(filhos.size() == 0)
			result.use(Results.json()).withoutRoot().from(0).serialize();
		else 
			result.use(Results.json()).withoutRoot().from(1).serialize();
	}
	
	@Post
	public void listarFilhos(String cpf){
		ArrayList<Aluno> alunos = (ArrayList<Aluno>) usuarios.listarFilhos(cpf);
		ArrayList<AlunoAndroid> filhos = new ArrayList<>();
		AlunoAndroid alunoAndroid;
		for(Aluno a : alunos){
			alunoAndroid = new AlunoAndroid(a.getId(), a.getNome(), a.getTurma().getSerie(),
					a.getTurma().getNome(), a.getTurma().getTurno().toString(), a.getTurma().getEscola().getNome(),
					a.isAluno(), null);
			filhos.add(alunoAndroid);
		}
		if(filhos.size() == 0)
			result.use(Results.json()).withoutRoot().from(0).serialize();
		else 
			result.use(Results.json()).withoutRoot().from(filhos).serialize();
	}
	
	@Post
	public void listaDisciplinas(long idFilho){
		System.out.println("ID FILHO: " + idFilho);
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idFilho);
		List<Disciplina> disciplinas = disciplinasDao.listarDisciplinasPorTurma(aluno.getTurma().getId());
		List<DisciplinaAluno> disciplinasAluno = new ArrayList<DisciplinaAluno>();
		for(Disciplina d : disciplinas){
			DisciplinaAluno disciplinaAluno = new DisciplinaAluno(d.getId(), d.getNome(), d.getTurma().getTurno().toString(), d.getTurma().getSerie(), "Professor João");
			disciplinasAluno.add(disciplinaAluno);
		}
		if(disciplinasAluno.size() == 0)
			result.use(Results.json()).withoutRoot().from(0).serialize();
		else
			result.use(Results.json()).withoutRoot().from(disciplinasAluno).serialize();
	}
	
	@Post
	public void listaNotas(long idDisciplina, long idFilho){
		ArrayList<Prova> provas = (ArrayList<Prova>) provasDao.listarProvasPorDisciplina(idDisciplina);
		ArrayList<NotaAndroid> notas = new ArrayList<NotaAndroid>();
		Nota nota =  new Nota();
		NotaAndroid notaAndroid = null;
		for(Prova p : provas){
			nota = notasDao.pesquisarNota(idFilho, p.getId());
			if(nota != null){
				notaAndroid = new NotaAndroid(nota.getId(), nota.getNota(), nota.getNumeroDeErros(), nota.getPorcentagemDeAcerto(), nota.getQuestoesSemResposta(), p.getNome(), p.getId());
				notas.add(notaAndroid);
			}
		}
		if(notas.size() == 0)
			result.use(Results.json()).withoutRoot().from(0).serialize();
		else 
			result.use(Results.json()).withoutRoot().from(notas).serialize();
	}
}