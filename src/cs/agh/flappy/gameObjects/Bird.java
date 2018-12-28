package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Input;
import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.Follower;
import cs.agh.flappy.components.RectangularRenderer;
import cs.agh.flappy.scenes.GameOverScene;
import cs.agh.flappy.scenes.Scene;

import java.io.IOException;
import java.util.function.Consumer;

public class Bird extends GameObject {
    private double time = 0;
    private Input input;
    private PipeCreator pipeCreator;
    private Label scoreLabel;
    private int score;
    private Pipe currentPipe = null;

    private int width;
    private int height;

    public Bird(int width, int height) {
        this.width = width;
        this.height = height;


    }

    @Override
    public void start() {
        Camera mainCamera = getScene().getCamera();
        setPosition(new Position(3, getScene().getCamera().getHeight() >> 1));
        input = getScene().getInput();
        pipeCreator = getScene().getGameObjectsOfType(PipeCreator.class).get(0);
        scoreLabel = new Label("0") {
            @Override
            public void update(double delta) {
                setPosition(getPosition().add(30 * delta, 0));
            }
        };
        getScene().addGameObject(scoreLabel);
        scoreLabel.addComponent(new Follower(mainCamera, mainCamera.getWidth() - 4, mainCamera.getHeight() - 3));

        Consumer<Collider> onCollide = collider -> {
            GameOverScene gameOver = new GameOverScene(this.score);
            getScene().getSceneManager().changeScene(gameOver);
            destroy();
        };

        Collider collider = new Collider(onCollide, width, height);
        addComponent(collider);
        RectangularRenderer renderer = new RectangularRenderer(width, height);
        addComponent(renderer);

        addComponent(new Follower(mainCamera, 3));
        score = 0;
    }

    @Override
    public void update(double delta) {

        if (input.getPressed() == 65) {
            setPosition(getPosition().add(0, 10 * delta));
            time = 0;
        } else {
            setPosition(getPosition().add(0, -(time * time) / 2));
            time += delta;
        }

        Pipe newPipe = pipeCreator.getOverPipe(getPosition().getX());
        if (newPipe != null && newPipe != currentPipe) {
            score += 1;
        }
        currentPipe = newPipe;
        scoreLabel.setText(Integer.toString(score));

    }
}
