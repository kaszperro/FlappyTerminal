package cs.agh.flappy;

import cs.agh.flappy.gameObjects.Bird;
import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.PipeCreator;
import cs.agh.flappy.scenes.GameScene;
import cs.agh.flappy.scenes.Scene;
import cs.agh.flappy.scenes.SceneManager;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    public static void main(String[] args) throws IOException {
        SceneManager manager = new SceneManager();
        manager.changeScene(new GameScene());
    }

}
