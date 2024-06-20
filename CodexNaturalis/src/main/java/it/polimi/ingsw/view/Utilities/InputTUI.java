package it.polimi.ingsw.view.Utilities;

import java.util.Scanner;

import static it.polimi.ingsw.view.TUI.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Class designed to read user input from the console and add it to a data buffer.
 * Extends Thread to operate asynchronously.
 */
public class InputTUI extends Thread implements InputReader {

    private final Buffer buffer;

    /**
     * Constructs an InputTUI object, initializing the buffer and starting the thread.
     */
    public InputTUI(){
        buffer = new Buffer();
        this.start();
    }

    /**
     * Continuously reads user input from the console and adds it to the buffer.
     * Uses printAsync to clear previously printed input and position the cursor correctly for the next input.
     */
    @Override
    public void run(){
        Scanner sc = new Scanner(System.in);
        while(!this.isInterrupted()){
            String txt = sc.nextLine();
            buffer.addInputData(txt);
            printAsync(ansi().cursorUpLine().eraseLine());
        }
    }

    /**
     * Retrieves the buffer used for storing user inputs.
     *
     * @return The Buffer instance used for storing user inputs.
     */
    @Override
    public Buffer getBuffer() {
        return buffer;
    }
}
