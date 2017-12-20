package br.ufpi.easii.iscool.sessao;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufpi.easii.iscool.entidade.Aluno;

@SessionScoped
@Named("alunoLogado")
public class AlunoLogado implements Serializable{

	private static final long serialVersionUID = 5684522034570958860L;

	private Aluno aluno;
	
	public void login(final Aluno aluno){
		this.aluno = aluno;
	}
	
	public String getNome(){
		return aluno.getNome();
	}
	
	public boolean isLogado(){
		return aluno != null;
	}
	
	public void logout(){
		this.aluno = null;
	}
	
	public String getEmail(){
		return aluno.getEmail();
	}
	
	public Aluno getAluno(){
		return aluno;
	}
	
	public long getId(){
		return this.aluno.getId();
	}
}
