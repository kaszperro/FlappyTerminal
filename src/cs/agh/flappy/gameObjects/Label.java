package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.components.RectangularRenderer;


import java.util.function.Function;

public class Label extends GameObject {
    private String text;

    private Function<Position, Character> myRender = new Function<Position, Character>() {
        @Override
        public Character apply(Position position) {
            return text.charAt((int) position.getX());
        }
    };

    private RectangularRenderer renderer;

    public Label(String text) {
        this.text = text;
        createRenderer();
    }

    public int getWidth() {
        return text.length();
    }

    private void createRenderer() {
        renderer = new RectangularRenderer(
                text.length(),
                1,
                myRender
        );

        addComponent(renderer);
    }


    public void setText(String text) {
        this.text = text;
        renderer.setWidth(getWidth());
    }
}
