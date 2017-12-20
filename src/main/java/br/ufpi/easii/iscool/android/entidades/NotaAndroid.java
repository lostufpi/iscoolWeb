package br.ufpi.easii.iscool.android.entidades;

public class NotaAndroid {
	private String nomeDaProva;
	private long idProva;
	private long id;
	private int acertos;
	private int erros;
	private double porcentagemDeAcerto;
	private int questoesSemResposta;
	
	public NotaAndroid(long id, int acertos, int erros, double porcentagemDeAcerto, int questoesSemResposta, String nomeDaProva, long idProva) {
		super();
		this.id = id;
		this.acertos = acertos;
		this.erros = erros;
		this.porcentagemDeAcerto = porcentagemDeAcerto;
		this.questoesSemResposta = questoesSemResposta;
		this.nomeDaProva = nomeDaProva;
		this.idProva = idProva;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public int getErros() {
		return erros;
	}

	public void setErros(int erros) {
		this.erros = erros;
	}

	public double getPorcentagemDeAcerto() {
		return porcentagemDeAcerto;
	}

	public void setPorcentagemDeAcerto(double porcentagemDeAcerto) {
		this.porcentagemDeAcerto = porcentagemDeAcerto;
	}

	public int getQuestoesSemResposta() {
		return questoesSemResposta;
	}

	public void setQuestoesSemResposta(int questoesSemResposta) {
		this.questoesSemResposta = questoesSemResposta;
	}

	public String getNomeDaProva() {
		return nomeDaProva;
	}

	public void setNomeDaProva(String nomeDaProva) {
		this.nomeDaProva = nomeDaProva;
	}

	public long getIdProva() {
		return idProva;
	}

	public void setIdProva(long idProva) {
		this.idProva = idProva;
	}
}