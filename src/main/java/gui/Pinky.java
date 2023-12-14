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



public class Pinky {

    private static long startTime = System.currentTimeMillis();

        //deplacement de pinky
        public static void p(MazeConfig config, long deltaTns) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            if(elapsedTime<2000){
                return;
            }
            else if(elapsedTime<17000){
                Ghost.PINKY.suivi(config,deltaTns);
            }
            else if(elapsedTime<27000){
                Ghost.PINKY.alea(config,deltaTns);
            }
            else{
                startTime+=25000;
            }
        }

        public static void reset(){
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            startTime+=elapsedTime;
        }

    
    }