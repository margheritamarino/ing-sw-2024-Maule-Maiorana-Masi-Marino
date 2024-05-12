package it.polimi.ingsw.network.Main;


import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.view.flow.GameFlow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.view.TUI.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

public class ClientMain {

    public static void main(String[] args) {
        clearCMD(); //ripulisco il terminale
        int selection;

        if (!DefaultValue.DEBUG) {
            String input;


            do { //chiedo all'utente di inserire l'indirizzo IP del server remoto (se vuole connettersi a un server diverso dal LocalHost)
                printAsync(ansi().a("""
                        Insert remote IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
                if(!input.equals("empty") && !isValidIP(input)){
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.equals("empty") && !isValidIP(input)); //ripeto fino a quando non viene inserito un IP valido
            if (!input.equals("empty"))
                DefaultValue.serverIp = input;

            clearCMD();

            do {
                printAsync(ansi().a("""
                        Insert your IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
                if(!input.equals("empty") && !isValidIP(input)){
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.equals("empty") && !isValidIP(input));
            if (!input.equals("empty"))
                System.setProperty("java.rmi.server.hostname", input);


            clearCMD();
            do {
                printAsync(ansi().a("""
                        Select option:
                        \t (1) TUI + Socket
                        \t (2) TUI + RMI
                        \t
                        \t (3) GUI + Socket
                        \t (4) GUI + RMI
                        """));
                System.out.println("""
                        Select option:
                        \t (1) TUI + Socket
                        \t (2) TUI + RMI
                        \t
                        \t (3) GUI + Socket
                        \t (4) GUI + RMI
                        """);
                input = new Scanner(System.in).nextLine();
                try {
                    selection = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    selection = -1;
                    printAsync("Nan");
                }
            } while (selection != 1 && selection != 2 && selection != 3 && selection != 4);
        } else {
            selection = 2; //Default run configuration
        }


        //Get the Communication Protocol wanted
        ConnectionType conSel;
        if (selection == 1 || selection == 3) {
            conSel = ConnectionType.SOCKET;
        } else {
            conSel = ConnectionType.RMI;
        }

        printAsync("Starting the game!");
        System.out.println("Starting the game!");
        //Starts the UI wanted
        if (selection == 1 || selection == 2) {
            //Starts the game with TUI
            //I can start directly here the GameFlow
            new GameFlow(conSel);
        }

    }

    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            printAsync("\033\143");   //for Mac
        }
    }

    /**
     * @param input contains the IP inserted by the player
     * @return true if the input value is a valid IP (X.X.X.X with 0<=X<=255 )
     */
    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }


}



