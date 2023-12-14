package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import main.java.gui.ElementUnlocker;
import main.java.gui.FileIntegerOperations;
import config.MazeConfig;
import model.MazeState;
import model.Ghost;
import gui.PacmanController;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        var root = new Pane();
        var gameScene = new Scene(root);
        var mazeConfig = MazeConfig.mapLoader("map1.map");
        var pacmanController = new PacmanController(mazeConfig);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.mapLoader("map1.map"), pacmanController);
        primaryStage.setScene(gameScene);
        ElementUnlocker Element = new ElementUnlocker();
        FileIntegerOperations operations = new FileIntegerOperations();
        Menu startMenu = new Menu(primaryStage, root, gameScene, maze,Element,operations);
    }
}
