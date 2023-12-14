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
import main.java.gui.ElementUnlocker;
import main.java.gui.FileIntegerOperations;

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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.text.Font;

public class Menu{
    private static Clip clip;
    private static long startTime = System.currentTimeMillis();
    private static boolean bloque=true;
    private Pane root;
    private GameView game;
    private Button play, quit, options,shopB,classement, button1, button2, button3, button4, button5;
    private VBox menuContainer;
    private VBox optionsContainer;
    private VBox classementContainer;
    private VBox levelsContainer; 
    private Stage primaryStage; 
    private Stage menuStage;
    private Scene gameScene;
    private MazeState maze;
    private TilePane shop;
    private TilePane classement2;
    private String Skin = "pacman.gif";
    private boolean fullscreen;
    private MazeConfig map;
    private MazeConfig config;
    private Label score, choix_skin;
    private ElementUnlocker elementUnlocker;
    private FileIntegerOperations fileIntegerOperations;
    private Label score10;
    private Label scoreNiv1;
    private Label scoreNiv2;
    private Label scoreNiv3;
    private Label scoreTotal;

    public Menu(Stage primaryStage, Pane root , Scene gameScene , MazeState maze , ElementUnlocker elementUnlocker, FileIntegerOperations fileIntegerOperations) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.gameScene = gameScene;
        this.maze = maze;
        this.fullscreen = false;
        this.elementUnlocker = elementUnlocker;
        this.fileIntegerOperations = fileIntegerOperations;
        shop = new TilePane();
        classement2= new TilePane();
        map=null;

        initializeButtons();
        CreateLevels();
        CreateOptions();
        CreateMenu();
        Createshop();
        CreateClassement();
        Scene scene = new Scene(shop, 300, 200);
        menuStage = new Stage();
        //primaryStage.setScene(gameScene);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(shop,optionsContainer,levelsContainer,classementContainer,menuContainer);

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
        classement = new Button("Classement");

        setstyleB2(play);
        setstyleB2(options);
        setstyleB2(quit);
        setstyleB2(shopB);
        setstyleB2(classement);



        play.setMaxWidth(160); // Permet de augmenter la largeur du bouton jusqu'a la limite de la vbox
        quit.setMaxWidth(160); 
        options.setMaxWidth(160); 
        shopB.setMaxWidth(160); 
        classement.setMaxWidth(160); 



        play.setMinHeight(50);
        quit.setMinHeight(50);
        options.setMinHeight(50);
        shopB.setMinHeight(50);
        classement.setMinHeight(50);



        play.setFont(new javafx.scene.text.Font(22));
        quit.setFont(new javafx.scene.text.Font(22));
        options.setFont(new javafx.scene.text.Font(22));
        shopB.setFont(new javafx.scene.text.Font(22));
        classement.setFont(new javafx.scene.text.Font(22));


        play.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        quit.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        options.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        shopB.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");
        classement.setStyle("-fx-background-color: #000080; -fx-text-fill: white;");



