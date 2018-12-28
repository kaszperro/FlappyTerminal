package cs.agh.flappy.components;

import cs.agh.flappy.Position;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Collider extends GameComponent {
    private Position anchor;
    private float width;
    private float height;
    private Consumer<Collider> onCollision;

    public Collider(Consumer<Collider> onCollision, float width, float height) {
        anchor = new Position(0, 0);
        this.width = width;
        this.height = height;
        this.onCollision = onCollision;
    }

    Collider(Consumer<Collider> onCollision, float width, float height, Position anchor) {
        this.anchor = anchor;
        this.width = width;
        this.height = height;
        this.onCollision = onCollision;
    }


    public Position getAnchor() {
        return new Position(anchor);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    public boolean collides(Collider other) {
        Position myPosition = this.getGameObject().getPosition().add(anchor);
        Position otherPosition = other.getGameObject().getPosition().add(other.getAnchor());
        float otherWidth = other.getWidth();
        float otherHeight = other.getHeight();

        Position myTopRight = myPosition.add(width / 2, height / 2);
        Position myBotLeft = myPosition.sub(width / 2, height / 2);

        Position otherTopRight = otherPosition.add(otherWidth / 2, otherHeight / 2);
        Position otherBotLeft = otherPosition.sub(otherWidth / 2, otherHeight / 2);


        if (myTopRight.getY() < otherBotLeft.getY() || myBotLeft.getY() > otherTopRight.getY())
            return false;

        return !(myTopRight.getX() < otherBotLeft.getX()) && !(myBotLeft.getX() > otherTopRight.getX());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collider collider = (Collider) o;
        return getGameObject().equals(collider.getGameObject());

    }


    @Override
    public void update() {
        List<Collider> colliderList = getGameObject().getScene().getComponentsOfType(Collider.class);
        for (Collider other : colliderList) {
            if (other.equals(this))
                continue;
            if (collides(other))
                onCollision.accept(other);
        }
    }
}
