package com.aldora.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x;

    private int y;

    private Direction direction;

    private Direction movingDirection;

    private boolean up = false;

    private boolean down = false;

    private boolean left = false;

    private boolean right = false;

    private boolean isEnemy = false;

    private Image tankImage;

    public Tank(int x, int y, Direction direction, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isEnemy = isEnemy;
        this.tankImage = this.getImage();
    }

    public void paint(Graphics graphics) {
        int oldX = this.x, oldY = this.y;

        this.determineMovingDirection();
        this.determineMovingPosition();
        this.tankImage = this.getImage();

        if (this.x < 0) {
            this.x = 0;
        } else if (this.x + this.tankImage.getWidth(null) > 800) {
            this.x = 800 - this.tankImage.getWidth(null);
        }

        if (this.y < 0) {
            this.y = 0;
        } else if (this.y + this.tankImage.getHeight(null) > 600) {
            this.y = 600 - this.tankImage.getHeight(null);
        }

        for (Wall wall: App.getInstance().getWall()) {
            if (this.getRectangle().intersects(wall.getRectangle())) {
                this.x = oldX;
                this.y = oldY;
                break;
            }
        }

        for (Tank enemyTank: App.getInstance().getEnemyTanks()) {
            if (this.getRectangle().intersects(enemyTank.getRectangle())) {
                this.x = oldX;
                this.y = oldY;
                break;
            }
        }

        graphics.drawImage(this.tankImage, this.x, this.y, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.tankImage.getWidth(null), this.tankImage.getHeight(null));
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.up = true;
                break;
            case KeyEvent.VK_DOWN:
                this.down = true;
                break;
            case KeyEvent.VK_LEFT:
                this.left = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.up = false;
                break;
            case KeyEvent.VK_DOWN:
                this.down = false;
                break;
            case KeyEvent.VK_LEFT:
                this.left = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = false;
                break;
        }
    }

    protected Image getImage() {
        String prefix = this.isEnemy ? "e" : "";

        switch (this.direction) {
            case UP:
                this.tankImage = Tools.getImage(prefix + "tankU.gif");
                break;
            case DOWN:
                this.tankImage = Tools.getImage(prefix + "tankD.gif");
                break;
            case LEFT:
                this.tankImage = Tools.getImage(prefix + "tankL.gif");
                break;
            case RIGHT:
                this.tankImage = Tools.getImage(prefix + "tankR.gif");
                break;
            case UPLEFT:
                this.tankImage = Tools.getImage(prefix + "tankLU.gif");
                break;
            case UPRIGHT:
                this.tankImage = Tools.getImage(prefix + "tankRU.gif");
                break;
            case DOWNLEFT:
                this.tankImage = Tools.getImage(prefix + "tankLD.gif");
                break;
            case DOWNRIGHT:
                this.tankImage = Tools.getImage(prefix + "tankRD.gif");
                break;
        }

        return this.tankImage;
    }

    protected void determineMovingDirection() {
        Direction horizontal, vertical;

        if ((this.left || this.right) && !(this.left && this.right)) {
            horizontal = this.right ? Direction.RIGHT : Direction.LEFT;
        } else {
            horizontal = null;
        }

        if ((this.up || this.down) && !(this.up && this.down)) {
            vertical = this.up ? Direction.UP : Direction.DOWN;
        } else {
            vertical = null;
        }

        if (horizontal != null && vertical == null) {
            this.direction = this.movingDirection = (horizontal == Direction.LEFT) ? Direction.LEFT : Direction.RIGHT;
        } else if (horizontal == null && vertical != null) {
            this.direction = this.movingDirection = (vertical == Direction.UP) ? Direction.UP : Direction.DOWN;
        } else if (horizontal != null && vertical != null) {
            if (horizontal == Direction.LEFT && vertical == Direction.UP) {
                this.direction = this.movingDirection = Direction.UPLEFT;
            } else if (horizontal == Direction.LEFT && vertical == Direction.DOWN) {
                this.direction = this.movingDirection = Direction.DOWNLEFT;
            } else if (horizontal == Direction.RIGHT && vertical == Direction.UP) {
                this.direction = this.movingDirection = Direction.UPRIGHT;
            } else if (horizontal == Direction.RIGHT && vertical == Direction.DOWN) {
                this.direction = this.movingDirection = Direction.DOWNRIGHT;
            }
        } else if (horizontal == null && vertical == null) {
            this.movingDirection = null;
        }
    }

    protected void determineMovingPosition() {
        if (this.movingDirection == null) {
            return;
        }

        switch (this.movingDirection) {
            case UP:
                this.y -= 5;
                break;
            case DOWN:
                this.y += 5;
                break;
            case LEFT:
                this.x -= 5;
                break;
            case RIGHT:
                this.x += 5;
                break;
            case UPLEFT:
                this.x -= 5;
                this.y -= 5;
                break;
            case UPRIGHT:
                this.x += 5;
                this.y -= 5;
                break;
            case DOWNLEFT:
                this.x -= 5;
                this.y += 5;
                break;
            case DOWNRIGHT:
                this.x += 5;
                this.y += 5;
                break;
        }
    }
}
