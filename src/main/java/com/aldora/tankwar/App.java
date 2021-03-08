package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class App extends JComponent {
    private Tank playerTank;

    private ArrayList<Tank> enemyTanks = new ArrayList<>();

    public App() {
        this.playerTank = new Tank(400, 100, Direction.DOWN, false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.enemyTanks.add(new Tank(
                        200 + j * 60,
                        400 + i * 40,
                        Direction.UP,
                        true));
            }
        }

        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.playerTank.paint(g);

        for (Tank enemyTank: this.enemyTanks) {
            enemyTank.paint(g);
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
