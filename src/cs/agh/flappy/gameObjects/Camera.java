package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.Scene;
import cs.agh.flappy.components.Renderer;

import java.io.IOException;
import java.util.List;

public class Camera extends GameObject {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Camera(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public String getImage(Scene scene) throws IOException {


        List<Renderer> rendererList = scene.getComponentsOfType(Renderer.class);
        StringBuilder builder = new StringBuilder();
        for (int h = 1; h <= height; ++h) {
            for (int w = 1; w <= width; ++w) {
                double x = w + getPosition().getX();
                double y = height - h + getPosition().getY();

                char myChar = ' ';

                for (Renderer renderer : rendererList) {
                    GameObject gameObject = renderer.getGameObject();
                    Position objAnchor = gameObject.getPosition().add(renderer.getAnchor());
                    Position relative = new Position(x, y).sub(objAnchor);

                    if (renderer.isPositionInside(relative)) {
                        myChar = renderer.getCharAtPosition(relative);
                    }
                }

                builder.append(myChar);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public String getEmptyImage() {
        StringBuilder builder = new StringBuilder();
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }


    @Override
    public void update(double delta) {
        Position myPos = getPosition();
        setPosition(myPos.add(30*delta, 0));
    }
}
