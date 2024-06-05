package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.Utilities.InputGUI;

//controller per gestire gli input dell'utente
//in teoria è abstract e viene implementata dai vari controller per le varie scene
public abstract class ControllerGUI {
    private InputGUI inputGUI;

    public InputGUI getInputGUI(){
        return inputGUI;
    }
    public void setInputGUI(InputGUI inputGUI) {
        this.inputGUI = inputGUI;
    }
}
