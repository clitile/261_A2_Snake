package Game;

import Element.PlayerNum;

import javax.swing.*;
import java.awt.*;

public class BeginPanel extends JPanel {
    private PlayerNum playNum;

    public BeginPanel() {
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.setFont(new Font("San-Serif",Font.BOLD,40));
        g.drawString("Element.Snake", 90, 40);

        JButton single_mode = new JButton("Single Mode");
        single_mode.setLocation(75, 70);
        single_mode.setSize(150, 50);
        single_mode.addActionListener(e -> playNum = PlayerNum.One);
        this.add(single_mode);

        JButton double_mode = new JButton("Double Mode");
        double_mode.addActionListener(e -> playNum = PlayerNum.Two);
        double_mode.setLocation(75, 150);
        double_mode.setSize(150, 50);
        this.add(double_mode);

        JButton help = new JButton("Help");
        help.setLocation(75, 230);
        help.setSize(150, 50);
        help.addActionListener(e -> {
            String instructions =
                    "In single mode, You can use \u2191 for the upward, \u2190 for the left, \u2193 for the down, \u2192 for the right\n" +
                            "In double mode, the second player can use \u0057 for the upward, \u0041 for the left, \u0053 for the down,  \u0044 for the right";
            JOptionPane.showMessageDialog(null, instructions, "Instructions", JOptionPane.PLAIN_MESSAGE);
        });
        this.add(help);
    }

    public PlayerNum getPlayNum() {
        return playNum;
    }
}
