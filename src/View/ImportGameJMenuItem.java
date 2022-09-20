package View;

import Util.Constants;
import Controller.FileReceiver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImportGameJMenuItem extends JMenuItem implements ActionListener {
    private FileReceiver receiver;

    public ImportGameJMenuItem(FileReceiver WhereToSendInformations) {
        super("Import game");
        this.receiver = WhereToSendInformations;
        this.addActionListener(this);
    }

    public void setReceiver(FileReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(Constants.DEFAULT_IMPORT_PATH));

        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fichier = jFileChooser.getSelectedFile();
            try {
                if(receiver != null) {
                    receiver.updateAllPawns(fichier);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
