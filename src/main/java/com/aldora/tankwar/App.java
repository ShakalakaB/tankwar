package com.aldora.tankwar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App extends JComponent {
    private Tank playerTank;

    private List<Tank> enemyTanks;

    private final List<Wall> walls;

    private List<Missle> missiles;

    private List<Explosion> explosions;

    private Blood blood;

    private static App instance;

    private Image cactusImage;

    private AtomicInteger killedTanks = new AtomicInteger(0);

    private Random random = new Random();

    private App() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        this.playerTank = new Tank(400, 100, Direction.DOWN, false);

        this.cactusImage = Tools.getImage("tree.png");

        this.initEnemyTanks();

        this.walls = Arrays.asList(
                new Wall(190, 140, 12, true),
                new Wall(190, 540, 12, true),
                new Wall(70, 120, 12, false),
                new Wall(670, 120, 12, false)
        );

//        this.missles = new ArrayList<>();
        this.missiles = new CopyOnWriteArrayList<>();

        this.explosions = new ArrayList<>();

        this.blood = new Blood(350, 250);

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

    List<Missle> getMissiles() {
        return this.missiles;
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
        g.drawImage(this.cactusImage, 10, 520, null);
        g.drawImage(this.cactusImage, 720, 10, null);

        this.playerTank.paint(g);

        if (this.playerTank.getHp() <= Tank.MAX_HP * 0.2 &&
                this.random.nextInt(3) == 1) {
            this.blood.setIsAlive(true);
        }

        if (this.blood.isAlive()) {
            this.blood.paint(g);
        }

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

        this.missiles.removeIf(n -> !n.isAlive());
        for (Missle missle : this.missiles) {
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
        this.blood = new Blood(350, 250);
    }

    void stash() throws IOException {
        String filePath = "snapshot";
        this.stash(filePath);
//        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
//        writer.println(JSON.toJSONString(App.getInstance().playerTank, true));
//        writer.close();
    }

    void stash(String filePath) throws IOException {
        Snapshot snapshot = new Snapshot(
                App.getInstance().playerTank.isAlive(),
                App.getInstance().playerTank.getPosition(),
                App.getInstance().enemyTanks.stream().
                        filter(Tank::isAlive).
                        map(Tank::getPosition).
                        collect(Collectors.toList())
        );

        FileUtils.write(
                new File(filePath),
                JSON.toJSONString(snapshot, true),
                StandardCharsets.UTF_8
        );
    }

    public static void main(String[] args) {
//        com.sun.javafx.application.PlatformImpl.startup(()->{});

        JFrame frame = new JFrame("tank war");
        frame.setTitle("tank war");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final App app = App.getInstance();
        frame.add(app);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    app.stash();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null,
                            "Failed to save current status",
                            "Oops! Errors Occurred!", JOptionPane.ERROR_MESSAGE);
                    System.exit(4);
                }
                System.exit(0);
            }
        });

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
