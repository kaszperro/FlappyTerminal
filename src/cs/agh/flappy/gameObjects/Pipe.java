package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.RectangularRenderer;

import java.util.function.Consumer;

public class Pipe extends GameObject {

    Consumer<Collider> onCollide = collider -> {
       // System.out.print("koliduje");
    };

    public Pipe(int width, int height) {
        Collider collider = new Collider(onCollide, width, height);
        addComponent(collider);
        RectangularRenderer renderer = new RectangularRenderer(width, height);
        addComponent(renderer);
    }


    /*@Override
    public void update() {
        Position myPos = getPosition();
        setPosition(myPos.add(1, 0));
    }*/
}
