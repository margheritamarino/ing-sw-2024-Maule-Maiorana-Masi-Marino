package it.polimi.ingsw.view.TUI;

import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

public class PrintAsync {

    /**
     * Asynchronously prints an Ansi message to the console.
     *
     * @param msg The Ansi message to print.
     */
    public static void printAsync(Ansi msg){
        new Thread(()->{System.out.println(msg); System.out.println(ansi());}).start();
    }

    /**
     * Asynchronously prints a String message to the console.
     *
     * @param msg The String message to print.
     */
    public static void printAsync(String msg){
        new Thread(()->{System.out.println(msg);System.out.println(ansi());}).start();
    }

    /**
     * Asynchronously prints a StringBuilder message to the console.
     *
     * @param msg The StringBuilder message to print.
     */
    public static void printAsync(StringBuilder msg){
        new Thread(()->{System.out.println(msg);System.out.println(ansi());}).start();
    }

    /**
     * Asynchronously prints two String messages to the console.
     *
     * @param msg1 The first String message to print.
     * @param msg2 The second String message to print.
     */
    public static void printAsync(String msg1, String msg2) {
        new Thread(() -> {
            System.out.println(msg1);
            System.out.println(msg2);
        }).start();
    }

    /**
     * Asynchronously prints a String message without a new line at the end.
     *
     * @param msg The String message to print.
     */
    public static void printAsyncNoLine(String msg){
        new Thread(()->{System.out.print(msg);System.out.println(ansi());}).start();
    }

    /**
     * Asynchronously prints an Ansi message without a new line at the end.
     *
     * @param msg The Ansi message to print.
     */
    public static void printAsyncNoLine(Ansi msg){
        new Thread(()->{System.out.print(msg);System.out.println(ansi());}).start();
    }

    /**
     * Asynchronously prints a String message without resetting the Ansi attributes.
     *
     * @param msg The String message to print.
     */
    public static void printAsyncNoCursorReset(String msg){
        new Thread(()->{System.out.print(msg);}).start();
    }

    /**
     * Asynchronously prints an Ansi message without resetting the Ansi attributes.
     *
     * @param msg The Ansi message to print.
     */
    public static void printAsyncNoCursorReset(Ansi msg){
        new Thread(()->{System.out.print(msg);}).start();
    }

    /**
     * Asynchronously prints a StringBuilder message without resetting the Ansi attributes.
     *
     * @param msg The StringBuilder message to print.
     */
    public static void printAsyncNoCursorReset(StringBuilder msg){
        new Thread(()->{System.out.print(msg);}).start();
    }

}
