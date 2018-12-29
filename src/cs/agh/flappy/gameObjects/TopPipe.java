package cs.agh.flappy.gameObjects;

import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.RectangularRenderer;
import org.fusesource.jansi.Ansi;

import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class TopPipe extends GameObject {
    private int width;


    private Random rand = new Random();

    int n = rand.nextInt(50) + 1;

    public TopPipe(int width, int height) {
        Collider collider = new Collider(width, height);
        addComponent(collider);

        RectangularRenderer renderer = new RectangularRenderer(
                width,
                height,
                position -> {
                    Ansi myRet = ansi();
                    if (position.getY() < 2) {
                        myRet.fgRed().a('*');
                    } else {
                        if (position.getX() >= 1 && position.getX() < width - 1)
                            myRet.fgBlue().a('*');
                        else
                            myRet.a(' ');
                    }

                    return myRet.fgDefault().toString();
                }
        );

        addComponent(renderer);
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

}
