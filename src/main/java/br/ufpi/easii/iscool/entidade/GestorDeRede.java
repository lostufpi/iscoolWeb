package br.ufpi.easii.iscool.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class GestorDeRede extends Usuario{

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	private RedeEscolar redeEscolar;

	public final RedeEscolar getRedeEscolar() {
		return redeEscolar;
	}

	public final void setRedeEscolar(RedeEscolar redeEscolar) {
		this.redeEscolar = redeEscolar;
	}
}