package gui;


import java.io.File;
/* 
import javax.management.ObjectName;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import javafx.collections.MapChangeListener;
import javafx.geometry.Pos;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;                // imports qui pourraient servir
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import gui.OptionTouche;
import model.Court;*/
import javafx.util.Duration;
import java.lang.StackWalker.Option;
import java.util.PrimitiveIterator;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;
import model.Ghost;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import gui.PacmanController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu{
    private Pane root;
    private GameView game;
    private Button play, quit, options,shopB;
    private VBox menuContainer;
    private VBox optionsContainer;
    private VBox levelsContainer; 
    private Stage primaryStage; 
    private Stage menuStage;
    private Scene gameScene;
    private MazeState maze;
    private TilePane shop;
    private String Skin = "pacman.gif";
    private boolean fullscreen;
    private MazeConfig map;
    private MazeConfig config;
    private Label score;

    public Menu(Stage primaryStage, Pane root , Scene gameScene , MazeState maze ) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.gameScene = gameScene;
        this.maze = maze;
        this.fullscreen = false;
        shop = new TilePane();
        map=null;

        initializeButtons();
        CreateLevels();
        CreateOptions();
        CreateMenu();
        Createshop();
        Scene scene = new Scene(shop, 300, 200);
        menuStage = new Stage();
        //primaryStage.setScene(gameScene);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(shop,optionsContainer,levelsContainer,menuContainer);

        menuStage.setTitle("Pacman");
        menuStage.setScene(new Scene(stackPane, 1000, 1000));
        setIcon();
        lance_menu();
    }


    private static void setstyleC(CheckBox C){
        C.setStyle("-fx-text-fill: white;");
    }

    private static void setstyleB(Button B){
        B.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
    }

    private static void setstyleB2(Button B){
        B.setOnMouseEntered(event -> {
            B.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
        });

        // Événement lorsque la souris quitte le bouton
        B.setOnMouseExited(event -> {
            B.setStyle("-fx-background-color: #000080; -fx-text-fill: white;"); // Réinitialise le style par défaut du bouton
        });
    }

    private void initializeButtons() {
        play = new Button("Play"); // crée le bouton play
        shopB = new Button("Shop"); // crée le bouton shop
        options = new Button("Options"); // crée le bouton Options
        quit = new Button("Quit"); // crée le bouton quit

        setstyleB2(play);
        setstyleB2(options);
        setstyleB2(quit);
        setstyleB2(shopB);


        play.setMaxWidth(150); // Permet de augmenter la largeur du bouton jusqu'a la limite de la vbox
        quit.setMaxWidth(150); 
        options.setMaxWidth(150); 
        shopB.setMaxWidth(150); 


        play.setMinHeight(50);
        quit.setMinHeight(50);
        options.setMinHeight(50);
        shopB.setMinHeight(50);


        play.setFont(new javafx.scene.text.Font(22));
        quit.setFont(new javafx.scene.text.Font(22));
        options.setFont(new javafx.scene.text.Font(22));
        shopB.setFont(new javafx.scene.text.Font(22));


        play.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        quit.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        options.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        shopB.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");


        play.setOnAction(event -> lancer_le_jeu()); // appelle lancer_le_jeu() si appuie sur play
        quit.setOnAction(event -> quitter_application()); // appelle quitter_application() si appuie sur quit
        options.setOnAction(event -> lance_options()); // appelle lance_options() si appuie sur options
        shopB.setOnAction(event -> lance_shop()); // appelle lance_shop() si appuie sur shop

    }

        private void Createshop(){
            int n=200;
        shop.setPrefColumns(3);
        shop.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
        Button back = new Button("Retour");
        setstyleB(back);
        setstyleB2(back);
        back.setOnAction(event -> Retourshop()); // appelle Retourshop() si appuie sur retour

        Button button1 = new Button("Bouton 1");
        Button button2 = new Button("Bouton 2");
        Button button3 = new Button("Bouton 3");
        Button button4 = new Button("Bouton 4");
        Button button5 = new Button("Bouton 5");
        button1.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button2.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button3.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button4.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button5.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");

        button1.setPrefWidth(n); 
        button1.setPrefHeight(n); 
        Image image1 = new Image("amongRED.gif");
        ImageView imageV1 = new ImageView(image1);
        imageV1.setFitWidth(n); // Set image width
        imageV1.setFitHeight(n); // Set image height
        button1.setGraphic(imageV1);
        button1.setOnAction(event -> transfertskin("amongRED.gif")); 


        button2.setPrefWidth(n); 
        button2.setPrefHeight(n); 
        Image image2 = new Image("Crash.gif");
        ImageView imageV2 = new ImageView(image2);
        imageV2.setFitWidth(n); // Set image width
        imageV2.setFitHeight(n); // Set image height
        button2.setGraphic(imageV2);
        button2.setOnAction(event -> transfertskin("Crash.gif")); 


        button3.setPrefWidth(n); 
        button3.setPrefHeight(n); 
        Image image3 = new Image("run.gif");
        ImageView imageV3 = new ImageView(image3);
        imageV3.setFitWidth(n); // Set image width
        imageV3.setFitHeight(n); // Set image height
        button3.setGraphic(imageV3);
        button3.setOnAction(event -> transfertskin("run.gif")); 


        button4.setPrefWidth(n); 
        button4.setPrefHeight(n); 
        Image image4 = new Image("goku.gif");
        ImageView imageV4 = new ImageView(image4);
        imageV4.setFitWidth(n); // Set image width
        imageV4.setFitHeight(n); // Set image height
        button4.setGraphic(imageV4);
        button4.setOnAction(event -> transfertskin("goku.gif")); 


        button5.setPrefWidth(n); 
        button5.setPrefHeight(n); 
        Image image5 = new Image("sonic.gif");
        ImageView imageV5 = new ImageView(image5);
        imageV5.setFitWidth(n); // Set image width
        imageV5.setFitHeight(n); // Set image height
        button5.setGraphic(imageV5);
        button5.setOnAction(event -> transfertskin("sonic.gif")); 
        score = new Label();
        uptadeScore();

        
        shop.getChildren().addAll(button1, button2, button3, button4, button5,back,score);
        }

        public void transfertskin(String skin){
            this.Skin = skin;
        }

    private void CreateMenu(){
        ImageView logoimageView = new ImageView(new Image("logo.png")); // prend l'image pacman.png qui se trouve dans le répertoire menu.java
        logoimageView.setFitWidth(1000); // Largeur souhaitée de l'image
        logoimageView.setFitHeight(300); // Hauteur souhaitée de l'image
        menuContainer = new VBox();
        menuContainer.getChildren().add(logoimageView); // Ajoute l'image dans la Vbox
        menuContainer.getChildren().add(play); // Ajoute le bouton dans la Vbox
        menuContainer.getChildren().add(shopB); // Ajoute le bouton dans la Vbox
        menuContainer.getChildren().add(options); // Ajoute le bouton dans la Vbox
        menuContainer.getChildren().add(quit); // Ajoute le bouton dans la Vbox
        menuContainer.setPrefWidth(200); // modifie la largeur du menu
        menuContainer.setPrefHeight(50); // modifie la hauteur du menu
        menuContainer.setPadding(new Insets(10)); // modifie la distance avec la bordure du menu
        menuContainer.setSpacing(10); // modifie la distance entre les boutons
        menuContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
        ImageView imageView = new ImageView(new Image("pacmang.gif")); // prend l'image pacman.png qui se trouve dans le répertoire menu.java
        imageView.setFitWidth(1000); // Largeur souhaitée de l'image
        imageView.setFitHeight(500); // Hauteur souhaitée de l'image
        menuContainer.getChildren().add(imageView); // Ajoute l'image dans la Vbox
        menuContainer.setAlignment(Pos.CENTER); // Centrer le contenu de la VBox
    }

    private void CreateOptions(){
        optionsContainer = new VBox();
        Label titleLabel = new Label("Options de son");
        titleLabel.setStyle("-fx-text-fill: white;");
        Slider volumeSlider = new Slider(0, 1, 0.5);
        CheckBox soundEffectsCheckbox = new CheckBox("Effets sonores");
        CheckBox musicCheckbox = new CheckBox("Musique de fond");
        CheckBox fullscreen = new CheckBox("Plein écran");
        setstyleC(soundEffectsCheckbox);
        setstyleC(musicCheckbox);
        setstyleC(fullscreen);
        Button testButton = new Button("Tester le son");
        Button resetButton = new Button("Réinitialiser");
        Button backButton = new Button("Retour");
        setstyleB(testButton);
        setstyleB(resetButton);
        setstyleB(backButton);
        setstyleB2(testButton);
        setstyleB2(resetButton);
        setstyleB2(backButton);
        Label p = new Label("Veuillez entrer votre pseudonyme :");  
        p.setStyle("-fx-text-fill: white;");

        TextField textField = new TextField();
        textField.setMaxWidth(150);
        Button button = new Button("Valider");
        Label pseudo2 = new Label();
        button.setOnAction(e -> {
            String pseudo = textField.getText();
            System.out.println("Pseudo saisi : " + pseudo);
            try {
                File file = new File("pseudo.txt"); 
                FileWriter fileWriter = new FileWriter(file, false); 
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(pseudo);
                bufferedWriter.close();
                try {
                    Path filePath = Paths.get("pseudo.txt");
                    String ligne = Files.readAllLines(filePath).get(0);
                    // Afficher le contenu dans le label
                    pseudo2.setText("Votre pseudo actuel est: "+ligne);
                } catch (IOException ex) {
                    System.out.println("Erreur lors de la lecture du fichier pseudo.txt : " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.out.println("Erreur lors de l'enregistrement du pseudo : " + ex.getMessage());
            }
        });
        try {
            Path filePath = Paths.get("pseudo.txt");
            String ligne = Files.readAllLines(filePath).get(0);
            // Afficher le contenu dans le label
            pseudo2.setText("Votre pseudo actuel est: "+ligne);
        } catch (IOException ex) {
            System.out.println("Erreur lors de la lecture du fichier pseudo.txt : " + ex.getMessage());
        }   
        pseudo2.setStyle("-fx-text-fill: white;");





        fullscreen.setOnAction(event -> {
            if (fullscreen.isSelected()) {
                primaryStage.setFullScreen(true);
                menuStage.setFullScreen(true);
                fonc(true);
            } else {
                primaryStage.setFullScreen(false);
                menuStage.setFullScreen(false);
                fonc(false);

            }
        });
                backButton.setOnAction(event -> Retour()); // appelle Retour() si appuie sur backButton

        optionsContainer.getChildren().addAll(titleLabel,volumeSlider,soundEffectsCheckbox,musicCheckbox,testButton,fullscreen,p,textField,button,pseudo2,resetButton,backButton);
        optionsContainer.setPrefWidth(200); // modifie la largeur du menu
        optionsContainer.setPrefHeight(50); // modifie la hauteur du menu
        optionsContainer.setPadding(new Insets(10)); // modifie la distance avec la bordure du menu
        optionsContainer.setSpacing(10); // modifie la distance entre les boutons
        optionsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
    }
     
    public void fonc(boolean b){
        if(b){
            game.getGameRoot().getChildren().clear();
            game = new GameView(Skin,maze, root, 60.0,1750,22,menuStage,50);
            this.fullscreen = true;
        } else {
            game.getGameRoot().getChildren().clear();
            game = new GameView(Skin,maze, root, 30.0,825,12,menuStage,50);
            this.fullscreen = false;
        }
    }
    public void lance_menu(){
        menuStage.show();
        
    }
    public void lance_options(){
        optionsContainer.toFront();
    }

    public void lance_shop(){
        uptadeScore();
            shop.toFront();
           
    }

    public void Retourshop() {
            shop.toBack();
    }

    public void Retour() {
        optionsContainer.toBack();
    }

    public void Retour2() {//pour play
        levelsContainer.toBack();
    }

    private void lancer_le_jeu() {
        maze.mettreToutesValeursATrue();
        maze.resetCritters();
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,0);


        levelsContainer.toFront();
    }

    private void quitter_application() {
        System.exit(0); 
    }

    public void setIcon(){
        Image icon = new Image("icon.png");
        //primaryStage.getIcons().add(icon);
        menuStage.getIcons().add(icon);

    }

    private void CreateLevels(){
        Label titleLabel = new Label("Choix de niveau");
        titleLabel.setStyle("-fx-text-fill: white;");
        levelsContainer = new VBox();
        Button level1 = new Button("Niveau 1");
        Button level2 = new Button("Niveau 2");
        Button level3 = new Button("Niveau 3");
        Button retour = new Button("Retour");
        levelsContainer.getChildren().addAll(titleLabel,level1,level2,level3,retour);
        setstyleB(level1);
        setstyleB(level2);
        setstyleB(level3);
        setstyleB(retour);
        setstyleB2(level1);
        setstyleB2(level2);
        setstyleB2(level3);
        setstyleB2(retour);
        levelsContainer.setPrefWidth(200); 
        levelsContainer.setPrefHeight(50); 
        levelsContainer.setPadding(new Insets(10)); 
        levelsContainer.setSpacing(10); 
        level1.setFont(new javafx.scene.text.Font(22));
        level2.setFont(new javafx.scene.text.Font(22));
        level3.setFont(new javafx.scene.text.Font(22));
        retour.setFont(new javafx.scene.text.Font(22));
        levelsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
        levelsContainer.setAlignment(Pos.CENTER); 
        level1.setOnAction(event -> n1());
        level2.setOnAction(event -> n2());
        level3.setOnAction(event -> n3());
        retour.setOnAction(event -> Retour2());
    }

    public void n1(){
        game.getGameRoot().getChildren().clear();

        map=MazeConfig.mapLoader("map1.map");
        var he = new Stage();
        var root = new Pane();
        var gameScene = new Scene(root);
        var mazeConfig = MazeConfig.mapLoader("map1.map");
        var pacmanController = new PacmanController(mazeConfig);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mapLoader("map1.map"), pacmanController);
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,535);
        he.setScene(gameScene);
        he.show();
        he.requestFocus();
        game.animate(); 
        menuStage.hide();
    }
    public void n2(){
        game.getGameRoot().getChildren().clear();

        var he = new Stage();
        var root = new Pane();
        var gameScene = new Scene(root);
        var mazeConfig = MazeConfig.mapLoader("map2.map");
        var pacmanController = new PacmanController(mazeConfig);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mapLoader("map2.map"), pacmanController);
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,45);
        he.setScene(gameScene);
        he.show();
        he.requestFocus();
        game.animate(); 
        menuStage.hide();
    }
    public void n3(){
        game.getGameRoot().getChildren().clear();

        var he = new Stage();
        var root = new Pane();
        var gameScene = new Scene(root);
        var mazeConfig = MazeConfig.mapLoader("map3.map");
        var pacmanController = new PacmanController(mazeConfig);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mapLoader("map3.map"), pacmanController);
        game.getGameRoot().getChildren().clear();

        game = new GameView(Skin,maze, root, 30,825,12,menuStage,152);
        he.setScene(gameScene);
        he.show();
        he.requestFocus();
        game.animate(); 
        menuStage.hide();
    }

    public MazeConfig getMap(){
        return this.map;
    }

    public void uptadeScore(){
        try {
            File file = new File("score.txt");
            Scanner scanner = new Scanner(file);
            int score2 = scanner.nextInt();
            scanner.close();
            score.setText("Score : " + score2);
            score.setText("Votre nombre de points actuel est: "+ score2);
            score.setStyle("-fx-text-fill: white;");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    
}


    

