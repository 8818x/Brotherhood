package main;

import entity.Entity;

public class CollisionChecker {
    public GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean rectangularCollision(Entity e1, Entity e2) {
        return e1.attackBox.x + e1.attackBox.width >= e2.x && e1.attackBox.x <= e2.x + e2.hitbox.width
                && e1.attackBox.y + e1.attackBox.height >= e2.y && e1.attackBox.y <= e2.y + e2.hitbox.height;

    }
}
