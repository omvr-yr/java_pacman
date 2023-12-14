package gui;
import model.Direction;

public class Noeud {
    private int x;
    private int y;
    private Direction direction;
    private Noeud precedant;
    

    public Noeud(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Noeud getPrecedant() {
        return precedant;
    }
    
    public void setPrecedant(Noeud precedant) {
        this.precedant = precedant;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Noeud [x=" + x + ", y=" + y + ", direction=" + direction + "]";
    }
}
