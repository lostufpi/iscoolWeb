package br.ufpi.easii.iscool.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.InsigniaDao;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Insignia;
import br.ufpi.easii.iscool.entidade.InsigniaDisciplina;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.util.NotificacaoUtil;

@Controller
public class InsigniaController {
	private Result result;
	private InsigniaDao insignias;
	private ProvaDao provas;
	private NotaDao notas;

	protected InsigniaController(){
		this(null, null, null, null);
	}

	@Inject
	public InsigniaController(Result result, InsigniaDao insignias, ProvaDao provas, NotaDao notas){
		this.result = result;
		this.insignias = insignias;
		this.provas = provas;
		this.notas = notas;
	}

	@Post
	public void listarInsigniasPorAluno(long idAluno){
		List<Insignia> insigniasList = insignias.listarInsigniasPorAluno(idAluno);
		if(insigniasList.size() == 0)
			result.use(Results.json()).withoutRoot().from(0).serialize();
		else 
			result.use(Results.json()).withoutRoot().from(insigniasList).serialize();
	}

	@Get("/insignia/{idAluno}/{codigo}")
	public void pesquisarInsignia(long idAluno, String codigo){
		Insignia insignia = insignias.pesquisarInsignia(idAluno, codigo);
		if(insignia != null){
			result.use(Results.json()).withoutRoot().from(insignia).serialize();
		}else{
			result.use(Results.status()).created();
		}
	}

	public void inserirInsigniasPorProva(Nota nota){
		inserirInsigniaPorNota(nota);
		insigniaPorProvaAnterior(nota);
	}

