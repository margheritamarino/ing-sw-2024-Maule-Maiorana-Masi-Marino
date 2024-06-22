package it.polimi.ingsw.view.GUI.controllers;

import it.polimi.ingsw.view.Utilities.InputGUI;

/**
 * Abstract controller class to manage user inputs in the GUI.
 * This class serves as a base class implemented by various controllers for different scenes.
 */
public abstract class ControllerGUI {
    private InputGUI inputGUI;

    /**
     * Returns the input GUI instance associated with this controller.
     *
     * @return the input GUI instance
     */
    public InputGUI getInputGUI(){
        return inputGUI;
    }

    /**
     * Sets the input GUI instance for this controller.
     *
     * @param inputGUI the input GUI instance to set
     */
    public void setInputGUI(InputGUI inputGUI) {
        this.inputGUI = inputGUI;
    }
}
