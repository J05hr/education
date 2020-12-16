
//Joshua Rozenberg - jr922
//Kenneth Scholwinski - kjs270

package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.SongLibController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/List.fxml"));
		AnchorPane root = loader.load();

		// get our list controller
		SongLibController listController = loader.getController();
		listController.start(primaryStage);

		// create the main scene
		Scene scene = new Scene(root, 500, 700);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sad Tunes For Emo Teens");
		primaryStage.show();
	}

	// use main only to launch the app
	public static void main(String[] args) {
		launch(args);
	}
}