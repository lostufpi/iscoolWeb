package br.ufpi.easii.iscool.entidade;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DadosCadastrais implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String razaoSocial;
	private String cnpj;
	private String telefone;
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}