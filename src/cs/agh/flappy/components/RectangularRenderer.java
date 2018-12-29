package cs.agh.flappy.components;


import cs.agh.flappy.Position;

import java.util.function.Function;

public class RectangularRenderer extends Renderer {
    private int width;
    private int height;

    private Function<Position, String> renderFunc = position -> "*";

    RectangularRenderer(int width, int height, Position anchor) {
        super(anchor);
        this.width = width;
        this.height = height;
    }


    public RectangularRenderer(int width, int height) {
        super(new Position(0, 0));
        this.width = width;
        this.height = height;
    }

    public RectangularRenderer(int width, int height, Function<Position, String> renderFunc) {
        super(new Position(0, 0));
        this.width = width;
        this.height = height;
        this.renderFunc = renderFunc;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    @Override
    public boolean isPositionInside(Position relativePosition) {
        return relativePosition.getX() < width
                && relativePosition.getY() < height
                && relativePosition.getX() >= 0
                && relativePosition.getY() >= 0;
    }

    @Override
    public String getCharAtPosition(Position relativePosition) {
        return renderFunc.apply(relativePosition);
    }

    @Override
    protected void update(double delta) {

    }
}
