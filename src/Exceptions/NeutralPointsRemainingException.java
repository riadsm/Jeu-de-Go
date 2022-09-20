package Exceptions;

import Util.Constants;

public class NeutralPointsRemainingException extends Exception{

    public NeutralPointsRemainingException(){
        super(Constants.EXC_NEUTRAL);
    }
}
