package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends JComponent {
    private Tank playerTank;

    private ArrayList<Tank> enemyTanks = new ArrayList<>();

    private List<Wall> walls;

    public App() {
        this.playerTank = new Tank(400, 100, Direction.DOWN, false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.enemyTanks.add(new Tank(
                        280 + j * 120,
                        400 + i * 40,
                        Direction.UP,
                        true));
            }
        }

        this.walls = Arrays.asList(
                new Wall(200, 140, 15, true),
                new Wall(200, 540, 15, true),
                new Wall(100, 80, 15, false),
                new Wall(700, 80, 15, false)
        );
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        this.playerTank.paint(g);

        for (Tank enemyTank: this.enemyTanks) {
            enemyTank.paint(g);
        }

        for (Wall wall: this.walls) {
            wall.paint(g);
        }

        super.paintComponent(g);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("tank war");
        frame.setTitle("tank war");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final App app = new App();
        frame.add(app);
        frame.pack();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                app.playerTank.keyPressed(e);
                app.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                app.playerTank.keyReleased(e);
                app.repaint();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
