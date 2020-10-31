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
public class TipoVoid extends TipoMetodo {

	public boolean esValido() {
		return false;
	}
	
	public boolean esVoid() { 
		return true;
	}
	
	public boolean esTipoPrimitivo() {
		return false;
	}
	
	public String getNombreClase() {
		return "void";
	}
	
	public boolean conforma(TipoMetodo t) {
		return false;
	}
	
	public boolean esTipoClase() {
		return false;
	}
    
}
