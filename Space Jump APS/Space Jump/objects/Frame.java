package objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import util.Resource;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Frame extends JFrame {

    private static final Dimension SIZE;

    static {
        int screenHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) - 100;
        if (screenHeight > 800) {
            screenHeight = 800;
        }
        SIZE = new Dimension(500, screenHeight);
    }
    private GamePanel gamePanel;
    private Clip clip;

    public Frame() {
        super();
        this.setIconImage(Resource.getImage("astronaut.png"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setSizeWithoutInsets(SIZE);
        this.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error " + ex);
        }

        this.setTitle(0);
        this.gamePanel = new GamePanel(SIZE);
        this.gamePanel.setNewGameListener(new NewGameListener() {

            @Override
            public void updateScore(int score) {
                setTitle(score);
            }

            @Override
            public void gameOver() {
                setTitle(0);
            }
        });
        this.add(this.gamePanel);
        this.gamePanel.requestFocus();

       
        try {
            URL url = getClass().getResource("/sounds/gameplay.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        JOptionPane.showOptionDialog(null, "Preparado...?",
                "Iniciar", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"VAI!"}, 0);
        this.gamePanel.start();
    }

    public static void main(String[] args) {
        Frame f = new Frame();
        f.setVisible(true);
        f.start();
    }

    private void setTitle(int points) {
        this.setTitle("Space Jump - " + points + " Pontos");
    }

    
    private void setSizeWithoutInsets(Dimension d) {
        Insets i = this.getInsets();
        this.setSize(d.width + i.left + i.right, d.height + i.top + i.bottom);
    }
}
