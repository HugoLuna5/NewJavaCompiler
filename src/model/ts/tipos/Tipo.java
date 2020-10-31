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
public abstract class Tipo extends TipoMetodo {

	public abstract boolean esTipoPrimitivo();
	
	public abstract String getNombreClase();
	
	public boolean esVoid() {
		return false;
	}
    
}
