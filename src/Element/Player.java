package Element;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private Snake snake;
    private Direction direction;
    private int score;

    public Player(String headPath, String bodyPath) {
        snake = new Snake(headPath, bodyPath);
        score = 0;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SnakeHead getSnakeHead() {
        return snake.getHead();
    }

    public void setSnake(SnakeHead head, ArrayList<SnakeBody> bodies) {
        this.snake.setHead(head);
        this.snake.setBody(bodies);
    }

    public void setSnakeHead(SnakeHead head) {
        this.snake.setHead(head);
    }

    public void setSnakeBody(ArrayList<SnakeBody> bodies) {
        this.snake.setBody(bodies);
    }

    public int getSnakeHeadX() {
        return snake.getHead().getX();
    }

    public int getSnakeHeadY() {
        return snake.getHead().getY();
    }

    public Image getSnakeHeadImage() {
        return snake.getHead().getImage();
    }

    public int getSnakeHeadWeight() {
        return snake.getHead().getWidth();
    }

    public int getSnakeHeadHeight() {
        return snake.getHead().getHeight();
    }

    public ArrayList<SnakeBody> getSnakeBody() {
        return snake.getBody();
    }

    public void setSnakeHeadX(int X) {
        snake.getHead().setX(X);
    }

    public void setSnakeHeadY(int Y) {
        snake.getHead().setY(Y);
    }

    public int getLength() {
        return 1 + getSnakeBody().size();
    }
}
