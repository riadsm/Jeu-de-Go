package Util;

public enum DirectionClockWise implements Directionnal {
    UP(0,-1),
    RIGHT(1,0),
    DOWN(0,1),
    LEFT(-1,0);


    private final int x, y;
    DirectionClockWise(int x, int y) {
        this.x=x;
        this.y=y;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
