package lite.sql.utility.help;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpboxLunch extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"/lite/sql/utility/help/helpbox.fxml"));

		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

}
