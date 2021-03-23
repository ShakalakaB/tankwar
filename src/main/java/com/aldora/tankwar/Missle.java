package com.aldora.tankwar;

import java.awt.*;

public class Missle {
    public static final int MISSLE_SPEED = 10;

    private int x;

    private int y;

    private final Direction direction;

    private final boolean isEnemy;

    private boolean isAlive = true;

    private Image missleImage;

    public boolean isAlive() {
        return isAlive;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    boolean isEnemy() {
        return isEnemy;
    }

    Missle(int x, int y, Direction direction, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isEnemy = isEnemy;
        this.missleImage = this.getImage();
    }

    void paint(Graphics graphics) {
        this.determineMovingPosition();

        if (this.x < 0 || this.x + this.missleImage.getWidth(null) > 800) {
            this.isAlive = false;
            return;
        }

        if (this.y < 0 || this.y + this.missleImage.getHeight(null) > 600) {
            this.isAlive = false;
            return;
        }

        Rectangle missleRectangle = this.getRectangle();
        for (Wall wall : App.getInstance().getWalls()) {
            if (wall.getRectangle().intersects(missleRectangle)) {
                this.isAlive = false;
                break;
            }
        }

        if (this.isEnemy) {
            Tank playerTank = App.getInstance().getPlayerTank();
            if (playerTank.getRectangle().intersects(missleRectangle)) {
                playerTank.setHp(App.getInstance().getPlayerTank().getHp() - 20);

                if (playerTank.getHp() <= 0) {
                    playerTank.setAlive(false);
                }

                this.isAlive = false;
            }
        } else {
            for (Tank enemyTank : App.getInstance().getEnemyTanks()) {
                if (enemyTank.getRectangle().intersects(missleRectangle)) {
                    enemyTank.setAlive(false);

                    this.isAlive = false;
                }
            }
        }

        graphics.drawImage(this.missleImage, this.x, this.y, null);
    }

    Image getImage() {
        this.missleImage = this.direction.getImage("missile");

        return this.missleImage;
    }

    void determineMovingPosition() {
        this.x += this.direction.xFactor * MISSLE_SPEED;
        this.y += this.direction.yFactor * MISSLE_SPEED;
    }

    Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.missleImage.getWidth(null), this.missleImage.getWidth(null));
    }
}
