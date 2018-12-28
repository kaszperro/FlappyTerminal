package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Input;
import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.RectangularRenderer;
import cs.agh.flappy.scenes.GameOverScene;
import cs.agh.flappy.scenes.Scene;

import java.io.IOException;
import java.util.function.Consumer;

public class Bird extends GameObject {

    private double time = 0;
    private Input input;

    public Bird(int width, int height) {
        Consumer<Collider> onCollide = collider -> {
            GameOverScene gameOver = new GameOverScene();
            getScene().getSceneManager().changeScene(gameOver);
            destroy();
        };

        Collider collider = new Collider(onCollide, width, height);
        addComponent(collider);
        RectangularRenderer renderer = new RectangularRenderer(width, height);
        addComponent(renderer);
    }

    @Override
    public void start() {
        setPosition(new Position(3, getScene().getCamera().getHeight() >> 1));
        input = getScene().getInput();
    }

    @Override
    public void update(double delta) {
        Position myPos = getPosition();
        setPosition(myPos.add(30 * delta, 0));

        if (input.getPressed() == 65) {
            myPos = getPosition();
            setPosition(myPos.add(0, 10 * delta));
            time=0;
        } else {
            myPos = getPosition();
            setPosition(myPos.add(0, -(time*time)/2));
            time+=delta;
        }

    }
}
