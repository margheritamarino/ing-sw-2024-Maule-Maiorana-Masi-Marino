module PSP24 {
    requires javafx.controls;
    requires javafx.fxml;
    //requires org.junit.jupiter.api;
    requires com.google.gson;
    requires java.rmi;
    requires org.fusesource.jansi;
    requires java.desktop;
    requires json.simple;
    exports it.polimi.ingsw.view.GUI.scenes;
    exports it.polimi.ingsw.view.GUI;
    exports it.polimi.ingsw.model.player;
    exports it.polimi.ingsw.model.cards;
    exports it.polimi.ingsw.model;
    exports it.polimi.ingsw.view.GUI.controllers;
    exports it.polimi.ingsw.Chat;
    exports it.polimi.ingsw.exceptions;
    exports it.polimi.ingsw.view.Utilities;
    exports it.polimi.ingsw.network.rmi;
    exports it.polimi.ingsw.listener;
    exports it.polimi.ingsw.model.game;
    opens it.polimi.ingsw.view.GUI.controllers to javafx.fxml, javafx. graphics;
    opens it.polimi.ingsw.model to com.google.gson;
    opens it.polimi.ingsw.model.cards to com.google.gson;

}