package Util;

import Model.Pawn;

public class PawnDescriptor {
    private PawnDescriptor(){}

    public static String getDescription(Pawn pawn){
        char letter = (char) (pawn.getCoordX() + 65);
        if(letter == 'I'){
            letter = 'J';
        }
        return "" + letter + (9 - pawn.getCoordY());
    }
}
