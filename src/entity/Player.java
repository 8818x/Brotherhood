package entity;

// import java.awt.BasicStroke;
// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
  

    public double scale = 2.5;
    public double pwidth;
    public double pheight;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        hitbox.x = 70;
        hitbox.y = 30;
        hitbox.width = (int) (24 * scale);
        hitbox.height = (int) (50 * scale);

        attackBox.x = 100;
        attackBox.y = 50;
        attackBox.width = 160;
        attackBox.height = 50;

        setDefaultValues();
        getPlayerImages();
    }

    public void setDefaultValues() {

        x = 0;
        y = 0;
        speed = 0;
        direction = "idle";
        gravity = 0.7;
        dead = false;
        maxLife = 13;
        life = maxLife;

    }

    public void getPlayerImages() {
        try {
            for (int i = 1; i <= 8; i++) {

                String idleDir = "/assets/Yasuo/Yasuo_Idle/Yasuo_Idle (" + i + ").png";
                String runDir = "/assets/Yasuo/Yasuo_Run/Yasuo_Run (" + i + ").png";
                String fallDir = "/assets/Yasuo/Yasuo_Fall/Yasuo_Fall (" + i + ").png";
                String jumpDir = "/assets/Yasuo/Yasuo_Jump/Yasuo_Jump (" + i + ").png";
                String atkDir = "/assets/Yasuo/Yasuo_Attack/Yasuo_Attack (" + i + ").png";

                Yasuo_Idle[i] = ImageIO.read(getClass().getResourceAsStream(idleDir));
                Yasuo_Run[i] = ImageIO.read(getClass().getResourceAsStream(runDir));
                Yasuo_Fall[i] = ImageIO.read(getClass().getResourceAsStream(fallDir));
                Yasuo_Jump[i] = ImageIO.read(getClass().getResourceAsStream(jumpDir));
                Yasuo_Attack[i] = ImageIO.read(getClass().getResourceAsStream(atkDir));

            }

        } catch (IOException e) {

        }
    }

    public void update() {
        boolean left = keyH.leftPressed;
        boolean right = keyH.rightPressed;

        if (attacking == true) {
            attacking();
        } else {
            if (y + keyH.vely >= 175) {
                keyH.vely = 0;
            } else {
                keyH.vely += gravity;
            }

            if (y + keyH.vely >= -200) {
                y += keyH.vely;
            }

            if (keyH.vely == 0) {
                y = 175;
                click = false;
            }

            if (x <= 730 && x >= -225) {
                if (left) {
                    x += speed;
                }

                if (right) {
                    x += speed;
                }
            } else if (x > 0) {
                x = 729;
            } else if (x < 0) {
                x = -224;
            }
        }

        if (life <= 0) {
            dead = true;
        }
        walking();
        jumping();
        falling();

    }

    public void jumping() {
        if (keyH.vely < 0) {
            jumpCounter++;
            if (jumpCounter <= 3) {
                jumpNum = jumpCheck;
                jumpCheck++;
            }
            if (jumpCounter > 3 && jumpCounter <= 8) {
                jumpNum = 2;
            }
            if (jumpCounter > 8) {
                jumpNum = 1;
                jumpCheck = jumpNum;
                jumpCounter = 0;
            }
        } else {
            jumpCheck = 1;
        }
    }

    public void falling() {
        if (keyH.vely > 0) {
            fallCounter++;
            if (fallCounter <= 8) {
                fallNum = fallCheck;
                fallCheck++;
            }
            if (fallCounter > 8 && fallCounter <= 30) {
                fallNum = 2;
            }
            if (fallCounter > 30) {
                fallNum = 1;
                fallCheck = fallNum;
                fallCounter = 0;
            }
        } else {
            fallCheck = 1;
        }
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

            int tempScreenX = (int) x + hitbox.x + (int) (16 * scale);
            int tempScreenY = (int) y + hitbox.y;
            attackBox.x = tempScreenX;
            attackBox.y = tempScreenY;

            if (gp.cc.rectangularCollision(gp.player, gp.enemy) == true) {
                gp.enemy.life--;
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
            case "jump":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (jumpNum == i) {
                            image = Yasuo_Jump[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yasuo_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "fall":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (fallNum == i) {
                            image = Yasuo_Fall[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yasuo_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "idle":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yasuo_Idle[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yasuo_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yasuo_Run[9 - i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yasuo_Attack[i];
                            break;
                        }
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    for (int i = 1; i <= 8; i++) {
                        if (spriteNum == i) {
                            image = Yasuo_Run[i];
                            break;
                        }
                    }
                }
                if (attacking == true) {
                    for (int i = 1; i <= 8; i++) {
                        if (attackNum == i) {
                            image = Yasuo_Attack[i];
                            break;
                        }
                    }
                }
                break;

        }

        pwidth = image.getWidth() * scale;
        pheight = image.getHeight() * scale;

        g2.drawImage(image, (int) x, (int) y, (int) pwidth, (int) pheight, null);

        if (keyH.leftPressed && keyH.lastKey == 'a') {
            direction = "left";
            speed = -10;
        } else if (keyH.rightPressed && keyH.lastKey == 'd') {
            direction = "right";
            speed = 10;
        } else {
            direction = "idle";
        }

        if (keyH.vely < 0) {
            direction = "jump";
        } else if (keyH.vely > 0) {
            direction = "fall";
        }

        if (keyH.downPressed == true && click == false) {
            attacking = true;
            click = true;
        }

        // DEBUG
        // AttackArea
        // int tempScreenX = (int)x + hitbox.x + (int) (16*scale) + 200;
        // int tempScreenY = (int)y + hitbox.y + 200;

        // g2.setColor(Color.red);
        // g2.setStroke(new BasicStroke(1));
        // g2.drawRect(tempScreenX, tempScreenY, attackBox.width, attackBox.height);

    }

}
