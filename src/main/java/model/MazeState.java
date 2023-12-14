package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.PacMan;

import gui.*;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//import apple.laf.JRSUIConstants.Direction;

import static model.Ghost.*;

public final class MazeState {
    private  MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;
    private int nombrepacgomme = 551;
    private final PacmanController pacmanController;
    
    public int getNombrepacgomme() {
        return nombrepacgomme;
    }

    public void setNombrepacgomme(int nombrepacgomme) {
        this.nombrepacgomme = nombrepacgomme;
    }

    public void setVie(int n){
        this.lives = n;
    }

    public void mettreToutesValeursATrue() {
        for (int i = 0; i < gridState.length; i++) {
            for (int j = 0; j < gridState[i].length; j++) {
                gridState[i][j] = false;
            }
        }
    }

    public MazeState(MazeConfig config, PacmanController pacmanController) {
        this.config = config;
        this.pacmanController = pacmanController;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0));
        resetCritters();
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVie(){
        return lives;
    }

    public void update(long deltaTns) {
        // FIXME: too many things in this method. Maybe some responsibilities can be
        // delegated to other methods or classes?
        for (var critter : critters) {
            pacmanController.update();

            if (PacMan.INSTANCE.isEnergized() && critter instanceof Ghost) {
                Ghost g = (Ghost) critter;
                g.alea(config, deltaTns);
            } else if (critter == CLYDE) {// appel de Clyde
                Clyde.c(config, deltaTns);
            } else if (critter == INKY) {// appel de Inky
                Inky.i(config, deltaTns);
            } else if (critter == PINKY) {// appel de Pinky
                Pinky.p(config, deltaTns);
            } else if (critter == BLINKY) {// appel de Blinky
                Blinky.b(config, deltaTns);
            } else {
                // System.out.println(PacMan.INSTANCE.getPos());
                var curPos = critter.getPos();
                var nextPos = critter.nextPos(deltaTns);
                var curNeighbours = curPos.intNeighbours();
                var nextNeighbours = nextPos.intNeighbours();
                if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow
                                                                  // it?
                    switch (critter.getDirection()) {
                        case NORTH -> {
                            for (var n : curNeighbours)
                                if (config.getCell(n).northWall()) {
                                    nextPos = curPos.floorY();
                                    critter.setDirection(Direction.NONE);
                                    break;
                                }
                        }
                        case EAST -> {
                            for (var n : curNeighbours)
                                if (config.getCell(n).eastWall()) {
                                    nextPos = curPos.ceilX();
                                    critter.setDirection(Direction.NONE);
                                    break;
                                }
                        }
                        case SOUTH -> {
                            for (var n : curNeighbours)
                                if (config.getCell(n).southWall()) {
                                    nextPos = curPos.ceilY();
                                    critter.setDirection(Direction.NONE);
                                    break;
                                }
                        }
                        case WEST -> {
                            for (var n : curNeighbours)
                                if (config.getCell(n).westWall()) {
                                    nextPos = curPos.floorX();
                                    critter.setDirection(Direction.NONE);
                                    break;
                                }
                        }
                    }

                }

                critter.setPos(nextPos.warp(width, height));
            }
        }
        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();
        // gestion entre colision entre fantomes et pacman
        if (!gridState[pacPos.y() % height][pacPos.x() % width]) {
            addScore(1);
            nombrepacgomme--;
            gridState[pacPos.y() % height][pacPos.x() % width] = true;
        }
        for (var critter : critters) {
            if (critter instanceof PacMan) {
                PacMan p = (PacMan) critter;
                if (config.getPacGomme(pacPos.x(), pacPos.y())) {
                    //System.out.println("Dans le if coordonées");
                    p.setEnergized(true);
                    //System.out.println(p.isEnergized());
                    config.setPacGomme(pacPos.x(), pacPos.y(), false);
                    addScore(50);
                    nombrepacgomme--;
                    ajoutScoreP();
                }
                if (p.isEnergized()) {
                    //System.out.println("Checkpoint : " + p.getEnergizationTime());
                    p.setEnergizationTime(p.getEnergizationTime() + 1);
                    //System.out.println("EnerTime:" + p.getEnergizationTime());
                    // Vérifiez si la durée d'énergisation est écoulée
                    if (p.getEnergizationTime() >= p.getEnergizationDuration()) {
                        p.setEnergized(false); // Énergisation terminée
                        System.out.println("Fin du pouvoir");
                    }
                }
            }
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) {
                    addScore(10);
                    ajoutScoreF();
                    resetCritter(critter);

                } else {
                    playerLost();
                    return;
                }
            }
        }
    }
    
    public void annule_pouvoir_de_pacman(){
     PacMan.INSTANCE.setEnergized(false);
    }
    public void resetpacgomme(){
        for(int i = 0 ; i < config.getKeepPacgomme().length ; i++){
            for(int y = 0 ; y < config.getKeepPacgomme()[0].length; y++){
                if(config.getKeepPacgomme()[i][y] == 1){
                    config.setPacGomme(y, i, true);
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        ajoutScore();
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A
        // game over screen would be nice too.
        lives--;

        Pinky.reset();
        Inky.reset();
        Clyde.reset();
        Blinky.reset();
        if (lives == 0) {
            System.out.println("Game over!");
            //System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    public void individualreset(){
        Pinky.reset();
        Inky.reset();
        Clyde.reset();
        Blinky.reset();
    }
    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NORTH);
        critter.setPos(initialPos.get(critter));
    }

    public void resetCritters() {
        for (var critter : critters)
            resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    public void setMaze(MazeConfig Maze){
        this.config=Maze;
    }

    public static void ajoutScore(){
        
        // Chemin vers le fichier score.txt
        String lien = "score.txt";

        try {
            // Lecture du contenu du fichier
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int scoreA = scanner.nextInt();
            scanner.close();

            // Incrémentation du score
            scoreA += 1;

            // Réécriture du nouveau score dans le fichier
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(scoreA));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ajoutScoreP(){
        
        // Chemin vers le fichier score.txt
        String lien = "score.txt";

        try {
            // Lecture du contenu du fichier
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int scoreA = scanner.nextInt();
            scanner.close();

            // Incrémentation du score
            scoreA += 49;

            // Réécriture du nouveau score dans le fichier
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(scoreA));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ajoutScoreF(){
        
        // Chemin vers le fichier score.txt
        String lien = "score.txt";

        try {
            // Lecture du contenu du fichier
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int scoreA = scanner.nextInt();
            scanner.close();

            // Incrémentation du score
            scoreA += 9;

            // Réécriture du nouveau score dans le fichier
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(scoreA));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
