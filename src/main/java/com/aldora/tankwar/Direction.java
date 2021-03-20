package com.aldora.tankwar;

import java.awt.*;

public enum Direction {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R"),
    LEFT_UP("LU"),
    RIGHT_UP("RU"),
    LEFT_DOWN("LD"),
    RIGHT_DOWN("RD");

    private final String acronym;

    Direction(String acronym) {
        this.acronym = acronym;
    }

    Image getImage(String prefix) {
        return Tools.getImage(prefix + this.acronym + ".gif");
    }
}
