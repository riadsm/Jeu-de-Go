package Controller;

import Model.PawnColor;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PlayerManagerSingleton {
    //Fields
    private static PlayerManagerSingleton pms;
    private PlayerManagerSingleton(){
        players = new ArrayList<>();
    }

    //Initalisation
    static{
        pms = new PlayerManagerSingleton();
    }
    //Instantiate
    public static PlayerManagerSingleton getInstance(){
        return pms;
    }

    //Methods
    ArrayList<Player> players;
    private static int selectedPlayer = 0;

    public Player getSelectedPlayer(){
        return players.get(selectedPlayer);
    }

    public Player getPlayer(int index){
        if(index < players.size()){
            return players.get(index);
        }else{
            return null;
        }
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void resetPlayers(){
        selectedPlayer=0;
        players.forEach(x->{
            x.setPoints(0);
        });
    }

    public Player changePlayer(){
        Player re = players.get(selectedPlayer);
        selectedPlayer = (selectedPlayer+1)%(players.size());

        return re;
    }

    public void forEach(Consumer<Player> consumer) {
        players.forEach(consumer);
    }


}
