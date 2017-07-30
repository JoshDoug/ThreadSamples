package RunLater;

/*
 * Background task that updates the UI
 */

import javafx.application.Platform;
import javafx.scene.control.Label;

public class BackgroundTask extends Thread {
    View view;

    public BackgroundTask(View view) {
        this.view = view;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            int temp = 0;

            while (temp < 10000000) {
                temp++;
            }

            Label label = new Label("Wooh" + (temp + i));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    view.updateScene(label);
                }
            });
        }
    }

}
