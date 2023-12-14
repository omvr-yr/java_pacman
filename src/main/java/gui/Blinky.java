package gui;
import java.util.*;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import config.MazeConfig;
import model.MazeState;
import javafx.css.Size;
import javafx.scene.Node;
import javafx.scene.control.TreeView.EditEvent;
import model.Direction;
import model.Ghost;
import model.PacMan;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import gui.Noeud;



public class Blinky {

    private static long startTime = System.currentTimeMillis();

        //deplacement de blinky
        public static void b(MazeConfig config, long deltaTns) { 
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            if(elapsedTime<6000 ){
                return;
            }        
            Ghost.BLINKY.suivi(config,deltaTns);
        }


        public static void reset(){
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            startTime+=elapsedTime;
        }
    }