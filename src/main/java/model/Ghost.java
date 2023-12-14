package model;
import java.util.*;
import config.MazeConfig;
import geometry.RealCoordinates;
import gui.Noeud;
import model.MazeState;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description in Wikipedia's page
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;    
    private Direction direction = Direction.NORTH;



    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public double getSpeed() {
        return 2;
    }

    public static void setAllGhostsDirectionNone() {
        for (Ghost ghost : values()) {
            ghost.setDirection(Direction.NONE);
        }
    }
    
    public static String[][] tab (MazeConfig config){
        String tableau [][]= new String [config.getHeight()][config.getWidth()];
        for(int i=0;i<tableau.length;i++){
            for(int j=0;j<tableau[i].length;j++){
                tableau[i][j]="";
            }
        }
        for(int i=0;i<tableau.length;i++){
            for(int j=0;j<tableau[i].length;j++){
                if(!config.getGrid()[i][j].northWall()){
                    tableau[i][j]=tableau[i][j]+"n";
                }
                if(!config.getGrid()[i][j].southWall()){
                    tableau[i][j]=tableau[i][j]+"s";
                }
                if(!config.getGrid()[i][j].eastWall()){
                    tableau[i][j]=tableau[i][j]+"e";
                }
                if(!config.getGrid()[i][j].westWall()){
                    tableau[i][j]=tableau[i][j]+"w";
                }
            }
        }
        return tableau;
        
    }
     
    public void alea (MazeConfig config, long deltaTns){
        int a=0;
        int height = config.getHeight();
        int width = config.getWidth();
        var curPos = this.getPos();
        var nextPos = this.nextPos(deltaTns);
        var curNeighbours = curPos.intNeighbours();
        var nextNeighbours = nextPos.intNeighbours();
        String [][]tableau=tab(config);
        if(!curNeighbours.containsAll(nextNeighbours)){
            int t=0;
            String coord =tableau[this.getPos().round().y()][this.getPos().round().x()];
            if(coord.length()!=1){
                if(!coord.equals("ns")&&!coord.equals("we")&&!coord.equals("ew")&&!coord.equals("sn")){
                    while(t==0){
                        Direction d=r();
                        if (d==Direction.EAST&&coord.contains("e")&&this.getDirection()!=Direction.WEST) {
                            // Déplacement vers l'est
                            this.setDirection(Direction.EAST);
                            t++;
                        } else if (d==Direction.WEST&&coord.contains("w")&&this.getDirection()!=Direction.EAST) {
                            // Déplacement vers l'ouest
                            this.setDirection(Direction.WEST);
                            t++;
                        } else if (d==Direction.SOUTH&&coord.contains("s")&&this.getDirection()!=Direction.NORTH) {
                            // Déplacement vers le sud
                            this.setDirection(Direction.SOUTH);
                            t++;
                        } else if (d==Direction.NORTH&&coord.contains("n")&&this.getDirection()!=Direction.SOUTH) {
                            // Déplacement vers le nord
                            this.setDirection(Direction.NORTH);
                            t++;
                        }
                    }
                }
            }
            else{
                if (coord.equals("e")) {
                    // Déplacement vers l'est
                    this.setDirection(Direction.EAST);
                } else if (coord.equals("w")) {
                    // Déplacement vers l'ouest
                    this.setDirection(Direction.WEST);
                } else if (coord.equals("s")) {
                    // Déplacement vers le sud
                    this.setDirection(Direction.SOUTH);
                } else if (coord.equals("n")) {
                    // Déplacement vers le nord
                    this.setDirection(Direction.NORTH);
                }
            }
        }
            this.setPos(nextPos.warp(width, height));                    
        }
        //////////////////////////////////////////////
        public boolean suivi(MazeConfig config, long deltaTns) {

            //on déclare toutes les variable dont on aura besoin
            int a=0;
            int height = config.getHeight();
            int width = config.getWidth();
            Noeud start = new Noeud (this.getPos().round().x(), this.getPos().round().y(), null);
            Noeud end = new Noeud(PacMan.INSTANCE.getPos().round().x(), PacMan.INSTANCE.getPos().round().y(), null);
            var curPos = this.getPos();
            var nextPos = this.nextPos(deltaTns);
            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();
            boolean[][] visited = new boolean[height][width];
            String [][]tableau=tab(config);


            Queue<Noeud> queue = new LinkedList<>();
            queue.offer(start);
            visited[start.getY()][start.getX()] = true;
        
            while (!queue.isEmpty()) {
                //System.out.println(a);
                a++;
                Noeud current = queue.poll();
                int x = current.getX();
                int y = current.getY();
                //Quand l'algorithme trouve pacman
                if (current.getX() == end.getX() && current.getY() == end.getY()) {
                    while (current.getPrecedant() != null) {
                        Noeud previous = current.getPrecedant();
                        int diffX = current.getX() - previous.getX();
                        int diffY = current.getY() - previous.getY();
                    if(!curNeighbours.containsAll(nextNeighbours)){
                        if (diffX == 1) {
                            // Déplacement vers l'est
                            this.setDirection(Direction.EAST);
                        } else if (diffX == -1) {
                            // Déplacement vers l'ouest
                            this.setDirection(Direction.WEST);
                        } else if (diffY == 1) {
                            // Déplacement vers le sud
                            this.setDirection(Direction.SOUTH);
                        } else if (diffY == -1) {
                            // Déplacement vers le nord
                            this.setDirection(Direction.NORTH);
                        }
                    }
                    this.setPos(nextPos.warp(width, height));
                        current = previous;  // Mettez à jour current pour avancer dans la séquence des nœuds precedant.
                    }
                    return true;
                }
                
                //on teste les 4 directions (on prend en compte les téléportations)
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                for (Direction direction : directions) {
                    int newX = x;
                    int newY = y;
        
                    if (direction == Direction.NORTH) {
                        newX = x;
                        newY = y - 1;
                    }
                    if (direction == Direction.SOUTH) {
                        newX = x;
                        newY = y + 1;
                    }
                    if (direction == Direction.EAST) {
                        newX = x + 1;
                        newY = y;
                    }
                    if (direction == Direction.WEST) {
                        newX = x - 1;
                        newY = y;

                    }
                    //config.getHeight()
                    //config.getWidth()
                    if(newX==-1){
                        newX=config.getWidth();
                    }
                    if(newY==-1){
                        newY=config.getHeight();
                    }
                    if(newX==config.getWidth()){
                        newX=0;
                    }
                    if(newY==config.getHeight()){
                        newY=0;
                    }
                    //on teste si un mouvement est possible puis on l'ajoute au noeuds visité + on teste les chemins qui partent de ce noeud

                    if (isValidMove(x, y, direction, tableau) && !visited[newY][newX]) {
                        current.setDirection(direction);
                        Noeud nextNode = new Noeud(newX, newY, direction);
                        nextNode.setPrecedant(current);
                        queue.offer(nextNode);
                        visited[newY][newX] = true;
                    }
                }
            }
            // La destination n'a pas été trouvée.
            return false;//ce n'est pas sensé arrrivé
        }
        //On teste pour savoir s'il y a un mur
        public static boolean isValidMove(int x, int y, Direction d, String [][] tableau) {
            String walls = tableau[y][x];
        
            if (walls.contains("n") && d == Direction.NORTH) {
                return true;
            }
            if (walls.contains("w") && d == Direction.WEST) {
                return true;
            }
            if (walls.contains("e") && d == Direction.EAST) {
                return true;
            }
            if (walls.contains("s") && d == Direction.SOUTH) {
                return true;
            }
            return false;
        }
    
        //Direction aléatoire
        public static Direction r() {
            Random random = new Random();
            int randomNumber = random.nextInt(4) + 1;
            if (randomNumber == 1) {
                return Direction.NORTH;
            } else if (randomNumber == 2) {
                return Direction.SOUTH;
            } else if (randomNumber == 3) {
                return Direction.WEST;
            } else if (randomNumber == 4) {
                return Direction.EAST;
            }
            return null;
    }

}