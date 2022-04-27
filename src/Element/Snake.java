package Element;

import java.util.ArrayList;

public class Snake {
    private SnakeHead head;
    private ArrayList<SnakeBody> body;

    public Snake(String headPath, String bodyPath) {
        head = new SnakeHead(headPath);
        body = new ArrayList<>() {{
            add(new SnakeBody(bodyPath));
            add(new SnakeBody(bodyPath));
        }};
    }

    public SnakeHead getHead() {
        return head;
    }

    public void setHead(SnakeHead head) {
        this.head = head;
    }

    public ArrayList<SnakeBody> getBody() {
        return body;
    }

    public void setBody(ArrayList<SnakeBody> body) {
        this.body = body;
    }
}
