package View;

import Util.FileIO;
import Model.Savable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SaveGameJMenuItem extends JMenuItem implements ActionListener {

    Savable elementToSave;

    public SaveGameJMenuItem(Savable elementToSave){
        super("Save game");
        this.elementToSave = elementToSave;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        JFileChooser jFileChooser =new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("src/GameFile"));
        if(jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File fichier = jFileChooser.getSelectedFile();
            try {
                FileIO.saveToFile(fichier,elementToSave.retrieveContent());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
