package it.polimi.ingsw.view.TUI;

import org.fusesource.jansi.Ansi;
//import it.polimi.ingsw.model.DefaultValue;
//da fare


public class PrintAsync {
    public static void printAsync(Ansi msg){
        //new Thread(()->{System.out.println(msg); System.out.println(ansi().cursor(DefaultValue.row_input,0));}).start();
    }
    public static void printAsync(String msg){
        //new Thread(()->{System.out.println(msg);System.out.println(ansi().cursor(DefaultValue.row_input,0));}).start();
    }
    public static void printAsync(StringBuilder msg){
        //new Thread(()->{System.out.println(msg);System.out.println(ansi().cursor(DefaultValue.row_input,0));}).start();
    }
    public static void printAsyncNoLine(String msg){
        //new Thread(()->{System.out.print(msg);System.out.println(ansi().cursor(DefaultValue.row_input,0));}).start();
    }
    public static void printAsyncNoLine(Ansi msg){
        //new Thread(()->{System.out.print(msg);System.out.println(ansi().cursor(DefaultValue.row_input,0));}).start();
    }
    public static void printAsyncNoCursorReset(String msg){
        //new Thread(()->{System.out.print(msg);}).start();
    }
    public static void printAsyncNoCursorReset(Ansi msg){
        //new Thread(()->{System.out.print(msg);}).start();
    }
    public static void printAsyncNoCursorReset(StringBuilder msg){
        //new Thread(()->{System.out.print(msg);}).start();
    }

}
