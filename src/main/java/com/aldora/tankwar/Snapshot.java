package com.aldora.tankwar;

import javafx.geometry.Pos;

import java.util.List;

public class Snapshot {
    private boolean isGameOnGoing;

    private Position playerTankPosition;

    private List<Position> enemyTanksPosition;

    public static class Position {
        private int x, y;
        private Direction direction;

        public Position(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public Direction getDirection() {
            return  this.direction;
        }
    }

    public Snapshot(boolean isGameOnGoing, Snapshot.Position playerTankPosition, List<Position> enemyTanksPosition) {
        this.isGameOnGoing = isGameOnGoing;
        this.playerTankPosition = playerTankPosition;
        this.enemyTanksPosition = enemyTanksPosition;
    }

    public boolean getIsGameOnGoing() {
        return isGameOnGoing;
    }

    public List<Position> getEnemyTanksPosition() {
        return enemyTanksPosition;
    }

    public Position getPlayerTankPosition() {
        return this.playerTankPosition;
    }
}
