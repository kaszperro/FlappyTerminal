package cs.agh.flappy;

import cs.agh.flappy.components.GameComponent;
import cs.agh.flappy.gameObjects.Camera;
import cs.agh.flappy.gameObjects.GameObject;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.fusesource.jansi.Ansi.ansi;

public class Scene {
    class Pair<T, V> {
        T first;
        V second;

        Pair(T t, V v) {
            first = t;
            second = v;
        }

        public T getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }


    private List<GameObject> gameObjectsList = new LinkedList<>();
    private List<GameObject> cacheGameObjects = new LinkedList<>();
    private Camera mainCamera;
    public Terminal terminal;
    public Input input;

    Instant previousTime;

    Scene(Camera camera) throws IOException {
        AnsiConsole.systemInstall();
        this.mainCamera = camera;
        addGameObject(camera);

        Terminal terminal = TerminalBuilder.builder()
                .jna(true)
                .system(true)
                .build();
        terminal.enterRawMode();

        this.terminal = terminal;

        input = new Input(terminal.reader());
        Thread inputThread = new Thread(input);
        inputThread.start();

        terminal.writer().print(ansi().a(camera.getEmptyImage()));
    }

    public void addGameObject(GameObject gameObject) {
        cacheGameObjects.add(gameObject);
        gameObject.setScene(this);
    }


    public void addCachedGameObjects() {
        gameObjectsList.addAll(cacheGameObjects);
        cacheGameObjects.clear();
    }

    public <T extends GameObject> List<T> getGameObjectOfType(Class<T> myClass) {
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

    public void run() throws IOException {
        addCachedGameObjects();
        startObjects();

        previousTime = Instant.now();
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateObjects();
                previousTime = Instant.now();
            }
        }, 0, 3);*/


        while (true) {
            addCachedGameObjects();
            updateComponents();
            render();
            updateObjects();
            previousTime = Instant.now();
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public Camera getCamera() {
        return mainCamera;
    }


    private void render() throws IOException {
        terminal.writer().print(ansi().cursorUp(mainCamera.getHeight()).cursorLeft(mainCamera.getWidth()));
        terminal.writer().print(ansi().a(mainCamera.getImage(this)));
    }
}
