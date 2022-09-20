package Model;

import Exceptions.AlreadyPresentElementException;
import Exceptions.InfinityBegunException;
import Exceptions.InfinityException;
import Exceptions.SuicidalMoveException;
import Util.*;

import java.util.ArrayList;

public class PawnGroup extends ArrayList<Pawn> implements Savable {

    private ArrayList<String> historic =new ArrayList<>();

    UpdateListener updateListener;
    private Pawn invalidPawn;
    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }


    public PawnGroup(){ }

    @Override
    public boolean add(Pawn pawn) {
        historic.add(PawnDescriptor.getDescription(pawn));
        return super.add(pawn);
    }

    @Override
    public void clear() {
        super.clear();
        historic.clear();
        invalidPawn =null;
    }

    public String getLastMove(){
        if (!historic.isEmpty()) {
            return historic.get(historic.size() - 1);
        }
        return null;
    }

    public void addPawn(Pawn p) throws Exception {
        System.out.println("added: " + p);
        int deadTiles =0;
        if(this.contains(p)){
            throw new AlreadyPresentElementException();
        }

         if(getLiberties(p) == 0) {
            deadTiles = addPawnWithoutLiberties(p);
        }else{
            invalidPawn = null;
            add(p);
            deadTiles = removeDeadTiles();
        }

        if(updateListener != null){
            UpdateEvent event = new UpdateEvent(this, deadTiles);
            updateListener.updatePlayerScore(event);
        }
    }


    public void addPass(){
        this.historic.add("PASS");
        invalidPawn = null;
    }

    /** Ajoute un Pawn a @this si son ajout est valide
     *  Sinon, retourne une exception
     *
     * @InfinityException n'est pas une exception d'erreur, c'est une exception qui signale une future Infinité
     *
     * @param pawnToAdd
     * @return nombre pions tués
     * @throws Exception
     */
    private int addPawnWithoutLiberties(Pawn pawnToAdd) throws Exception {
        // Un pion sans liberté cause 3 possibilités
        // 1) Il tue un ennemi
        // 2) Il tue self
        // 3) Il ne fait rien

        int numberOfDeadTiles=0;

        //2 Copies
        // On veut éviter de modifier l'original
        PawnGroup after = new PawnGroup();
        forEach(after::add);

        //Position jouée au tour d'avant(infini)
        if(pawnToAdd.equals(invalidPawn)){
            System.out.println("invalid pawn");
            after.add(pawnToAdd);
            // Jouer sur la case infinite n'est pas toujours une infinite( voir SnapBack)
            if((numberOfDeadTiles = after.removeDeadTilesNotOfColor(pawnToAdd.getPawnColor())) ==1){
                // TODO verifier si circularite est possible
                throw new InfinityException();
            }else{
                //TODO (rien ?)
            }
        }else {
            invalidPawn = null;
        }
        after = new PawnGroup();
        forEach(after::add);

        //Donnes avant ajout
        int originalPawnColorNumber = (int) PawnGroupOperations.howManyColor(pawnToAdd.getPawnColor(), after);


        //Le mouvement tue des éléments ennemis
        after.add(pawnToAdd);
        if((numberOfDeadTiles = after.removeDeadTilesNotOfColor(pawnToAdd.getPawnColor())) > 0){
            System.out.println("dead: " + numberOfDeadTiles);
            //Debut infinite
            if(numberOfDeadTiles == 1){

                //Permet de faire une différence +/- matricielle
                ArrayList<Pawn> originalCopy = new ArrayList<>(this);
                //Trouver celui qui est mort
                originalCopy.removeAll(after);
                if(originalCopy.size() > 0) {
                    invalidPawn = originalCopy.get(0);
                }
                add(pawnToAdd);
                this.removeDeadTilesNotOfColor(pawnToAdd.getPawnColor());
                throw new InfinityBegunException();
            }
            this.add(pawnToAdd);
            numberOfDeadTiles = this.removeDeadTilesNotOfColor(pawnToAdd.getPawnColor());
            return numberOfDeadTiles;
        }

        //Le mouvement ne tue pas d'ennemis
        after.removeDeadTiles();
        int afterPawnColorNumber = (int) PawnGroupOperations.howManyColor(pawnToAdd.getPawnColor(),after);
        if(originalPawnColorNumber >= afterPawnColorNumber){
            throw new SuicidalMoveException();
        }

        add(pawnToAdd);
        return 0;
    }

    /**Enlève les groupes qui ne possèdent plus de libertés
     *
     * Fonctionement:
     * Récupère chacun des sous-groupes
     * regarde si le sous groupe a des libertés
     * Si non, les retire de THIS
     *
     * @return le nombre d'éléments retirés
     */
    public int removeDeadTilesNotOfColor(PawnColor color){
        int c=0;
        //copie this dans une array modificable
        ArrayList<Pawn> modifiableArray = new ArrayList<>();
        this.stream().filter(x-> {return x.getPawnColor() != color;}).forEach(x ->modifiableArray.add(x));

        while(!modifiableArray.isEmpty()){
            ArrayList<Pawn> cluster = new ArrayList<>();
            PawnGroupOperations.extractSubgroup(modifiableArray.get(0),modifiableArray,cluster);
            if(!this.hasLiberties(cluster)){
                c += cluster.size();
                this.removeAll(cluster);
            }
        }
        return c;
    }
    /**Enlève les groupes qui ne possèdent plus de libertés
     *
     * Fonctionement:
     * Récupère chacun des sous-groupes
     * regarde si le sous groupe a des libertés
     * Si non, les retire de THIS
     *
     * @return le nombre d'éléments retirés
     */
    public int removeDeadTiles(){
        int c=0;
        //copie this dans une array modificable
        ArrayList<Pawn> modifiableArray = new ArrayList<>();
        forEach(modifiableArray::add);

        while(!modifiableArray.isEmpty()){
            ArrayList<Pawn> cluster = new ArrayList<>();
            PawnGroupOperations.extractSubgroup(modifiableArray.get(0),modifiableArray,cluster);
            if(!this.hasLiberties(cluster)){
                c += cluster.size();
                this.removeAll(cluster);
            }
        }
        return c;
    }

    public boolean hasLiberties(ArrayList<Pawn> aglomeration){
        for(Pawn pawn : aglomeration){
            if(getLiberties(pawn) >0) return true;
        }
        return false;
    }

    public int getLiberties(Pawn pawn){
        int count=0;
        for(Direction d: Direction.values()){
            if(PawnGroupOperations.isValid(pawn.getCoordX() + d.getX(), pawn.getCoordY() + d.getY())){
                //Regarde si la position (x + dx)(y+dy) est occupée
                //Si innocupée --> c'est une liberté
                int add = 1;
                for(Pawn pawn1: this){
                    if(pawn1.getCoordX() == (pawn.getCoordX()+d.getX()) && pawn1.getCoordY() == (pawn.getCoordY() + d.getY())){
                        add = 0;
                    }
                }
                count += add;
            }
        }
        return count;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Pawn p : this){
            sb.append(p);
        }
        return sb.toString();
    }

    @Override
    public String retrieveContent() {
        StringBuilder sb = new StringBuilder();
        for(String s: historic) {
            sb.append(s+" ");
        }
        return sb.toString();
    }
}