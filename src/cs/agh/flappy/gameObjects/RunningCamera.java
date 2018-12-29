package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;

public class RunningCamera extends Camera {
    private float speed;
    private Position startPos;
    private double time = 0;

    public RunningCamera(int width, int height, float speed) {
        super(width, height);
        this.speed = speed;
    }

    @Override
    public void start() {
        startPos = getPosition();
        time = 0;
    }

    @Override
    protected void update(double delta) {
        time += delta;
        setPosition(new Position(startPos.getX() + (int) (time * speed), startPos.getY()));
    }
}
