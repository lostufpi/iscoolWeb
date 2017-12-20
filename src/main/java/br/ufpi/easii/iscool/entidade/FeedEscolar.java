package br.ufpi.easii.iscool.entidade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_feed")
public class FeedEscolar extends Feed {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_escola")
	private Escola escola;

	public Escola getEscola() {
		return escola;
	}

	public void setEscola(Escola escola) {
		this.escola = escola;
	}
}