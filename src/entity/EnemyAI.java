package entity;

import main.GamePanel;

public class EnemyAI {
    public GamePanel gp;
    public int actionLockcounter = 0;
    public double vely = 0;

    public EnemyAI(GamePanel gp) {
        this.gp = gp;
    }

    public void follow(int interval) {
        actionLockcounter++;
        if (actionLockcounter > interval) {
            double distance = gp.player.x - gp.enemy.x;
            double air = gp.player.y - gp.enemy.y;

            if (distance <= 0) {
                gp.enemy.direction = "left";
            } else if (distance > 0) {
                gp.enemy.direction = "right";
            }

            if (air < 0) {
                gp.enemy.direction = "jump";
                vely = -30;
            } // else if(air > 0) {
              // gp.enemy.direction = "fall";
              // }

            actionLockcounter = 0;
        }
    }

    public boolean detect() {
        double distance = Math.abs(gp.player.x - gp.enemy.x);
        double air_d = Math.abs(gp.player.y - gp.enemy.y);
        if (distance <= 220 && distance >= 40 && air_d <= 30) {
            return true;
        }
        return false;
    }
}
