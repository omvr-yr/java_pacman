package gui;

import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.MazeState;

import static config.Cell.Content.DOT;
import static config.Cell.Content.ENERGIZER;
import static config.Cell.Content.NOTHING;

import java.util.Random;

import static config.Cell.Content.FRUIT;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();

        String f ="Fruit"; // les noms des fruits sont de Fruit1, Fruit2, Fruit3.. donc on a Fruit + un chiffre(String) aléatoir.
        Random random = new Random();
        f = f+  String.valueOf(random.nextInt(2) + 1);
        ImageView fruit = new ImageView(f+".png"); // dans f il y a que le nom du fruit sans l'extention.

        if(cell.initialContent() != FRUIT){
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) { case DOT -> scale/15; case ENERGIZER -> scale/5; case NOTHING -> 0; case FRUIT -> 0; });
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.YELLOW);
        }else{
            fruit.setFitHeight(scale*0.7);
            fruit.setFitWidth(scale*0.7);
            fruit.setLayoutY(fruit.getLayoutX()+scale*0.15);
            group.getChildren().add(fruit);
        }

        if (cell.northWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setArcWidth(150); 
            nWall.setArcHeight(150);  
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setArcWidth(150); 
            nWall.setArcHeight(150);   
            nWall.setY(0);
            nWall.setX(9*scale/10);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setArcWidth(150); 
            nWall.setArcHeight(150);  
            nWall.setY(9*scale/10);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setArcWidth(150); 
            nWall.setArcHeight(150);  
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.southWall() && cell.estInv()==1) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(9*scale/10);
            nWall.setX(0);
            nWall.setFill(Color.WHITE);
            group.getChildren().add(nWall);
        }
        return new GraphicsUpdater() {
            @Override
            public void update() {
                if(cell.initialContent() != FRUIT){
                dot.setVisible(!state.getGridState(pos));
                }
                fruit.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
