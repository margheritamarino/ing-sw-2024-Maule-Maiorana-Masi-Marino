package it.polimi.ingsw.view.Utilities;

//scopo: leggere input da un'interfaciia grafica e aggiungerli al buffer
public class InputGUI implements InputReader{

    private final Buffer buffer;

    public InputGUI(){
        buffer = new Buffer();
    }
    @Override
    public Buffer getBuffer() {
        return buffer;
    }

    //syncronized: può essere chiamato da più thread contemporaneamente in modo sicuro
    public synchronized void addTxt(String txt){
        buffer.addInputData(txt);
    }
}
