package FileTransferCP;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {
    TransferBehaviour fileTransfer;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,5,5,5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Button transferFile = new Button("Transfer File");
        Button stopThread = new Button("Stop Transfer, Kill Thread");
        Button checkProgress = new Button("Check Progress");

        transferFile.setOnAction(event -> startTransfer());
        stopThread.setOnAction(event -> stopThread());
        checkProgress.setOnAction(event -> checkProgress());

        gridPane.add(transferFile, 0, 0);
        gridPane.add(stopThread, 0,1);
        gridPane.add(checkProgress,1,1);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing File Transfer with Consumer Producer Model");
        primaryStage.show();
    }

    public void startTransfer() {
        this.fileTransfer = new FileTransferManager();
    }

    public void stopThread() {
        fileTransfer.stop();
    }

    public void checkProgress() {
        fileTransfer.checkProgress();
    }
}
