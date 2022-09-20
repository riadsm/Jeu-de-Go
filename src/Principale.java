import Controller.GameMaster;
import Model.PawnGroup;
import Util.Constants;
import View.ImportGameJMenuItem;
import View.SaveGameJMenuItem;

import View.ClickListener;
import View.ClickEvent;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principale {

    public static void main(String[] args) {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        View view = new View(group);

        //Manipulator
        GameMaster master = new GameMaster(view, group);

    //Menu
            JMenuBar menu = new JMenuBar();
                JMenu fileMenu = new JMenu("File");
                    fileMenu.add(new ImportGameJMenuItem(master));
                    fileMenu.add(new SaveGameJMenuItem(group));
    JMenu passMenu = new JMenu("Actions");
                    JMenuItem passMenuItem = new JMenuItem("Skip turn");
                    passMenuItem.addActionListener(new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                            if(JOptionPane.showConfirmDialog(view,Constants.MSG_SKIP_TURN) == JOptionPane.OK_OPTION){
                                master.skipTurn();
                            }else{ }
                        }
                    });
    JMenuItem surrenderMenuItem = new JMenuItem("Surrender");
                    surrenderMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(JOptionPane.showConfirmDialog(view, Constants.MSG_SURRENDER) == JOptionPane.OK_OPTION){
                                if(JOptionPane.showConfirmDialog(view, Constants.MSG_RESTART) == JOptionPane.OK_OPTION){
                                    master.newGame();
                                }else{
                                    System.exit(0);
                                }
                            }else{
                                // Nothing on not surrendering
                            }
                        }
                    });
                    passMenu.add(passMenuItem);
                    passMenu.add(surrenderMenuItem);
            menu.add(fileMenu);
            menu.add(passMenu);
        view.setJMenuBar(menu);

    //Liens
        // View --(onclick)--> Controller.GameMaster --(add element)--> group --(Refresh)--> Game

        //What happens 'onclick' on view
        //Calls master.onClick();
        view.setTileClickListener(master);

    //What happens when a group is done adding an element
        //Calls master.update()
        group.setUpdateListener(master);

        view.setVisible(true);

}
}
