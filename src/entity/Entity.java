package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public double x, y;
    public double speed;
    public double gravity;

    public int maxLife;
    public int life;
    public boolean dead = false;

    public String direction;

    public Rectangle hitbox = new Rectangle(0, 0, 0, 0);
    public Rectangle attackBox = new Rectangle(0, 0, 0, 0);
    public boolean attacking = false;
    public boolean click = false;

    public BufferedImage[] Yasuo_Idle = new BufferedImage[9];
    public BufferedImage[] Yasuo_Run = new BufferedImage[9];
    public BufferedImage[] Yasuo_Jump = new BufferedImage[9];
    public BufferedImage[] Yasuo_Fall = new BufferedImage[9];
    public BufferedImage[] Yasuo_Attack = new BufferedImage[9];

    public BufferedImage[] Yone_Idle = new BufferedImage[9];
    public BufferedImage[] Yone_Run = new BufferedImage[9];
    public BufferedImage[] Yone_Attack = new BufferedImage[9];
    public BufferedImage[] Yone_Jump = new BufferedImage[9];
    public BufferedImage[] Yone_Fall = new BufferedImage[9];

    public BufferedImage[] Shop_Animate = new BufferedImage[7];
    public BufferedImage Background_Image;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spriteCheck = 1;

    public int fallCounter = 0;
    public int fallNum = 1;
    public int fallCheck = 1;

    public int jumpCounter = 0;
    public int jumpNum = 1;
    public int jumpCheck = 1;

    public int attackCounter = 0;
    public int attackNum = 1;
    public int attackCheck = 1;

}
