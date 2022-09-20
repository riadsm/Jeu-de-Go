package Util;

public enum Direction implements Directionnal {
   UP(0,-1),
   DOWN(0,1),
   LEFT(-1,0),
   RIGHT(1,0);

   private final int x, y;
   Direction(int x, int y) {
       this.x=x;
       this.y=y;
   }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
