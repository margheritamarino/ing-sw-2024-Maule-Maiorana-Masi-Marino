package it.polimi.ingsw.network;

//da fare nel file POM
import org.fusesource.jansi.Ansi;

//classe che fornisce metodi per stampare messaggi in modo asincrono (ovvero in un thread separato)
// consentendo all'applicazione principale di continuare ad eseguire altre operazioni senza attendere che la stampa sia completata
//SCOPO: evitare che il thread principale si blocchi durante la stampa (tramite TUI o GUI) di elementi più pesanti (es. colorati)
public class PrintAsync {
    public static synchronized void printAsync(Ansi msg){
        System.out.println(msg);
    }

    public static synchronized void printAsync(String msg){
        System.out.println(msg);
    }

    public static synchronized void printAsync(StringBuilder msg){
        System.out.println(msg);
    }

    public static synchronized void printAsyncNoLine(String msg){
        System.out.print(msg);
    }
}


