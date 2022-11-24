package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Shop extends Entity {
    public GamePanel gp;

    public Shop(GamePanel gp) {
        this.gp = gp;

        getshop();
    }

    public void getshop() {
        try {
            for (int i = 1; i <= 6; i++) {
                String shopDir = "/assets/Shop/shop (" + i + ").png";
                Shop_Animate[i] = ImageIO.read(getClass().getResourceAsStream(shopDir));
            }

        } catch (IOException e) {

        }
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 4) {
            if (spriteNum == 6) {
                spriteCheck = 1;
                spriteNum = spriteCheck;
            } else if (spriteNum == spriteCheck) {
                spriteCheck++;
                spriteNum = spriteCheck;
            }
            spriteCounter = 0;
        }
    }

    public double scale = 2.75;
    public double shop_width;
    public double shop_height;

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        for (int i = 1; i <= 6; i++) {
            if (spriteNum == i) {
                image = Shop_Animate[i];
                break;
            }
        }

        shop_width = image.getWidth() * scale;
        shop_height = image.getHeight() * scale;
        g2.drawImage(image, 600, 128, (int) shop_width, (int) shop_height, null);
    }
}
