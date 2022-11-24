package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Background;
import entity.Enemy;
import entity.EnemyAI;
import entity.Player;
import entity.Shop;
import ui.Dialogue;
import ui.UI;

/* Class GamePanel inherites JPanel class */
public class GamePanel extends JPanel implements Runnable {

    /* Tile Settings */
    final int originalTileSize = 16;
    final int scale = 4;
    public final int tileSize = originalTileSize * scale;

    /* Screen Settings */
    final int maxScreenCol = 16; // 1024px
    final int maxScreenRow = 9; // 576px
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    /* Object */
    public KeyHandler keyH = new KeyHandler(this);
    public Thread gameThread;
    public Player player = new Player(this, keyH);
    public Enemy enemy = new Enemy(this);
    public EnemyAI ai = new EnemyAI(this);
    public Dialogue dg = new Dialogue(this);

    public Background bg = new Background(this);
    public Shop sh = new Shop(this);

    public UI ui = new UI(this);

    public CollisionChecker cc = new CollisionChecker(this);

    /* Constructor */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /* Game State */
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int startState = 3;
    public final int endState = 4;
    public final int afterState = 5;

    /* Setup */
    public void setup() {
        gameState = titleState;
    }

    /* Thread */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /* FPS */
    int FPS = 60;

    /* Game Loop (Not Optimized Method) */
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /* Animation Update */
    public void update() {
        if (gameState == playState) {
            player.update();
            enemy.update();
            sh.update();
            ui.checkState();
        }
        if (gameState == pauseState) {

        }

    }

    /* Animation Draw */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2);
        } else if (gameState == startState) {
            ui.draw(g2);
        } else if (gameState == afterState) {
            ui.draw(g2);
        } else {
            bg.draw(g2);
            sh.draw(g2);
            player.draw(g2);
            enemy.draw(g2);
            ui.draw(g2);
        }

        g2.dispose();
    }

}
