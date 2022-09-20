package Controller.Actions;

import Controller.FileReceiver;
import Controller.PlayerManagerSingleton;
import Exceptions.InfinityBegunException;
import Exceptions.SuicidalMoveException;
import Model.Pawn;
import Model.PawnGroup;
import Util.FileIO;

import java.io.File;
import java.io.IOException;

public class LoadGameFromFile implements FileReceiver {
    PawnGroup group;
    PlayerManagerSingleton pm;

    public LoadGameFromFile(PawnGroup group, PlayerManagerSingleton pm) {
        this.group = group;
        this.pm = pm;
    }

    /** Écrase et remplace tous les pions données en arguments au Groupe.
     *
     * Cette methode ajoute les pions en fonction du joueur actuel.
     *
     * Elle respecte donc toutes les règles mises en place dans la version originale du jeu.
     *
     *      * && IL FAUT CRÉER UN UPDATE_lISTENER ET L'ATTRIBUER À PawnGroup.setUpdateListener(...) POUR CHANGER LE JOUEUR &&     *
     * @param loadGame
     * @throws IOException
     */
    @Override
    public void updateAllPawns(File loadGame) throws IOException {
        if(!loadGame.exists()){
            throw new IOException("The file is invalid!");
        }
        String text = text = FileIO.getFileContent(loadGame);
        updateAllPawns(text);

    }
    private void updateAllPawns(String text) {
        if(text.isEmpty()){
            return;
        }
        String list[] = text.toLowerCase().split(" ");

        group.clear();
        pm.resetPlayers();

        for(String s: list) {
            if(s.toLowerCase().equals("pass")){
                group.addPass();
                pm.getSelectedPlayer().addPoints(1);
                pm.changePlayer();
                continue;
            }
            int x =0;
            if(s.charAt(0) == 'j'){
                x = 8;
            }
            else{
                x=s.charAt(0) - 97;
            }
            Pawn p = new Pawn(x, 9 - (s.charAt(1) - 48), pm.getSelectedPlayer().getPawnColor());
            try {
                group.addPawn(p);
            } catch (SuicidalMoveException e) {
            } catch (InfinityBegunException e) {
                pm.getSelectedPlayer().addPoints(1);
                pm.changePlayer();
            }catch (Exception e){

            }
        }
    }

}
