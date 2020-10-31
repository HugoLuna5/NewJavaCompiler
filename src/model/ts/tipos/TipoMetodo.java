/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ts.tipos;

/**
 *
 * @author Hugo Luna
 */
public abstract class TipoMetodo {
	
	public abstract String getNombreClase();
	
	public abstract boolean esValido();
	
	public abstract boolean esVoid();
	
	public abstract boolean esTipoPrimitivo();
	
	public abstract boolean esTipoClase();
	
	public abstract boolean conforma(TipoMetodo tipo);
	
	public boolean equals(TipoMetodo tipo) {
		return this.getNombreClase().equals(tipo.getNombreClase());
	}
	
}

