package View;

import Model.Pawn;
import Model.PawnGroup;
import Util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class GameGrid extends JLabel {
    ClickListener tcl;
    public void setTileClickListener(ClickListener tcl){
        this.tcl = tcl;
    }

    PawnGroup group;

    GameGrid(PawnGroup group){
        super("", SwingConstants.CENTER);
        this.group = group;
        Image backgroundImage = new ImageIcon(getClass().getResource("/Images/GoGameBoard.png")).getImage().getScaledInstance(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, Image.SCALE_DEFAULT);
        ImageIcon image = new ImageIcon(backgroundImage);
        setIcon(image);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tcl != null){
                    int x = (int) e.getX()/(Constants.GAME_WIDTH / Constants.GRID_SIZE_X);
                    int y = (int) e.getY()/(Constants.GAME_WIDTH / Constants.GRID_SIZE_Y);
                    System.out.println("x " + x + "y " + y);
                    ClickEvent event = new ClickEvent(e,x,y);
                    tcl.onClick(event);
                    repaint();
                }
                System.out.println(e.getX() + " " + e.getY());
            }
        });
    }

    public void refresh(){
        repaint();
    }

    public void paintPawn(Pawn p, Graphics g){
        int x = (int) (Constants.PAWN_DIAMETER/4 + p.getCoordX()*(Constants.GRID_WIDTH-0.7));

        int y = (int) (Constants.PAWN_DIAMETER/4 + p.getCoordY()*(Constants.GRID_WIDTH -0.7));
        g.setColor(p.getPawnColor().getColor());
        g.fillOval(x,y, Constants.PAWN_DIAMETER, Constants.PAWN_DIAMETER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(group != null) {
            group.forEach(x -> {
                paintPawn(x, g);
            });
        }
    }

}
