package ui;

import main.GamePanel;

public class Dialogue {
    public GamePanel gp;
    String[] dialogues = new String[20];

    public Dialogue(GamePanel gp) {
        this.gp = gp;
    }

    public void setDialogue() {
        dialogues[0] = "Today is your death day, Yone!";
        dialogues[1] = "Are you sure?";
        dialogues[2] = "10 years, it's still not too late to take revenge.";
        dialogues[3] = "You are too strong.";
    }

}
