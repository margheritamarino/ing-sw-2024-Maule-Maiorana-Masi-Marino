package it.polimi.ingsw.model;
import java.io.Serializable;

//This class contains all the DEFAULT VALUES needed in the project

public class DefaultValue implements Serializable {

    public final static boolean DEBUG = false;
    public final static int printHeight = 7;
    public final static int printLenght = 30;
    public final static int max_messagesShow = 5;
    public final static int BookSizeMax = 70;
    public final static int BookSizeMin = 0;
    public final static int MaxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;
    public final static int NumOfGoldCards = 40;
    public final static int NumOfResourceCards = 40;
    public final static int NumOfInitialCards = 6;
    public final static int NumOfObjectiveCards = 16;


    public final static int NumOfColumnsBook = 70;
    public final static int NumOfRowsBook = 70;

    public final static int Default_port_RMI = 4321;
    public final static int Default_port_Socket = 4320;
    public static String serverIp = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";

    public final static int time_publisher_showing_seconds = 20;

    public final static int MaxEventToShow = 6; //da definire
    public final static String Default_servername_RMI = "CodexNaturalis";

    public final static int secondsToWaitReconnection = 30;
    public final static int timeoutConnection_millis =3000;
    public final static int secondToWaitToSend_heartbeat =500;
    public final static Long timeout_for_detecting_disconnection = 4000L;

    public final static int maxAttemptsBeforeGiveUp= 5;
    public final static int secondsToReconnection = 5;
    public static final int secondToWaitToSend_ping = 500;


    //aggiungiamo tutti i valori ROW/COL che specificano in quale parte
    //dell'INTERFACCIA UTENTE appariranno le INFO di ciascuna classe e i messaggi


}
