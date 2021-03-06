package com.aldora.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools {

    public static Image getImage(String filename) {
        return new ImageIcon("assets/images/" + filename).getImage();
    }

    public static void playSound(String filename) {
        Media media = new Media(new File("assets/audios/" + filename).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
