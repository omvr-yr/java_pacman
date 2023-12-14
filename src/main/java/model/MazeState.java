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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static config.Cell.Content.FRUIT;

//import apple.laf.JRSUIConstants.Direction;

import static model.Ghost.*;

public final class MazeState {
    public static boolean son=true;
    public static boolean pacson=true;
    private static boolean sonenergized=false;
    private static Clip clip;
    private static Clip clip2;
    private static Clip clip3;
    private static Clip clip4;
    private static Clip clip5;
    private static boolean audio;
    private  MazeConfig config;
    private final int height;
    private final int width;
    private static long startTime = System.currentTimeMillis();
    private final boolean[][] gridState;

    private final List<Critter> critters;
    private int score;
    public static int niv=0;
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
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            if(elapsedTime>800||!audio){
                if(audio&&son){
                    clip.stop();
                    clip.close();
                }
                sonpacgomme();
                startTime=endTime;
            }
        }
        for (var critter : critters) {
            if (critter instanceof PacMan) {
                PacMan p = (PacMan) critter;
                if (config.getPacGomme(pacPos.x(), pacPos.y())) {
                    //System.out.println("Dans le if coordonées");
                    p.setEnergized(true);
                    //System.out.println(p.isEnergized());
                    config.setPacGomme(pacPos.x(), pacPos.y(), false);
                    if(pacson){
                        pacson=false;
                        sonsuper();
                    }
                    else{
                        clip4.stop();
                        clip4.close();
                        sonsuper();
                    }
                    addScore(50);
                    nombrepacgomme--;
                    ajoutScore(49);
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
                if(config.getFruit()[pacPos.y()][pacPos.x()]){
                    config.getFruit()[pacPos.y()][pacPos.x()] = false;
                    addScore(100);
                    ajoutScore(99);
                }
            }
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) {
                    sonmange();
                    addScore(10);
                    ajoutScore(9);
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
        if(score==0){
            sondebut();
        }
        score += increment;
        ajoutScore(1);
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A
        // game over screen would be nice too.
        lives--;
        if(son&&sonenergized){
            clip4.stop();
            clip4.close();
        }
        sonmort();
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

    public static void ajoutScore(int n){
        
        // Chemin vers le fichier score.txt
        String lien = "score.txt";

        try {
            // Lecture du contenu du fichier
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int scoreA = scanner.nextInt();
            scanner.close();

            // Incrémentation du score
            scoreA += n;

            // Réécriture du nouveau score dans le fichier
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(scoreA));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void classement() {
        String lien = "classement.txt";
        try {
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int currentLine = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (currentLine == niv) {
                    int bestScore = Integer.valueOf(line);
                    if (this.score > bestScore) {
                        String newLine = String.valueOf(this.score);
                        Path path = Paths.get(lien);
                        List<String> lines = Files.readAllLines(path);
                        lines.set(currentLine - 1, newLine);
                        Files.write(path, lines);
                    }
                    break;
                }
                currentLine++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sonpacgomme(){
        if(son){
            try {
                File audioFile = new File("src/main/resources/pacgomme.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                audio=true;           
            } catch (Exception e) {
                e.printStackTrace();
                }
        }
    }

    private static void sonmort() {
        if(son){
            try{
                File audioFile = new File("src/main/resources/death.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip2 = AudioSystem.getClip();
                clip2.open(audioInputStream);
                clip2.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sonmange() {
        if(son){
            try{
                File audioFile = new File("src/main/resources/mange.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip3 = AudioSystem.getClip();
                clip3.open(audioInputStream);
                clip3.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sonsuper() {
        if(son){
            try{
                File audioFile = new File("src/main/resources/super.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip4 = AudioSystem.getClip();
                clip4.open(audioInputStream);
                clip4.start();
                sonenergized=true;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sondebut() {
        if(son){
            try{
                File audioFile = new File("src/main/resources/debut.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip5 = AudioSystem.getClip();
                clip5.open(audioInputStream);
                clip5.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
