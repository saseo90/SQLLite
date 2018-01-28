package lite.sql.frontdoor.checkbox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckboxLunch extends Application{
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/lite/sql/frontdoor/checkbox/checkbox.fxml")
				);
		
		Parent root;
		Scene scene;
		try {
			root = loader.load();
			scene = new Scene(root);
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stage.setResizable(false);		
		stage.show();
		
	}

}
