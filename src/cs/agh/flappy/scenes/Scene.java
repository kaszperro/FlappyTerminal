package cs.agh.flappy.scenes;

import cs.agh.flappy.Input;
import cs.agh.flappy.components.GameComponent;
import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.GameObject;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class Scene {
    private List<GameObject> gameObjectsList = new LinkedList<>();
    private List<GameObject> cacheGameObjects = new LinkedList<>();
    private List<GameObject> gameObjectsToDestroy = new LinkedList<>();
    private Camera mainCamera;

    private Instant previousTime = null;
    private SceneManager sceneManager = null;


    public void addCamera(Camera camera) {
        this.mainCamera = camera;
        addGameObject(mainCamera);
    }

    public Input getInput() {
        return sceneManager.getInput();
    }


    public void addGameObject(GameObject gameObject) {
        cacheGameObjects.add(gameObject);
        gameObject.setScene(this);
    }


    private void addCachedGameObjects() {
        gameObjectsList.addAll(cacheGameObjects);
        cacheGameObjects.clear();
    }

    public void destroyGameObject(GameObject gameObject) {
        gameObjectsToDestroy.add(gameObject);
    }

    private void destroyFromList() {
        gameObjectsToDestroy.removeIf(gameObject -> gameObjectsList.remove(gameObject));
    }

    public <T extends GameObject> List<T> getGameObjectsOfType(Class<T> myClass) {
        List<T> returnList = new LinkedList<>();
        for (GameObject object : gameObjectsList) {
            if (myClass.isInstance(object)) {
                returnList.add((T) object);
            }
        }
        return returnList;
    }

    public <T extends GameComponent> List<T> getComponentsOfType(Class<T> myClass) {
        List<T> returnList = new LinkedList<>();
        for (GameObject object : gameObjectsList) {
            T myComponent = object.getComponentOfType(myClass);
            if (myComponent != null) {
                returnList.add(myComponent);
            }
        }
        return returnList;
    }

    private void updateComponents() {
        for (GameObject gameObject : gameObjectsList) {
            for (GameComponent component : gameObject.getComponentsList()) {
                component.update();
            }
        }
    }

    private void updateObjects() {
        for (GameObject object : gameObjectsList) {
            double duration = Duration.between(previousTime, Instant.now()).toNanos();
            object.update(duration / 1e9);
        }
    }

    private void startObjects() {
        for (GameObject object : gameObjectsList) {
            object.start();
        }
    }
/*
    public void run() throws IOException {
        addCachedGameObjects();
        startObjects();

        previousTime = Instant.now();

        Thread runThread = new Thread() {
            @Override
            public void run() {

                synchronized (isRunning) {

                }

                super.run();
            }
        };
        runThread.start();

        while (isRunning) {
            addCachedGameObjects();
            updateComponents();
            destroyFromList();
            updateObjects();
            render();
            previousTime = Instant.now();
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    */

    public void startScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        addCachedGameObjects();
        startObjects();
        previousTime = Instant.now();

    }

    public void runFrame(Terminal terminal) throws IOException, InterruptedException {
        addCachedGameObjects();
        updateComponents();
        destroyFromList();
        updateObjects();
        render(terminal);
        previousTime = Instant.now();
        Thread.sleep(2);
    }


    public Camera getCamera() {
        return mainCamera;
    }


    private void render(Terminal terminal) throws IOException {
        terminal.writer().print(ansi().cursorUp(mainCamera.getHeight()).cursorLeft(mainCamera.getWidth()));
        terminal.writer().print(ansi().a(mainCamera.getImage(this)));
    }


    public void stopScene() {
        gameObjectsList.clear();
        cacheGameObjects.clear();
        mainCamera = null;
        previousTime = null;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void writeEmptyImage(Terminal terminal) {
        terminal.writer().print(ansi().a(mainCamera.getEmptyImage()));
    }
}


