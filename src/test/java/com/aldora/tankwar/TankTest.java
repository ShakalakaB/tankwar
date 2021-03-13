package com.aldora.tankwar;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    @org.junit.jupiter.api.Test
    void getImage() {
        for (Direction direction : Direction.values()) {
            Tank tank = new Tank(0, 0, direction, false);

            assertTrue(tank.getImage().getWidth(null) > 0, direction + " failed");

            Tank enemyTank = new Tank(0, 0, direction, true);

            assertTrue(enemyTank.getImage().getWidth(null) > 0);
        }
    }
}