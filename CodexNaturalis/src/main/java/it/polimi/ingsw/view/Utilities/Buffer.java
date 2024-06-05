package it.polimi.ingsw.view.Utilities;

import java.util.ArrayDeque;
import java.util.Queue;

public class Buffer {

    private Queue<String> inputData;

    public Buffer(){
        inputData = new ArrayDeque<>();
    }

    public void addInputData(String txt){
        synchronized (this) {
            inputData.add(txt);
            this.notifyAll();
        }
    }

    /**
     * Pops one element from the queue
     * @return the popped element
     * @throws InterruptedException
     */
    public String popInputData() throws InterruptedException {
        synchronized (this){
            while(inputData.isEmpty()){this.wait();}
            return inputData.poll();
        }
    }

    public void popAllData(){
        synchronized (this) {
            while (!inputData.isEmpty()) {
                inputData.poll();
            }
        }
    }


}
