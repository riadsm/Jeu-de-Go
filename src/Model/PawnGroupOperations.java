package Model;

import Util.Constants;
import Util.Direction;
import Util.Directionnal;

import java.util.ArrayList;

public class PawnGroupOperations {



    public static boolean isValid(int x, int y){
        if(x<0 || x > Constants.GRID_SIZE_X-1) return false;
        if(y<0 || y > Constants.GRID_SIZE_Y-1) return false;
        return true;
    }

    public static Pawn findNeighbour(Pawn pawnOriginal, Directionnal direction, ArrayList<Pawn> searchList){
        //Verifier si les bornes sont bonnes
        Pawn up;
        if(PawnGroupOperations.isValid(pawnOriginal.getCoordX() + direction.getX(),pawnOriginal.getCoordY() +direction.getY())){
            up = new Pawn(  pawnOriginal.getCoordX() +direction.getX(),
                    pawnOriginal.getCoordY() + direction.getY(),
                    pawnOriginal.getPawnColor());
        }else{
            return null;
        }
        //Trouve le pawn DE LA MEME COULEUR
        for(Pawn i: searchList){
            if((up.getCoordX() == i.getCoordX()) && (up.getCoordY() == i.getCoordY())){ return i;}
        }
        return null;
    }
    public static Pawn findNeighbourColor(Pawn pawnOriginal, Directionnal direction, ArrayList<Pawn> searchList){
        //Verifier si les bornes sont bonnes
        Pawn up;
        if(PawnGroupOperations.isValid(pawnOriginal.getCoordX() + direction.getX(),pawnOriginal.getCoordY() +direction.getY())){
            up = new Pawn(  pawnOriginal.getCoordX() +direction.getX(),
                    pawnOriginal.getCoordY() + direction.getY(),
                    pawnOriginal.getPawnColor());
        }else{
            return null;
        }
        //Trouve le pawn DE LA MEME COULEUR
        for(Pawn i: searchList){
            if((up.getCoordX() == i.getCoordX()) && (up.getCoordY() == i.getCoordY()) && (up.getPawnColor() == i.getPawnColor())){ return i;}
        }
        return null;
    }

    public static long howManyColor(PawnColor pawnColor, ArrayList<Pawn> search){
        return search.stream().filter(x-> x.getPawnColor() == pawnColor).count();
    }


    /**
     *
     * @param p --> Element dans le cluster
     * @param orginalGroup --> groupe dans lequel la recherche est faite
     * @return ArrayList contenant le cluster
     * @return l'arraylist fournie en argument ne contient plus les éléments du cluster
     */
    static public void extractSubgroup(Pawn p, ArrayList<Pawn> orginalGroup, ArrayList<Pawn> extractedSubgroup){
        Pawn pawnVoisin;
        extractedSubgroup.add(p);
        orginalGroup.remove(p);
        //Regarde dans toutes les Directions pour un voisin
        for(Direction d : Direction.values()) {
            pawnVoisin = PawnGroupOperations.findNeighbourColor(p, d, orginalGroup);
            //S'il y a un voisin, rappelle la methode
            if (pawnVoisin != null) {
                //ajoute le Model.Pawn a la liste s'il n'est pas dedans
                if(!extractedSubgroup.contains(pawnVoisin)){
                   // extractedSubgroup.add(pawnVoisin);
                    orginalGroup.remove(pawnVoisin);
                }
                extractSubgroup(pawnVoisin, orginalGroup, extractedSubgroup);
            }
        }
    }
}
