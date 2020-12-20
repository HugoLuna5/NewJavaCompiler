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
    private NodoAsignacion init;
    private NodoExpresion condicion;
    private NodoSentencia incremento;
    private NodoSentencia sentencia;

    public NodoFor(Token identificador, NodoAsignacion init, NodoExpresion condicion, NodoSentencia incremento, NodoSentencia sentencia) {
        this.identificador = identificador;
        this.init = init;
        this.condicion = condicion;
        this.incremento = incremento;
        this.sentencia = sentencia;
    }

    public Token getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Token identificador) {
        this.identificador = identificador;
    }

    public NodoAsignacion getInit() {
        return init;
    }

    public void setInit(NodoAsignacion init) {
        this.init = init;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public NodoSentencia getIncremento() {
        return incremento;
    }

    public void setIncremento(NodoSentencia incremento) {
        this.incremento = incremento;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

        String str = GenCod.generarEtiqueta();
        GenCod.comment("Comienzo del FOR_" + str);
        init.chequear();//i = 0;
        GenCod.gen("FOR_L1_" + str, "NOP", "");//(
        TipoMetodo tc = condicion.chequear();//i<b
       
        GenCod.gen("BF FOR_L2_" + str);
        if (!tc.getNombreClase().equals("boolean")){
            throw new ExcepcionSemanticaPersonalizada(identificador.getLinea(), "La condicion del For no es una expresion logica");
        }
               
        sentencia.chequear();
        incremento.chequear();
         
        GenCod.gen("JUMP FOR_L1_" + str);
        GenCod.gen("FOR_L2_" + str, "NOP", "");
        GenCod.comment("Fin del FOR_" + str);
        
        

    }

}
