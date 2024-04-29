package it.polimi.ingsw.main;

import it.polimi.ingsw.network.rmi.RMIServer;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        try {
            // Crea il server RMI e associa l'oggetto all'oggetto registro RMI
            RMIServer server = RMIServer.bind();

            // Visualizza messaggio di successo
            System.out.println("Server RMI start success.");

            // A questo punto, il server è pronto per ascoltare le richieste dei client.
            // Puoi aggiungere ulteriore codice per simulare i client e testare le funzionalità del server

        } catch (Exception e) {
            // Gestisce eventuali errori durante l'avvio del server
            System.err.println("[ERROR] Error during server start: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
