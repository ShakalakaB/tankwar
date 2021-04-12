package com.aldora.tankwar;

import java.awt.*;

public enum Direction {
    UP("U", 0, -1, 1),
    DOWN("D", 0, 1, 2),
    LEFT("L", -1, 0, 4),
    RIGHT("R", 1, 0, 8),
    LEFT_UP("LU", -1, -1, 5),
    RIGHT_UP("RU", 1, -1, 9),
    LEFT_DOWN("LD", -1, 1, 6),
    RIGHT_DOWN("RD", 1, 1, 10);

    private final String acronym;
    final int xFactor;
    final int yFactor;
    final int code;

    Direction(String acronym, int xFactor, int yFactor, int code) {
        this.acronym = acronym;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
        this.code = code;
    }

    static Direction getDirection(int code) {
        for (Direction direction : Direction.values()) {
            if (direction.code == code) {
                return direction;
            }
        }

        return null;
    }

    Image getImage(String prefix) {
        return Tools.getImage(prefix + this.acronym + ".gif");
    }
}
