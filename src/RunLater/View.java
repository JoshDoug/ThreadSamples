package RunLater;

/*
 * UI that runs a task in a background thread
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {
    GridPane gridPane;
    Stage primaryStage;
    int currentRow = 1;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,5,5,5));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Button updateScene = new Button("Update Scene");
        gridPane.add(updateScene, 0, 1);

        updateScene.setOnAction(event -> {
            BackgroundTask bg = new BackgroundTask(this);
            bg.start();
        });

        Scene scene = new Scene(gridPane);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Test RunLater");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateScene(Label label) {
        gridPane.addRow(currentRow, label);
        currentRow++;
        this.primaryStage.sizeToScene();
    }

}
