package it.polimi.ingsw.network;

//client manda al server in un thread separato il suo heartbeat per indicare che il client è ancora attivo.


import it.polimi.ingsw.view.flow.Flow;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.network.PrintAsync.printAsync;
/*
public class HeartbeatSender extends Thread{
    private Flow flow;
    private ClientInterface server;

    public HeartbeatSender(Flow flow, ClientInterface server) {
        this.flow=flow;
        this.server=server;
    }


    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
            timer.schedule(task, 3000);
            //send heartbeat so the server knows I am still online
            try {
                server.heartbeat();
            } catch (RemoteException e) {
                printAsync("Connection to server lost! Impossible to send heartbeat...");
            }
            timer.cancel();

            try {
                Thread.sleep(500); //thread dorme per 500 ms
            } catch (InterruptedException ignored) {}
        }

    }
}
*/