        play.setOnAction(event -> lancer_le_jeu()); // appelle lancer_le_jeu() si appuie sur play
        quit.setOnAction(event -> quitter_application()); // appelle quitter_application() si appuie sur quit
        options.setOnAction(event -> lance_options()); // appelle lance_options() si appuie sur options
        shopB.setOnAction(event -> lance_shop()); // appelle lance_shop() si appuie sur shop
        classement.setOnAction(event -> lance_classement()); // appelle lance_shop() si appuie sur shop


    }

        private void Createshop(){
            int n=180;
        shop.setPrefColumns(3);
        shop.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
        Button back = new Button("Retour");
        setstyleB(back);
        setstyleB2(back);
        back.setOnAction(event -> Retourshop()); // appelle Retourshop() si appuie sur retour
        Button button = new Button();
        button1 = new Button("Bouton 1");
        button2 = new Button("Bouton 2");
        button3 = new Button("Bouton 3");
        button4 = new Button("Bouton 4");
        button5 = new Button("Bouton 5");
        button1.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button2.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button3.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button4.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        button5.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");


        button.setPrefWidth(n); 
        button.setPrefHeight(n); 
        Image image = new Image("pacman.gif");
        ImageView imageV = new ImageView(image);
        imageV.setFitWidth(n); 
        imageV.setFitHeight(n); 
        button.setGraphic(imageV);
        button.setOnAction(event -> transfertskin("pacman.gif")); 
        button.setText("PacMan");
        button.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");


        button1.setPrefWidth(n); 
        button1.setPrefHeight(n); 
        Image image1 = new Image("bleu.gif");
        ImageView imageV1 = new ImageView(image1);
        imageV1.setFitWidth(n); 
        imageV1.setFitHeight(n); 
        button1.setGraphic(imageV1);
        button1.setOnAction(event -> transfertskin("bleu.gif")); 
        if(elementUnlocker.isElementUnlocked("bleu.gif")){
            button1.setText("bleu");
        } else {
            Image image1NB = new Image("question.png");
            ImageView imageV1NB = new ImageView(image1NB);
            button1.setGraphic(imageV1NB);
            button1.setText("bleu : 1000");
        }
        button1.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");
        
        button2.setPrefWidth(n); 
        button2.setPrefHeight(n); 
        Image image2 = new Image("blanc.gif");
        ImageView imageV2 = new ImageView(image2);
        imageV2.setFitWidth(n); 
        imageV2.setFitHeight(n); 
        button2.setGraphic(imageV2);
        button2.setOnAction(event -> transfertskin("blanc.gif")); 
        if(elementUnlocker.isElementUnlocked("blanc.gif")){
            button2.setText("blanc");
        } else {
            Image image2NB = new Image("question.png");
            ImageView imageV2NB = new ImageView(image2NB);
            button2.setGraphic(imageV2NB);
            button2.setText("blanc : 2000");
        }
        button2.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");

        button3.setPrefWidth(n); 
        button3.setPrefHeight(n); 
        Image image3 = new Image("rose.gif");
        ImageView imageV3 = new ImageView(image3);
        imageV3.setFitWidth(n); 
        imageV3.setFitHeight(n); 
        button3.setGraphic(imageV3);
        button3.setOnAction(event -> transfertskin("rose.gif"));
        if(elementUnlocker.isElementUnlocked("rose.gif")){
            button3.setText("rose");
        } else {
            Image image3NB = new Image("question.png");
            ImageView imageV3NB = new ImageView(image3NB);
            button3.setGraphic(imageV3NB);
            button3.setText("rose : 4000");
        } 
        button3.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");

        button4.setPrefWidth(n); 
        button4.setPrefHeight(n); 
        Image image4 = new Image("rouge.gif");
        ImageView imageV4 = new ImageView(image4);
        imageV4.setFitWidth(n); 
        imageV4.setFitHeight(n); 
        button4.setGraphic(imageV4);
        button4.setOnAction(event -> transfertskin("rouge.gif")); 
        if(elementUnlocker.isElementUnlocked("rouge.gif")){
            button4.setText("rouge");
        } else {
            Image image4NB = new Image("question.png");
            ImageView imageV4NB = new ImageView(image4NB);
            button4.setGraphic(imageV4NB);
            button4.setText("rouge : 6000");
        } 
        button4.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");

        button5.setPrefWidth(n); 
        button5.setPrefHeight(n); 
        Image image5 = new Image("violet.gif");
        ImageView imageV5 = new ImageView(image5);
        imageV5.setFitWidth(n); 
        imageV5.setFitHeight(n); 
        button5.setGraphic(imageV5);
        button5.setOnAction(event -> transfertskin("violet.gif")); 
        if(elementUnlocker.isElementUnlocked("violet.gif")){
            button5.setText("violet");
        } else {
            Image image5NB = new Image("question.png");
            ImageView imageV5NB = new ImageView(image5NB);
            button5.setGraphic(imageV5NB);
            button5.setText("violet : 10000");
        } 
        button5.setStyle("-fx-content-display: top;-fx-text-fill: white;-fx-background-color: #000000;");


        choix_skin = new Label();
        score10 = new Label();
        uptadeScore();
        createChoix_Skin();
        
        shop.getChildren().addAll(button,button1, button2, button3, button4, button5,back,score10,choix_skin);
        }

        public void updateScore(int score1){
            score10.setText("Score : " + score1);
                score10.setText("Votre nombre de points actuel est: "+ score1);
                score10.setStyle("-fx-text-fill: white;");
        }

        public void transfertskin(String skin){
            int score = 0;
            try{
            File file = new File("score.txt");
            Scanner scanner = new Scanner(file);
            int score2 = scanner.nextInt();
            score = score2;
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(skin.equals("pacman.gif")){
            this.Skin = skin;
            choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            return;
        }
            if(elementUnlocker.isElementUnlocked(skin)){
                this.Skin = skin;
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else if(skin.equals("bleu.gif") && score > 1000){
                this.Skin = skin;
                String filePath = "score.txt";
                fileIntegerOperations.subtractValueFromFile(filePath, 1000);
                elementUnlocker.unlockElement(skin);
                Image image1 = new Image("bleu.gif");
                ImageView imageV1 = new ImageView(image1);
                imageV1.setFitWidth(180); 
                imageV1.setFitHeight(180); 
                button1.setText("bleu");
                button1.setGraphic(imageV1);
                updateScore(score);
                uptadeScore();
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else if(skin.equals("blanc.gif") && score > 2000){
                this.Skin = skin;
                String filePath = "score.txt";
                fileIntegerOperations.subtractValueFromFile(filePath, 2000);
                elementUnlocker.unlockElement(skin);
                Image image2 = new Image("blanc.gif");
                ImageView imageV2 = new ImageView(image2);
                imageV2.setFitWidth(180); 
                imageV2.setFitHeight(180); 
                button2.setText("blanc");
                button2.setGraphic(imageV2);
                updateScore(score);
                uptadeScore();
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else if(skin.equals("rose.gif") && score > 4000){
                this.Skin = skin;
                String filePath = "score.txt";
                fileIntegerOperations.subtractValueFromFile(filePath, 4000);
                elementUnlocker.unlockElement(skin);
                Image image3 = new Image("rose.gif");
                ImageView imageV3 = new ImageView(image3);
                imageV3.setFitWidth(180); 
                imageV3.setFitHeight(180); 
                button3.setText("rose");
                button3.setGraphic(imageV3);
                updateScore(score);
                uptadeScore();
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else if(skin.equals("rouge.gif") && score > 6000){
                this.Skin = skin;
                String filePath = "score.txt";
                fileIntegerOperations.subtractValueFromFile(filePath, 6000);
                elementUnlocker.unlockElement(skin);
                Image image4 = new Image("rouge.gif");
                ImageView imageV4 = new ImageView(image4);
                imageV4.setFitWidth(180); 
                imageV4.setFitHeight(180); 
                button4.setText("rouge");
                button4.setGraphic(imageV4);
                updateScore(score);
                uptadeScore();
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else if(skin.equals("violet.gif") && score > 10000){
                this.Skin = skin;
                String filePath = "score.txt";
                fileIntegerOperations.subtractValueFromFile(filePath, 10000);
                elementUnlocker.unlockElement(skin);
                Image image5 = new Image("violet.gif");
                ImageView imageV5 = new ImageView(image5);
                imageV5.setFitWidth(180); 
                imageV5.setFitHeight(180); 
                button5.setText("violet");
                button5.setGraphic(imageV5);
                updateScore(score);
                uptadeScore();
                choix_skin.setText("Votre skin actuel est : "+retirer_extension(skin));

            } else {
                return;
            }
        }

    private void CreateMenu(){
        ImageView logoimageView = new ImageView(new Image("logo.png")); // prend l'image pacman.png qui se trouve dans le répertoire menu.java
        logoimageView.setFitWidth(1000); // Largeur souhaitée de l'image
        logoimageView.setFitHeight(300); // Hauteur souhaitée de l'image
        menuContainer = new VBox();
        menuContainer.getChildren().add(logoimageView); // Ajoute l'image dans la Vbox
        menuContainer.getChildren().add(play); // Ajoute le bouton dans la Vbox
        menuContainer.getChildren().add(shopB); // Ajoute le bouton dans la Vbox
        menuContainer.getChildren().add(classement);
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
        CheckBox soundEffectsCheckbox = new CheckBox("Enlever les effets sonores");
        CheckBox musicCheckbox = new CheckBox("Enlever la musique de fond");
        CheckBox fullscreen = new CheckBox("Plein écran");
        setstyleC(soundEffectsCheckbox);
        setstyleC(musicCheckbox);
        setstyleC(fullscreen);
        Button testButton = new Button("Tester le son");
        Button backButton = new Button("Retour");
        setstyleB(testButton);
        setstyleB(backButton);
        setstyleB2(testButton);
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

        musicCheckbox.setOnAction(event -> {
            if (musicCheckbox.isSelected()) {
                enlevermusique(); 
            } else {
                remettremusique();
            }
        });

        soundEffectsCheckbox.setOnAction(event -> {
            if (soundEffectsCheckbox.isSelected()) {
                enleverson(); 
            } else {
                remettreson();
            }
        });

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

        optionsContainer.getChildren().addAll(titleLabel,soundEffectsCheckbox,musicCheckbox,testButton,fullscreen,p,textField,button,pseudo2,backButton);
        optionsContainer.setPrefWidth(200); // modifie la largeur du menu
        optionsContainer.setPrefHeight(50); // modifie la hauteur du menu
        optionsContainer.setPadding(new Insets(10)); // modifie la distance avec la bordure du menu
        optionsContainer.setSpacing(10); // modifie la distance entre les boutons
        optionsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
    }
     
    public void CreateClassement(){
        classementContainer = new VBox();
        classementContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");

        scoreNiv1 = new Label ();
        scoreNiv1.setStyle("-fx-text-fill: white;");

        scoreNiv2 = new Label ();
        scoreNiv2.setStyle("-fx-text-fill: white;");

        scoreNiv3 = new Label ();
        scoreNiv3.setStyle("-fx-text-fill: white;");

        scoreTotal = new Label ();
        scoreTotal.setStyle("-fx-text-fill: white;");

        Button backButton = new Button("Retour");
        backButton.setOnAction(event -> Retour3());
        classementContainer.getChildren().addAll(scoreNiv1,scoreNiv2,scoreNiv3,scoreTotal,backButton);
    }

    public void uptadeClassement(){
        String lien = "classement.txt";
        int total =0;
        try {
            File file = new File(lien);
            Scanner scanner = new Scanner(file);
            int currentLine = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                total+=Integer.valueOf(line);
                if (currentLine == 1) {
                    scoreNiv1.setText("Votre meilleur score au niveau 1 est: " + line);
                }
                else if(currentLine == 2){
                    scoreNiv2.setText("Votre meilleur score au niveau 2 est: " + line);
                }
                else if(currentLine == 3){
                    scoreNiv3.setText("Votre meilleur score au niveau 3 est: " + line);
                }
                currentLine++;
            }
            scoreTotal.setText("Votre score total est: "+total);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fonc(boolean b){
        if(b){
            game.getGameRoot().getChildren().clear();
            game = new GameView(Skin,maze, root, 60.0,1750,22,menuStage,50,50,100);
            this.fullscreen = true;
        } else {
            game.getGameRoot().getChildren().clear();
            game = new GameView(Skin,maze, root, 30.0,825,12,menuStage,50,50,100);
            this.fullscreen = false;
        }
    }
    public void lance_menu(){
        musiquereset();
        menuStage.show();
        musiquemenu();
        
    }
    public void lance_options(){
        musiquereset();
        optionsContainer.toFront();
    }

    public void lance_shop(){
        musiquereset();
        uptadeScore();
        shop.toFront();
           
    }

    public void lance_classement(){
        musiquereset();
        uptadeClassement();
        classementContainer.toFront();
    }

    public void Retourshop() {
        musiquereset();
            shop.toBack();
    }

    public void Retour() {
        musiquereset();
        optionsContainer.toBack();
    }

    public void Retour2() {//pour play
        musiquereset();
        levelsContainer.toBack();
    }
    public void Retour3() {//pour play
        musiquereset();
        classementContainer.toBack();
    }

    private void lancer_le_jeu() {
        musiquereset();
        maze.mettreToutesValeursATrue();
        maze.resetCritters();
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,0,50,100);


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
        titleLabel.setFont(new Font(50)); 
        titleLabel.setPrefHeight(50);   


        levelsContainer = new VBox();
        Button level1 = new Button("Niveau 1");
        Button level2 = new Button("Niveau 2");
        Button level3 = new Button("Niveau 3");
        Button retour = new Button("Retour");
        levelsContainer.getChildren().addAll(titleLabel,level1,level2,level3,retour);
        Image image = new Image("lv2.png");
        Image image2 = new Image("lv3.png");
        Image image3 = new Image("lv1.png");

        // Créer une vue d'image pour chaque bouton
        ImageView imageView1 = new ImageView(image);
        imageView1.setFitWidth(200);
        imageView1.setFitHeight(200);

        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(200);
        imageView2.setFitHeight(200);

        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(200);
        imageView3.setFitHeight(200);

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
        level1.setGraphic(imageView1);
        level2.setGraphic(imageView2);
        level3.setGraphic(imageView3);
        retour.setFont(new javafx.scene.text.Font(22));
        levelsContainer.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-spacing: 10;");
        levelsContainer.setAlignment(Pos.CENTER); 
        level1.setOnAction(event -> n2());
        level2.setOnAction(event -> n3());
        level3.setOnAction(event -> n1());
        retour.setOnAction(event -> Retour2());
    }

    public void n1(){
        musiquestop();
        MazeState.niv=1;
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
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,550,75,130);
        he.setScene(gameScene);
        he.show();
        he.requestFocus();
        game.animate(); 
        menuStage.hide();
    }
    public void n2(){
        musiquestop();
        MazeState.niv=2;
        game.getGameRoot().getChildren().clear();

        var he = new Stage();
        var root = new Pane();
        var gameScene = new Scene(root);
        var mazeConfig = MazeConfig.mapLoader("map2.map");
        var pacmanController = new PacmanController(mazeConfig);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mapLoader("map2.map"), pacmanController);
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,47,15,33);
        he.setScene(gameScene);
        he.show();
        he.requestFocus();
        game.animate(); 
        menuStage.hide();
    }
    public void n3(){
        musiquestop();
        MazeState.niv=3;
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
        game = new GameView(Skin,maze, root, 30,825,12,menuStage,152,35,75);
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
            updateScore(score2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createChoix_Skin(){
        choix_skin.setText("Votre skin actuel est: "+ retirer_extension(Skin));
        choix_skin.setStyle("-fx-text-fill: white;");
    }

    public String retirer_extension(String string){
        int i = 0;
        String finalString = "";
        while(string.charAt(i) != '.'){
            finalString += string.charAt(i);
            i++;
        }
        return finalString;
    }

    public static void musiquemenu(){
        if(bloque){
            try{
                File audioFile = new File("src/main/resources/menu.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);            
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();           
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void musiquestop(){
        try{
            clip.stop();
            clip.close();           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void musiquereset(){
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        if(elapsedTime>68000){
            musiquestop();
            musiquemenu();
            startTime=endTime;
        }    
    }

    public static void enlevermusique(){
        musiquestop();
        bloque=false;
    }

    public static void remettremusique(){
        bloque=true;
        musiquemenu();
    }

    public static void enleverson(){
        MazeState.son=false;
    }

    public static void remettreson(){
        MazeState.son=true;
    }
}


    

