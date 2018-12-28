package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PipeCreator extends GameObject {
    private Camera mainCamera;

    private List<Pipe> pipeList = new LinkedList<>();

    private int pipesWidth;
    private int pipesSpaceX;
    private int pipesSpaceY;
    private int margin;
    private Random myRandom = new Random();

    private Scene myScene;

    public PipeCreator(int pipesWidth, int pipesSpaceX, int pipesSpaceY, int margin) {
        this.pipesWidth = pipesWidth;
        this.pipesSpaceX = pipesSpaceX;
        this.pipesSpaceY = pipesSpaceY;
        this.margin = margin;
    }

    @Override
    public void start() {
        mainCamera = getScene().getCamera();
        myScene = getScene();
    }

    private int randomRange(int start, int end) {
        return myRandom.nextInt(end - start + 1) + start;
    }

    @Override
    public void update(double delta) {
        double lastPos = mainCamera.getWidth() + mainCamera.getPosition().getX();
        if (!pipeList.isEmpty()) {
            lastPos = pipeList.get(pipeList.size() - 1).getPosition().getX();
        }
        if (lastPos <= mainCamera.getWidth() + mainCamera.getPosition().getX()) {
            int mySpacePos = randomRange(margin+pipesSpaceY, mainCamera.getHeight() - margin);

            int botSpace = mySpacePos - pipesSpaceY;
            Pipe newTopPipe = new Pipe(pipesWidth, mainCamera.getHeight() - mySpacePos);
            Pipe newBotPipe = new Pipe(pipesWidth, botSpace);

            double newXPos = lastPos + pipesSpaceX + pipesWidth;

            newTopPipe.setPosition(new Position(newXPos, mySpacePos));
            newBotPipe.setPosition(new Position(newXPos, 0));

            pipeList.add(newBotPipe);
            myScene.addGameObject(newTopPipe);
            myScene.addGameObject(newBotPipe);
        }
    }
}

