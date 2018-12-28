package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.RectangularRenderer;

import java.util.function.Consumer;

public class Pipe extends GameObject {
    int width;

    public Pipe(int width, int height) {
        Collider collider = new Collider(width, height);
        addComponent(collider);
        RectangularRenderer renderer = new RectangularRenderer(width, height);
        addComponent(renderer);
        this.width = width;
    }

    public int getWidth() {
        return width;
    }


}
