package it.polimi.ingsw.view;

import org.fusesource.jansi.Ansi;

/**
 * Utility class for asynchronously printing messages to the console.
 */
public class PrintAsync {

    /**
     * Asynchronously prints a message with ANSI escape codes to the console.
     * This method launches a new thread for printing, allowing the main thread to continue its execution without
     * being blocked by console output. Useful in scenarios where console output might cause the program to freeze,
     * such as during quick edit mode.
     *
     * @param msg ANSI-formatted message to print
     */
    public static void printAsync(Ansi msg){
        new Thread(()->{System.out.println(msg);}).start();
    }

    /**
     * Asynchronously prints a string message to the console.
     *
     * @param msg String message to print
     */
    public static void printAsync(String msg){
        new Thread(()->{System.out.println(msg);}).start();
    }

    /**
     * Asynchronously prints a StringBuilder message to the console.
     *
     * @param msg StringBuilder message to print
     */
    public static void printAsync(StringBuilder msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
}
