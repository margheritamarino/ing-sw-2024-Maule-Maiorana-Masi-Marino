package it.polimi.ingsw.network;

import java.util.TimerTask;


import it.polimi.ingsw.view.flow.*;

public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;
    public TaskOnNetworkDisconnection(Flow flow){
        this.flow=flow;
    }
    public void run() {
        flow.noConnectionError();
    }

}
