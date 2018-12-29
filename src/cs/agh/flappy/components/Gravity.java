package cs.agh.flappy.components;

import cs.agh.flappy.Position;

import java.time.Instant;

public class Gravity extends GameComponent {

    private double time;
    private Position startPos;
    private double downForce;

    public Gravity(double downForce) {
        this.downForce = downForce;
        time = 0;
    }

    public void resetGravity() {
        time = 0;
        startPos = getGameObject().getPosition();
    }

    @Override
    protected void update(double delta) {
        time += delta;
        Position pos = getGameObject().getPosition();
        getGameObject().setPosition(new Position(pos.getX(), startPos.getY() - (int) (downForce * time * time / 2)));
    }
}
