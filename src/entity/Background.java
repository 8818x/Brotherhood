package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background extends Entity {

    GamePanel gp;

    public Background(GamePanel gp) {
        this.gp = gp;
        getbg();
    }

 
    public void getbg() {
        try {
            Background_Image = ImageIO.read(getClass().getResourceAsStream("/assets/background.png"));
        } catch (IOException e) {

        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = Background_Image;
        g2.drawImage(image, 0, 0, 1024, 576, null);
    }
}
