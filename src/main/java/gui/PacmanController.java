package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Direction;
import model.PacMan;
import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import java.lang.Math;

public class PacmanController {
    private final MazeConfig mazeConfig;
    // Le last input direction sert pour enregistrer une entrée de direction si elle
    // n'est pas faisable immédiatement.
    private Direction lastInputDirection = Direction.NONE;

    public PacmanController(MazeConfig mazeConfig) {
        this.mazeConfig = mazeConfig;
    }

    public void keyPressedHandler(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        Direction newDirection = null;

        // On récupère la clé appuyée pour voir quelle direction est demandée.
        if (keyCode == KeyCode.LEFT) {
            newDirection = Direction.WEST;
        } else if (keyCode == KeyCode.RIGHT) {
            newDirection = Direction.EAST;
        } else if (keyCode == KeyCode.UP) {
            newDirection = Direction.NORTH;
        } else if (keyCode == KeyCode.DOWN) {
            newDirection = Direction.SOUTH;
        }
        // Code factorisable, je teste si le déplacement est faisable instantanément
        // pour pas perdre de temps
        // à attendre une update (par exemple si on veut aller à gauche alors qu'on
        // allait à droite, sur la meme ligne)
        if (newDirection == Direction.NORTH && PacMan.INSTANCE.getDirection() == Direction.SOUTH) {
            PacMan.INSTANCE.setDirection(newDirection);
        } else if (newDirection == Direction.SOUTH && PacMan.INSTANCE.getDirection() == Direction.NORTH) {
            PacMan.INSTANCE.setDirection(newDirection);
        } else if (newDirection == Direction.EAST && PacMan.INSTANCE.getDirection() == Direction.WEST) {
            PacMan.INSTANCE.setDirection(newDirection);
        } else if (newDirection == Direction.WEST && PacMan.INSTANCE.getDirection() == Direction.EAST) {
            PacMan.INSTANCE.setDirection(newDirection);
        } else {
            // sinon on update le "lastInputDirection" pour enregistrer le souhait de
            // tourner dès que c'est faisable.
            if (newDirection != null) {
                lastInputDirection = newDirection;
            }
        }
    }

    /*
     * Cette fonction update est appelée dans MazeState.update() pour voir si la
     * dernière direction demandée
     * est possible à effectuer à chaque rafraichissement de la grille.
     */
    public void update() {
        // On vérifie si pacman peut se déplacer dans la direction demandée.
        if (lastInputDirection != null && canMoveInDirection(lastInputDirection)) {
            RealCoordinates currentPos = PacMan.INSTANCE.getPos();
            RealCoordinates NewPos = new RealCoordinates(Math.round(currentPos.getX()), Math.round(currentPos.getY()));
            /*
             * ici on a une mini zone de tolérance, mais en 4.9999 pacman ne peut pas
             * descendre par exemple, il doit etre
             * exactement en 5.0 donc on "Téléporte" pacman à la position pour qu'il reste
             * centré.
             * La zone de tolérance étant réellement petite, on ne voit pas la différence,
             * le mouvement reste entièrement fluide
             */
            PacMan.INSTANCE.setPos(NewPos);
            // un fois qu'il est centré sur la case on change sa direction et ça marche car
            // il est pile en .0
            PacMan.INSTANCE.setDirection(lastInputDirection);
            lastInputDirection = null;
        }
    }

    private boolean canMoveInDirection(Direction direction) {
        // On récipère la position actuelle de pacman
        RealCoordinates currentPos = PacMan.INSTANCE.getPos();
        /*
         * on définit l'écart de tolérance pour trouver quand pacman s'approche le plus
         * possible du centre de la cellule
         * en effet, en faisant les updates, pacman sera par exemple en 1,823 puis en
         * 1,934040 puis en 2,003983 mais
         * jamais pile en 2,0 donc on teste quand il entre dans un certain ecart.
         */
        double tolerance = 0.025;

        // ensuite on calcule au vu des ses deux coordonées s'il peut passer.
        boolean isCenteredX = Math.abs(currentPos.getX() - Math.round(currentPos.getX())) < tolerance;
        boolean isCenteredY = Math.abs(currentPos.getY() - Math.round(currentPos.getY())) < tolerance;

        if (isCenteredX && isCenteredY) {
            // Enfin, on vérifie selon la direction s'il y a un mur dans la cellule
            // suivante.
            // En effet, même s'il est centré, la présence d'un mur empeche tout simplement
            // pacman de se déplacer.
            switch (direction) {
                case NORTH:
                    return !isWallAtPosition(currentPos.round(), direction);
                case EAST:
                    return !isWallAtPosition(currentPos.round(), direction);
                case SOUTH:
                    return !isWallAtPosition(currentPos.round(), direction);
                case WEST:
                    return !isWallAtPosition(currentPos.round(), direction);
                case NONE:
                    return true;
            }
        }

        return false;
    }

    private boolean isWallAtPosition(IntCoordinates position, Direction direction) {
        Cell cell = mazeConfig.getCell(position);
        // on Vérifie selon la cellule si il y a un mur dans une direction demandée.
        switch (direction) {
            case NORTH:
                return cell.northWall();
            case EAST:
                return cell.eastWall();
            case SOUTH:
                return cell.southWall();
            case WEST:
                return cell.westWall();
            default:
                return false; // pas de mur dans la direction demandée
        }
    }

    /*
     * //Je commente ça car je ne pense pas qu'on en aura besoin, mais je le laisse
     * au cas ou on se rend compte que si.
     */public void keyReleasedHandler(KeyEvent event) {
    }
}