package br.ufpi.easii.iscool.sessao;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.GestorEscolar;

@SessionScoped
@Named("gestorEscolarLogado")
public class GestorEscolarLogado implements Serializable{

private static final long serialVersionUID = -6150275512411947374L;
	
	private GestorEscolar gestor;
	
	public void login(final GestorEscolar gestor){
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
	
	public Escola getEscola(){
		return this.gestor.getEscola();
	}
	
	public long getId(){
		return this.gestor.getId();
	}
}
