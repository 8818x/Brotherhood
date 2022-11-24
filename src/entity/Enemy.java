package entity;

// import java.awt.BasicStroke;
// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Enemy extends Entity {
    GamePanel gp;

    public double scale = 2.5;
    public double pwidth;
    public double pheight;

    public Enemy(GamePanel gp) {
        this.gp = gp;

        hitbox.x = 70;
        hitbox.y = 30;
        hitbox.width = (int) (24 * scale);
        hitbox.height = (int) (50 * scale);

        attackBox.width = 170;
        attackBox.height = 50;

        setDefaultValues();
        getPlayerImages();
    }

    public void setDefaultValues() {

        x = 600;
        y = 0;
        direction = "idle";
        dead = false;
        speed = 10;
        maxLife = 13;
        life = maxLife;

    }

    public void getPlayerImages() {
        try {
            for (int i = 1; i <= 8; i++) {

                String idleDir = "/assets/Yone/Yone_Idle/Yone_Idle (" + i + ").png";
                String runDir = "/assets/Yone/Yone_Run/Yone_Run (" + i + ").png";
                String atkDir = "/assets/Yone/Yone_Attack/Yone_Attack (" + i + ").png";
                String fallDir = "/assets/Yone/Yone_Fall/Yone_Fall (" + i + ").png";
                String jumpDir = "/assets/Yone/Yone_Jump/Yone_Jump (" + i + ").png";

                Yone_Idle[i] = ImageIO.read(getClass().getResourceAsStream(idleDir));
                Yone_Run[i] = ImageIO.read(getClass().getResourceAsStream(runDir));
                Yone_Attack[i] = ImageIO.read(getClass().getResourceAsStream(atkDir));
                Yone_Fall[i] = ImageIO.read(getClass().getResourceAsStream(fallDir));
                Yone_Jump[i] = ImageIO.read(getClass().getResourceAsStream(jumpDir));

            }

        } catch (IOException e) {

        }
    }

    public void update() {

        gp.ai.follow(20);

        if (gp.ai.detect() == true) {
            attacking = true;
        }

        if (attacking == true) {
            attacking();
        } else {
            if (y < 175) {
                y += 17.5;
            }
            if (x <= 730 && x >= -225) {

            } else if (x > 0) {
                x = 729;
            } else if (x < 0) {
                x = -224;
            }
        }

        if (direction == "left") {
            x -= speed;
        }
        if (direction == "right") {
            x += speed;
        }
        if (direction == "jump") {
            y += gp.ai.vely;
        }

        if (gp.ui.timecount <= 10) {
            gp.ai.vely = -50;
        }

        if (life <= 0) {
            dead = true;
        }

        walking();

    }

    public void walking() {
        spriteCounter++;
        if (spriteCounter > 2) {
            if (spriteNum == 8) {
                spriteCheck = 1;
                spriteNum = spriteCheck;
            } else if (spriteNum == spriteCheck) {
                spriteCheck++;
                spriteNum = spriteCheck;
            }
            spriteCounter = 0;
        }
    }

    public void attacking() {
        attackCounter++;
        if (attackCounter <= 6) {
            attackNum = attackCheck;
            attackCheck++;
        }
        if (attackCounter > 6 && attackCounter <= 8) {
            attackNum = 7;

            int tempScreenX = (int) x - hitbox.x - (int) (16 * 6);
            int tempScreenY = (int) y + hitbox.y;
            attackBox.x = tempScreenX;
            attackBox.y = tempScreenY;

            if (gp.cc.rectangularCollision(gp.enemy, gp.player) == true) {
                gp.player.life--;
            }

        }
        if (attackCounter > 8) {
            attackNum = 1;
            attackCheck = attackNum;
            attackCounter = 0;
            attacking = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "idle":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yone_Idle[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yone_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yone_Run[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yone_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yone_Run[9 - i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yone_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "jump":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yone_Jump[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yone_Attack[i];
                            break;
                        }
                    }
                }
                break;
            // case "fall":
            // if(attacking == false) {
            // for (int i = 1; i <= 8; i++) {
            // if (spriteNum == i) {
            // image = Yone_Fall[i];
            // break;
            // }
            // }
            // }
            // if(attacking == true) {
            // for (int i = 1; i <= 8; i++) {
            // if (attackNum == i) {
            // image = Yone_Attack[i];
            // break;
            // }
            // }
            // }
            // break;
        }

        pwidth = image.getWidth() * scale;
        pheight = image.getHeight() * scale - 16;

        g2.drawImage(image, (int) x, (int) y, (int) pwidth, (int) pheight, null);

        // DEBUG
        // AttackBox
        // int tempScreenX = (int)x - hitbox.x - (int) (16*6) + 200;
        // int tempScreenY = (int)y + hitbox.y + 200;

        // g2.setColor(Color.blue);
        // g2.setStroke(new BasicStroke(1));
        // g2.drawRect(tempScreenX, tempScreenY, attackBox.width, attackBox.height);

    }
}
