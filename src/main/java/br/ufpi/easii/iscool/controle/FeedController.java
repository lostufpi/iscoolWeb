package br.ufpi.easii.iscool.controle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.EscolaDao;
import br.ufpi.easii.iscool.dao.FeedDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Aluno;
import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.Feed;
import br.ufpi.easii.iscool.entidade.FeedEscolar;
import br.ufpi.easii.iscool.util.ImagemUtil;

@Controller
public class FeedController {
	private Result result;
	private FeedDao feedDao;
	private EscolaDao escolaDao;
	private UsuarioDao usuarioDao;
	
	protected FeedController() {
		this(null, null, null, null);
	}
	
	@Inject
	public FeedController(Result result, FeedDao feedDao, EscolaDao escolaDao, UsuarioDao usuarioDao) {
		this.result = result;
		this.feedDao = feedDao;
		this.escolaDao = escolaDao;
		this.usuarioDao = usuarioDao;
	}
	
	@Get
	public void inserir() {
		
	}
	
	
	@Post
	public void inserir(UploadedFile uploadedFile, final Feed feed) throws IOException {
		BufferedImage bufferedImage = ImagemUtil.retornaBufferedImage(uploadedFile);
		Date data = new Date();
		String dataString = Long.toString(data.getTime());
		String url = "http://localhost:8080/teste/feed/fotofeed-" + dataString + ".png";
		
		File sourceImage = new File("C:/Users/Alexandre/Documents/Lost/iscool/src/main/webapp/feed/fotofeed.png");
		ImageIO.write(bufferedImage, "png", ImagemUtil.newFile(sourceImage, dataString));
		
		feed.setAutor("feed");
		feed.setData(dataString);
		feed.setImagemUrl(url);
		
		feedDao.inserirFeed(feed);
	}
	
	@Post
	public void inserirFeedEscolar(UploadedFile uploadedFile, final FeedEscolar feed, long idEscola) throws IOException {
		BufferedImage bufferedImage = ImagemUtil.retornaBufferedImage(uploadedFile);
		Date data = new Date();
		String dataString = Long.toString(data.getTime());
		String url = "http://10.13.38.106:8080/teste/feed/fotofeed-" + dataString + ".png";
		
		File sourceImage = new File("C:/Users/Alexandre/Documents/Lost/iscool/src/main/webapp/feed/fotofeed.png");
		ImageIO.write(bufferedImage, "png", ImagemUtil.newFile(sourceImage, dataString));
		
		Escola escola = escolaDao.pesquisarEscolaPorId(idEscola);
		
		feed.setAutor("Diretor: " + escola.getGestorEscolar().getNome());
		feed.setData(dataString);
		feed.setImagemUrl(url);
		feed.setEscola(escola);
		
		feedDao.inserirFeed(feed);
		
		result.include("feedback", "Notícia cadastrada com sucesso!");
		result.redirectTo(EscolaController.class).telaPrincipal();
	}
	
	@Get("/feed/lista")
	public void listaTodas() {
		ArrayList<Feed> feeds = (ArrayList<Feed>) feedDao.listarTodos();
		result.use(Results.json()).withoutRoot().from(feeds).serialize();
	}
	
	public void lista(long idAluno) {
		Aluno aluno = (Aluno) usuarioDao.pesquisarUsuarioPorId(idAluno);
		ArrayList<Feed> feeds = (ArrayList<Feed>) feedDao.listarMax(20, aluno.getTurma().getEscola().getId());
		result.use(Results.json()).withoutRoot().from(feeds).serialize();
	}
}