package br.ufpi.easii.iscool.controle;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.RespostaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Resposta;

@Controller
public class RespostaController {

	private RespostaDao respostas;
	private Result result;
	private ProvaDao provas;
	private UsuarioDao alunos;
	private NotaDao notas;
	
	protected RespostaController(){
		this(null, null, null, null, null);
	}
	
	@Inject
	public RespostaController(RespostaDao respostas, Result result, ProvaDao provas, UsuarioDao alunos, NotaDao notas){
		this.respostas = respostas;
		this.result = result;
		this.provas = provas;
		this.alunos = alunos;
		this.notas = notas;
	}
	
	@Get
	public List<Resposta> listarRespostas(long idDoAluno, long idDaProva){
		result.include("prova", provas.pesquisarProvaPorId(idDaProva));
		result.include("aluno", (Aluno) alunos.pesquisarUsuarioPorId(idDoAluno));
		result.include("notas", notas.pesquisarNota(idDoAluno, idDaProva));
		return respostas.listarRespostasPorProvaEAluno(idDoAluno, idDaProva);
	}
	
	@Get
	public List<Resposta> listarRespostasPorAluno(long idDoAluno, long idDaProva){
		result.include("prova", provas.pesquisarProvaPorId(idDaProva));
		result.include("respostasCorretas", provas.pesquisarProvaPorId(idDaProva).getGabarito().getQuestoes());
		result.include("nota", notas.pesquisarNota(idDoAluno, idDaProva));
		result.include("numeroDeQuestoes", provas.pesquisarProvaPorId(idDaProva).getGabarito().getQuestoes().size());
		result.include("aluno", (Aluno) alunos.pesquisarUsuarioPorId(idDoAluno));
		return respostas.listarRespostasPorProvaEAluno(idDoAluno, idDaProva);
	}
}
