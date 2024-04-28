package it.polimi.ingsw.network.rmi;

import java.rmi.server.UnicastRemoteObject;

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the RMI Network Protocol
 */
public class RMIClient extends UnicastRemoteObject implements Client{

}
