/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ast.sentencias;

import Utils.GenCod;
import exceptions.semanticas.ExcepcionSemantica;
import exceptions.semanticas.ExcepcionSemanticaPersonalizada;
import model.Token;
import model.ast.expresiones.NodoExpresion;
import model.ts.tipos.TipoMetodo;

/**
 *
 * @author Hugo Luna
 */
public class NodoFor extends NodoSentencia {

    private Token identificador;
    private NodoExpresion condicion;
    private NodoSentencia sentencia;

    public NodoFor(Token identificador, NodoExpresion condicion, NodoSentencia sentencia) {
        this.identificador = identificador;
        this.condicion = condicion;
        this.sentencia = sentencia;
    }

    public Token getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Token identificador) {
        this.identificador = identificador;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

       

    }

}
