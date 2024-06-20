package it.polimi.ingsw.view.Utilities;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A synchronized buffer for storing and retrieving input data.
 * Provides methods to add and retrieve data safely from a queue.
 */
public class Buffer {

    private Queue<String> inputData;

    /**
     * Initializes an empty buffer using {@link ArrayDeque}.
     */
    public Buffer(){
        inputData = new ArrayDeque<>();
    }

    /**
     * Adds input data to the buffer.
     *
     * @param txt The string data to be added.
     */
    public void addInputData(String txt){
        synchronized (this) {
            inputData.add(txt);
            this.notifyAll();
        }
    }

    /**
     * Retrieves and removes the oldest element from the buffer.
     * Waits if the buffer is empty until data is available.
     *
     * @return The oldest element in the buffer.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public String popInputData() throws InterruptedException {
        synchronized (this){
            while(inputData.isEmpty()){this.wait();}
            return inputData.poll();
        }
    }

    /**
     * Removes all elements from the buffer.
     */
    public void popAllData(){
        synchronized (this) {
            while (!inputData.isEmpty()) {
                inputData.poll();
            }
        }
    }


}
