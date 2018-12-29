package cs.agh.flappy.gameObjects;

import cs.agh.flappy.Position;
import cs.agh.flappy.scenes.Scene;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PipeCreator extends GameObject {
    private Camera mainCamera;

    private List<GameObject> pipeList = new LinkedList<>();

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

    public GameObject getOverPipe(double xPos) {
        for (GameObject pipe : pipeList) {
            if (pipe.getPosition().getX() <= xPos && pipe.getPosition().getX() + pipesWidth >= xPos)
                return pipe;
        }
        return null;
    }


    @Override
    protected void update(double delta) {
        int lastPos = mainCamera.getWidth() + mainCamera.getPosition().getX();
        if (!pipeList.isEmpty()) {
            lastPos = pipeList.get(pipeList.size() - 1).getPosition().getX();
        }
        if (lastPos <= mainCamera.getWidth() + mainCamera.getPosition().getX()) {
            int mySpacePos = randomRange(margin + pipesSpaceY, mainCamera.getHeight() - margin);

            int botSpace = mySpacePos - pipesSpaceY;
            TopPipe newTopPipe = new TopPipe(pipesWidth, mainCamera.getHeight() - mySpacePos);
            BottomPipe newBotPipe = new BottomPipe(pipesWidth, botSpace);

            int newXPos = lastPos + pipesSpaceX + pipesWidth;

            newTopPipe.setPosition(new Position(newXPos, mySpacePos));
            newBotPipe.setPosition(new Position(newXPos, 0));

            pipeList.add(newBotPipe);
            pipeList.add(newTopPipe);
            myScene.addGameObject(newTopPipe);
            myScene.addGameObject(newBotPipe);
        }


        for (Iterator<GameObject> iter = pipeList.listIterator(); iter.hasNext(); ) {
            GameObject a = iter.next();
            if (a.getPosition().getX() + pipesWidth < mainCamera.getWordPosition().getX()) {
                a.destroy();
                iter.remove();
            } else {
                break;
            }
        }
    }
}

