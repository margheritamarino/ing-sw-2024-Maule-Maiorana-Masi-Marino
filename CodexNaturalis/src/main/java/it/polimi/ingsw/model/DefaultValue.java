package it.polimi.ingsw.model;
import java.io.Serializable;

/**
 * This class contains all the default values we need in the project
 */
public class DefaultValue implements Serializable {

    public final static boolean DEBUG = false;

    //public final static int longest_commonCardMessage = 81;
    //public final static int time_publisher_showing_seconds = 1;

    public final static int MaxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;

    public final static int NumOfGoldCards = 40;
    public final static int NumOfResourceCards = 40;
    public final static int NumOfInitialCards = 6;
    public final static int NumOfObjectiveCards = 16;
}
