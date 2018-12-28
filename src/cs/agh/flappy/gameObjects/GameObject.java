package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.scenes.Scene;
import cs.agh.flappy.components.GameComponent;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameObject {
    private List<GameComponent> componentsList = new LinkedList<>();
    private Position position = new Position();
    private Scene scene = null;
    private int zPos = 0;
    private Instant lastUpdated = null;
    private GameObject parent = null;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public final void update() {
        if (lastUpdated == null) {
            lastUpdated = Instant.now();
        }

        Instant now = Instant.now();
        double duration = Duration.between(lastUpdated, now).toNanos();
        lastUpdated = now;
        update(duration / 1e9);
    }

    protected void update(double delta) {
    }

    public void start() {

    }

    public void destroy() {
        scene.destroyGameObject(this);
    }

    public void addComponent(GameComponent gameComponent) {
        componentsList.add(gameComponent);
        gameComponent.setGameObject(this);
    }

    public <T extends GameComponent> T getComponentOfType(Class<T> myClass) {
        for (GameComponent object : componentsList) {
            if (myClass.isInstance(object)) {
                return (T) object;
            }
        }
        return null;
    }

    public List<GameComponent> getComponentsList() {
        return new LinkedList<>(componentsList);
    }

    public void setPosition(Position other) {
        this.position = other;
    }

    public Position getPosition() {
        return new Position(this.position);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setzPos(int zPos) {
        this.zPos = zPos;
    }

    public int getzPos() {
        return zPos;
    }

    public <T extends GameComponent> void removeComponent(Class<T> myClass) {
        componentsList.removeIf(component -> myClass.isInstance(component.getClass()));
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getParent() {
        return parent;
    }

    public Position getWordPosition() {
        if (getParent() == null) {
            return getPosition();
        }
        return getPosition().add(getParent().getWordPosition());
    }

    public void addChild(GameObject child) {
        getScene().addGameObject(child);
        child.setParent(this);
    }
}


