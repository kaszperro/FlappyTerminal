package cs.agh.flappy.scenes;

import cs.agh.flappy.gameObjects.Bird;
import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.PipeCreator;
import cs.agh.flappy.gameObjects.RunningCamera;


public class GameScene extends Scene {
    public GameScene() {
        Camera myCamera = new RunningCamera(50,20,30);
        addCamera(myCamera);
    }

    @Override
    public void startScene(SceneManager sceneManager) {
        addGameObject(new PipeCreator(6, 10, 5, 5));
        addGameObject(new Bird(2, 2));
        super.startScene(sceneManager);
    }
}
