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
public abstract class TipoPrimitivo extends Tipo {

	public boolean esTipoPrimitivo() {
		return true;
	}
	
	public boolean esValido() {
		return true;
	}
	
	public boolean conforma(TipoMetodo t) {
		return t.getNombreClase().equals(this.getNombreClase());
	}
	
	public boolean esTipoClase() {
		return false;
	}
	
}
