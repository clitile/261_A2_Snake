package Game;

import Element.PlayerNum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame{
    BeginPanel bp;
    public GameFrame() throws HeadlessException, InterruptedException {
        PlayerNum num;
        this.setTitle("Snake");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 420);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        bp = new BeginPanel();

        this.add(bp);
        this.setVisible(true);

        do {
            Thread.sleep(5);
            num = bp.getPlayNum();
        } while (num == null);
        this.setVisible(false);

        while (true) {
            this.remove(bp);
            this.setSize(900, 720);
            GamePanel gp = new GamePanel(num);
            this.add(gp);
            this.setLocationRelativeTo(null);
            this.setVisible(true);

            while (true) {
                if (gp.change) {
                    this.setVisible(false);
                    this.remove(gp);
                    this.setSize(300, 420);
                    this.setLocationRelativeTo(null);
                    this.setResizable(false);

                    bp = new BeginPanel();
                    this.add(bp);
                    this.setVisible(true);

                    do {
                        Thread.sleep(5);
                        num = bp.getPlayNum();
                    } while (num == null);
                    this.setVisible(false);
                    break;
                }
            }

        }

    }

    public static void main(String[] args) throws InterruptedException {
        new GameFrame();
    }
}
