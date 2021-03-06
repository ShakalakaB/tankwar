package com.aldora.tankwar;

import javax.swing.*;
import java.awt.*;

public class App extends JComponent {
    public App() {
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon("assets/images/tankD.gif").getImage(),
                400, 100, null);
        super.paintComponent(g);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("tank war");
        frame.setTitle("tank war");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new App());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
