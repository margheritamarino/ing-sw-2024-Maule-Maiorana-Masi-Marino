package it.polimi.ingsw.view.Utilities;

import java.util.Scanner;

import static it.polimi.ingsw.view.TUI.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;


//classe progettata per leggere l'input dell'utente da console e aggiungerlo a un buffer di dati
public class InputTUI extends Thread implements InputReader {

    private final Buffer buffer;

    public InputTUI(){
        buffer = new Buffer();
        this.start(); //avvio del thread
    }

    /**
     * Reads player's inputs
     */
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in); //scanner per leggere l'input dell'utente
        while(!this.isInterrupted()){
            //leggo l'input e lo aggiungo al buffer
            String txt = sc.nextLine();
            buffer.addInputData(txt);
            //dopo aver letto uso printAsync per cancellare l'input precedentemente stampato e spostare il cursore nella posizione corretta per leggere l'input successivo
            printAsync(ansi().cursorUpLine().eraseLine());
        }
    }

    @Override
    public Buffer getBuffer() {
        return buffer;
    }
}
