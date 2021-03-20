package com.aldora.tankwar;

import java.awt.*;

public class Missle {
    public static final int SPEED = 10;

    private int x;

    private int y;

    private final Direction direction;

    private final boolean isEnemy;

    private Image missleImage;

    public Missle(int x, int y, Direction direction, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isEnemy = isEnemy;
        this.missleImage = this.getImage();
    }

    public void paint(Graphics graphics) {
        this.determineMovingPosition();

        if (this.x < 0 || this.x + this.missleImage.getWidth(null) > 800) {
            return;
        }

        if (this.y < 0 || this.y + this.missleImage.getHeight(null) > 600) {
            return;
        }

        graphics.drawImage(this.missleImage, this.x, this.y, null);
    }

    protected Image getImage() {
        this.missleImage = this.direction.getImage("missile");

        return this.missleImage;
    }

    protected void determineMovingPosition() {
        switch (this.direction) {
            case UP:
                this.y -= SPEED;
                break;
            case DOWN:
                this.y += SPEED;
                break;
            case LEFT:
                this.x -= SPEED;
                break;
            case RIGHT:
                this.x += SPEED;
                break;
            case LEFT_UP:
                this.x -= SPEED;
                this.y -= SPEED;
                break;
            case RIGHT_UP:
                this.x += SPEED;
                this.y -= SPEED;
                break;
            case LEFT_DOWN:
                this.x -= SPEED;
                this.y += SPEED;
                break;
            case RIGHT_DOWN:
                this.x += SPEED;
                this.y += SPEED;
                break;
        }
    }
}
