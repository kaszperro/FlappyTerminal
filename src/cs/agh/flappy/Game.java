package cs.agh.flappy;

import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.Pipe;
import cs.agh.flappy.gameObjects.PipeCreator;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {
    public static void main(String[] args) throws IOException {
        Camera mainCamera = new Camera(40, 20);
        Scene firstScene = new Scene(mainCamera);
        firstScene.addGameObject(new PipeCreator(6, 10, 3,5));

        firstScene.run();

    }
}
