package Model;

import java.util.EventObject;

public class UpdateEvent extends EventObject {
    int deadTiles;

    public UpdateEvent(Object source, int deadTiles){
        super(source);
        this.deadTiles = deadTiles;
    }

    public int getDeadTiles() {
        return deadTiles;
    }
}
