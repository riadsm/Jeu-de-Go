package View;

import Util.Constants;

import javax.swing.*;
import java.awt.*;

class LetterPanel extends JPanel {
    LetterPanel(int width){
        super();
        BoxLayout layout = new BoxLayout(this,BoxLayout.LINE_AXIS);
        this.setLayout(layout);


        //First
        JLabel l1 = new JLabel(" ", SwingConstants.CENTER);
        l1.setFont(new Font("Serif", Font.PLAIN, 20));
        l1.setBackground(new Color(241,176,108));

        l1.setMaximumSize(new Dimension(Constants.SIDE_WIDTH,Constants.PAWN_DIAMETER));
        l1.setPreferredSize(new Dimension(Constants.SIDE_WIDTH,Constants.GRID_HEIGHT));
        l1.setBorder(BorderFactory.createEtchedBorder());
        l1.setOpaque(true);
        add(l1);
        for(int i=0; i<width; i++){
            String text = Character.toString('A'+i);
            if(text.equals("I")){
                text = "J";
                i++;
            }
            JLabel l = new JLabel(text, SwingConstants.CENTER);
            l.setFont(new Font("Serif", Font.PLAIN, 20));
            l.setBackground(new Color(241,176,108));

            l.setMaximumSize(new Dimension(Constants.GRID_WIDTH,Constants.PAWN_DIAMETER));
            l.setPreferredSize(new Dimension(Constants.GRID_WIDTH,Constants.GRID_HEIGHT));
            l.setBorder(BorderFactory.createEtchedBorder());
            l.setOpaque(true);
            add(l);
        }

        //Last
        JLabel l2 = new JLabel(" ", SwingConstants.CENTER);
        l2.setFont(new Font("Serif", Font.PLAIN, 20));
        l2.setBackground(new Color(241,176,108));

        l2.setMaximumSize(new Dimension(Constants.SIDE_WIDTH,Constants.PAWN_DIAMETER));
        l2.setPreferredSize(new Dimension(Constants.SIDE_WIDTH,Constants.GRID_HEIGHT));
        l2.setBorder(BorderFactory.createEtchedBorder());
        l2.setOpaque(true);
        add(l2);
    }
}
