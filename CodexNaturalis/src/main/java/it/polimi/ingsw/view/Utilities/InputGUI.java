package it.polimi.ingsw.view.Utilities;

/**
 * Reads inputs from the graphical user interface (GUI) and adds them to a buffer for processing.
 * Implements the InputReader interface.
 */
public class InputGUI implements InputReader{

    private final Buffer buffer;

    /**
     * Constructs an InputGUI instance with an initialized buffer.
     */
    public InputGUI(){
        buffer = new Buffer();
    }

    /**
     * Retrieves the buffer used by this InputGUI instance.
     *
     * @return The Buffer instance used for storing GUI inputs.
     */
    @Override
    public Buffer getBuffer() {
        return buffer;
    }

    /**
     * Adds a text input from the GUI to the buffer in a synchronized manner.
     * This method ensures thread safety when called simultaneously from multiple threads.
     *
     * @param text The text input to be added to the buffer.
     */
    public synchronized void addTxt(String text){
        buffer.addInputData(text);
    }
}
