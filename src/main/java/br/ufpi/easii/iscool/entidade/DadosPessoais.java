package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DadosPessoais implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpf;
	private String identidade;
	private String telefone;
	
	public String getIdentidade() {
		return identidade;
	}
	
	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}