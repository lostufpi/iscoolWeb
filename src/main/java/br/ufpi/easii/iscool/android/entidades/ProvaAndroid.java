package br.ufpi.easii.iscool.android.entidades;

public class ProvaAndroid {
	private long id;
	private String nome;
	private long data;
	
	public ProvaAndroid(long id, String nome, long data) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = data;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public long getData() {
		return data;
	}
	
	public void setData(long data) {
		this.data = data;
	}
}