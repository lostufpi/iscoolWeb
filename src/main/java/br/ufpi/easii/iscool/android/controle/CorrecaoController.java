package br.ufpi.easii.iscool.android.controle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.poi.util.IOUtils;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.dao.QuestaoDao;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.enuns.Letra;

@Controller
public class CorrecaoController {
	private final Result result;
	private Carrega carrega;
	private QuestaoDao questaoDao;
	
	public CorrecaoController() {
		this(null, null, null);
	}
	
	@Inject
	protected CorrecaoController(Result result, Carrega carrega, QuestaoDao questaoDao) {
		this.result = result;
		this.carrega = carrega;
		this.questaoDao = questaoDao;
	}
	
	@Get("/correcao/respostas/{idProva}")
	public void respostasCorretas(long idProva){
		List<Questao> respostasCorretas = questaoDao.listarQuestoesPorProva(1);
		Map<Integer, Integer> respostas = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < respostasCorretas.size(); i++){
			Letra letra = respostasCorretas.get(i).getResposta();
			
			if(letra == Letra.A){
				respostas.put(i, 0);
			}else if(letra == Letra.B){
				respostas.put(i, 1);
			}else if(letra == Letra.C){
				respostas.put(i, 2);
			}else if(letra == Letra.D){
				respostas.put(i, 3);
			}else if(letra == Letra.E){
				respostas.put(i, 4);
			}
		}
		result.use(Results.json()).withoutRoot().from(respostas).serialize();
	}
	
	@Post
	public void uploadedImagem(UploadedFile uploadedFile, String idDoAluno, String idProva) throws IOException{
		InputStream inputStream = null;
		if(uploadedFile != null)
			inputStream = uploadedFile.getFile();
		
		byte[] bytes = IOUtils.toByteArray(inputStream);
		
		BufferedImage bufferedImage = createImageFromBytes(bytes);
		//File sourceImage = new File("C:/Users/Alexandre/Desktop/Projeto/iscool/src/main/webapp/gabarito/foto.png");
		//ImageIO.write(bufferedImage, "png", newFile(sourceImage, idDoAluno + "-" + idProva));
		
		try{
			int resultado = carrega.notaAluno(bufferedImage, Long.parseLong(idProva), Long.parseLong(idDoAluno));
			System.out.println(resultado);
		}catch(Exception e){
			e.printStackTrace();
		}
		result.use(Results.json()).withoutRoot().from("OK").serialize();
	}

	
	private BufferedImage createImageFromBytes(byte[] imageData){
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		try{
			return ImageIO.read(byteArrayInputStream);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings("unused")
	private static File newFile(File file, String detail){
		return new File(file.getParentFile(), file.getName().substring(0, file.getName().indexOf(".")) + "-" + detail + ".png");
	}
}