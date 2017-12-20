package br.ufpi.easii.iscool.sessao;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.ufpi.easii.iscool.entidade.Professor;

@SessionScoped
@Named("professorLogado")
public class ProfessorLogado implements Serializable{

	private static final long serialVersionUID = 6937634462102632604L;

	private Professor professor;
	
	public void login(final Professor professor){
		this.professor = professor;
	}

	public String getNome(){
		return professor.getNome();
	}
	
	public boolean isLogado(){
		return professor != null;
	}
	
	public void logout(){
		this.professor = null;
	}
	
	public String getEmail(){
		return professor.getEmail();
	}
	
	public long getId(){
		return this.professor.getId();
	}

	public Professor getProfessor() {
		return professor;
	}
}
