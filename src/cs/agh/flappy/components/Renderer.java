package cs.agh.flappy.components;

import cs.agh.flappy.Position;

public abstract class Renderer extends GameComponent {

    private Position anchor;

    public Renderer(Position anchor) {
        this.anchor = anchor;
    }

    public Position getAnchor(){
        return new Position(anchor);
    }

    public abstract boolean isPositionInside(Position relativePosition);

    public abstract String getCharAtPosition(Position relativePosition);

}
