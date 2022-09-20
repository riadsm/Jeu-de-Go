package test;

import Controller.RefreshEvent;

public interface ViewInterface {
    public void refresh(RefreshEvent event);
    public void showWinner( int blackPoints,int whitePoints);
}
