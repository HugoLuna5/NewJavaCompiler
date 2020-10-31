package model.ast.sentencias;

import Utils.GenCod;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.semanticas.ExcepcionSemanticaPersonalizada;
import model.Token;
import model.ast.expresiones.NodoExpPrimario;
import model.ast.expresiones.NodoPrimario;
import model.ts.tipos.TipoMetodo;


public class NodoSentenciaLlamada extends NodoSentencia {
	
	private Token identificador;
	private NodoExpPrimario llamada;
	
	public NodoSentenciaLlamada(Token id, NodoExpPrimario llamada) {
		this.llamada = llamada;
		identificador = id;
	}

	public NodoExpPrimario getLlamada() {
		return llamada;
	}

	public void setLlamada(NodoExpPrimario llamada) {
		this.llamada = llamada;
	}

	public void chequear() throws ExcepcionSemantica {
		TipoMetodo tipoRetorno = llamada.chequear();
		NodoPrimario np = (NodoPrimario)llamada;
		if (!np.terminaEnLlamada())
			throw new ExcepcionSemanticaPersonalizada(identificador.getLinea(),"La sentencia no termina en una llamada");
		// Si hago una llamada a algo que no es void me va a quedar en el tope de la pila el retorno
		if (!tipoRetorno.esVoid())
			GenCod.gen("POP","Desapilo el tope de la pila luego de una sentencia llamada");
	}

}
