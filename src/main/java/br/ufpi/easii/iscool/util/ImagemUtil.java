package br.ufpi.easii.iscool.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.util.IOUtils;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class ImagemUtil {

	public static BufferedImage retornaBufferedImage(UploadedFile uploadedFile) throws IOException {
		InputStream inputStream = null;
		
		if(uploadedFile != null) {
			inputStream = uploadedFile.getFile();
		}
		
		byte[] bytes = IOUtils.toByteArray(inputStream);
		
		return createImageFromBytes(bytes); 
	}
	
	private static BufferedImage createImageFromBytes(byte[] imageData){
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
		try{
			return ImageIO.read(byteArrayInputStream);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public static File newFile(File file, String detail){
		return new File(file.getParentFile(), file.getName().substring(0, file.getName().indexOf(".")) + "-" + detail + ".png");
	}
}
