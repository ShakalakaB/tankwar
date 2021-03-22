package com.aldora.tankwar;

import java.awt.*;

public enum Direction {
    UP("U", 0, -1),
    DOWN("D", 0, 1),
    LEFT("L", -1, 0),
    RIGHT("R", 1, 0),
    LEFT_UP("LU", -1, -1),
    RIGHT_UP("RU", 1, -1),
    LEFT_DOWN("LD", -1, 1),
    RIGHT_DOWN("RD", 1, 1);

    private final String acronym;
    final int xFactor;
    final int yFactor;

    Direction(String acronym, int xFactor, int yFactor) {
        this.acronym = acronym;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    Image getImage(String prefix) {
        return Tools.getImage(prefix + this.acronym + ".gif");
    }
}
