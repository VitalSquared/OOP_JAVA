package ru.nsu.spirin.battlecity.view.swing;

import ru.nsu.spirin.battlecity.controller.Action;
import ru.nsu.spirin.battlecity.controller.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingInputHandler implements KeyListener {
    private final Controller controller;
    private int escapePresses = 0;

    public SwingInputHandler(Controller controller) {
        this.controller = controller;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W -> controller.action(Action.UP);
            case KeyEvent.VK_S -> controller.action(Action.DOWN);
            case KeyEvent.VK_A -> controller.action(Action.LEFT);
            case KeyEvent.VK_D -> controller.action(Action.RIGHT);
            case KeyEvent.VK_SPACE -> controller.action(Action.ACTION);
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escapePresses++;
        }
        else {
            escapePresses = 0;
        }
        if (escapePresses >= 2) {
            controller.action(Action.ESCAPE);
        }
    }
}

