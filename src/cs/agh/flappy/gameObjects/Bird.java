package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Input;
import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.Follower;
import cs.agh.flappy.components.RectangularRenderer;
import cs.agh.flappy.scenes.GameOverScene;

import java.util.function.Consumer;

public class Bird extends GameObject {
    private Input input;
    private PipeCreator pipeCreator;
    private Label scoreLabel;
    private int score;
    private Pipe currentPipe = null;

    private int width;
    private int height;


    private double time;
    private Position startPos;
    private double downForce = 10;

    public void resetGravity() {
        time = 0;
        startPos = getPosition();
    }

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
        scoreLabel = new Label("0");
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

        resetGravity();
        score = 0;
    }

    @Override
    protected void update(double delta) {
        if (input.getPressed() == 65) {
            setPosition(getPosition().add(0, 10 * delta));
            resetGravity();
        } else {
            double posX = getPosition().getX();
            double posY = startPos.getY() - downForce * time * time / 2;
            setPosition(new Position(posX, posY));
        }
        time += delta;

        Pipe newPipe = pipeCreator.getOverPipe(getWordPosition().getX());
        if (newPipe != null && newPipe != currentPipe) {
            score += 1;
        }
        currentPipe = newPipe;
        scoreLabel.setText(Integer.toString(score));

    }
}
