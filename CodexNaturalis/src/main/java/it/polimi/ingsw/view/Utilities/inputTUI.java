package it.polimi.ingsw.view.Utilities;

import java.util.Scanner;

import static it.polimi.ingsw.view.TUI.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

//gestisce la lettura degli input utente dalla console e li aggiunge ad un buffer di dati per una elaborazione successiva
public class inputTUI extends Thread {

    private final BufferData buffer = newBufferData();

    /**
     * Init
     */
    public inputTUI(){
        this.start();
    }

    //è un loop che rappresenta il flusso principale del lettore di input
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            //Reads the input and add what It reads to the buffer synch
            String temp = sc.nextLine();
            buffer.addData(temp);
            printAsync(ansi().cursorUpLine().a(" ".repeat(temp.length())));
            printAsync(ansi().cursor(DefaultValue.row_input + 1, 0));
        }
    }

    /**
     * @return the buffer
     */
    public BufferData getBuffer(){
        return buffer;
    }





}
