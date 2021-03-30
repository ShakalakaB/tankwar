package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class App extends JComponent {
    private Tank playerTank;

    private List<Tank> enemyTanks;

    private final List<Wall> walls;

    private List<Missle> missles;

    private List<Explosion> explosions;

    private static App instance;

    private AtomicInteger killedTanks = new AtomicInteger(0);

    private App() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        this.playerTank = new Tank(400, 100, Direction.DOWN, false);

        this.initEnemyTanks();

        this.walls = Arrays.asList(
                new Wall(200, 140, 15, true),
                new Wall(200, 540, 15, true),
                new Wall(100, 80, 15, false),
                new Wall(700, 80, 15, false)
        );

//        this.missles = new ArrayList<>();
        this.missles = new CopyOnWriteArrayList<>();

        this.explosions = new ArrayList<>();

        this.setPreferredSize(new Dimension(800, 600));
    }

    private void initEnemyTanks() {
        this.enemyTanks = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.enemyTanks.add(new Tank(280 + j * 120, 400 + i * 40, Direction.UP, true));
            }
        }
    }

    static App getInstance() {
        if (App.instance == null) {
            App.instance = new App();
        }

        return App.instance;
    }

    List<Wall> getWalls() {
        return this.walls;
    }

    List<Tank> getEnemyTanks() {
        return this.enemyTanks;
    }

    List<Missle> getMissles() {
        return this.missles;
    }

    Tank getPlayerTank() {
        return playerTank;
    }

    List<Explosion> getExplosions() {
        return this.explosions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        if (!this.playerTank.isAlive()) {
            g.setColor(Color.red);
            g.setFont(new Font(null, Font.BOLD, 100));
            g.drawString("GAME OVER", 80, 300);
            g.setFont(new Font(null, Font.BOLD, 50));
            g.drawString("Press F2 to Restart", 150, 500);
            return;
        }

        g.setColor(Color.white);
        g.setFont(new Font(null, Font.BOLD, 16));
        g.drawString("killed tanks: " + this.killedTanks, 10, 20);

        this.playerTank.paint(g);

        int count = this.enemyTanks.size();
        this.enemyTanks.removeIf(n -> !n.isAlive());
        this.killedTanks.addAndGet(count - this.enemyTanks.size());

        if (this.enemyTanks.isEmpty()) {
            this.initEnemyTanks();
        }

        for (Tank enemyTank : this.enemyTanks) {
            enemyTank.paint(g);
        }

        for (Wall wall : this.walls) {
            wall.paint(g);
        }

        this.missles.removeIf(n -> !n.isAlive());
        for (Missle missle : this.missles) {
            missle.paint(g);
        }

        this.explosions.removeIf(n -> !n.isAlive());
        for (Explosion explosion : this.explosions) {
            explosion.paint(g);
        }

        super.paintComponent(g);
    }

    protected void restart() {
        this.killedTanks = new AtomicInteger(0);
        this.initEnemyTanks();
        this.playerTank = new Tank(400, 100, Direction.DOWN, false);
    }


    public static void main(String[] args) {
//        com.sun.javafx.application.PlatformImpl.startup(()->{});

        JFrame frame = new JFrame("tank war");
        frame.setTitle("tank war");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final App app = App.getInstance();
        frame.add(app);
        frame.pack();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                app.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                app.playerTank.keyReleased(e);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true) {
            app.repaint();

            if (!App.getInstance().playerTank.isAlive()) {
                continue;
            }

            for (Tank enemyTank : getInstance().getEnemyTanks()) {
                enemyTank.actRandomly();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
