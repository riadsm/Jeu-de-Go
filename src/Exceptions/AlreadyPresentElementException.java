package Exceptions;

public class AlreadyPresentElementException extends Exception{
    public AlreadyPresentElementException(){
        super("This element cannot be added to the list, it already exists");
    }
}

