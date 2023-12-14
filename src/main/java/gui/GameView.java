package gui;

import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import model.MazeState;
import model.PacMan;
import model.Critter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AttributeSet.FontAttribute;

import config.MazeConfig;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.MazeState;

public class GameView {
    // class parameters
    private final MazeState maze;
    private final Pane gameRoot; // main node of the game
    private Label l;
    private Label m;
    private int largeurl;
    private int Font;
    private double scale;
    private ImageView[] tabVie;
    private boolean Vie3P = true, Vie2P = true, Vie1P = true;
    private Label gameOverText,gameWonText;
    private Button boutonQuitter, boutonMenu, boutonRejouer;
    private Stage menuStage;
    private boolean pause;
    private int nombrepacgomme;
    private int mapLength;

    public Pane getGameRoot() {
        return gameRoot;
    }

    private final List<GraphicsUpdater> graphicsUpdaters;

    private void addGraphics(GraphicsUpdater updater) {
        gameRoot.getChildren().add(updater.getNode());
        graphicsUpdaters.add(updater);
    }

    /**
     * @param maze  le "modèle" de cette vue (le labyrinthe et tout ce qui s'y
     *              trouve)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le nombre de pixels représentant une unité du labyrinthe
     */
    public GameView(String skin, MazeState maze, Pane root, double scale, int largeurl, int Font, Stage menuStage,
            int nombrepacgomme, double size , double sizeover) {
        tabVie = new ImageView[] { new ImageView(skin), new ImageView(skin), new ImageView(skin) };
        this.Font = Font;
        this.largeurl = largeurl;
        this.maze = maze;
        this.gameRoot = root;
        this.scale = scale;
        this.menuStage = menuStage;
        pause = false;
        this.nombrepacgomme = nombrepacgomme;
        // pixels per cell
        root.setMinWidth(maze.getWidth() * scale);
        root.setMinHeight(maze.getHeight() * scale + 70);
        root.setStyle("-fx-background-color: #000000");
        creatScore();
        creatVie();
        var critterFactory = new CritterGraphicsFactory(scale, skin);
        var cellFactory = new CellGraphicsFactory(scale);
        graphicsUpdaters = new ArrayList<>();
        for (var critter : maze.getCritters())
            addGraphics(critterFactory.makeGraphics(critter));
        for (int x = 0; x < maze.getWidth(); x++)
            for (int y = 0; y < maze.getHeight(); y++)
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));
        gameOverText = new Label("Game Over");
        gameOverText.setFont(javafx.scene.text.Font.font(sizeover));
        gameOverText.setTextFill(Color.WHITE);
        gameOverText.setStyle("-fx-background-color: transparent;");
        this.gameOverText.setLayoutX(maze.getWidth() * scale * 0.15);
        this.gameOverText.setLayoutY(maze.getHeight() * scale * 0.1);
        gameRoot.getChildren().add(gameOverText);

        gameWonText = new Label("Game Won !");
        gameWonText.setFont(javafx.scene.text.Font.font(sizeover));
        gameWonText.setTextFill(Color.WHITE);
        gameWonText.setStyle("-fx-background-color: transparent;");
        this.gameWonText.setLayoutX(maze.getWidth() * scale * 0.15);
        this.gameWonText.setLayoutY(maze.getHeight() * scale * 0.1);
        gameRoot.getChildren().add(gameWonText);

        // Créer un bouton "Quitter" sans arrière-plan
        boutonQuitter = new Button("Quitter");

        // Appliquer le style pour la couleur du texte (blanc) et l'arrière-plan
        // transparent
        boutonQuitter.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        this.boutonQuitter.setLayoutX(maze.getWidth() * scale * 0.345);
        this.boutonQuitter.setLayoutY(maze.getHeight() * scale * 0.65);
        boutonQuitter.setFont(javafx.scene.text.Font.font(size));
        boutonQuitter.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        // Ajouter le bouton au StackPane
        gameRoot.getChildren().add(boutonQuitter);

        // Gérer l'événement de clic pour quitter l'application
        boutonQuitter.setOnAction(e -> System.exit(0));

        // Créer un bouton "Quitter" sans arrière-plan
        boutonMenu = new Button("Menu");

        // Appliquer le style pour la couleur du texte (blanc) et l'arrière-plan
        // transparent
        boutonMenu.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        this.boutonMenu.setLayoutX(maze.getWidth() * scale * 0.345);
        this.boutonMenu.setLayoutY(maze.getHeight() * scale * 0.5);
        boutonMenu.setFont(javafx.scene.text.Font.font(size));
        boutonMenu.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        // Ajouter le bouton au StackPane
        gameRoot.getChildren().add(boutonMenu);

        // Gérer l'événement de clic pour quitter l'application
        boutonMenu.setOnAction(e -> show());

        // Créer un bouton "Quitter" sans arrière-plan
        boutonRejouer = new Button("Rejouer");

        // Appliquer le style pour la couleur du texte (blanc) et l'arrière-plan
        // transparent
        boutonRejouer.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        this.boutonRejouer.setLayoutX(maze.getWidth() * scale * 0.345);
        this.boutonRejouer.setLayoutY(maze.getHeight() * scale * 0.35);
        boutonRejouer.setFont(javafx.scene.text.Font.font(size));
        boutonRejouer.setStyle("-fx-text-fill: white; -fx-background-color: transparent;");
        // Ajouter le bouton au StackPane
        gameRoot.getChildren().add(boutonRejouer);

        // Gérer l'événement de clic pour quitter l'application
        boutonRejouer.setOnAction(e -> Rejouer());

        boolean visible = false;
        gameWonText.setVisible(visible);
        gameOverText.setVisible(visible);
        boutonRejouer.setVisible(visible);
        boutonMenu.setVisible(visible);
        boutonQuitter.setVisible(visible);
        maze.setNombrepacgomme(nombrepacgomme);

    }

    public void Rejouer() {

        tabVie[2].setVisible(true);
        tabVie[1].setVisible(true);
        tabVie[0].setVisible(true);

        Vie1P = true;
        Vie2P = true;
        Vie3P = true;

        maze.mettreToutesValeursATrue();
        maze.setNombrepacgomme(nombrepacgomme);
        maze.setVie(3);
        maze.setScore(0);
        maze.resetpacgomme();
        maze.annule_pouvoir_de_pacman();
        pause = false;
        boolean visible = false;
        gameOverText.setVisible(visible);
        gameWonText.setVisible(visible);

        boutonRejouer.setVisible(visible);
        boutonMenu.setVisible(visible);
        boutonQuitter.setVisible(visible);
        maze.resetCritters();
        maze.individualreset();
    }

    public void show() {
        menuStage.show();
        ((Stage) gameRoot.getScene().getWindow()).close();
    }

    public void reset() {
        if (maze.getVie() <= 0 || maze.getNombrepacgomme() <= 0) {
            maze.setNombrepacgomme(nombrepacgomme);
            maze.setVie(3);
            maze.setScore(0);
            maze.resetpacgomme();
            maze.annule_pouvoir_de_pacman();
        }
    }

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (pause) {
                    return;
                }
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                var deltaT = now - last;
                maze.update(deltaT);
                updateScore();
                updateTemps();
                updateVie();
                gagner();
                reset();
                //replacement_par_force(); // force les images de vie a se replacer correctement a la position d'origine
                for (var updater : graphicsUpdaters) {
                    updater.update();
                }
                last = now;
            }
        }.start();
    }

    public void gagner() {
        if (maze.getNombrepacgomme() <= 0) {
            GAMEOVER();
        }
    }

    public void creatScore() {
        l = new Label();
        l.toFront();
        gameRoot.getChildren().add(l);
        l.setLayoutX(maze.getWidth() * scale * 0.05); // Position Horizontale du Score.
        l.setLayoutY(maze.getHeight() * scale * 1.05); // Position Verticale du Score.
        l.setTextFill(Color.rgb(255, 255, 255));

    }

    public void updateScore() {
        int newScore = maze.getScore();
        l.setText("Score: " + newScore);
    }

    public void creatTemps() {
        m = new Label();
        m.toFront();
        gameRoot.getChildren().add(m);
        m.setLayoutX(maze.getWidth() * scale * 0.05); // Position Horizontale du Score.
        m.setLayoutY(maze.getHeight() * scale * 1.05); // Position Verticale du Score.
        m.setTextFill(Color.rgb(255, 255, 255));

    }

    public void updateTemps() {
        List<Critter> critters = maze.getCritters();
        for (var critter : critters) {
            if (critter instanceof PacMan) {
                PacMan p = (PacMan) critter;
                if (p.isEnergized()) {
                    int temps = (int) (1000 - p.getEnergizationTime()) / 63;
                    l.setText("Temps: " + temps);
                }
            }
        }
    }

    // void replacement_par_force() {
    //     tabVie[0].setLayoutX(maze.getWidth() * scale * 0.475);
    //     tabVie[0].setLayoutY(maze.getHeight() * scale * 1.05);
    //     tabVie[1].setLayoutX(maze.getWidth() * scale * 0.5);
    //     tabVie[1].setLayoutY(maze.getHeight() * scale * 1.05);
    //     tabVie[2].setLayoutX(maze.getWidth() * scale * 0.525);
    //     tabVie[2].setLayoutY(maze.getHeight() * scale * 1.05);
    // }

    public void creatVie() {
        mapLength = maze.getConfig().getWidth();
        ImageView Vie1View = tabVie[0];
        ImageView Vie2View = tabVie[1];
        ImageView Vie3View = tabVie[2];
        double VieWidthetHeight = scale * 0.7;
        Vie1View.setFitHeight(VieWidthetHeight);
        Vie1View.setFitWidth(VieWidthetHeight);
        Vie2View.setFitHeight(VieWidthetHeight);
        Vie2View.setFitWidth(VieWidthetHeight);
        Vie3View.setFitHeight(VieWidthetHeight);
        Vie3View.setFitWidth(VieWidthetHeight);
        Vie1View.setLayoutX((mapLength*scale)/2 -VieWidthetHeight-VieWidthetHeight/2);
        Vie1View.setLayoutY(maze.getHeight() * scale * 1.05);
        Vie2View.setLayoutX((mapLength*scale)/2 -VieWidthetHeight/2);
        Vie2View.setLayoutY(maze.getHeight() * scale * 1.05);
        Vie3View.setLayoutX((mapLength*scale)/2 +VieWidthetHeight -VieWidthetHeight/2);
        Vie3View.setLayoutY(maze.getHeight() * scale * 1.05);

        gameRoot.getChildren().add(Vie1View);
        gameRoot.getChildren().add(Vie2View);
        gameRoot.getChildren().add(Vie3View);
    }

    public void updateVie() {
        if (maze.getVie() == 2 && Vie3P) {
            tabVie[2].setVisible(false);
            Vie3P = false;
        }
        if (maze.getVie() == 1 && Vie2P) {
            tabVie[1].setVisible(false);
            Vie2P = false;
        }
        if (maze.getVie() == 0 && Vie1P) {
            tabVie[0].setVisible(false);
            Vie1P = false;
            GAMEOVER();
        }
    }

    public void GAMEOVER() {
        this.maze.classement();
        if(maze.getVie() == 0){
            gameOverText.setVisible(true);
        } else {
            gameWonText.setVisible(true);
        }
        boutonQuitter.setVisible(true);
        boutonMenu.setVisible(true);
        boutonRejouer.setVisible(true);
        pause = true;
    }

}