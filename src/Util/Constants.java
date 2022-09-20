package Util;

public class Constants {
    public final static int GAME_WIDTH = 600;
    public final static int GAME_HEIGHT = 600;

    public final static int GRID_SIZE_X = 9;
    public final static int GRID_SIZE_Y = 9;

    public final static int GRID_WIDTH = GAME_WIDTH/GRID_SIZE_X;

    public final static int SIDE_WIDTH = (GAME_WIDTH/GRID_SIZE_X)/2;
    public final static int GRID_HEIGHT = GAME_HEIGHT/GRID_SIZE_Y;

    public final static int PAWN_DIAMETER = 40;

    public final static int SEKI_POINTS = 7;

    public final static String DEFAULT_IMPORT_PATH = "src/GameFile";
    public final static String DEFAULT_SAVE_PATH = "src/GameFile";


    public final static String MSG_SKIP_TURN = "Are you sure you want to skip your turn ? ";
    public final static String MSG_SURRENDER = "Are you sure you want to surrender and lose the game ?";
    public static final String MSG_RESTART = "Do you want to start a new game ?";
    public static final String EXC_NEUTRAL = "There are neutral points remaining, you need to remove them first.";
}
