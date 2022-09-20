package Exceptions;

public class SuicidalMoveException extends Exception{
    public SuicidalMoveException(){
        super("This element induce self death");
    }

}
