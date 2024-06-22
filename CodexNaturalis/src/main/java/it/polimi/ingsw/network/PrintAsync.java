package it.polimi.ingsw.network;

//da fare nel file POM
import org.fusesource.jansi.Ansi;

/**
 * Provides methods for printing messages asynchronously, allowing the main application to continue
 * executing other operations without waiting for the printing to complete.
 * <p>
 * The purpose is to prevent the main thread from blocking during the printing of heavier elements
 * (e.g., colored output in TUI or GUI).
 */
public class PrintAsync {
    /**
     * Prints the given Ansi formatted message asynchronously.
     *
     * @param msg The Ansi formatted message to print.
     */
    public static void printAsync(Ansi msg){
        System.out.println(msg);
    }

    /**
     * Prints the given string message asynchronously.
     *
     * @param msg The string message to print.
     */
    public static void printAsync(String msg){
        System.out.println(msg);
    }
    /**
     * Prints the contents of the StringBuilder asynchronously.
     *
     * @param msg The StringBuilder containing the message to print.
     */
    public static void printAsync(StringBuilder msg){
        System.out.println(msg);
    }
    /**
     * Prints the given string message asynchronously without adding a newline character.
     *
     * @param msg The string message to print.
     */
}