	public void inserirInsigniaPorNota(Nota nota){
		InsigniaDisciplina insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(1, nota.getProva().getDisciplina().getId());
		String codigo = null;
		String mensagem = null;
		String nome = null;
		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() >= 100){
				codigo = insigniaDisciplina.getImagem();
				mensagem = insigniaDisciplina.getMensagem();
				nome = insigniaDisciplina.getNome();

				if(insignias.pesquisarInsignia(nota.getAluno().getId(), codigo) != null){
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
				}else{
					Insignia insignia = new Insignia();
					insignia.setAluno(nota.getAluno());
					insignia.setCodigo(codigo);
					insignia.setDescricao(mensagem);
					insignia.setNome(nome);
					insignia.setQuantidade(1);
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
					insignias.inserirInsignia(insignia);
				}
			}
		}
		insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(2, nota.getProva().getDisciplina().getId());
		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() >= 90 && nota.getPorcentagemDeAcerto() < 100){
				codigo = insigniaDisciplina.getImagem();
				mensagem = insigniaDisciplina.getMensagem();
				nome = insigniaDisciplina.getNome();

				if(insignias.pesquisarInsignia(nota.getAluno().getId(), codigo) != null){
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
				}else{
					Insignia insignia = new Insignia();
					insignia.setAluno(nota.getAluno());
					insignia.setCodigo(codigo);
					insignia.setDescricao(mensagem);
					insignia.setNome(nome);
					insignia.setQuantidade(1);
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
					insignias.inserirInsignia(insignia);
				}
			}
		}
		insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(3, nota.getProva().getDisciplina().getId());
		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() >= 80 && nota.getPorcentagemDeAcerto() < 90){
				codigo = insigniaDisciplina.getImagem();
				mensagem = insigniaDisciplina.getMensagem();
				nome = insigniaDisciplina.getNome();

				if(insignias.pesquisarInsignia(nota.getAluno().getId(), codigo) != null){
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
				}else{
					Insignia insignia = new Insignia();
					insignia.setAluno(nota.getAluno());
					insignia.setCodigo(codigo);
					insignia.setDescricao(mensagem);
					insignia.setNome(nome);
					insignia.setQuantidade(1);
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
					insignias.inserirInsignia(insignia);
				}
			}
		}
		insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(4, nota.getProva().getDisciplina().getId());
		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() >= 70 && nota.getPorcentagemDeAcerto() < 80){
				codigo = insigniaDisciplina.getImagem();
				mensagem = insigniaDisciplina.getMensagem();
				nome = insigniaDisciplina.getNome();

				if(insignias.pesquisarInsignia(nota.getAluno().getId(), codigo) != null){
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
				}else{
					Insignia insignia = new Insignia();
					insignia.setAluno(nota.getAluno());
					insignia.setCodigo(codigo);
					insignia.setDescricao(mensagem);
					insignia.setNome(nome);
					insignia.setQuantidade(1);
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
					insignias.inserirInsignia(insignia);
				}
			}
		}
		insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(6, nota.getProva().getDisciplina().getId());
		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() < 70){
				codigo = insigniaDisciplina.getImagem();
				mensagem = insigniaDisciplina.getMensagem();
				nome = insigniaDisciplina.getNome();

				if(insignias.pesquisarInsignia(nota.getAluno().getId(), codigo) != null){
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
				}else{
					Insignia insignia = new Insignia();
					insignia.setAluno(nota.getAluno());
					insignia.setCodigo(codigo);
					insignia.setDescricao(mensagem);
					insignia.setNome(nome);
					insignia.setQuantidade(1);
					NotificacaoUtil.enviarNotificacao(mensagem, nota.getAluno().token, nome, codigo);
					insignias.inserirInsignia(insignia);
				}
			}
		}

	}

	public void insigniaPorProvaAnterior(Nota nota){
		Disciplina disciplina = provas.pesquisarProvaPorId(nota.getProva().getId()).getDisciplina();

		if(insignias.pesquisarInsigniaDisciplina(5, disciplina.getId()) != null){
			InsigniaDisciplina insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(5, disciplina.getId());
			List<Nota> notasAluno = new ArrayList<Nota>();
			Nota n = new Nota();

			for(Prova p : disciplina.getProvas()){
				n = notas.pesquisarNota(nota.getAluno().getId(), p.getId());
				notasAluno.add(n);
			}

			for(int i = 1; i < notasAluno.size(); i++){
				if(notasAluno.get(i) != null){
					if(notasAluno.get(i).getProva().getId() == nota.getProva().getId()){
						if(notasAluno.get(i).getPorcentagemDeAcerto() > notasAluno.get(i-1).getPorcentagemDeAcerto()){
							if(insignias.pesquisarInsignia(nota.getAluno().getId(), insigniaDisciplina.getImagem()) != null){
								NotificacaoUtil.enviarNotificacao(insigniaDisciplina.getMensagem(), nota.getAluno().token, insigniaDisciplina.getNome(), insigniaDisciplina.getImagem());
							}else{
								Insignia insignia = new Insignia();
								insignia.setAluno(nota.getAluno());
								insignia.setCodigo(insigniaDisciplina.getImagem());
								insignia.setDescricao(insigniaDisciplina.getMensagem());
								insignia.setNome(insigniaDisciplina.getNome());
								insignia.setQuantidade(1);
								NotificacaoUtil.enviarNotificacao(insigniaDisciplina.getMensagem(), nota.getAluno().token, insigniaDisciplina.getNome(), insigniaDisciplina.getImagem());
								insignias.inserirInsignia(insignia);
							}
							enviarNotificacaoESalvarInsignia(nota.getAluno(), insigniaDisciplina.getImagem(), insigniaDisciplina.getMensagem(), insigniaDisciplina.getNome());
						}
					}
				}
			}

		}
	}

	@Get
	public void insignias(long id){
		List<Insignia> insigniasList = insignias.listarInsigniasPorAluno(id);
		result.include("insigniasList", insigniasList);
	}

	public void inserirInsigniaPorMedia(Nota nota){
		Prova prova = nota.getProva();
		InsigniaDisciplina insigniaDisciplina = insignias.pesquisarInsigniaDisciplina(4, prova.getDisciplina().getId()); 
		double media = 0;
		String codigo = null; 
		String mensagem = null;
		String nome = null; 

		for(Nota n : prova.getNotas()){
			media = media + n.getPorcentagemDeAcerto();
		}

		media = media / prova.getNotas().size();

		if(insigniaDisciplina != null){
			if(nota.getPorcentagemDeAcerto() >= 70.0){
				if(nota.getPorcentagemDeAcerto() >= media){
					codigo = insigniaDisciplina.getImagem();
					mensagem = insigniaDisciplina.getMensagem();
					nome = insigniaDisciplina.getNome();
				}
			}
			enviarNotificacaoESalvarInsignia(nota.getAluno(), codigo, mensagem, nome);
		}
	}

	public void inserirInsigniaAlunoNota10(Nota nota){
		if(nota.getPorcentagemDeAcerto() == 100){
			Prova prova = nota.getProva();
			int qnt = 0;
			long id = nota.getAluno().getId();
			for(Nota n : prova.getNotas()){
				if(n.getAluno().getId() != id){
					qnt++;
					break;
				}
			}
			if(qnt == 0){
				String codigo;
				String mensagem;
				String nome;
				codigo = "s6";
				mensagem = "Parabéns, você foi o aluno nota 10 dessa prova! Vá até seu perfil e confira sua nova insígnia!";
				nome = "Aluno nota 10";
				enviarNotificacaoESalvarInsignia(nota.getAluno(), codigo, mensagem, nome);
			}
		}
	}

	public void enviarNotificacaoESalvarInsignia(Aluno aluno, String codigo, String mensagem, String nome){
		if(insignias.pesquisarInsignia(aluno.getId(), codigo) != null){
			NotificacaoUtil.enviarNotificacao(mensagem, aluno.getToken(), nome, codigo);
		}else{
			Insignia insignia = new Insignia();
			insignia.setAluno(aluno);
			insignia.setCodigo(codigo);
			insignia.setDescricao(mensagem);
			insignia.setNome(nome);
			insignia.setQuantidade(1);
			mensagem = "Uuuual, você consquistou uma nova medalha, vá até seu perfil e confira!";
			NotificacaoUtil.enviarNotificacao(mensagem, aluno.getToken(), nome, codigo);
			insignias.inserirInsignia(insignia);
		}
	}
}