package br.ufpi.easii.iscool.entidade;

public class RespostaPython {

	private long alunoId;
	private long provaId;
	private String resposta;
	
	public RespostaPython() {
		
	}
	
	public long getAlunoId() {
		return alunoId;
	}
	
	public void setAlunoId(long alunoId) {
		this.alunoId = alunoId;
	}
	
	public long getProvaId() {
		return provaId;
	}
	
	public void setProvaId(long provaId) {
		this.provaId = provaId;
	}
	
	public String getResposta() {
		return resposta;
	}
	
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
}