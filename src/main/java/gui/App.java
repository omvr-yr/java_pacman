package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
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
        Menu startMenu = new Menu(primaryStage, root, gameScene, maze);
    }
}
