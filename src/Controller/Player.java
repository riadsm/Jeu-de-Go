package Controller;

import Model.PawnColor;

import java.awt.*;
import java.util.stream.Stream;

public class Player {
    private PawnColor pawnColor;
    private int points;

    public Player(PawnColor color) {
        this.pawnColor = color;
        this.points = 0;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public Color getColor(){
        return pawnColor.getColor();
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
