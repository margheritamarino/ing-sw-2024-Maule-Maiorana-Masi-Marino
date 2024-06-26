package it.polimi.ingsw.network;

//client manda al server in un thread separato il suo heartbeat per indicare che il client è ancora attivo.


import it.polimi.ingsw.view.flow.Flow;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import it.polimi.ingsw.model.DefaultValue;
import static it.polimi.ingsw.model.DefaultValue.secondToWaitToSend_ping;
import static it.polimi.ingsw.network.PrintAsync.printAsync;
import it.polimi.ingsw.network.TaskOnNetworkDisconnection;


/**
 * Thread responsible for sending periodic "ping" messages to the server to indicate that the client is still active.
 */
public class PingSender extends Thread{
    private final Flow flow;
    private final ClientInterface clientSender;

    /**
     * Constructs a PingSender instance with the specified Flow and ClientInterface.
     *
     * @param flow         The flow instance for handling network errors.
     * @param clientSender The client interface used to send ping messages.
     */
    public PingSender(Flow flow, ClientInterface clientSender) {
        this.flow=flow;
        this.clientSender=clientSender;
    }

    /**
     * Executes the thread's task of sending periodic "ping" messages to the server.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, DefaultValue.timeoutConnection_millis); //cambiato da 20 a 3 sec
            //send ping every 3s so the server knows I am still online
            try {
                clientSender.ping();
            } catch (RemoteException e) {
                flow.noConnectionError();
                printAsync("Connection to server lost! Impossible to send ping ...");
                interrupt();
            }
            timer.cancel();
            try {
                Thread.sleep(DefaultValue.secondToWaitToSend_ping); //thread dorme per 500 ms
            } catch (InterruptedException ignored) {}
        }
    }
}
