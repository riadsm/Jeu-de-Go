package View;

import Controller.RefreshEvent;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    PointsJPanel pv;

    TopPanel(){
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx =0;
        c.gridy = 0;
        pv = new PointsJPanel();
        add(pv,c);

        c.gridy++;

        add(new LetterPanel(9),c);
    }

    public void refresh(RefreshEvent event){
        pv.refresh(event);
    }

}
