package Controller;

import Controller.Actions.AddOnClick;
import Controller.Actions.LoadGameFromFile;
import Model.*;
import Util.Constants;
import View.*;
import Model.End.EndGame;
import test.ViewInterface;

import java.io.File;
import java.io.IOException;

public class GameMaster implements ClickListener, FileReceiver, UpdateListener {
    private PlayerManagerSingleton pm;
    private PawnGroup group;
    private ViewInterface view;

    private FileReceiver fileReceiver;
    private ClickListener clickListener;

    /**Initialise PlayerManager avec les defauts
     *
     * @param group
     * @param view
     */
    public GameMaster(ViewInterface view, PawnGroup group) {
        this.group = group;
        this.view = view;

        //Players
        pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));
        initDefaultActions();
    }

    public GameMaster(ViewInterface view, PawnGroup group, PlayerManagerSingleton pm) {
        this.pm = pm;
        this.view = view;
        this.group = group;
        initDefaultActions();
    }


    private void initDefaultActions(){
        fileReceiver = new LoadGameFromFile(group,pm);
        clickListener = new AddOnClick(group,pm);
    }

    public void newGame(){
        group.clear();
        pm.resetPlayers();
        refreshScreen();
    }
    public void skipTurn(){
        if(group.getLastMove() == "PASS"){
            EndGame end = new EndGame();
            end.end(group);
            end.end(group);
            pm.forEach(p->{
                p.setPoints((int) PawnGroupOperations.howManyColor(p.getPawnColor(),group));
            });
            //Seki +7 on white
            pm.getPlayer(1).addPoints(Constants.SEKI_POINTS);
            refreshScreen();
            this.endGameScreen();
        }else {
            group.addPass();
            pm.getSelectedPlayer().addPoints(1);
            pm.changePlayer();
        }
        refreshScreen();
    }


    @Override
    public void updatePlayerScore(UpdateEvent event) {
        pm.getSelectedPlayer().addPoints(event.getDeadTiles());
        pm.changePlayer();
        refreshScreen();
    }

    @Override
    public void onClick(ClickEvent event) {
        if(clickListener!= null){
            clickListener.onClick(event);
        }
        refreshScreen();
    }

    @Override
    public void updateAllPawns(File loadGame) throws IOException {
        if(fileReceiver!=null){
            fileReceiver.updateAllPawns(loadGame);
        }
        refreshScreen();
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFileReceiver(FileReceiver fileReceiver) {
        this.fileReceiver = fileReceiver;
    }


    public void endGameScreen(){
        //black > white
        view.showWinner(pm.getPlayer(0).getPoints(),pm.getPlayer(1).getPoints());
    };
    public void refreshScreen(){
        RefreshEvent refreshEvent = new RefreshEvent(
                this,
                pm.getPlayer(1).getPoints(),
                pm.getPlayer(0).getPoints(),
                pm.getSelectedPlayer().getColor());
        view.refresh(refreshEvent);
    }

}
