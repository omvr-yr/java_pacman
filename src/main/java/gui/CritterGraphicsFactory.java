package gui;

import javax.imageio.ImageIO;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;

public final class CritterGraphicsFactory {
    private final double scale;
    int rota = 0;
    private boolean paused;
    private String skin;

    public CritterGraphicsFactory(double scale ,String skin) {
        this.scale = scale;
        this.skin = skin;
    }

    /**
     * @param critter
     * @return
     */
    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var url = (critter instanceof PacMan) ? skin : switch ((Ghost) critter) {
            case BLINKY -> "ghost_blinky.png";
            case CLYDE -> "ghost_clyde.png";
            case INKY -> "ghost_inky.png";
            case PINKY -> "ghost_pinky.png";
            default -> "";
        };
        Image image = new Image(url);
        Image f = new Image("src_main_resources_ghost_pinky.png", scale * size, scale * size, true, true);
        Image u = new Image(url, scale * size, scale * size, true, true);

        var imagev = new ImageView(new Image(url, scale * size, scale * size, true, true));
        
        return new GraphicsUpdater() {
            @Override
            public void update() {
                imagev.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                imagev.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                // Debug.out("sprite updated");
                
                if (critter instanceof PacMan) {
                    var dir = PacMan.INSTANCE.getDirection();
                    switch (dir) {
                        case EAST -> {
                            imagev.setScaleX(1); 
                            imagev.setRotationAxis(new Point3D(0, 0, 0));
                            imagev.setRotate(0);
                            rota = 0;
                        }
                        case SOUTH -> {
                            imagev.setScaleX(1); 
                            imagev.setRotationAxis(new Point3D(0, 0, 90));
                            imagev.setRotate(90);
                            rota = 90;
                        }
                        case WEST -> {
                            imagev.setRotationAxis(new Point3D(0, 0, 0));
                            imagev.setRotate(0);
                            rota = 0;
                            imagev.setScaleX(-1); 
                        }
                        case NORTH -> {
                            imagev.setScaleX(1); 
                            imagev.setRotationAxis(new Point3D(0, 0, 270));
                            imagev.setRotate(270);
                            rota = 270;
                        }
                        case NONE -> imagev.setRotate(rota);
                    }
                } else {
                    if(PacMan.INSTANCE.isEnergized()){
                        imagev.setImage(f);
                    } else {
                        imagev.setImage(u);
                    }
                    
                    var dir = critter.getDirection();
                    switch (dir) {
                        case EAST -> {
                            imagev.setScaleX(-1); 
                            imagev.setRotationAxis(new Point3D(0, 0, 0));
                            imagev.setRotate(0);
                            rota = 0;
                        }
                        case WEST -> {
                            imagev.setRotationAxis(new Point3D(0, 0, 0));
                            imagev.setRotate(0);
                            rota = 0;
                            imagev.setScaleX(1); 
                        }
                        case NONE -> imagev.setRotate(rota);
                    }
                }
            }

            @Override
            public Node getNode() {
                return imagev;
            }
        };
    }
}
