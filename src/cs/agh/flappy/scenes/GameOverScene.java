package cs.agh.flappy.scenes;

import cs.agh.flappy.Position;
import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.Label;

import java.io.IOException;

public class GameOverScene extends Scene {
    private int scoredPoints;

    public GameOverScene(int scoredPoints) {
        Camera myCamera = new Camera(50, 20);
        addCamera(myCamera);
        this.scoredPoints = scoredPoints;
    }

    @Override
    public void startScene(SceneManager sceneManager) {
        Label myLabel = new Label("Game Over :(");
        addGameObject(myLabel);
        myLabel.setPosition(new Position((getCamera().getWidth() - myLabel.getWidth()) >> 1, getCamera().getHeight() >> 1));

        Label scoreLabel = new Label("You scored: " + Integer.toString(scoredPoints));
        scoreLabel.setPosition(new Position((getCamera().getWidth() - scoreLabel.getWidth()) >> 1, (getCamera().getHeight() >> 1) - 2));
        addGameObject(scoreLabel);
        super.startScene(sceneManager);
    }
}
