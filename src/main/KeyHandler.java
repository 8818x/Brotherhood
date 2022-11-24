package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public char lastKey;
    public double vely = 0;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.startState;
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }
        } else if (gp.gameState == gp.startState) {
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.current_index < 1) {
                    gp.ui.current_index++;
                } else {
                    gp.gameState = gp.playState;
                }
            }
        }

        else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
                lastKey = 'w';
                vely = -20;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
                lastKey = 'a';
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
                lastKey = 'd';
            }
        }

        else if (gp.gameState == gp.endState) {
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.afterState;
            }

        }

        else if (gp.gameState == gp.afterState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.startState;
                    gp.player.setDefaultValues();
                    gp.enemy.setDefaultValues();
                    gp.ui.timecount = 60;
                    gp.ui.current_index = 0;
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }
        }

        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_G) {
            gp.player.life = 999;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
