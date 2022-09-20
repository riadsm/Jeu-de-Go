package Controller.Actions;

import Controller.PlayerManagerSingleton;
import Exceptions.AlreadyPresentElementException;
import Exceptions.InfinityBegunException;
import Exceptions.SuicidalMoveException;
import Model.*;
import View.ClickEvent;
import View.ClickListener;

public class AddOnClick implements ClickListener {
    private PawnGroup group;
    PlayerManagerSingleton pm;

    public AddOnClick(PawnGroup group, PlayerManagerSingleton pm) {
        this.group = group;
        this.pm = pm;
    }

    @Override
    public void onClick(ClickEvent event) {
        try {
            /*if(pm.getSelectedPlayer().getPawnColor() == PawnColor.WHITE){
                group.addPawn(new PawnWhite(event.getX(),event.getY()));
            }else{
                group.addPawn(new PawnBlack(event.getX(),event.getY()));
            }*/
            group.addPawn(new Pawn(event.getX(),event.getY(),pm.getSelectedPlayer().getPawnColor()));
        }catch (AlreadyPresentElementException e){
        }catch (SuicidalMoveException e) {
        }catch (InfinityBegunException e){
            pm.getSelectedPlayer().addPoints(1);
            pm.changePlayer();
        } catch (Exception e) {
        }
    }
}
