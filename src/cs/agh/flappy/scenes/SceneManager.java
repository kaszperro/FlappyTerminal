package cs.agh.flappy.scenes;

import cs.agh.flappy.Input;
import org.fusesource.jansi.AnsiConsole;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.fusesource.jansi.Ansi.ansi;

public class SceneManager {
    public class RunningSubThread implements Runnable {
        private AtomicBoolean running = new AtomicBoolean(false);
        private Scene myScene;
        private SceneManager sceneManager;

        RunningSubThread(Scene runScene, SceneManager sceneManager) {
            this.myScene = runScene;
            this.sceneManager = sceneManager;
        }


        public void interrupt() {
            running.set(false);

        }

        boolean isRunning() {
            return running.get();
        }

        public void run() {
            running.set(true);
            myScene.writeEmptyImage(terminal);
            myScene.startScene(sceneManager);
            while (running.get()) {
                try {
                    myScene.runFrame(terminal);
                } catch (InterruptedException e) {
                    myScene.stopScene();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private RunningSubThread sceneRunner = null;
    private Terminal terminal;
    private Input input;

    public void stopScene() {
        if (sceneRunner != null) {
            sceneRunner.interrupt();
        }
    }

    public void startScene(Scene newScene) {
        sceneRunner = new RunningSubThread(newScene, this);
        sceneRunner.run();
    }

    public void changeScene(Scene newScene) {
        if (sceneRunner != null) {
            Scene currentScene = sceneRunner.myScene;
            terminal.writer().print(ansi().cursorUp(currentScene.getCamera().getHeight()).
                    cursorLeft(currentScene.getCamera().getWidth()));
        }

        stopScene();
        startScene(newScene);
    }


    private String hideCursor() {
        return "\u001b[?25l";
    }

    private String showCursor() {
        return "\u001b[?25h";
    }

    public SceneManager() throws IOException {

        AnsiConsole.systemInstall();
        Terminal terminal = TerminalBuilder.builder()
                .jna(true)
                .system(true)
                .build();
        terminal.enterRawMode();
        this.terminal = terminal;
        terminal.writer().print(hideCursor());

        input = new Input(terminal.reader());
        Thread inputThread = new Thread(input);
        inputThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print(showCursor()); //show cursor on end
        }));
    }

    public Input getInput() {
        return input;
    }

}
