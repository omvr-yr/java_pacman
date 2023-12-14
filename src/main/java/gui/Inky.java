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




public class Inky{

    private static long startTime = System.currentTimeMillis();

        public static void i(MazeConfig config, long deltaTns) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            if(elapsedTime<4000){
                return;
            }
            else if(elapsedTime<19000){
                Ghost.INKY.alea(config,deltaTns);
            }
            else if(elapsedTime<29000){
                Ghost.INKY.suivi(config,deltaTns);
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
