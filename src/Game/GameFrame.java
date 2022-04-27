package Game;

import Element.PlayerNum;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    public GameFrame() throws HeadlessException, InterruptedException {
        PlayerNum num;
        this.setTitle("Element.Snake");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        BeginPanel bp = new BeginPanel();

        this.add(bp);
        this.setVisible(true);

        do {
            Thread.sleep(5);
            num = bp.getPlayNum();
        } while (num == null);
        this.setVisible(false);

        System.out.println(num);

        this.remove(bp);
        this.setSize(900, 720);
        GamePanel gp = new GamePanel(num);
        this.add(gp);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException {
        new GameFrame();
    }
}
