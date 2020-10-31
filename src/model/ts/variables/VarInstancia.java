package model.ts.variables;

import model.Token;
import model.ts.tipos.Tipo;



public class VarInstancia extends Variable {
	
	private boolean esPublico;
	
	public VarInstancia(Token id, Tipo tipo, boolean esPublico) {
		super(id,tipo);
		this.esPublico = esPublico;
	}

	public boolean getEsPublico() {
		return esPublico;
	}

	public void setEsPublico(boolean esPublico) {
		this.esPublico = esPublico;
	}

}
