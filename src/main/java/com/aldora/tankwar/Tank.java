package com.aldora.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank {
    public static final int TANK_SPEED = 5;

    public static final int MAX_HP = 100;

    private int x;

    private int y;

    private Direction direction;

    private Direction movingDirection;

    private boolean up = false;

    private boolean down = false;

    private boolean left = false;

    private boolean right = false;

    private int hp = MAX_HP;

    private boolean isAlive = true;

    private final boolean isEnemy;

    private Image tankImage;

    public boolean isAlive() {
        return isAlive;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

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

        if (!this.isEnemy) {
            graphics.setColor(Color.RED);
            graphics.fillRect(x, y - 10, this.getImage().getWidth(null), 10);

            graphics.setColor(Color.white);
            graphics.fillRect(x, y - 10,
                    ((MAX_HP - this.hp) * this.getImage().getWidth(null)) / MAX_HP, 10);
        }

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

        for (Wall wall : App.getInstance().getWalls()) {
            if (this.getRectangle().intersects(wall.getRectangle())) {
                this.x = oldX;
                this.y = oldY;
                break;
            }
        }

        for (Tank enemyTank : App.getInstance().getEnemyTanks()) {
            if (this != enemyTank && this.getRectangle().intersects(enemyTank.getRectangle())) {
                this.x = oldX;
                this.y = oldY;
                break;
            }
        }

        if (this.isEnemy &&
                this.getRectangle().intersects(App.getInstance().getPlayerTank().getRectangle())) {
            this.x = oldX;
            this.y = oldY;
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
            case KeyEvent.VK_CONTROL:
                this.fire();
                break;
            case KeyEvent.VK_A:
                this.superfire();
                break;
            case KeyEvent.VK_F2:
                App.getInstance().restart();
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

    int getHp() {
        return hp;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    protected Image getImage() {
        String prefix = this.isEnemy ? "e" : "";

        this.tankImage = this.direction.getImage(prefix + "tank");

        return this.tankImage;
    }

    protected void determineMovingDirection() {
        if (this.isEnemy) {
            return;
        }

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
                this.direction = this.movingDirection = Direction.LEFT_UP;
            } else if (horizontal == Direction.LEFT && vertical == Direction.DOWN) {
                this.direction = this.movingDirection = Direction.LEFT_DOWN;
            } else if (horizontal == Direction.RIGHT && vertical == Direction.UP) {
                this.direction = this.movingDirection = Direction.RIGHT_UP;
            } else if (horizontal == Direction.RIGHT && vertical == Direction.DOWN) {
                this.direction = this.movingDirection = Direction.RIGHT_DOWN;
            }
        } else if (horizontal == null && vertical == null) {
            this.movingDirection = null;
        }
    }

    protected void determineMovingPosition() {
        if (this.movingDirection == null) {
            return;
        }

        this.x += this.direction.xFactor * Tank.TANK_SPEED;
        this.y += this.direction.yFactor * Tank.TANK_SPEED;
    }

    protected void fire() {
        App.getInstance().getMissiles().add(
                new Missle(this.x + this.tankImage.getWidth(null) / 2 - 6,
                        this.y + this.tankImage.getHeight(null) / 2 - 6,
                        this.direction, this.isEnemy)
        );

//        Tools.playSound("shoot.wav");
    }

    protected void superfire() {
        for (Direction direction : Direction.values()) {
            App.getInstance().getMissiles().add(
                    new Missle(this.x + this.tankImage.getWidth(null) / 2 - 6,
                            this.y + this.tankImage.getHeight(null) / 2 - 6,
                            direction, this.isEnemy)
            );
        }

        String filename = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";
        Tools.playSound(filename);
    }

    private final Random random = new Random();
    private int step = random.nextInt(12) + 3;

    void actRandomly() {
        Direction[] directions = Direction.values();

        if (step == 0) {
            step = random.nextInt(12) + 3;

            this.direction = this.movingDirection = directions[random.nextInt(directions.length)];

            if (random.nextBoolean()) {
                this.fire();
            }
        }

        step--;
    }
}
