package View;

import Model.PawnGroup;
import Util.Constants;
import Controller.RefreshEvent;
import test.ViewInterface;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements ViewInterface {
    TopPanel topPanel;
    GameGrid game;

    public View(PawnGroup group){
        this.setTitle("Go game");
        this.setSize(Constants.GAME_WIDTH+ 2*Constants.SIDE_WIDTH,Constants.GAME_HEIGHT +150);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Lettres top
        topPanel = new TopPanel();
        this.add(topPanel, BorderLayout.NORTH);

        //Grilles au centre
        game = new GameGrid(group);
        this.add(game, BorderLayout.CENTER);

        this.add(new NumberPanel(9), BorderLayout.EAST);
        this.add(new NumberPanel(9), BorderLayout.WEST);
    }

    public void setTileClickListener(ClickListener cl){
        game.setTileClickListener(cl);
    };

    public void refresh(RefreshEvent event){
        topPanel.refresh(event);
        game.refresh();
    }

    public void showWinner( int blackPoints,int whitePoints){
        //black > white
        String endGameTitle = "AND THE WINNER";
        if(blackPoints > whitePoints) {
            String text = "Black player has won with " + blackPoints + " against " + whitePoints;
            if (JOptionPane.showConfirmDialog(this,
                    text,
                    endGameTitle,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE)
                    == JOptionPane.OK_OPTION) {
            }
        }else{
            String text = "White player has won with " + whitePoints + " against " + blackPoints;
            if (JOptionPane.showConfirmDialog(this,
                    text,
                    endGameTitle,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE)
                    == JOptionPane.OK_OPTION) {
            }
        }
    };
}

