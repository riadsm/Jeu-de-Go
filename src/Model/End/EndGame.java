package Model.End;

import Model.Pawn;
import Model.PawnColor;
import Model.PawnGroup;
import Model.PawnGroupOperations;
import Util.*;

import java.util.ArrayList;
import java.util.Comparator;

public class EndGame {
    public static int limitRemove = 6;

    public EndGame(){ }
    public EndGame(PawnGroup group){
        this.end(group);
        this.end(group);
    }

    public boolean isInsideWall(Pawn p, ArrayList<Pawn> wall){

        for(int i=0;i<wall.size()-1;i++){
            for(int j=i;j<wall.size()-1;j++){
                int minX = Math.min(wall.get(i).getCoordX(),wall.get(j).getCoordX());
                int maxX = Math.max(wall.get(i).getCoordX(),wall.get(j).getCoordX());

                int minY = Math.min(wall.get(i).getCoordY(),wall.get(j).getCoordY());
                int maxY = Math.max(wall.get(i).getCoordY(),wall.get(j).getCoordY());

                if(     minX <= p.getCoordX() && p.getCoordX() <= maxX &&
                        minY <= p.getCoordY() && p.getCoordY() <= maxY){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean sameWall(Pawn a, Pawn b){
        return a.getCoordX() == b.getCoordX() || a.getCoordY() == b.getCoordY();
    }

    public void end(PawnGroup group) {
        var empty = retrieveEveryEmptyGroup(group);
        for(var g: empty){
            System.out.println("\nFor the group with + " + g.get(0));
            Pawn first = findfirstOutside(g,group);
            System.out.println("Starting at: " +first);
            //Zone vide ?
            if(first != null) {
                //No wall
                if (this.getWallPawn(g).size() == 0) {
                    //est entouré
                    if (this.searchClockWise(first, g.get(0), group)) {
                        //FILL
                        System.out.println("is surrounded");
                        for (var x : g) {
                            x.setPawnColor(first.getPawnColor());
                        }
                        group.addAll(g);
                    } else {
                        //Regarde pour une blocade
                        System.out.println("common");
                        extractNreplace(g, first, group);
                    }
                    //with wall
                } else {
                    System.out.println("has a wall");
                    if (this.searchClockWise(first, g.get(0), group)) {
                        System.out.println("Has a wall is surrounded");
                        for (var x : g) {
                            x.setPawnColor(first.getPawnColor());
                        }
                        group.addAll(g);
                    } else {
                        extractNreplace(g, first, group);
                    }
                }

            }else{
                System.out.println("Le groupe n'a pas de first trouvable");
            }
        }
    }

    public boolean extractNreplace(ArrayList<Pawn> emptyGroup, Pawn representantExterne,PawnGroup groupeOriginal){
        //Non
        System.out.println("N'entoure pas la zone pour la 1ere fois...");
        //Recupere et extrait
        ArrayList<Pawn> extracted = new ArrayList<>();
        PawnGroupOperations.extractSubgroup(representantExterne,groupeOriginal,extracted);
        if(extracted.size()>this.limitRemove){
            System.out.println("Too big zone removed, cancelling");
            groupeOriginal.addAll(extracted);
            return false;
        }
        System.out.println("extracted : " + extracted);

        //Couleur
        PawnColor initialColor = representantExterne.getPawnColor();
        extracted.forEach(x-> x.setPawnColor(PawnColor.UNDEFINED));

        //ajoute
        emptyGroup.addAll(extracted);
        //cherche un nouveau representant
        representantExterne = findfirstOutside(emptyGroup,groupeOriginal);
        System.out.println("Nouveau first: " + representantExterne);
        if(representantExterne != null) {
            if (searchClockWise(representantExterne, emptyGroup.get(0), groupeOriginal)) {
                //Si le cercle fonctionne, remplace
                Pawn finalFirst = representantExterne;
                emptyGroup.forEach(x -> x.setPawnColor(finalFirst.getPawnColor()));
                groupeOriginal.addAll(emptyGroup);
                return true;
            }
        }
        System.out.println("First est null OU pas un cercle");
        //revert
        emptyGroup.removeAll(extracted);
        extracted.forEach(x->x.setPawnColor(initialColor));
        groupeOriginal.addAll(extracted);
        return false;
    }
    public ArrayList<Pawn> retrieveEveryEmpty(PawnGroup pawnGroup){
        ArrayList<Pawn> nonPlayerArray = new ArrayList<>();
        //Select everything
        for(int i=0;i< Constants.GRID_SIZE_X;i++){
            for(int j=0; j< Constants.GRID_SIZE_Y;j++){
                nonPlayerArray.add(new Pawn(i,j, PawnColor.UNDEFINED));
            }
        }
        //Remove player PAwns
        nonPlayerArray.removeAll(pawnGroup);
        return nonPlayerArray;
    }

    /** Forme un groupe de cluster à partir du groupe donné en argument
     *
     * @param group
     * @return
     */
    public ArrayList<ArrayList<Pawn>> retrieveEveryEmptyGroup(PawnGroup group){
        ArrayList<Pawn> nonPlayerArray = retrieveEveryEmpty(group);
        ArrayList<ArrayList<Pawn>> empty = new ArrayList<>();
        while(!nonPlayerArray.isEmpty()) {
            //Isole un cluster
            ArrayList<Pawn> cluster = new ArrayList<>();
            PawnGroupOperations.extractSubgroup(nonPlayerArray.get(0), nonPlayerArray, cluster);
            empty.add(cluster);
        }
        return empty;
    }

    /** Obtiens le dernier pawn qui permet de faire la rotation ClockWise
     *  On suppose que la liste est monochromatique
     * @param pawns
     * @return
     */
    public Pawn getClockwisePawn(ArrayList<Pawn> pawns){
        if(pawns.size() == 0) {
            System.out.println("\nL'array est empty\n");
        }
        Pawn pawn = null;
        switch(Direction.UP){
            case UP:
                // Most Right at y==0
                var pawnStream = pawns.stream().filter(p ->{ return p.getCoordY()==0;}).max(Comparator.comparingInt(Pawn::getCoordX));
                if(pawnStream.isPresent()){
                    pawn = pawnStream.get();
                }
            case RIGHT:
                //Most Down at x==8
                pawnStream = pawns.stream().filter(p ->{ return p.getCoordX()==Constants.GRID_SIZE_X-1;}).max(Comparator.comparingInt(Pawn::getCoordY));
                if(!pawnStream.isPresent()){
                    if(pawn != null){
                        return pawn;
                    }
                    //pawn = null;
                }else{
                    pawn = pawnStream.get();
                }
            case DOWN:
                pawnStream = pawns.stream().filter(p ->{ return p.getCoordY()==Constants.GRID_SIZE_Y-1;}).min(Comparator.comparingInt(Pawn::getCoordX));
                if(!pawnStream.isPresent()){
                    if(pawn != null){
                        return pawn;
                    }
                    //pawn = null;
                }else{
                    pawn = pawnStream.get();
                }
            case LEFT:
                pawnStream = pawns.stream().filter(p ->{ return p.getCoordX()==0;}).min(Comparator.comparingInt(Pawn::getCoordY));
                if(!pawnStream.isPresent()){
                    if(pawn != null){
                        return pawn;
                    }
                    //pawn = null;
                }else{
                    pawn = pawnStream.get();
                }
            default:
                pawnStream = pawns.stream().filter(p ->{ return p.getCoordY()==0;}).max(Comparator.comparingInt(Pawn::getCoordX));
                if(!pawnStream.isPresent()){
                    if(pawn != null){
                        return pawn;
                    }
                    //pawn = null;
                }
        }
        return pawns.get(0);
    }


    public ArrayList<Pawn> getWallPawn(ArrayList<Pawn> pawnGroup){
        ArrayList<Pawn> retour = new ArrayList<>();
        pawnGroup.stream().filter(pawn->{
           return   pawn.getCoordX() == 0 || pawn.getCoordX() == Constants.GRID_SIZE_X-1 ||
                    pawn.getCoordY() == 0 || pawn.getCoordY() == Constants.GRID_SIZE_Y-1;
        }).forEach(retour::add);

        return retour;

    }

    /**
     *
     * @param pawns inside
     * @param list outside
     * @return
     */
    public Pawn findfirstOutside(ArrayList<Pawn> pawns, ArrayList<Pawn> list){
        if(pawns.size() ==0){
            return null;
        }
        Pawn initial = getClockwisePawn(pawns);
        //Si side Droit
        if(initial.getCoordX()==Constants.GRID_SIZE_X-1){
            Pawn p = PawnGroupOperations.findNeighbour(initial,Direction.DOWN,list);
            if(p!= null && p.getPawnColor() != initial.getPawnColor()) {
                return p;
            }
        }
        //Si bottom
        if(initial.getCoordY()==Constants.GRID_SIZE_Y-1){
            Pawn p = PawnGroupOperations.findNeighbour(initial,Direction.LEFT,list);
            if(p!= null && p.getPawnColor() != initial.getPawnColor()) {
                return p;
            }
        }

        //else
        for(DirectionClockWise d: DirectionClockWise.values()){
            Pawn p = PawnGroupOperations.findNeighbour(initial,d,list);
            if(p!= null && p.getPawnColor() != initial.getPawnColor()) {
                return p;
            }
        }
        return null;

    }

    public boolean searchClockWise(Pawn source, Pawn recherche, PawnGroup search){
        ArrayList<Pawn> copy = new ArrayList<>(search);
        SideRegister sd = new SideRegister();
        boolean isClockWise = isCycleClockwise(source,recherche,null,source,copy, sd);
        System.out.println("is clockwise: " + isClockWise);
        System.out.println("is surrounded: "+ sd.isSurrounded());
        System.out.println(sd);
        return isClockWise && sd.isSurrounded();

    }


    private class SideRegister{
        boolean top;
        boolean left;
        boolean right;
        boolean bottom;

        public SideRegister() {
            top = false;
            left=false;
            right = false;
            bottom = false;
        }


        /**
         * Considere qu'un coin fonctionne pour 2
         * @param wall
         */
        public void lookWalls(Pawn wall){
            if(wall.getCoordX() == 0){
                left = true;
            }
            if(wall.getCoordY() == 0){
                top = true;
            }
            if(wall.getCoordX() == Constants.GRID_SIZE_X-1){
                right = true;
            }
            if(wall.getCoordY() == Constants.GRID_SIZE_Y-1){
                bottom = true;
            }

        }

        public void lookSides(Pawn search, Pawn actual){
            //Same X
            if(search.getCoordX() == actual.getCoordX()){
                //Before
                if(actual.getCoordY() < search.getCoordY()){
                    top = true;
                //After
                }else{
                    bottom = true;
                }
            //Same Y
            }else if(search.getCoordY() == actual.getCoordY()){
                //before
                if(actual.getCoordX() < search.getCoordX()){
                    left = true;
                }else{
                    right = true;
                }
            }
        }

        public boolean isSurrounded(){
            return top && left && right && bottom;
        }

        @Override
        public String toString() {
            return "SideRegister{" +
                    "top=" + top +
                    ", left=" + left +
                    ", right=" + right +
                    ", bottom=" + bottom +
                    '}';
        }
    }
    /**
     * Remove explored values
     *
     * @param source
     * @param recherche
     * @param previous
     * @param actual
     * @param searchGroup
     * @return
     */
    private boolean isCycleClockwise(Pawn source, Pawn recherche, Pawn previous, Pawn actual, ArrayList<Pawn> searchGroup, SideRegister registre){

        if(actual != source) {
            searchGroup.remove(actual);
        }
        int goX =0;
        int goY =0;
        registre.lookSides(recherche,actual);
        registre.lookWalls(actual);

        // Direction du vecteur directeur
        //X
        if( recherche.getCoordX() - actual.getCoordX() < 0){
            goX = -1;
        }else if(recherche.getCoordX() - actual.getCoordX() == 0){
            goX = 0;
        }else{
            goX = 1;
        }
        //Y
        if( recherche.getCoordY() - actual.getCoordY() <0){
            goY = -1;
        }else if(recherche.getCoordY() - actual.getCoordY() ==0){
            goY =0;
        }else{
            goY = 1;
        }
        // Direction du vecteur Normal
        int start = OrthogonalDirections.findClosest(goX,goY);

        //Parcours les valeurs de Direction dans le sens inverse du vecteur Directeur
        // Si la valeur est 7   --> 7,6,5,4,3,2,1,0,-1,-2
        //                      --> 7,6,5,4,3,2,1,0,9,8 (modulo)
        for(int i= start; i>= start-8;i--)
        {
            OrthogonalDirections direction = OrthogonalDirections.get(i);

            // Trouve un mur
            if(!PawnGroupOperations.isValid(actual.getCoordX()+direction.getX(),actual.getCoordY()+direction.getY())){
                    registre.lookWalls(actual);
                return true;
            };

            Pawn voisin = PawnGroupOperations.findNeighbourColor(actual,direction,searchGroup);
            //TODO prevenir le retour au début
            if(voisin!= null) {
                //Si rencontre l'origine
                if (actual != source && !voisin.equals(previous) && voisin == source) {
                    if(registre.isSurrounded()){
                       return true;
                    }
                }
            }
            if(voisin != null) {
                if(voisin.equals(previous)){
                    continue;
                }
                System.out.println("Coord x : " + voisin.getCoordX() + "Coord y : " + voisin.getCoordY());
                if(!isCycleClockwise(source, recherche, actual, voisin, searchGroup,registre)){
                    continue;
                }else{
                    return true;
                }
            }
        }
        return false;
    }
}
