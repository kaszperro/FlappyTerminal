package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;

public class RunningCamera extends Camera {
    private float speed;

    public RunningCamera(int width, int height, float speed) {
        super(width, height);
        this.speed = speed;
    }


    @Override
    public void update(double delta) {
        Position myPos = getPosition();
        setPosition(myPos.add(speed * delta, 0));
    }
}
