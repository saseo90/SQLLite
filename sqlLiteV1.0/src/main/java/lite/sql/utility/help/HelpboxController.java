package lite.sql.utility.help;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class  HelpboxController implements Initializable{
	
	
	@FXML
	private Button btnClose;
	   @FXML
	private TextArea textHelp;

	public void initialize(URL location, ResourceBundle resources) {

		btnClose.setOnAction(event -> {
			Close(event);
		
		});
		textHelp.setEditable(false);
	}

	private void Close(ActionEvent event) {
		if (true) {
			Parent home_page_parent;
			Scene home_page_scene;
			try {
				home_page_parent = FXMLLoader.load(getClass().getResource(
						"/lite/sql/frontdoor/main/Main.fxml"));
				home_page_scene = new Scene(home_page_parent);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Stage app_stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
			app_stage.hide(); // optional

		}

	}
}
