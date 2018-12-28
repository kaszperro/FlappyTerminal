package cs.agh.flappy.scenes;

import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.RectangularRenderer;
import cs.agh.flappy.gameObjects.*;


public class GameScene extends Scene {
    public GameScene() {
        Camera myCamera = new RunningCamera(50, 20, 30);
        addCamera(myCamera);
    }

    @Override
    public void startScene(SceneManager sceneManager) {
        addGameObject(new PipeCreator(6, 10, 5, 5));
        addGameObject(new Bird(2, 2));
        GameObject topGameObject = new GameObject() {
            @Override
            public void update(double delta) {
                setPosition(getPosition().add(30 * delta, 0));
            }
        };
        topGameObject.addComponent(new Collider(getCamera().getWidth(), 4));
        topGameObject.setPosition(new Position(0, getCamera().getHeight()-1));


        GameObject bottomGameObject = new GameObject() {
            @Override
            public void update(double delta) {
                setPosition(getPosition().add(30 * delta, 0));
            }
        };
        bottomGameObject.addComponent(new Collider(getCamera().getWidth(), 4));
        bottomGameObject.setPosition(new Position(0, -4));

        addGameObject(topGameObject);
        addGameObject(bottomGameObject);

        super.startScene(sceneManager);
    }
}
