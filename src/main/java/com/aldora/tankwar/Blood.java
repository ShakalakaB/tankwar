package com.aldora.tankwar;

import java.awt.*;

public class Blood {
    private int x, y;

    private boolean isAlive;

    private Image bloodImage;

    public Blood(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = false;
        this.bloodImage = Tools.getImage("blood.png");
    }

    boolean isAlive() {
        return this.isAlive;
    }

    void paint(Graphics graphics) {
        if (this.getRectangle().intersects(App.getInstance().getPlayerTank().getRectangle())
                && this.isAlive) {
            App.getInstance().getPlayerTank().setHp(Tank.MAX_HP);
            Tools.playSound("revive.wav");
            this.isAlive = false;
        }

        graphics.drawImage(this.bloodImage, this.x, this.y, null);
    }

    void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.bloodImage.getWidth(null), this.bloodImage.getHeight(null));
    }
}
