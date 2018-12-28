package cs.agh.flappy.components;

import cs.agh.flappy.gameObjects.GameObject;

public abstract class GameComponent {
    private GameObject gameObject = null;

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void update();

}
