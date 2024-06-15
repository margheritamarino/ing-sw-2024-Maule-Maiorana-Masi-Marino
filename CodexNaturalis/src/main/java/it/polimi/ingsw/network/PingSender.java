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


public class PingSender extends Thread{
    private final Flow flow;
    private final ClientInterface clientSender;

    public PingSender(Flow flow, ClientInterface clientSender) {
        this.flow=flow;
        this.clientSender=clientSender;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, DefaultValue.timeoutConnection_millis);
            //send ping every 3s so the server knows I am still online
            try {
                clientSender.ping();
            } catch (RemoteException e) {
                printAsync("Connection to server lost! Impossible to send ping ...");
            }
            timer.cancel();

            try {
                Thread.sleep(DefaultValue.secondToWaitToSend_ping); //thread dorme per 500 ms
            } catch (InterruptedException ignored) {}
        }

    }
}
