package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App extends JComponent {
    private Tank playerTank;

    public App() {
        this.playerTank = new Tank(400, 100, Direction.DOWN);
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.playerTank.paint(g);
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
