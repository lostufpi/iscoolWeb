package br.ufpi.easii.iscool.android.controle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.android.entidades.Media;
import br.ufpi.easii.iscool.android.entidades.NotaAndroid;
import br.ufpi.easii.iscool.dao.NotaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.entidade.Nota;
import br.ufpi.easii.iscool.entidade.Prova;

@Controller
public class NotaAndroidController {
	private final NotaDao notasDao;
	private final ProvaDao provasDao;
	private final Result result;
	
	protected NotaAndroidController(){
		this(null, null, null);
	}

	@Inject
	public NotaAndroidController(NotaDao notasDao, Result result, ProvaDao provasDao){
		this.notasDao = notasDao;
		this.result = result;
		this.provasDao = provasDao;
	}

	@Post
	public void exibirNota(String idAluno, String idProva){
		Nota nota = notasDao.pesquisarNota(Long.parseLong(idAluno), Long.parseLong(idProva));
		if(nota != null){
			NotaAndroid notaAndroid = new NotaAndroid(nota.getId(), nota.getNota(), nota.getNumeroDeErros(), nota.getPorcentagemDeAcerto(),
			nota.getQuestoesSemResposta(), nota.getProva().getNome(), nota.getProva().getId());
			result.use(Results.json()).withoutRoot().from(notaAndroid).serialize();
		}
		else
			result.use(Results.json()).withoutRoot().from(0).serialize();	
	}
	
	@Post
	public void graficoMediaTurma(String idDaDisciplina){
		List<Prova> provasDaDisciplina = provasDao.listarProvasPorDisciplina(Long.parseLong(idDaDisciplina));
		Media media;
		float soma = 0;
		List<Media> mediaDaTurma = new ArrayList<Media>();
		
		for(Prova p : provasDaDisciplina){
			soma = 0;
			if(p.getNotas().size() > 0){
				for(Nota n : p.getNotas())
					soma = (float) (soma + n.getPorcentagemDeAcerto());
				media = new Media(p.getNome(), (float) soma/p.getNotas().size());
				mediaDaTurma.add(media);
			}
		}
		if(mediaDaTurma.size() > 0)
			result.use(Results.json()).withoutRoot().from(mediaDaTurma).serialize();
		else
			result.use(Results.json()).withoutRoot().from(0).serialize();
	}

	@Post
	public void graficoMediaAluno(String idDaDisciplina, String idDoAluno){
		List<Prova> listaProvas = provasDao.listarProvasPorDisciplina(Long.parseLong(idDaDisciplina));
		List<Media> mediasDoAluno = new ArrayList<Media>();
		Media media;
		
		for(Prova p : listaProvas){
			if(notasDao.listarNotas(p.getId(), Long.parseLong(idDoAluno)) != null){
				Nota nota = notasDao.listarNotas(p.getId(), Long.parseLong(idDoAluno));
				media = new Media(p.getNome(),(float) nota.getPorcentagemDeAcerto());
				mediasDoAluno.add(media);
			}
		}
		if(mediasDoAluno.size() != 0)
			result.use(Results.json()).withoutRoot().from(mediasDoAluno).serialize();
		else
			result.use(Results.json()).withoutRoot().from(0).serialize();
	}
}