package app.sqllite.app.Workflow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Stage;



public class MainController {
    public MainController(Stage stage) {
        
    }
    @FXML private VBox pan;
    
    public void initialize() {
//    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/sqllite/app/Workflow/MainPane.fxml"));
        Parent parent = null;        
        try {
            parent = loader.load();
        } catch (Exception e) {
            System.out.println("로그");
        	System.out.println(e.getMessage());
        }
//         load 화면정보를 workTop에 append 한다.
        if(pan.getChildren() != null){
//         이전에 자식을 가지고 있는경우 제거한다.
            pan.getChildren().clear();
        }
//        workTop 에 자식을 추가한다.
        pan.getChildren().addAll(parent);
        
    }

}
