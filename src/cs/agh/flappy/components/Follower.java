package cs.agh.flappy.components;

import cs.agh.flappy.Game;
import cs.agh.flappy.Position;
import cs.agh.flappy.gameObjects.GameObject;

public class Follower extends GameComponent {
    private GameObject toFollow;
    private int xOffset;
    private int yOffset;
    private boolean xFollow = false;
    private boolean yFollow = false;

    public Follower(GameObject toFollow, int xOffset) {
        this.toFollow = toFollow;
        this.xOffset = xOffset;
        xFollow = true;
    }

    public Follower(GameObject toFollow, int xOffset, int yOffset) {
        this.toFollow = toFollow;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        xFollow = true;
        yFollow = true;
    }


    @Override
    public void update() {
        Position followerPosition = toFollow.getPosition();
        Position currentPos = getGameObject().getPosition();
        Position newPosition = currentPos;
        if (xFollow) {
            newPosition = new Position(followerPosition.getX() + xOffset, newPosition.getY());
        }
        if (yFollow) {
            newPosition = new Position(newPosition.getX(), followerPosition.getY() + yOffset);
        }
        getGameObject().setPosition(newPosition);
    }
}
