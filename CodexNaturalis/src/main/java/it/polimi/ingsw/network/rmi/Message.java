package it.polimi.ingsw.network.rmi;

import java.io.Serializable;


    public interface Message extends Serializable {
        public String getContent();
    }


