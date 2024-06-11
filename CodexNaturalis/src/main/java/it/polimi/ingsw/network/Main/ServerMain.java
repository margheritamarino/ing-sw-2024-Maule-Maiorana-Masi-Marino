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



public class ServerMain {
    public static void main(String[] args) throws IOException {

        String input;

        /*do {
            clearCMD();
            printAsync(ansi().a("""
                    Insert remote IP(leave empty for localhost)
                    """));
            input = new Scanner(System.in).next();
            System.out.println("Input ricevuto: " + input);
        } while (!(input.equals("empty") || input.isEmpty())&& !isValidIP(input));
        if (input.equals("empty")|| input.isEmpty())
            System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        else{
            DefaultValue.serverIp = input;
            System.setProperty("java.rmi.server.hostname", input);
        }*/
        do {
            clearCMD();
            printAsync(ansi().a("""
                    Insert remote IP (leave empty for localhost):
                    """));
            input = new Scanner(System.in).nextLine().trim();  // Usa nextLine() e trim() per catturare l'input vuoto
            if(input.isEmpty()||input.equals("empty")){
                System.out.println("Input ricevuto: localhost ");
            } else {
                System.out.println("Input ricevuto: " + input);
            }
        } while (!input.isEmpty() && !(input.equals("empty")) && !isValidIP(input));  // Solo controllo per input vuoto e IP valido

        if (input.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", DefaultValue.Remote_ip);
        } else {
            DefaultValue.serverIp = input;
            System.setProperty("java.rmi.server.hostname", input);
        }

        //System.out.println("Sto per eseguire il BIND (da ServerMain chiamo ServerRMI.bind()): " + input);
        // Pausa di 1 secondo
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
            System.exit(1); // Termina il programma in caso di errore
        }
        // Pausa di 1 second0
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ServerTCP serverSOCKET = new ServerTCP();
        serverSOCKET.start(DefaultValue.Default_port_Socket);
        System.out.println("Server Started");
    }

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