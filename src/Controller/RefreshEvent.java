package Controller;

import Model.PawnGroup;

import java.awt.*;
import java.util.EventObject;

public class RefreshEvent extends EventObject {
    int whitePlayerPoints;
    int blackPlayerPoints;
    Color playingColor;

    public RefreshEvent(Object source){
        super(source);
    }

    public RefreshEvent(Object source,int whitePlayerPoints, int blackPlayerPoints, Color playingColor) {
        super(source);
        this.whitePlayerPoints = whitePlayerPoints;
        this.blackPlayerPoints = blackPlayerPoints;
        this.playingColor = playingColor;
    }

    public int getWhitePlayerPoints() {
        return whitePlayerPoints;
    }

    public int getBlackPlayerPoints() {
        return blackPlayerPoints;
    }

    public Color getPlayingColor() {
        return playingColor;
    }
}
