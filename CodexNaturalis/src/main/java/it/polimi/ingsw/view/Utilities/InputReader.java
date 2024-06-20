package it.polimi.ingsw.view.Utilities;

/**
 * Interface for handling input readers, applicable to both Text-based User Interface (TUI) and Graphical User Interface (GUI).
 */
public interface InputReader {

    /**
     * Retrieves the buffer used for storing user inputs.
     *
     * @return The Buffer instance used for storing user inputs.
     */
    Buffer getBuffer();
}
