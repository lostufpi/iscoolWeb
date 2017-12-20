package br.ufpi.easii.iscool.android.controle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.poi.util.IOUtils;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.easii.iscool.android.entidades.ProvaAndroid;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.entidade.Prova;

@Controller
public class ProvaAndroidController {
	private final ProvaDao provasDao;
	private final Result result;
	
	protected ProvaAndroidController(){
		this(null, null);
	}

	@Inject
	public ProvaAndroidController(ProvaDao provasDao, Result result){
		this.provasDao = provasDao;
		this.result = result;
	}

	@Post
	public void listar(long idDisciplina){
		System.out.println("ID DISCIPLINA: " + idDisciplina);
		List<Prova> provas = provasDao.listarProvasPorDisciplina(idDisciplina);
		List<ProvaAndroid> provasAndroid = new ArrayList<ProvaAndroid>();
		
		if(provas.size() > 0){
			for(Prova p : provas){
				ProvaAndroid provaAndroid;
				if(p.getDataDeRealizacao() != null)
					provaAndroid = new ProvaAndroid(p.getId(), p.getNome(), p.getDataDeRealizacao().getTime());
				else
					provaAndroid = new ProvaAndroid(p.getId(), p.getNome(), 0);
				provasAndroid.add(provaAndroid);
			}
			result.use(Results.json()).withoutRoot().from(provasAndroid).serialize();
		}
		else
			result.use(Results.json()).withoutRoot().from(0).serialize();
	}
	
	@Post
	public void uploadedImagem(UploadedFile uploadedFile, String idDoAluno, String idProva) throws IOException{
		InputStream inputStream = null;
		if(uploadedFile != null)
			inputStream = uploadedFile.getFile();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		
		BufferedImage bufferedImage = createImageFromBytes(bytes);
		File sourceImage = new File("c:/provas/foto-" + idDoAluno + idProva + ".png");
		ImageIO.write(bufferedImage, "png", newFile(sourceImage, idDoAluno + idProva));
		
		result.use(Results.json()).withoutRoot().from(0).serialize();
	}
	
	private BufferedImage createImageFromBytes(byte[] imageData){
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		try{
			return ImageIO.read(byteArrayInputStream);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	private static File newFile(File file, String detail){
		return new File(file.getParentFile(), file.getName().substring(0, file.getName().indexOf(".")) + "-" + detail + ".png");
	}
}