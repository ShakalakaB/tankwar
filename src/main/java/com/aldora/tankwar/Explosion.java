package com.aldora.tankwar;

import java.awt.*;

public class Explosion {
    private int x;

    private int y;

    private boolean isAlive = true;

    private int step = 0;

    Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setAlive(boolean status) {
        this.isAlive = status;
    }

    void paint(Graphics graphics) {
        if (this.step > 10) {
            this.isAlive = false;
            return;
        }

        String imageName = String.valueOf(this.step++);
        graphics.drawImage(Tools.getImage(imageName + ".gif"), this.x, this.y, null);
    }

    boolean isAlive() {
        return this.isAlive;
    }
}
