package com.aldora.tankwar;

import javax.swing.*;
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

    public Tank(int x, int y, Direction direction, boolean isEnemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isEnemy = isEnemy;
    }

    Image getImage() {
        String prefix = this.isEnemy ? "e" : "";

        switch (this.direction) {
            case UP:
                return new ImageIcon("assets/images/" + prefix + "tankU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/" + prefix + "tankD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/" + prefix + "tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankR.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/" + prefix + "tankLU.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankRU.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/" + prefix + "tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankRD.gif").getImage();
        }

        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public void paint(Graphics graphics) {
        this.determineMovingDirection();
        this.determineMovingPosition();
        graphics.drawImage(this.getImage(), this.x, this.y, null);
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
