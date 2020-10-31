/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ts.tipos;

import model.Token;

/**
 *
 * @author Hugo Luna
 */
public class TipoNull extends TipoClase {

	public TipoNull(Token id) {
		super(id);
	}

	public String getNombreClase() {
		return "null";
	}
	
	public boolean conforma(TipoMetodo t) {
		return t.esTipoClase();
	}
	
    
}
