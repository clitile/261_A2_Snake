package Element;

import javax.swing.*;
import java.awt.*;

public class GameElem {
    private int X;
    private int Y;

    private final Image image;

    public GameElem(String path) {
        this.image = new ImageIcon(path).getImage();
    }

    public GameElem(int x, int y, String path) {
        X = x;
        Y = y;
        this.image = new ImageIcon(path).getImage();
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setLocation(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getWidth() {
        return 10;
    }

    public int getHeight() {
        return 10;
    }


    public Image getImage() {
        return image;
    }
}
