package br.ufpi.easii.iscool.android.entidades;

import java.util.ArrayList;
import java.util.List;

public class MediaDaTurma {

	private List<Media> listaDeMedia = new ArrayList<Media>();

	public List<Media> getListaDeMedia() {
		return listaDeMedia;
	}

	public void setListaDeMedia(List<Media> listaDeMedia) {
		this.listaDeMedia = listaDeMedia;
	}
	
	public void adicionar(Media media){
		this.listaDeMedia.add(media);
	}
}