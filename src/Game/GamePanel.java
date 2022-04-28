package Game;

import Element.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel  implements KeyListener, ActionListener {
    private final String HEAD_PATH = "image/head.png";
    private final String BODY_PATH = "image/dot.png";
    private final String FOOD_PATH = "image/apple.png";

    private final String DRUG_PATH = "image/drug.png";
    private final PlayerNum mode;

    Random r = new Random();

    private Player p1;
    private Player p2;
    private Food food1;
    private Food food2;

    private Food drug;

    Timer timer = new Timer(100, this);
    boolean isStart;
    boolean isFail;

    public GamePanel(PlayerNum playerNum) {
        this.mode = playerNum;
        initialize();
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }

    private void initialize() {
        isStart = false;
        isFail = false;
        p1 = new Player(HEAD_PATH, BODY_PATH);
        p1.setSnakeHeadX(100);
        p1.setSnakeHeadY(100);

        ArrayList<SnakeBody> bodies = p1.getSnakeBody();
        bodies.get(0).setLocation(90, 100);
        bodies.get(1).setLocation(80, 100);
        p1.setDirection(Direction.RIGHT);

        if (mode == PlayerNum.One) {
            p1.setScore(0);
            int[] pos = setFoodPos(p1);
            food1 = new Food(FOOD_PATH);
            food1.setLocation(pos[0], pos[1]);

            pos = setDrugPos(p1, food1);
            drug = new Food(DRUG_PATH);
            drug.setLocation(pos[0], pos[1]);
        } else {
            p2 = new Player(BODY_PATH, HEAD_PATH);
            p2.setSnakeHeadX(600);
            p2.setSnakeHeadY(110);
            p2.setScore(0);

            bodies = p2.getSnakeBody();
            bodies.get(0).setLocation(600, 120);
            bodies.get(1).setLocation(600, 130);
            p2.setDirection(Direction.UP);
            food2 = new Food(FOOD_PATH);
            food1 = new Food(FOOD_PATH);
            int[] pos = setFoodPos();
            food1.setLocation(pos[0], pos[1]);
            food2.setLocation(pos[2], pos[3]);

            int[] p = setDrugPos();
            drug = new Food(DRUG_PATH);
            drug.setLocation(p[0], p[1]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.setFont(new Font("San-Serif",Font.BOLD,60));
        g.drawString("Snake",100,60);
        g.setColor(Color.black);
        g.fillRect(20,70,850,600);

        drawSnake(g);
        drawFood(g);
        drawDrug(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("San-Serif", Font.BOLD, 20));
        g.drawString("length of user1: " + p1.getLength(), 700, 30);
        g.drawString("score of user1: " + p1.getScore(), 700, 60);
        if (mode == PlayerNum.Two) {
            g.drawString("length of user2: " + p2.getLength(), 400, 30);
            g.drawString("score of user2: " + p2.getScore(), 400, 60);
        }

        if (!isStart) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("San-Serif",Font.BOLD,30));
            g.drawString("Press the space bar to start or parse the game",100,300);
            g.drawString("Press the Q to Quit.", 100, 400);
        }

        if (isFail) {
            g.setColor(Color.RED);
            g.setFont(new Font("San-Serif",Font.BOLD,30));
            g.drawString("Game Over，Press the space bar to start the game",80,300);
        }
    }

    private int[] setDrugPos(Player player, Food food) {
        int[] pos;
        boolean f = false;
        while (true) {
            pos = setFoodPos(player);
            if (pos[0] == food.getX() && pos[1] == food.getY()) {
                pos = setFoodPos(player);
                f = true;
            }
            if (!f) {
                break;
            }
            f = false;
        }
        return pos;
    }

    private int[] setDrugPos() {
        int[] pos;
        boolean f = false;
        while (true) {
            pos = setFoodPos(food1, p1, p2);
            System.out.println(food1.getX());
            System.out.println(Arrays.toString(pos));
            if (pos[0] == food2.getX() && pos[1] == food2.getY()) {
                pos = setFoodPos(food2, p1, p2);
                f = true;
            }
            if (!f) {
                break;
            }
            f = false;
        }
        System.out.println(Arrays.toString(pos));
        return pos;
    }

    private int[] setFoodPos(Player player) {
        int x = 20 + 10 * r.nextInt(85);
        int y = 70 + 10 * r.nextInt(60);
        int[] pos = new int[2];
        boolean f = false;
        while (true) {
            for (int i = 0; i < player.getLength(); i++) {
                if (i == 0) {
                    if (player.getSnakeHeadX() == x && player.getSnakeHeadY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                        f = true;
                        break;
                    }
                } else {
                    if(player.getSnakeBody().get(i - 1).getX() == x && player.getSnakeBody().get(i - 1).getY() == y){
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                        f = true;
                        break;
                    }
                }
            }
            if (!f) {
                break;
            }
            f = false;
        }

        pos[0] = x;
        pos[1] = y;
        return pos;
    }

    private int[] setFoodPos(Food food, Player player1, Player player2) {
        int x = 20 + 10 * r.nextInt(85);
        int y = 70 + 10 * r.nextInt(60);
        int[] pos = new int[2];
        boolean f = false;
        while (true) {
            for (int i = 0; i < Math.max(player1.getLength(), player2.getLength()); i++) {
                int a = i < player1.getLength() ? i : player1.getLength() - 1;
                int b = i < player2.getLength() ? i : player2.getLength() - 1;
                if (i == 0) {
                    if (player1.getSnakeHeadX() == x && player1.getSnakeHeadY() == y || player2.getSnakeHeadX() == x && player2.getSnakeHeadY() == y || food.getX() == x && food.getY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                        f = true;
                        break;
                    }
                } else {
                    if(player1.getSnakeBody().get(a - 1).getX() == x && player1.getSnakeBody().get(a - 1).getY() == y || player2.getSnakeBody().get(b - 1).getX() == x && player2.getSnakeBody().get(b - 1).getY() == y || food.getX() == x && food.getY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                        f = true;
                        break;
                    }
                }
            }
            if (!f) {
                break;
            }
            f = false;
        }

        pos[0] = x;
        pos[1] = y;
        return pos;
    }

    private int[] setFoodPos() {
        int x = 20 + 10 * r.nextInt(85);
        int y = 70 + 10 * r.nextInt(60);
        int[] pos = new int[4];

        if (mode == PlayerNum.One) {
            for (int i = 0; i < p1.getLength(); i++) {
                if (i == 0) {
                    if (p1.getSnakeHeadX() == x && p1.getSnakeHeadY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                } else {
                    if(p1.getSnakeBody().get(i - 1).getX() == x && p1.getSnakeBody().get(i - 1).getY() == y){
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                }
            }
            pos[0] = x;
            pos[1] = y;
        } else {
            for (int i = 0; i < Math.max(p1.getLength(), p2.getLength()); i++) {
                int a = i < p1.getLength() ? i : p1.getLength() - 1;
                int b = i < p2.getLength() ? i : p2.getLength() - 1;
                if (i == 0) {
                    if (p1.getSnakeHeadX() == x && p1.getSnakeHeadY() == y || p2.getSnakeHeadX() == x && p2.getSnakeHeadY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                } else {
                    if(p1.getSnakeBody().get(a - 1).getX() == x && p1.getSnakeBody().get(a - 1).getY() == y || p2.getSnakeBody().get(b - 1).getX() == x && p2.getSnakeBody().get(b - 1).getY() == y){
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                }
            }
            pos[0] = x;
            pos[1] = y;
            for (int i = 0; i < Math.max(p1.getLength(), p2.getLength()); i++) {
                int a = i < p1.getLength() ? i : p1.getLength() - 1;
                int b = i < p2.getLength() ? i : p2.getLength() - 1;
                if (i == 0) {
                    if (p1.getSnakeHeadX() == x && p1.getSnakeHeadY() == y || p2.getSnakeHeadX() == x && p2.getSnakeHeadY() == y) {
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                } else {
                    if(pos[0] == x && pos[1] == y || p1.getSnakeBody().get(a - 1).getX() == x && p1.getSnakeBody().get(a - 1).getY() == y || p2.getSnakeBody().get(b - 1).getX() == x && p2.getSnakeBody().get(b - 1).getY() == y){
                        x = 20 + 10 * r.nextInt(85);
                        y = 70 + 10 * r.nextInt(60);
                    }
                }
            }
            pos[2] = x;
            pos[3] = y;
        }
        return pos;
    }

    private void drawDrug(Graphics g) {
        g.drawImage(
                drug.getImage(),
                drug.getX(),
                drug.getY(),
                drug.getWidth(),
                drug.getHeight(),
                this
        );
    }

    private void drawFood(Graphics g) {
        g.drawImage(
                food1.getImage(),
                food1.getX(),
                food1.getY(),
                food1.getWidth(),
                food1.getHeight(),
                this
        );
        if (mode == PlayerNum.Two) {
            g.drawImage(
                    food2.getImage(),
                    food2.getX(),
                    food2.getY(),
                    food2.getWidth(),
                    food2.getHeight(),
                    this
            );
        }
    }

    private void drawSnake(Graphics g) {
        drawHead(g, p1);
        drawBody(g, p1.getSnakeBody());

        if (mode == PlayerNum.Two) {
            drawHead(g, p2);
            drawBody(g, p2.getSnakeBody());
        }
    }

    private void drawHead(Graphics g, Player player) {
        g.drawImage(
                player.getSnakeHeadImage(),
                player.getSnakeHeadX(),
                player.getSnakeHeadY(),
                player.getSnakeHeadWeight(),
                player.getSnakeHeadHeight(),
                this
        );
    }

    private void drawBody(Graphics g, ArrayList<SnakeBody> bodies) {
        for (SnakeBody body : bodies) {
            g.drawImage(
                    body.getImage(),
                    body.getX(),
                    body.getY(),
                    body.getWidth(),
                    body.getHeight(),
                    this
            );
        }
    }

    private SnakeBody eatFood(Player player, String path) {
        SnakeBody body = new SnakeBody(path);
        if (player.getDirection() == Direction.RIGHT) {
            body.setLocation(player.getSnakeBody().get(player.getSnakeBody().size() - 1).getX() - 10, player.getSnakeBody().get(player.getSnakeBody().size() - 1).getY());
        } else if (player.getDirection() == Direction.LEFT) {
            body.setLocation(player.getSnakeBody().get(player.getSnakeBody().size() - 1).getX() + 10, player.getSnakeBody().get(player.getSnakeBody().size() - 1).getY());
        } else if (player.getDirection() == Direction.UP) {
            body.setLocation(player.getSnakeBody().get(player.getSnakeBody().size() - 1).getX(), player.getSnakeBody().get(player.getSnakeBody().size() - 1).getY() + 10);
        } else {
            body.setLocation(player.getSnakeBody().get(player.getSnakeBody().size() - 1).getX(), player.getSnakeBody().get(player.getSnakeBody().size() - 1).getY() - 10);
        }
        return body;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && !isFail) {
            ArrayList<SnakeBody> bodies = moveBody(p1);
            SnakeHead head = moveHead(p1);
            p1.setSnake(head, bodies);

            if(haveEatenFood(p1, food1)){
                p1.setScore(p1.getScore() + 10);
                int[] pos = setFoodPos(p1);
                food1.setLocation(pos[0], pos[1]);
                p1.getSnakeBody().add(eatFood(p1, BODY_PATH));
            }

            if (haveEatenDrug(p1)) {
                if (p1.getScore() <= 0) {
                    isFail = true;
                } else {
                    p1.setScore(p1.getScore() - 10);
                    int[] pos = setDrugPos(p1, food1);
                    drug.setLocation(pos[0], pos[1]);
                }
            }

            for (int i = 0; i < p1.getSnakeBody().size(); i++) {
                if (p1.getSnakeHeadX() == p1.getSnakeBody().get(i).getX() && p1.getSnakeHeadY() == p1.getSnakeBody().get(i).getY()) {
                    isFail = true;
                    break;
                }
            }

            if (mode == PlayerNum.Two) {
                bodies = moveBody(p2);
                head = moveHead(p2);
                p2.setSnake(head, bodies);

                if (haveEatenFood(p2, food1)) {
                    p2.setScore(p2.getScore() + 10);
                    int[] pos = setFoodPos(food1, p1, p2);
                    food1.setLocation(pos[0], pos[1]);
                    p2.getSnakeBody().add(eatFood(p2, HEAD_PATH));
                }

                if (haveEatenFood(p2, food2)) {
                    p2.setScore(p2.getScore() + 10);
                    int[] pos = setFoodPos(food1, p1, p2);
                    food1.setLocation(pos[0], pos[1]);
                    p2.getSnakeBody().add(eatFood(p2, HEAD_PATH));
                }

                if (haveEatenFood(p1, food2)) {
                    p1.setScore(p1.getScore() + 10);
                    int[] pos = setFoodPos(food1, p1, p2);
                    food2.setLocation(pos[0], pos[1]);
                    p1.getSnakeBody().add(eatFood(p1, BODY_PATH));
                }

                if (haveEatenDrug(p2)) {
                    if (p2.getScore() <= 0) {
                        isFail = true;
                    } else {
                        p2.setScore(p2.getScore() - 10);
                        int[] pos = setDrugPos();
                        drug.setLocation(pos[0], pos[1]);
                    }
                }

                for (int i = 0; i < p2.getSnakeBody().size(); i++) {
                    if (p2.getSnakeHeadX() == p2.getSnakeBody().get(i).getX() && p2.getSnakeHeadY() == p2.getSnakeBody().get(i).getY()) {
                        isFail = true;
                        break;
                    }
                }
            }
            repaint();
        }
        timer.start();
    }

    private boolean haveEatenFood(Player player, Food food) {
        return player.getSnakeHeadX() == food.getX() && player.getSnakeHeadY() == food.getY();
    }

    private boolean haveEatenDrug(Player player) {
        return player.getSnakeHeadX() == drug.getX() && player.getSnakeHeadY() == drug.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private Direction CheckDirection(Direction direction, int keyCode, int[] D) {
        if (keyCode == D[0] && direction != Direction.RIGHT) {
            return Direction.LEFT;
        }
        else if (keyCode == D[1] && direction != Direction.LEFT) {
            return Direction.RIGHT;
        }
        else if (keyCode == D[2] && direction != Direction.DOWN) {
            return Direction.UP;
        }
        else if (keyCode == D[3] && direction != Direction.UP) {
            return Direction.DOWN;
        }
        return direction;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int[] u1D = new int[] {
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN
        };
        int[] u2D = new int[] {
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_W,
                KeyEvent.VK_S
        };
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFail) {
                isFail = false;
                initialize();
            } else {
                isStart = !isStart;
            }
            repaint();
        } else if (keyCode == KeyEvent.VK_Q) {
            System.exit(0);
        }

        p1.setDirection(CheckDirection(p1.getDirection(), keyCode, u1D));
        if (mode == PlayerNum.Two) {
            p2.setDirection(CheckDirection(p2.getDirection(), keyCode, u2D));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private SnakeHead moveHead(Player player) {
        SnakeHead head = player.getSnakeHead();
        switch (player.getDirection()) {
            case RIGHT -> {
                head.setX(head.getX() + 10);
                if (head.getX() > 860) {
                    isFail = true;
                }
            }
            case LEFT -> {
                head.setX(head.getX() - 10);
                if (head.getX() < 20) {
                    isFail = true;
                }
            }
            case UP -> {
                head.setY(head.getY() - 10);
                if (head.getY() < 70) {
                    isFail = true;
                }
            }
            case DOWN -> {
                head.setY(head.getY() + 10);
                if (head.getY() > 660) {
                    isFail = true;
                }
            }
        }
        return head;
    }

    private ArrayList<SnakeBody> moveBody(Player player) {
        ArrayList<SnakeBody> bodies = player.getSnakeBody();
        for (int i = bodies.size() -1; i > 0 ; i--) {
            bodies.get(i).setLocation(bodies.get(i - 1).getX(), bodies.get(i - 1).getY());
        }
        bodies.get(0).setLocation(player.getSnakeHead().getX(), player.getSnakeHead().getY());
        return bodies;
    }
}
