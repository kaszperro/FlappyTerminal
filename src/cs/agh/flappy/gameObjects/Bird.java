package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Input;
import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.Follower;
import cs.agh.flappy.components.Gravity;
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

    private Gravity myGravity;

    private double accumulation = 0;

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
        scoreLabel.setPosition(new Position(mainCamera.getWidth() - 4, mainCamera.getHeight() - 3));
        mainCamera.addChild(scoreLabel);

        Consumer<Collider> onCollide = collider -> {
            GameOverScene gameOver = new GameOverScene(this.score);
            getScene().getSceneManager().changeScene(gameOver);
            destroy();
        };

        Collider collider = new Collider(onCollide, width, height);
        addComponent(collider);
        RectangularRenderer renderer = new RectangularRenderer(width, height);
        addComponent(renderer);
        myGravity = new Gravity(6);
        addComponent(myGravity);
        myGravity.resetGravity();
        score = 0;

        accumulation = 0;
    }

    @Override
    protected void update(double delta) {
        if (input.getPressed() == 65) {
            accumulation += delta * 15;
            Position prevPos = getPosition();
            setPosition(getPosition().add(0, (int) accumulation));
            accumulation -= (int) accumulation;
            myGravity.resetGravity();
        }

        Pipe newPipe = pipeCreator.getOverPipe(getWordPosition().getX());
        if (newPipe != null && newPipe != currentPipe) {
            score += 1;
        }
        currentPipe = newPipe;
        scoreLabel.setText(Integer.toString(score));

    }
}
