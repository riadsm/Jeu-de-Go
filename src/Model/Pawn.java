package Model;

import java.awt.*;

public class Pawn {
    private int coordX;
    private int coordY;
    private PawnColor pawnColor;

    public Pawn(int coordX, int coordY, PawnColor pawnColor) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.pawnColor = pawnColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return coordX == pawn.getCoordX() &&
                coordY == pawn.getCoordY();
    }

    @Override
    public String toString() {
        return "Model.Pawn{" +
                "x=" + coordX +
                ", y=" + coordY + " color " + pawnColor +
                '}';
    }
    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }
}
