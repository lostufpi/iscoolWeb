package br.ufpi.easii.iscool.android.entidades;

public class Media {

	private String nome;
	private float media;
	
	public Media(String nome, float media) {
		this.nome = nome;
		this.media = media;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getMedia() {
		return media;
	}
	
	public void setMedia(float media) {
		this.media = media;
	}
}