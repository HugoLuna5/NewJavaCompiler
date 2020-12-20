/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ast.sentencias;

import exceptions.semanticas.ExcepcionSemantica;
import model.Token;
import model.ast.expresiones.NodoLiteral;
import model.ast.expresiones.NodoPrimario;

/**
 *
 * @author Hugo Luna
 */
public class NodoSwitch extends NodoSentencia{

    private Token identificador;
    private NodoSentencia sentencia;

    public NodoSwitch(Token identificador, NodoSentencia sentencia) {
        this.identificador = identificador;
        this.sentencia = sentencia;
    }

    
    
    
    
    @Override
    public void chequear() throws ExcepcionSemantica {
        
    }
    
}
