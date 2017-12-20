package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.InsigniaDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.AlunoRanking;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.InsigniaDefault;
import br.ufpi.easii.iscool.entidade.InsigniaDisciplina;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.enuns.Privacidade;
import br.ufpi.easii.iscool.sessao.AlunoLogado;
import br.ufpi.easii.iscool.util.NotificacaoUtil;

@Controller
public class GamificationController {

	private UsuarioDao usuarios;
	private NotaDao notas;
	private ProvaDao provas;
	private DisciplinaDao disciplinas;
	private Result result;
	private InsigniaDao insigniasDao;
	private AlunoLogado alunoLogado;

	protected GamificationController(){
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public GamificationController(NotaDao notas, Result result, UsuarioDao usuarios, ProvaDao provas, DisciplinaDao disciplinas, AlunoLogado alunoLogado, InsigniaDao insigniasDao){
		this.notas = notas;
		this.result = result;
		this.usuarios = usuarios;
		this.provas = provas;
		this.disciplinas = disciplinas;
		this.insigniasDao = insigniasDao;
		this.alunoLogado = alunoLogado;
	}

	@Post
	public void listaXPPorProva(long id){
		List<Nota> listNotas = notas.listarNotasPorOrdem(id);
		List<AlunoRanking> ranking = new ArrayList<AlunoRanking>();
		double media = 0;
		int i = 1;
		int xpAnterior = 0;

		for(Nota n : listNotas){
			media = media + n.getPorcentagemDeAcerto();
		}
		media = media / listNotas.size();

		for(int j = 0; j < listNotas.size(); j++){
			if(j != 0){
				if(xpAnterior > calculaXPGeral(listNotas.get(j), media))
					i++;
			}
			xpAnterior = calculaXPGeral(listNotas.get(j), media);
			if(xpAnterior >= 0)
				ranking.add(new AlunoRanking(i, listNotas.get(j).getAluno().getNome(), xpAnterior, listNotas.get(j).getAluno().getId()));
			else
				ranking.add(new AlunoRanking(i, listNotas.get(j).getAluno().getNome(), 0, listNotas.get(j).getAluno().getId()));
		}
		result.use(Results.json()).withoutRoot().from(ranking).serialize();
	}
	
	@Get("/{id}")
	public void listaXPPorDisciplina(long id){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		List<AlunoRanking> ranking = new ArrayList<AlunoRanking>();
		int xp = 0;
		Nota nota = null;
		for(Aluno a : disciplina.getTurma().getAlunos()){
			for(Prova p : disciplina.getProvas()){
				nota = notas.pesquisarNota(a.getId(), p.getId());
				if(nota.getPorcentagemDeAcerto() != 0){
					xp = (int) (xp + nota.getPorcentagemDeAcerto());
				}
			}
			ranking.add(new AlunoRanking(1, a.getNome(), xp,a.getId()));
			xp = 0;
		}
		result.use(Results.json()).withoutRoot().from(ranking).serialize();
	}

	public int calcularXPPorNota(Double nota, long idDaDisciplina){
		InsigniaDisciplina insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(1, idDaDisciplina);
		if(insigniaDisciplina != null){
			if(nota == 100){
				return insigniaDisciplina.getPontuacao();
			}
		}
		insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(2, idDaDisciplina);
		if(insigniaDisciplina != null){
			if(nota >= 90 && nota < 100){
				return insigniaDisciplina.getPontuacao();
			}
		}
		insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(3, idDaDisciplina);
		if(insigniaDisciplina != null){
			if(nota >= 80 && nota < 90){
				return insigniaDisciplina.getPontuacao();
			}
		}
		insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(4, idDaDisciplina);
		if(insigniaDisciplina != null){
			if(nota >= 70 && nota < 80){
				return insigniaDisciplina.getPontuacao();
			}
		}
		return 0;
	}

	public int calculaXPPorQuestoesSemResposta(int qtdDeQuestoesSemResposta){
		return -(qtdDeQuestoesSemResposta * 3);
	}

	public int calculaXPPorMedia(double nota, double media, Nota n){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(n.getProva().getDisciplina().getId());
		InsigniaDisciplina insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(4, disciplina.getId());
		if(insigniaDisciplina != null){
			if(nota >= 70.0){
				if(nota >= media){
					return insigniaDisciplina.getPontuacao();
				}
			}
		}
		return 0;
	}

	public int calcularXPPorProvaAnterior(long idProva, long idAluno){
		Disciplina disciplina = provas.pesquisarProvaPorId(idProva).getDisciplina();

		if(insigniasDao.pesquisarInsigniaDisciplina(5, disciplina.getId()) != null){
			List<Nota> notasAluno = new ArrayList<Nota>();
			Nota nota = new Nota();

			for(Prova p : disciplina.getProvas()){
				nota = notas.pesquisarNota(idAluno, p.getId());
				notasAluno.add(nota);
			}

			for(int i = 1; i < notasAluno.size(); i++){
				if(notasAluno.get(i) != null){
					if(notasAluno.get(i).getProva().getId() == idProva){
						if(notasAluno.get(i).getPorcentagemDeAcerto() > notasAluno.get(i-1).getPorcentagemDeAcerto())
							return insigniasDao.pesquisarInsigniaDisciplina(11, disciplina.getId()).getPontuacao();
					}
				}
			}

		}
		return 0;
	}

	public int calculaXPGeral(Nota nota, double media){
		int xp = 0;
		xp = xp + calcularXPPorNota(nota.getPorcentagemDeAcerto(), nota.getProva().getDisciplina().getId());
		xp = xp + calculaXPPorMedia(nota.getPorcentagemDeAcerto(), media, nota);
		xp = xp + calcularXPPorProvaAnterior(nota.getProva().getId(), nota.getAluno().getId());
		salvarXP(nota, xp);
		return xp;
	}

	@Get
	public void rankingPorProva(long id){
		List<AlunoRanking> ranking = null;
		Prova prova = provas.pesquisarProvaPorId(id);
		if(alunoLogado.getAluno().getPrivacidade() != Privacidade.INVISIVEL){
			List<Nota> listNotas = notas.listarNotasPorOrdem(id);
			ranking = new ArrayList<AlunoRanking>();			
			double media = 0;
			int i = 1;
			int xpAnterior = 0;

			for(Nota n : listNotas){
				media = media + n.getPorcentagemDeAcerto();
			}
			media = media / listNotas.size();

			for(int j = 0; j < listNotas.size(); j++){
				if(j != 0){
					if(xpAnterior > calculaXPGeral(listNotas.get(j), media))
						i++;
				}
				xpAnterior = calculaXPGeral(listNotas.get(j), media);
				if(xpAnterior >= 0){
					ranking.add(new AlunoRanking(i, listNotas.get(j).getAluno().getNome(), xpAnterior, listNotas.get(j).getAluno().getId()));
				} else {
					ranking.add(new AlunoRanking(i, listNotas.get(j).getAluno().getNome(), 0, listNotas.get(j).getAluno().getId()));
				}
			}
			result.include("ranking", ranking);	
		}else{
			result.include("prova", prova);
		}
	}

	public void salvarXP(Nota nota, int xp){
		Nota n = notas.pesquisarNota(nota.getAluno().getId(), nota.getProva().getId());
		n.setXP(xp);
		notas.inserirNota(n);
	}

	@Get
	public void rankingPorDisciplina(long id){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		result.include("disciplina", disciplina);
	}

	@Get("/gamificacao/enviarNotificacao/{idAluno}")
	public void enviarNotificacao(long idAluno){
		Aluno aluno = (Aluno) usuarios.pesquisarUsuarioPorId(idAluno);
		String token = aluno.getToken();
		String message = "Parabéns, mais uma vez você tirou um dez consecutivo. Continue assim!";
		String title = "Uuuuau, confira sua nova insígnea";

		NotificacaoUtil.enviarNotificacao(message, token, title, "");
	}

	@Get
	public void gamificacao(long id){
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(id);
		List<InsigniaDefault> insignias = insigniasDao.listarTodas();
		List<InsigniaDisciplina> insigniasD = insigniasDao.listarInsigniasDisciplina(id);
		result.include("insignias", insignias);
		result.include("insigniasD", insigniasD);
		result.include("disciplina", disciplina);
	}

	@Get
	public void configurarInsignia(long id, long idDisciplina){
		InsigniaDefault insigniaDefault = insigniasDao.pesquisarInsigniaDefault(id);
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(idDisciplina);
		result.include("insignia", insigniaDefault);
		result.include("disciplina", disciplina);
	}

	@Get
	public void suasInsignias(long id){
		List<InsigniaDisciplina> insigniasD = insigniasDao.listarInsigniasDisciplina(id);
		result.include("disciplina", disciplinas.pesquisarDisciplinaPorId(id));
		result.include("insigniasD", insigniasD);
	}

	@Post
	public void salvarInsignia(String nomeDaInsignia, String mensagemDaInsignia, long idDisciplina, long idInsignia, int pontuacaoDaInsignia){
		InsigniaDefault insigniaDefault = insigniasDao.pesquisarInsigniaDefault(idInsignia);
		Disciplina disciplina = disciplinas.pesquisarDisciplinaPorId(idDisciplina);
		InsigniaDisciplina insigniaDisciplina = new InsigniaDisciplina();
		insigniaDisciplina.setDisciplina(disciplina);
		insigniaDisciplina.setInsigniaDefault(insigniaDefault);
		insigniaDisciplina.setMensagem(mensagemDaInsignia);
		insigniaDisciplina.setNome(nomeDaInsignia);
		insigniaDisciplina.setImagem(insigniaDefault.getImagem());
		insigniaDisciplina.setPontuacao(pontuacaoDaInsignia);
		if(insigniasDao.pesquisarInsigniaDisciplina(idInsignia, idDisciplina) == null){
			insigniasDao.inserirInsigniaDisciplina(insigniaDisciplina);
		}
		result.redirectTo(GamificationController.class).gamificacao(idDisciplina);
	}

	@Delete("insignia/{id}")
	public void deletar(long id){
		InsigniaDisciplina insigniaDisciplina = insigniasDao.pesquisarInsigniaDisciplina(id);
		insigniasDao.deletarInsigniaDisciplina(insigniaDisciplina);
		result.include("mensagem", "Insígnia removida com sucesso!");
		result.redirectTo(GamificationController.class).suasInsignias(insigniaDisciplina.getDisciplina().getId());
	}

}