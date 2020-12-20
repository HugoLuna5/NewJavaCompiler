package model.ast.sentencias;

import Utils.GenCod;
import exceptions.semanticas.ExcepcionSemantica;

public class NodoBloqueSystem extends NodoBloque {

    private String tipoBloque;

    public NodoBloqueSystem(String tipo) {
        tipoBloque = tipo;
    }

    /**
     * Generar codigo ensamblador bloques (Mostrar y leer datos)
     *
     * @throws ExcepcionSemantica
     */
    public void chequear() throws ExcepcionSemantica {
        switch (tipoBloque) {
            case "readString":
                GenCod.gen("READSTRING");
                GenCod.gen("STORE 3");
                break;
            case "readInt":
                GenCod.gen("READINT");
                GenCod.gen("STORE 3");
                break;
            case "printB":
            case "printBln":
                GenCod.gen("LOAD 3");
                GenCod.gen("BPRINT");
                break;
            case "printI":
            case "printIln":
                GenCod.gen("LOAD 3");
                GenCod.gen("IPRINT");
                break;
            case "printC":
            case "printCln":
                GenCod.gen("LOAD 3");
                GenCod.gen("CPRINT");
                break;
            case "printS":
            case "printSln":
                GenCod.gen("LOAD 3");
                GenCod.gen("SPRINT");
                break;
            case "printSIO":
                GenCod.gen("LOAD 3");
                GenCod.gen("SPRINTIO");
                break;
            case "printFloat":
                GenCod.gen("LOAD 3");
                GenCod.gen("FLOATPRINT");
                break;
        }
        switch (tipoBloque) {
            case "printBln":
            case "printIln":
            case "printCln":
            case "printSln":
            case "println":
                GenCod.gen("PRNLN");
                break;
        }

    }

}
