package View;

import java.util.EventObject;

public class ClickEvent extends EventObject{
    int x;
    int y;
    public ClickEvent(Object source, int x, int y){
        super(source);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
