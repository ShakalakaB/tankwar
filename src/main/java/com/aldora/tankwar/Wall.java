package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private int x;

    private int y;

    private int bricksNumber;

    private boolean isHorizontal;

    private Image brickImage;

    public Wall(int x, int y, int bricksNumber, boolean isHorizontal) {
        this.x = x;
        this.y = y;
        this.bricksNumber = bricksNumber;
        this.isHorizontal = isHorizontal;
        this.brickImage = Tools.getImage("brick.png");
    }

    public void paint(Graphics graphics) {
        for (int i = 0; i < this.bricksNumber; i++) {
            if (this.isHorizontal) {
                graphics.drawImage(brickImage, this.x + i * brickImage.getWidth(null), this.y, null);
            } else {
                graphics.drawImage(brickImage, this.x, this.y + i * brickImage.getHeight(null), null);
            }
        }
    }

    public Rectangle getRectangle() {
        return this.isHorizontal ?
                new Rectangle(this.x, this.y, this.bricksNumber * this.brickImage.getWidth(null),
                        this.brickImage.getHeight(null)) :
                new Rectangle(this.x, this.y, this.brickImage.getWidth(null),
                        this.bricksNumber * this.brickImage.getHeight(null));
    }
}
