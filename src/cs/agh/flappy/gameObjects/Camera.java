package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.scenes.Scene;
import cs.agh.flappy.components.Renderer;

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

    public String getImage(Scene scene) {
        List<Renderer> rendererList = scene.getComponentsOfType(Renderer.class);
        StringBuilder builder = new StringBuilder();
        for (int h = 1; h <= height; ++h) {
            for (int w = 0; w < width; ++w) {
                int x = w + getWordPosition().getX();
                int y = height - h + getWordPosition().getY();

                char myChar = ' ';
                int zPos = -1;

                for (Renderer renderer : rendererList) {
                    GameObject gameObject = renderer.getGameObject();
                    Position objAnchor = gameObject.getWordPosition().add(renderer.getAnchor());
                    Position relative = new Position(x, y).sub(objAnchor);
                    GameObject renGameO = renderer.getGameObject();

                    if (renderer.isPositionInside(relative) && renGameO.getzPos() > zPos) {
                        myChar = renderer.getCharAtPosition(relative);
                        zPos = renGameO.getzPos();
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
}
