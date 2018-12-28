package cs.agh.flappy.components;

import cs.agh.flappy.gameObjects.GameObject;

import java.time.Duration;
import java.time.Instant;

public abstract class GameComponent {
    private GameObject gameObject = null;
    private Instant lastUpdated = null;

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
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

    protected abstract void update(double delta);

}
