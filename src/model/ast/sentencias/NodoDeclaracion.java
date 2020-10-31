package model.ast.sentencias;

import Utils.GenCod;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.semanticas.ExcepcionSemanticaPersonalizada;
import java.util.HashMap;
import java.util.LinkedList;
import model.ts.TablaSimbolos;
import model.ts.tipos.Tipo;
import model.ts.variables.VarLocal;
import model.ts.variables.Variable;


public class NodoDeclaracion extends NodoSentencia {
	
	private Tipo tipo;
	private LinkedList<VarLocal> variables;
	
	public NodoDeclaracion(Tipo tipo, LinkedList<VarLocal> listaVars) {
		this.tipo = tipo;
		variables = listaVars;
	}

	public LinkedList<VarLocal> getVariables() {
		return variables;
	}

	public void setVariables(LinkedList<VarLocal> variables) {
		this.variables = variables;
	}

	@Override
	public void chequear() throws ExcepcionSemantica {
		if (!tipo.esValido()) 
			throw new ExcepcionSemanticaPersonalizada(variables.get(0).getToken().getLinea(),"El tipo "+tipo.getNombreClase()+" no corresponde a un tipo valido");
		else {
			HashMap<String, Variable> tablaVarsLocales = TablaSimbolos.metodoActual.getTablaVariables();
			for (VarLocal var : variables) {
				if (tablaVarsLocales.containsKey(var.getToken().getLexema())) 
					throw new ExcepcionSemanticaPersonalizada(var.getToken().getLinea(),"No se puede declarar una variable con el nombre '"+var.getToken().getLexema()+"' porque su nombre ya esta en uso.");
				else {
					// Agrego la variable a la tabla del metodo y a la del bloque
					// El propio metodo agregarVariable (de Metodo) setea el offset!
					TablaSimbolos.metodoActual.agregarVariable(var);
					//tablaVarsLocales.put(var.getToken().getLexema(), var);	// Obsoleto, ahora con lo de arriba actualiza offset
					TablaSimbolos.bloqueActual.agregarVariable(var);
				}
			}
			GenCod.gen("RMEM "+variables.size(),"Se declaran "+variables.size()+" variables locales. Reservo lugar.");
			
		}

	}

}
