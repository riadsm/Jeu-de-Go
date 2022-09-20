package Util;

public enum OrthogonalDirections implements Directionnal {
    TOP(0,-1),
    TOP_RIGHT(1,-1),
    RIGHT(1,0 ),
    BOTTOM_RIGHT(1,1),

    BOTTOM(0,1),
    BOTTOM_LEFT(-1,1),
    LEFT(-1,0),
    TOP_LEFT(-1,-1);


    private int x, y;
    OrthogonalDirections(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static OrthogonalDirections get(int index){
        int len = OrthogonalDirections.values().length;
      index = (index%len + len)%len;
      return OrthogonalDirections.values()[index];
    }

    public static int findClosest(int x, int y){
        for(int i=0; i<= OrthogonalDirections.values().length;i++){
            if(get(i).x == x && get(i).y == y){
                return i;
            }
        }
        return -1;
    }
}
