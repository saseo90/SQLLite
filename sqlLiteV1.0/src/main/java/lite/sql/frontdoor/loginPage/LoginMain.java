package lite.sql.frontdoor.loginPage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				//경로에 주의 한다
				//경로의 시작은 project명 밑의 src가 root가 한다. 처음에도 /를 넣어야 한다.
					getClass().getResource("/lite/sql/frontdoor/loginPage/login.fxml")
				);
		//javafx.scene.Parent
		Parent root = loader.load();
		//javafx.scene.Scene
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
