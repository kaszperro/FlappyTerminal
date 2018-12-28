package cs.agh.flappy;

import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Input implements Runnable {

    private AtomicInteger myCharCode = new AtomicInteger();
    private NonBlockingReader reader;

    public Input(NonBlockingReader reader) {
        this.reader = reader;


    }

    @Override
    public void run() {
        while (true) {
            try {
                myCharCode.set(reader.read(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPressed() {
        return myCharCode.get();
    }

}
