package model.ast.expresiones;

import exceptions.semanticas.ExcepcionSemantica;
import model.ts.tipos.TipoMetodo;


public abstract class NodoExpresion {

	public abstract TipoMetodo chequear() throws ExcepcionSemantica;
	
}
