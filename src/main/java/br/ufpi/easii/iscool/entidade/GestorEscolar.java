package br.ufpi.easii.iscool.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")

public class GestorEscolar extends Usuario{

	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Escola escola;

	public final Escola getEscola() {
		return escola;
	}

	public final void setEscola(Escola escola) {
		this.escola = escola;
	}
}