package Model;

import java.awt.*;

public enum PawnColor {
    WHITE(Color.WHITE),
    BLACK(Color.BLACK),
    UNDEFINED(Color.BLUE);

    Color color;
    PawnColor(Color c) {
        this.color =c;
    }

    public Color getColor() {
        return color;
    }
}
