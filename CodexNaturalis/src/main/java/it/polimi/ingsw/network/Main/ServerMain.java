package it.polimi.ingsw.network.Main;

import it.polimi.ingsw.model.DefaultValue;
import it.polimi.ingsw.network.rmi.ServerRMI;
import it.polimi.ingsw.network.socket.server.ServerTCP;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import java.util.List;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

import java.rmi.RemoteException;



/**
 * The main class for the server application. Handles the initialization
 * and startup of the RMI and TCP servers.
 */
public class ServerMain {
    /**
     * The main method that serves as the entry point for the server application.
     * It prompts the user to input the server IP address, configures the RMI server,
     * and starts the TCP server.
     *
     * @param args the command-line arguments (not used)
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {

        String input;

        do {
            clearCMD();

            printAsync(ansi().a("""
                    Insert remote IP (leave empty for localhost):
                    """));
            input = new Scanner(System.in).nextLine().trim();
            if(input.isEmpty()||input.equals("empty")){
                System.out.println("Input ricevuto: localhost ");
            } else {
                System.out.println("Input ricevuto: " + input);
            }
        } while (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input));

        if (input.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        } else {
            DefaultValue.serverIp = input;
            System.setProperty("java.rmi.server.hostname", input);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            ServerRMI.bind();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING RMI SERVER: \n\tServer RMI exception: " + e);
            System.exit(1);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ServerTCP serverSOCKET = new ServerTCP();
        serverSOCKET.start(DefaultValue.Default_port_Socket);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Server started!");
    }

    /**
     * Validates the given IP address.
     *
     * @param input the IP address to validate
     * @return true if the input value is a valid IP address (X.X.X.X with 0 <= X <= 255), false otherwise
     */
    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
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

}