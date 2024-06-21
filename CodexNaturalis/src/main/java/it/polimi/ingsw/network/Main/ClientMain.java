package it.polimi.ingsw.network.Main;


import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.view.GUI.GUIApplication;
import it.polimi.ingsw.view.flow.GameFlow;
import javafx.application.Application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.view.TUI.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * The main class for the client application. Handles user input to configure
 * and start the game with the desired settings.
 */
public class ClientMain {

    /**
     * The main method that serves as the entry point for the client application.
     * It handles the user input for configuring the server IP, client IP, and
     * selecting the UI and communication protocol to be used.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        clearCMD();
        int selection;

        if (!DefaultValue.DEBUG) {
            String input;


            do {
                clearCMD();
                printAsync(ansi().a("""
                        Insert remote IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine().trim();
                if (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input)) {
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input));

            if (!input.isEmpty() && !input.equals("empty")) {
                DefaultValue.serverIp = input;
            }
            clearCMD();

            do {
                printAsync(ansi().a("""
                        Insert your IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine().trim();
                if (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input)) {
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input));

            if (!input.isEmpty() && !input.equals("empty")) {
                System.setProperty("java.rmi.server.hostname", input);
            }

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

                input = new Scanner(System.in).nextLine();
                try {
                    selection = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    selection = -1;
                    printAsync("Nan");
                }
            } while (selection != 1 && selection != 2 && selection != 3 && selection != 4);
        } else {
            selection = 2;
        }

        ConnectionType conSel;
        if (selection == 1 || selection == 3) {
            conSel = ConnectionType.SOCKET;
        } else {
            conSel = ConnectionType.RMI;
        }

        printAsync("Starting the game!");

        if (selection == 1 || selection == 2) {
            new GameFlow(conSel);
        } else {
            Application.launch(GUIApplication.class, String.valueOf(conSel));
        }

    }

    /**
     * Clears the terminal screen. Works for both Windows and Unix-based systems.
     */
    private static void clearCMD() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param input contains the IP inserted by the player
     * @return true if the input value is a valid IP (X.X.X.X with 0<=X<=255 )
     */
    private static boolean isValidIP(String input) {
        List<String> parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                int segment = Integer.parseInt(part);
                if (segment < 0 || segment > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }


}



