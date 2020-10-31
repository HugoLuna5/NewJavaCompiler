package model.ast.expresiones;

import model.ast.encadenados.NodoEncadenado;


public abstract class NodoPrimario extends NodoExpPrimario {
	
	private NodoEncadenado cadena;
	
	public NodoPrimario() {
		cadena = null;
		this.setEsLadoIzq(false);
	}
	
	public NodoPrimario(NodoEncadenado cad) {
		cadena = cad;
	}
	
	public NodoEncadenado getCadena() {
		return cadena;
	}

	public void setCadena(NodoEncadenado cadena) {
		this.cadena = cadena;
	}


	public abstract boolean terminaEnVar();
	
	public abstract boolean terminaEnLlamada();

}
