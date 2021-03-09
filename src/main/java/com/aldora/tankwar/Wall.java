package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private int x;

    private int y;

    private int bricksNumber;

    private boolean isHorizontal;

    public Wall(int x, int y, int bricksNumber, boolean isHorizontal) {
        this.x = x;
        this.y = y;
        this.bricksNumber = bricksNumber;
        this.isHorizontal = isHorizontal;
    }

    public void paint(Graphics graphics) {
        Image brickImage = Tools.getImage("brick.png");

        for (int i = 0; i < this.bricksNumber; i++) {
            if (this.isHorizontal) {
                graphics.drawImage(brickImage, this.x + i * brickImage.getWidth(null), this.y, null);
            } else {
                graphics.drawImage(brickImage, this.x, this.y  + i * brickImage.getHeight(null), null);
            }
        }
    }
}
