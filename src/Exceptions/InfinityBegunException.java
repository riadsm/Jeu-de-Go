package Exceptions;

public class InfinityBegunException extends Exception {
    public InfinityBegunException(){
        super("An infinity move has begun, the deleted pawn will not be available");
    }
}
