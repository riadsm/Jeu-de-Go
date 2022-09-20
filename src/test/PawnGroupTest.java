package test;

import Controller.GameMaster;
import Exceptions.InfinityBegunException;
import Exceptions.InfinityException;
import Exceptions.SuicidalMoveException;
import Model.Pawn;
import Model.PawnColor;
import Model.PawnGroup;
import Model.PawnGroupOperations;
import View.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PawnGroupTest {
    PawnGroup group;
    ViewInterface view;
    GameMaster master;

    @BeforeEach
    void setUp() {
        //Logic
        group = new PawnGroup();
        //Visual
        view = new ViewProxy();
        //Manipulator
        master = new GameMaster(view, group);
        //What happens 'onclick' on view
        //What happens when a group is done adding an element
        //Calls master.update()
        group.setUpdateListener(master);
    }

    @Test
    void Ko() throws Exception {
        try {
            master.updateAllPawns(new File("src/GameFile/TEST/Model_tests/Ko/ko.go"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Void
        assertThrows(InfinityBegunException.class, ()->group.addPawn(new Pawn(2,2,PawnColor.WHITE)));
        assertThrows(InfinityException.class, ()->group.addPawn(new Pawn(3,2,PawnColor.BLACK)));

        //Side
        assertThrows(InfinityBegunException.class, ()->group.addPawn(new Pawn(2,8,PawnColor.BLACK)));
        assertThrows(InfinityException.class, ()->group.addPawn(new Pawn(3,8,PawnColor.WHITE)));
    }

    @Test
    void snapBack() throws Exception {
        try {
            master.updateAllPawns(new File("src/GameFile/TEST/Model_tests/SnapBack/snapBack_wiki.go"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThrows(InfinityBegunException.class, ()->group.addPawn(new Pawn(4,4, PawnColor.BLACK)));
        assertEquals(10,PawnGroupOperations.howManyColor(PawnColor.WHITE,group));
        assertEquals(12,PawnGroupOperations.howManyColor(PawnColor.BLACK,group));

        group.addPawn(new Pawn(4,5, PawnColor.WHITE));
        assertEquals(11,PawnGroupOperations.howManyColor(PawnColor.WHITE,group));
        assertEquals(9,PawnGroupOperations.howManyColor(PawnColor.BLACK,group));

    }

    @Test
    void addPawnInfinityException(){
        try {
            master.updateAllPawns(new File("src/GameFile/TEST/Model_tests/circular_infinity_1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        group.add(new Pawn(5,2,PawnColor.BLACK));

        System.out.println(group);
        assertThrows(InfinityBegunException.class, () -> group.addPawn(new Pawn(5,1,PawnColor.WHITE)));
    }

    @Test
    void sekiWhite() throws Exception {
        try {
            master.updateAllPawns(new File("src\\GameFile\\TEST\\Model_tests\\seki_mutual_life\\seki.go"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("groupe : " + group);
        group.addPawn(new Pawn(4,7,PawnColor.WHITE));
        assertEquals(5,PawnGroupOperations.howManyColor(PawnColor.BLACK,group));
        assertEquals(10,PawnGroupOperations.howManyColor(PawnColor.WHITE,group));
    }

    @Test
    void sekiBlack() throws Exception {
        try {
            master.updateAllPawns(new File("src\\GameFile\\TEST\\Model_tests\\seki_mutual_life\\seki.go"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("groupe : " + group);
        group.addPawn(new Pawn(4,7,PawnColor.BLACK));
        assertEquals(11,PawnGroupOperations.howManyColor(PawnColor.BLACK,group));
        assertEquals(6,PawnGroupOperations.howManyColor(PawnColor.WHITE,group));
    }


    @Test
    void suicidalMoves(){
        try {
            master.updateAllPawns(new File("src/GameFile/TEST/Model_tests/Suicidal/suicide.go"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThrows(SuicidalMoveException.class, ()->group.addPawn(new Pawn(4,4,PawnColor.WHITE)));
        assertThrows(SuicidalMoveException.class, ()->group.addPawn(new Pawn(0,0,PawnColor.WHITE)));
        assertThrows(SuicidalMoveException.class, ()->group.addPawn(new Pawn(5,0,PawnColor.BLACK)));

    }
}