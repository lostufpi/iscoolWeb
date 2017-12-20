package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.util.GraficoNotasPorProva;

@Controller
public class NotaController {
	private NotaDao notas;
	private ProvaDao provas;
	private UsuarioDao alunos;
	private DisciplinaDao disciplinas;
	private Result result;
	
	protected NotaController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public NotaController(NotaDao notas, ProvaDao provas, UsuarioDao alunos, DisciplinaDao disciplinas, Result result){
		this.notas = notas;
		this.result = result;
		this.provas = provas;
		this.alunos = alunos;
		this.disciplinas = disciplinas;
	}
	
	@Get
	public List<Nota> listarNotasPorProva(long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		List<Nota> listNota = notas.listarNotasPorProva(idDaProva);
		result.include("prova", prova);
		result.include("numeroDeQuestoes", prova.getGabarito().getQuestoes().size());
		return listNota;
	}
	
	@Get
	public List<Nota> listarNotasPorAlunoEDisciplina(long idDaDisciplina, long idDoAluno){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(idDaDisciplina);
		ArrayList<Nota> listaDeNotas = new ArrayList<Nota>();
		Nota nota = null;
		for(Prova p : disciplina.getProvas()){
			nota = notas.pesquisarNota(idDoAluno, p.getId());
			listaDeNotas.add(nota);
		}
		result.include("disciplina", disciplina);
		return listaDeNotas;
	}
	
	@Get
	public List<Nota> listarNotasPorAluno(long idDoAluno, long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		Aluno aluno = (Aluno) alunos.pesquisarUsuarioPorId(idDoAluno);
		ArrayList<Nota> listaDeNotas = new ArrayList<Nota>();
		Nota nota;
		for(Prova p : prova.getDisciplina().getProvas()){
			nota  = notas.pesquisarNota(idDoAluno, p.getId());
			listaDeNotas.add(nota);
		}
		result.include("prova", prova);
		result.include("aluno", aluno);
		return listaDeNotas;
	}
	
	@Get("nota/{idDaProva}/graficos")
	public void graficos(long idDaProva){
		GraficoNotasPorProva grafico = new GraficoNotasPorProva();
		grafico.setTitulo("Média do alunos");
		result.use(Results.json()).withoutRoot().from(grafico).serialize();
	}
}