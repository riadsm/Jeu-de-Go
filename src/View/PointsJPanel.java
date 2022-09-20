package View;

import Util.Constants;
import Controller.RefreshEvent;

import javax.swing.*;
import java.awt.*;

public class PointsJPanel extends JPanel {
    private int blackPoints;
    private int whitePoints;

    JLabel whiteLabel;
    JLabel blackLabel;
    PlayerTurnJLabel playing;

    PointsJPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.weighty = 1;

        c.gridx=0;
        c.gridy=0;
        whiteLabel = new JLabel("White Points: 0");
        add(whiteLabel,c);


        c.gridx++;
        playing = new PlayerTurnJLabel();
        add(playing,c);

        c.gridx++;
        blackLabel = new JLabel("Black Points: 0");
        add(blackLabel,c);

    }
    public void refresh(RefreshEvent event){
        whiteLabel.setText("White Points: " + event.getWhitePlayerPoints());
        blackLabel.setText("Black Points: " + event.getBlackPlayerPoints());
        playing.setPlayerColor(event.getPlayingColor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        playing.repaint();
    }
}

class PlayerTurnJLabel extends JLabel{

    Color color;
    PlayerTurnJLabel(){
        super("");
        int width = Constants.PAWN_DIAMETER+5;
        int height = Constants.PAWN_DIAMETER+5;
        Dimension d = new Dimension(width,height);
        setMinimumSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
    }

    public void setPlayerColor( Color color){
        this.color = color;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(0,0,Constants.PAWN_DIAMETER,Constants.PAWN_DIAMETER);
        g.setColor(Color.BLACK);
        g.drawOval(0,0, Constants.PAWN_DIAMETER, Constants.PAWN_DIAMETER);
    }
}
