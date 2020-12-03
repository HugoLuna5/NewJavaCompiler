package Controller;

import vm.CeIVMAPI;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class CeIVM {

    public static void main(String[] args) {
        CeIVMAPI ceivmApi = new CeIVMAPI();
        System.out.println("------------------------------------------------------------------");
        System.out.println("CeI Virtual Machine (CeIVM) - v" + ceivmApi.getVersion() + ".");
        System.out.println("------------------------------------------------------------------");
        System.out.println();

        if (args.length < 1) {
            System.err.println("Argumentos insuficientes.");
            showUsageAndExit(1);
        }
        try {
            if (args.length > 1) {
                if (args[1].compareToIgnoreCase("-v") == 0) {
                    String filename;
                    if (args.length == 2) {
                        filename = String.valueOf(args[0]) + ".list.txt";
                    } else if (args.length == 3) {
                        filename = args[2];
                    } else {
                        System.err.println("Demasiados argumentos.");
                        showUsageAndExit(2);
                        return;
                    }
                    PrintStream outDump = new PrintStream(filename);
                    ceivmApi.enableListingGeneration(outDump);
                } else {
                    System.err.println("Opciincorrecta: " + args[1] + ".");
                    showUsageAndExit(3);
                }
            } else {
                ceivmApi.disableListingGeneration();
            }
            ceivmApi.parseAndAssemble(args[0]);
            ceivmApi.loadProgram();
            ceivmApi.initializeVM();
            ceivmApi.executeToCompletion();
        } catch (FileNotFoundException e) {
            System.err.println("Error: No se pudo abrir el archivo " + args[0] + ".\n");
        } catch (Exception e) {
            System.err.println("\n" + e.getMessage() + "\n");
        }

    }

    private static void showUsageAndExit(int errorlevel) {
        System.err.println("Uso: java -jar CeIVM.jar ArchivoOrigen [-v [ArchList]]");
        System.err.println("Opciones:");
        System.err.println(" -v         : Genera listados con información de ensamblado, linkeo y carga.");
        System.err.println(" -v ArchList: Genera listados en el archivo especificado.");
        System.exit(errorlevel);
    }
}
