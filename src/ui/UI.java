package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GamePanel;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font PressStart2P;
    InputStream is;
    BufferedImage image;

    public double timecount = 60;
    public int commandNum = 0;
    public int current_index = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        importFont();
    }

    public void importFont() {
        try {
            is = getClass().getResourceAsStream("/assets/PressStart2P-Regular.ttf");
            PressStart2P = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException f) {
        } catch (IOException e) {
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(PressStart2P);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.startState) {
            drawStartScreen();
        }
        if (gp.gameState == gp.playState) {
            drawHealth();
            drawEnemyHealth();
            drawTimer();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawHealth();
            drawEnemyHealth();
            drawLatestTime();
        }
        if (gp.gameState == gp.endState) {
            drawEndScreen();
        }
        if (gp.gameState == gp.afterState) {
            drawAfterGameScreen();
        }
    }

    public void checkState() {
        if (timecount <= 0 || gp.player.dead == true || gp.enemy.dead == true) {
            gp.gameState = gp.endState;
        }
    }

    public void drawTimer() {
        g2.setColor(Color.white);
        if (timecount <= 0) {
            timecount = 0;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
            String timevalue = String.valueOf((int) timecount);
            int x1 = getxCenter(timevalue);
            g2.drawString(timevalue, x1, 50);

        } else {
            timecount -= (double) 1 / 60;
            String strTime = String.valueOf((int) timecount);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
            int x = getxCenter(strTime);
            g2.drawString(strTime, x, 50);

        }
    }

    public void drawLatestTime() {
        g2.setColor(Color.white);
        String strTime = String.valueOf((int) timecount);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        int x = getxCenter(strTime);
        g2.drawString(strTime, x, 50);
    }

    private void drawHealth() {

        int x = 430;
        int y = 7;
        int i = 0;

        while (i < gp.player.maxLife) {
            g2.setColor(Color.WHITE);
            g2.drawRect(x, y, 34, 50);
            g2.fillRect(x, y, 34, 50);
            i++;
            x -= 34;
        }

        x = 430;
        y = 7;
        i = 0;

        while (i < gp.player.life) {
            if (i < gp.player.life) {
                g2.setColor(new Color(167, 199, 231));
                g2.drawRect(x, y, 34, 50);
                g2.fillRect(x, y, 34, 50);
            }
            i++;
            x -= 34;
        }

    }

    private void drawEnemyHealth() {

        int x = 556;
        int y = 7;
        int i = 0;

        while (i < gp.enemy.maxLife) {
            g2.setColor(Color.WHITE);
            g2.drawRect(x, y, 34, 50);
            g2.fillRect(x, y, 34, 50);
            i++;
            x += 34;
        }

        x = 556;
        y = 7;
        i = 0;

        while (i < gp.enemy.life) {
            if (i < gp.enemy.life) {
                g2.setColor(new Color(250, 160, 160));
                g2.drawRect(x, y, 34, 50);
                g2.fillRect(x, y, 34, 50);
            }
            i++;
            x += 34;
        }

    }

    public void drawPauseScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        String text = "PAUSED";
        int x = getxCenter(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/start.png"));
        } catch (IOException e) {

        }

        g2.drawImage(image, 0, 0, gp.screenWidth, gp.screenHeight, null);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        String text1 = "Brotherhood";
        int x1 = getxCenter(text1);
        int y1 = (gp.getHeight() / 5) + 50;

        g2.setColor(Color.white);
        g2.drawString(text1, x1, y1);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String text2 = "START";
        int x2 = getxCenter(text2);
        int y2 = gp.getHeight() - 200;
        g2.drawString(text2, x2, y2);

        if (commandNum == 0) {
            g2.drawString(">", x2 - gp.tileSize, y2);
        }

        String text3 = "EXIT";
        int x3 = getxCenter(text3);
        int y3 = y2 + gp.tileSize + gp.tileSize / 4;
        g2.drawString(text3, x3, y3);

        if (commandNum == 1) {
            g2.drawString(">", x3 - gp.tileSize, y3);
        }
    }

    public void drawStartScreen() {
        gp.dg.setDialogue();
        g2.setBackground(Color.BLACK);
        g2.setColor(Color.white);
        String text = "";
        if (current_index % 2 == 0) {
            text = "Yasuo";
        } else {
            text = "Yone";
        }

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        int x = 75;
        int y = 450;
        g2.drawString(text, x, y);

        String current_dialogue = gp.dg.dialogues[current_index];
        g2.drawString(current_dialogue, x, y + 50);
    }

    public void drawEndScreen() {
        g2.setColor(Color.WHITE);
        String text = "";
        if (timecount <= 0 || gp.player.dead == true) {
            text = "DEFEAT";
        } else {
            text = "VICTORY";
        }

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        int x2 = getxCenter(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x2, y);

    }

    public void drawAfterGameScreen() {
        g2.setColor(Color.WHITE);
        String text = "Yasuo";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
        int x = 75;
        int y = 450;
        g2.drawString(text, x, y);

        if (timecount <= 0 || gp.player.dead == true) {
            current_index = 3;
        } else {
            current_index = 2;
        }
        String current_dialogue = gp.dg.dialogues[current_index];
        g2.drawString(current_dialogue, x, y + 50);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12F));
        String text2 = "RETRY";
        int x2 = 900;
        int y2 = 525;
        g2.drawString(text2, x2, y2);

        if (commandNum == 0) {
            g2.drawString(">", x2 - 16, y2);
        }

        String text3 = "EXIT";
        int x3 = 900;
        int y3 = y2 + 20;
        g2.drawString(text3, x3, y3);

        if (commandNum == 1) {
            g2.drawString(">", x3 - 16, y3);
        }
    }

    public int getxCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
