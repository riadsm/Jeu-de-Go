package Exceptions;

public class InfinityException extends Exception{
    public InfinityException(){
        super("This movement is part of an infinite set.");
    }
}
