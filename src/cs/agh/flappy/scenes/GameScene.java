package cs.agh.flappy.scenes;

import cs.agh.flappy.Position;
import cs.agh.flappy.components.Collider;
import cs.agh.flappy.components.Follower;
import cs.agh.flappy.components.RectangularRenderer;
import cs.agh.flappy.gameObjects.*;

import static org.fusesource.jansi.Ansi.ansi;


public class GameScene extends Scene {
    public GameScene() {
        Camera myCamera = new RunningCamera(50, 20, 15);
        addCamera(myCamera);
    }

    @Override
    public void startScene(SceneManager sceneManager) {
        getCamera().addChild(new Bird(2, 2));
        addGameObject(new PipeCreator(6, 10, 5, 5));

        GameObject topGameObject = new GameObject();
        topGameObject.setPosition(new Position(0, getCamera().getHeight() - 3));
        topGameObject.setzPos(-1);
        getCamera().addChild(topGameObject);
        topGameObject.addComponent(new RectangularRenderer(getCamera().getWidth(), 4,
                position -> ansi().fgBrightBlue().a('^').fgDefault().toString()));
        topGameObject.addComponent(new Collider(getCamera().getWidth(), 4));


        GameObject bottomGameObject = new GameObject();
        bottomGameObject.setPosition(new Position(0, -1));
        bottomGameObject.addComponent(new RectangularRenderer(getCamera().getWidth(), 4,
                position -> ansi().fgBrightGreen().a('-').fgDefault().toString()));
        bottomGameObject.addComponent(new Collider(getCamera().getWidth(), 4));
        bottomGameObject.setzPos(-1);
        getCamera().addChild(bottomGameObject);


        super.startScene(sceneManager);
    }
}
