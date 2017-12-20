package br.ufpi.easii.iscool.sessao;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufpi.easii.iscool.entidade.GestorDeRede;
import br.ufpi.easii.iscool.entidade.RedeEscolar;

@SessionScoped
@Named("gestorDeRedeLogado")
public class GestorDeRedeLogado implements Serializable{

	private static final long serialVersionUID = -6150275512411947374L;
	
	private GestorDeRede gestor;
		
	public void login(final GestorDeRede gestor){
		this.gestor = gestor;
	}

	public String getNome(){
		return gestor.getNome();
	}
	
	public boolean isLogado(){
		return gestor != null;
	}
	
	public void logout(){
		this.gestor = null;
	}
	
	public String getEmail(){
		return gestor.getEmail();
	}
	
	public RedeEscolar getRede(){
		return this.gestor.getRedeEscolar();
	}
	
	public long getId(){
		return this.gestor.getId();
	}
}