package br.ufpi.easii.iscool.util;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class LeituraDeArquivo {

	UploadedFile uplaodedFile;
	
	public LeituraDeArquivo(UploadedFile uploadedFile){
		this.uplaodedFile = uploadedFile;
	}
	
	public UploadedFile lerArquivo(){
		if(this.uplaodedFile != null){
			String nomeDoArquivo = this.uplaodedFile.getFileName();
			String  extencao = null;
			
			for(int i = 0;i < nomeDoArquivo.length(); i++){
				if(nomeDoArquivo.charAt(i) == '.'){
					extencao = nomeDoArquivo.substring(i, nomeDoArquivo.length());
					System.out.println("Extenção do arquivo: " + extencao);
				}
			}
			
			if(extencao.equals(".csv") || extencao.equals(".CSV")){
				return this.uplaodedFile;
			}
		}
		return null;
	}
}