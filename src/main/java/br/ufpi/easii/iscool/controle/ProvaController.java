package br.ufpi.easii.iscool.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.GabaritoDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.DisciplinaDeProfessor;
import br.ufpi.easii.iscool.entidade.Gabarito;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Resposta;
import br.ufpi.easii.iscool.entidade.Usuario;
import br.ufpi.easii.iscool.enuns.Letra;
import br.ufpi.easii.iscool.sessao.ProfessorLogado;

@Controller
public class ProvaController {

	private ProvaDao provas;
	private UsuarioDao usuarios;
	private Result result;
	private DisciplinaDao disciplinas;
	private QuestaoDao questoes;
	private GabaritoDao gabaritos;
	private RespostaDao respostas;
	private NotaDao notas;

	protected ProvaController(){
		this(null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public ProvaController(DisciplinaDao disciplinas, ProvaDao provas, Result result, UsuarioDao usuarios, QuestaoDao questoes, GabaritoDao gabaritos, RespostaDao respostas, NotaDao notas, ProfessorLogado professorLogado){
		this.disciplinas = disciplinas;
		this.result = result;
		this.provas = provas;
		this.usuarios = usuarios;
		this.questoes = questoes;
		this.gabaritos = gabaritos;
		this.respostas = respostas;
		this.notas = notas;
	}

	@Get
	public Disciplina inserirProva(long id){
		return disciplinas.pesquisarDisciplinaPorId(id);
	}

	@Post
	public void inserirProva(Prova prova, long id, String dataDaProva){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dataUsuario = sdf.parse(dataDaProva);
			System.out.println("Time em milissegundos: " + dataUsuario.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gabarito gabarito = new Gabarito();

		prova.setDisciplina(disciplina);
		prova.setGabarito(gabarito);
		try {
			prova.setDataDeRealizacao(sdf.parse(dataDaProva));
		} catch (ParseException e) {
			System.out.println("Erro na conversão de data");
		}
		gabarito.setProva(prova);

		gabaritos.inserirGabarito(gabarito);
		provas.inserirProva(prova);


		result.include("mensagem", "Prova cadastrada com sucesso!");
		result.redirectTo(DisciplinaController.class).acessarDisciplina(id);
	}

	@Get
	public List<Usuario> listarAlunosPorProva(long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);

		if(prova.getDisciplina() instanceof DisciplinaDeProfessor){
			DisciplinaDeProfessor disciplina = (DisciplinaDeProfessor) prova.getDisciplina();
			System.out.println("Disciplina de professor: " + disciplina.getNome());
			result.include("prova", prova);
		}else if(prova.getDisciplina().getTurma() != null){
			long idDaTurma = prova.getDisciplina().getTurma().getId();
			result.include("prova", prova);
			return usuarios.listarAlunosPorTurma(idDaTurma);
		}
		result.include("prova", prova);
		return null;
	}

	@Get
	public void corrigirProva(long idDoAluno, long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		List<Questao> listaQuestoes = (List<Questao>) questoes.listarQuestoesPorProva(idDaProva); 
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idDoAluno);

		result.include("aluno", aluno);
		result.include("lista", listaQuestoes);
		result.include("prova", prova);
	}

	@Post
	public void corrigirQuestao(long idDaProva, long idDoAluno, Resposta resposta){
		int numero = resposta.getNumero();
		Letra correta = resposta.getResposta();

		if(respostas.pesquisarResposta(idDoAluno, idDaProva, numero) == null){
			resposta.setAluno((Aluno) usuarios.pesquisarUsuarioPorId(idDoAluno));
			resposta.setProva(provas.pesquisarProvaPorId(idDaProva));
			respostas.inserirResposta(resposta);
		}else{
			Resposta r = respostas.pesquisarResposta(idDoAluno, idDaProva, numero);
			r.setResposta(correta);
			respostas.inserirResposta(r);
		}
		result.redirectTo(ProvaController.class).corrigirProva(idDoAluno, idDaProva);
	}

	@Get
	public void criarGabarito(long idDoAluno, long idDaProva){
		Gabarito gabarito = new Gabarito();
		Prova prova = provas.pesquisarProvaPorId(idDaProva);

		gabarito.setProva(prova);

		gabaritos.inserirGabarito(gabarito);
		result.redirectTo(ProvaController.class).listarAlunosPorProva(idDaProva);
	}

	@Post
	public void calcularNota(long idDoAluno, long idDaProva){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);
		Nota nota = new Nota();
		int valor = 0;
		ArrayList<Questao> questoesDaProva = (ArrayList<Questao>) questoes.listarQuestoesPorProva(idDaProva);
		ArrayList<Resposta> respostasDaProva = (ArrayList<Resposta>) respostas.listarRespostasPorProvaEAluno(idDoAluno, idDaProva);
		int size = questoesDaProva.size();

		for(int i = 0;i < size; i++){
			if(questoesDaProva.get(i).getResposta() == respostasDaProva.get(i).getResposta()) 
				valor++;
		}

		if(notas.pesquisarNota(idDoAluno, idDaProva) == null){
			nota.setAluno((Aluno) usuarios.pesquisarUsuarioPorId(idDoAluno));
			nota.setProva(prova);
			nota.setNota(valor);
			notas.inserirNota(nota);
		}else{
			nota = notas.pesquisarNota(idDoAluno, idDaProva);
			nota.setNota(valor);
			notas.inserirNota(nota);
		}
		result.redirectTo(DisciplinaController.class).acessarDisciplina(prova.getDisciplina().getId());
	}

	@Get
	public Prova acessarProva(long idDaProva, long idDoAluno){
		Prova prova = provas.pesquisarProvaPorId(idDaProva);

		result.include("gabarito", questoes.listarQuestoesPorProva(idDaProva));
		result.include("respostas", respostas.listarRespostasPorProvaEAluno(idDoAluno, idDaProva));
		result.include("nota", notas.pesquisarNota(idDoAluno, idDaProva));

		return prova;
	}

	@Delete("prova/{id}")
	public void excluirProva(long id){
		Prova prova = provas.pesquisarProvaPorId(id);
		long idDaDisciplina = prova.getDisciplina().getId();

		provas.excluirProva(prova);
		result.redirectTo(DisciplinaController.class).acessarDisciplina(idDaDisciplina);
	}
}