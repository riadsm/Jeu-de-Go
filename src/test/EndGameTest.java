package test;

import Controller.GameMaster;
import Controller.Player;
import Controller.PlayerManagerSingleton;
import Exceptions.AlreadyPresentElementException;
import Model.Pawn;
import Model.PawnColor;
import Model.PawnGroup;
import Model.PawnGroupOperations;
import Model.End.EndGame;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EndGameTest {

    @Test
    void isSurrounded4(){

    }

    /** 2 Cercle vides basiques Blancs et noirs qui doivent être remplis
     *
     * @throws IOException
     */
    @Test
    void filledUPCircle() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\Circle\\circle1.go"));;
        new EndGame(group);
        assertEquals(true,group.contains(new Pawn(2,2,PawnColor.BLACK)));
        assertEquals(true,group.contains(new Pawn(7,2,PawnColor.WHITE)));
        assertEquals(18,group.size());

    }
    /** Une grosse zone monochormatique
     *
     * @throws IOException
     */
    @Test
    void filledUPCircle2() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\Circular_F9_E7_Black"));
        new EndGame(group);
        assertEquals(17, PawnGroupOperations.howManyColor(PawnColor.BLACK,group));
        assertEquals(29,group.size());

    }
    /** Une grosse zone monochormatique bloquée par 1 blanc(donc séparée en 2)
     *
     * @throws IOException
     */
    @Test
    void filledUPCircle_1DeadBlock() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\TEST_2_Circular_(4,1)_Black_INSIDE"));
        new EndGame(group);
        assertEquals(25, PawnGroupOperations.howManyColor(PawnColor.BLACK,group));
        assertEquals(15,PawnGroupOperations.howManyColor(PawnColor.WHITE,group));

    }



    /** Tests des coins et des wall a 3 pieces
     *
     * @throws IOException
     */
    @Test
    void filledUP_Wall_Basic() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\getWalls\\wall_basis.go"));
        new EndGame(group);
        assertEquals(13, PawnGroupOperations.howManyColor(PawnColor.BLACK,group));
        assertEquals(24,group.size());

    }



    @Test
    void findMaximumPawn() throws Exception {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\getWalls\\getWalls2"));;
        EndGame end = new EndGame();
        end.retrieveEveryEmptyGroup(group).forEach(x->{
            System.out.println("\ngroup: " + x);
            Pawn pawn = null;
            pawn = end.getClockwisePawn(x);
            System.out.println("last one:" +pawn);
            var k = end.findfirstOutside(x,group);
            System.out.println("next move: " + k);
            System.out.println("");
        });

    }

    @Test
    void end() throws Exception {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

       // master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\getWalls\\getWalls2"));;
       // master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\End\\end2_3Side.go"));;
        master.updateAllPawns(new File("src\\GameFile\\mku"));;
        new EndGame(group);

    }

    //getWallPawn(ArrayList<Pawn> pawnGroup)
    @Test
    void getWallPawn() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\getWalls\\getWalls1"));;
        EndGame end = new EndGame();
        var x = end.retrieveEveryEmptyGroup(group);
        System.out.println(x.size());
        assertEquals(5, x.size());
    }

    @Test
    public void isSurrounded() throws IOException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\End\\getWalls\\getWalls1"));;
        EndGame end = new EndGame();
    }


    @Test
    void isCycle() throws IOException, AlreadyPresentElementException {
            //Logic
            PawnGroup group = new PawnGroup();
            //Visual
            ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
            GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\Circular_F9_E7_Black"));
        EndGame end = new EndGame();
        end.searchClockWise(group.get(0),new Pawn(4,2, PawnColor.UNDEFINED),group);
    }
    @Test
    void isCycle2() throws IOException, AlreadyPresentElementException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group,pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\TEST_2_Circular_(4,1)_Black_INSIDE"));
        EndGame end = new EndGame();
        assertEquals(true, end.searchClockWise(group.get(0),new Pawn(5,1,PawnColor.UNDEFINED),group));
    }

    @Test
    void isCycle3_Wall() throws IOException, AlreadyPresentElementException {
        //Logic
        PawnGroup group = new PawnGroup();
        //Visual
        ViewInterface view = new ViewProxy();

        //Players
        PlayerManagerSingleton pm = PlayerManagerSingleton.getInstance();
        pm.addPlayer(new Player(PawnColor.BLACK));
        pm.addPlayer(new Player(PawnColor.WHITE));

        //Manipulator
        GameMaster master = new GameMaster(view, group, pm);
        
        group.setUpdateListener(master);

        master.updateAllPawns(new File("src\\GameFile\\TEST\\TEST_3_SIDE_(3,0)"));
        EndGame end = new EndGame();
        assertEquals(true, end.searchClockWise(new Pawn(6,0, PawnColor.BLACK),new Pawn(5,0,PawnColor.UNDEFINED),group)) ;

    }
}