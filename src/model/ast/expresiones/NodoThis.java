package model.ast.expresiones;

import Utils.GenCod;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.semanticas.ExcepcionSemanticaPersonalizada;
import model.Token;
import model.ast.encadenados.NodoEncadenado;
import model.ts.Metodo;
import model.ts.TablaSimbolos;
import model.ts.tipos.Tipo;
import model.ts.tipos.TipoClase;
import model.ts.tipos.TipoMetodo;


public class NodoThis extends NodoPrimario {
	
	private Token identificador;
	
	public NodoThis(Token t) {
		identificador = t;
	}
	
	public NodoThis(Token t, NodoEncadenado cad) {
		super(cad);
		identificador = t;
	}

	public Token getToken() {
		return identificador;
	}

	public void setIdentificador(Token identificador) {
		this.identificador = identificador;
	}

	public TipoMetodo chequear() throws ExcepcionSemantica {
		if (!TablaSimbolos.metodoActual.esConstructor()) {
			Metodo met = (Metodo)TablaSimbolos.metodoActual;
			if (met.getEsEstatico()) 
				throw new ExcepcionSemanticaPersonalizada(identificador.getLinea(),"No se puede hacer uso de 'this' dentro de un metodo estatico.");
		}
		// El tipo de this es el mismo que el de la clase que se esta utilizando
		// Asumo que tambien vale para los constructores
		Token tk = new Token("idClase",TablaSimbolos.claseActual.getNombreClase(),identificador.getLinea());
		
                Tipo receptor = new TipoClase(tk);
		GenCod.gen("LOAD 3","Apilo 'this'");
		if (this.getCadena()!=null) {
			this.getCadena().setEsLadoIzq(this.getEsLadoIzq());
			return this.getCadena().chequear(receptor);
		} else return receptor;
		
	}

	public boolean terminaEnVar() {
		if (this.getCadena()==null)
			return false;
		else return this.getCadena().terminaEnVar();
	}

	public boolean terminaEnLlamada() {
		if (this.getCadena()==null)
			return false;
		else return this.getCadena().terminaEnLlamada();
	}


}
