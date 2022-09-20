package View;

import Util.Constants;

import javax.swing.*;
import java.awt.*;

public class NumberPanel extends JPanel {
    NumberPanel(int height){
        super();
        BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        for(int i=height; i>0; i--){

            JLabel l = new JLabel(""+i, SwingConstants.CENTER);
            l.setFont(new Font("Serif", Font.PLAIN, 20));
            l.setBackground(new Color(254, 251,188));

            l.setMaximumSize(new Dimension(Constants.SIDE_WIDTH,Constants.PAWN_DIAMETER));
            l.setPreferredSize(new Dimension(Constants.SIDE_WIDTH,Constants.GRID_HEIGHT));
            l.setBorder(BorderFactory.createEtchedBorder());
            l.setOpaque(true);
            add(l);
        }
    }
}
