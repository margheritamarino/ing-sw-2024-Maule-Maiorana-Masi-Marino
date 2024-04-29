package it.polimi.ingsw.network;

//da fare nel file POM
import org.fusesource.jansi.Ansi;

//classe che fornisce metodi per stampare messaggi in modo asincrono (ovvero in un thread separato)
// consentendo all'applicazione principale di continuare ad eseguire altre operazioni senza attendere che la stampa sia completata
//SCOPO: evitare che il thread principale si blocchi durante la stampa (tramite TUI o GUI) di elementi più pesanti (es. colorati)
public class PrintAsync {

    //metodo per stampare messaggi formattati con colori o effetti speciali
    public static void printAsync(Ansi msg){
        new Thread(()->{System.out.println(msg);}).start();
    }

    //metodo per stampare semplici stringhe
    public static void printAsync(String msg){
        new Thread(()->{System.out.println(msg);}).start();
    }

    //metodo per stampare stringhe modificabili
    public static void printAsync(StringBuilder msg){
        new Thread(()->{System.out.println(msg);}).start();
    }

    //metodo per stampare messaggi senza andare a capo alla fine della riga
    public static void printAsyncNoLine(String msg){
        new Thread(()->{System.out.print(msg);}).start();
    }
}
