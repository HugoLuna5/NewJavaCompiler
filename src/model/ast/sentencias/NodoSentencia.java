package model.ast.sentencias;

import exceptions.semanticas.ExcepcionSemantica;


public abstract class NodoSentencia {
	
	public abstract void chequear() throws ExcepcionSemantica;

}
